package com.cdvcloud.rochecloud.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cdvcloud.pass.upload.domin.UploadOther;
import com.cdvcloud.pass.upload.domin.UploadRequest;
import com.cdvcloud.pass.upload.domin.UploadResponse;
import com.cdvcloud.pass.upload.service.Upload;
import com.cdvcloud.rochecloud.common.Constants;
import com.cdvcloud.rochecloud.exception.MyDefineException;

/**
 * File工具类
 * 
 * @author TYW
 * 
 */
public class FileUtil {

	private static final Logger logger = Logger.getLogger(FileUtil.class);
	private static final BlockingQueue<File> queue = new ArrayBlockingQueue<File>(100);

	/**
	 * 获取日期格式的路径 年/月/日/
	 * 
	 * @return
	 */
	public static String datePath() {
		return File.separator + DateUtil.getYear() + File.separator + DateUtil.getMonth() + File.separator + DateUtil.getDay() + File.separator;
	}

	public static String linuxDatePath() {
		return "/" + DateUtil.getYear() + "/" + DateUtil.getMonth() + "/" + DateUtil.getDay() + "/";
	}

	/**
	 * 检查路径，如果没有则创建
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createPath(String filePath) {
		boolean isCreate = false;
		File file = new File(filePath);
		if (!file.exists()) {
			isCreate = file.mkdirs();
		}
		return isCreate;
	}

	/**
	 * 获取指定目录的第一个文件
	 * 
	 * @param filepath
	 * @return
	 */
	public static File getFirstFile(String filepath) {
		File file = new File(filepath);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (null != files && files.length > 0) {
				return files[0];
			}
		}
		return null;
	}

	/**
	 * 获取指定目录的文件集合
	 * 
	 * @param filepath
	 * @return
	 */
	public static List<File> getFiles(String filepath) {
		List<File> list = new ArrayList<File>();
		File file = new File(filepath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.renameTo(file2)) {
					list.add(file2);
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * 获取指定目录的文件集合
	 * 
	 * @param filepath
	 * @return
	 * @throws MyDefineException 
	 * @throws Exception
	 */
	public static BlockingQueue<File> getQueue(String filepath) throws MyDefineException {
		BlockingQueue<File> q = new ArrayBlockingQueue<File>(1000);
		try {
			File file = new File(filepath);
			if (file.exists()) {
				File[] files = file.listFiles();
				for (File file2 : files) {
					if (file2.renameTo(file2)) {
						q.put(file2);
					}
				}
				return q;
			}
			return null;
		} catch (Exception e) {
			logger.error("获取文件队列出错：" + e.getMessage());
			throw new MyDefineException(StringUtil.format("获取文件队列出错，文件路径：{}",filepath));
		}
	}

	/**
	 * 扫描指定目录的文件
	 * 
	 * @param filepath
	 *            目录
	 * @param rname
	 *            添加的后缀名
	 * @param fileNameFilter
	 *            名称过滤
	 * @return
	 * @throws Exception
	 */
	public static BlockingQueue<File> doFileList(String filepath, String rname, FilenameFilter fileNameFilter) {
		try {
			File file = new File(filepath);
			if (!file.exists()) {
				logger.error("路径不存在：" + filepath);
				return null;
			}
			File[] files = file.listFiles(fileNameFilter);
			if (null != rname) {
				for (File f : files) {
					if (f.renameTo(f)) {
						f = renameFile(f, rname);
						queue.put(f);
					}
				}
			} else {
				for (File f : files) {
					if (f.renameTo(f)) {
						queue.put(f);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queue;
	}

	/**
	 * 扫描指定目录的文件
	 * 
	 * @param filepath
	 *            目录
	 * @param rname
	 *            添加的后缀名
	 * @param fileNameFilter
	 *            名称过滤
	 * @return
	 * @throws InterruptedException
	 */
	public static BlockingQueue<File> doRecordFileList(String filepath, String rname, FilenameFilter fileNameFilter) throws InterruptedException {
		File file = new File(filepath);
		if (!file.exists()) {
			logger.error("路径不存在：" + filepath);
			return null;
		}
		File[] files = file.listFiles(fileNameFilter);
		if (null != rname) {
			for (File f : files) {
				if (f.renameTo(f)) {
					f = renameFile(f, rname);
					queue.put(f);
				}
			}
		} else {
			for (File f : files) {
				if (f.renameTo(f)) {
					queue.put(f);
				}
			}
		}
		return queue;
	}

	/**
	 * 重命名文件
	 * 
	 * @param file
	 *            E:\test.avi
	 * @param replaceName
	 *            _vms
	 * @return E:\test_vms.avi
	 */
	public static File renameFile(File file, String replaceName) {
		String newName = file.getName();
		newName = newName.replace(".", replaceName + ".");
		String parentPath = file.getParent();
		String newFileName = parentPath + File.separator + newName;
		File newFile = new File(newFileName);
		file.renameTo(newFile);// 重命名
		return newFile;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path) {
		File f = new File(path);
		if (f.exists()) {
			return f.delete();
		}
		return false;
	}

	/**
	 * 使用文件通道的方式复制文件
	 * 
	 * @param srcFilename
	 * @param destFilename
	 */
	public static boolean fileChannelCopy(String srcFilename, String destFilename) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			File srcFile = new File(srcFilename);
			if (srcFile.exists()) {
				File destFile = new File(destFilename);
				if (!destFile.getParentFile().exists()) {
					destFile.getParentFile().mkdirs();
				}
				if (!destFile.exists()) {
					destFile.createNewFile();
				}
				fi = new FileInputStream(srcFile);
				fo = new FileOutputStream(destFile);
				in = fi.getChannel();// 得到对应的文件通道
				out = fo.getChannel();// 得到对应的文件通道
				ByteBuffer dsts = ByteBuffer.allocate(1024000);
				try {
					while ((in.read(dsts)) != -1) {
						dsts.flip();// 做好被写的准备
						out.write(dsts);
						dsts.clear();// 做好被读的准备
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// in.transferTo(0, in.size(), out);//
				// 连接两个通道，并且从in通道读取，然后写入out通道
				return true;
			}
			return false;
		} catch (IOException e) {
			logger.error("拷贝文件错误：" + e.getMessage());
			return false;
		} finally {
				if (null != fi) {
					try {
						fi.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != fo) {
					try {
						fo.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
		}
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getSuffix(String filename) {
		String suffix = null;
		if (!filename.isEmpty()) {
			suffix = filename.substring(filename.lastIndexOf(".") + 1);
		}
		return suffix;
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileName(String filename) {
		if (!filename.isEmpty() && filename.indexOf(".") !=-1) {
			filename = filename.substring(0, filename.lastIndexOf("."));
		}
		return filename;
	}

	/**
	 * 将源文件的数据写入到目标文件中， 不会检查源文件是否存在， 若目标文件存在则直接写入， 否则创建目标文件后再进行写入。
	 * 
	 * @param srcPath
	 * @param desPath
	 */
	public static void copyFile(String srcPath, String desPath) {
		File fileSrc = new File(srcPath);
		File fileDest = new File(desPath);
		if (!fileSrc.isFile()) {
			logger.error("图片不存在" + srcPath);
		}
		if (!fileDest.exists()) {
			String strPath = fileDest.getParent();
			createPath(strPath);
		}
		FileInputStream in =null;
		FileOutputStream out=null;
		try {
			  in = new FileInputStream(srcPath);
			  out = new FileOutputStream(desPath);
			byte[] bt = new byte[Constants.BYTE_LENGTH];
			int count;
			while ((count = in.read(bt)) > 0) {
				out.write(bt, 0, count);
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
				if(null !=in){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null !=out){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给linux目录赋权限
	 * 
	 * @param path
	 *            /ss/dd/ff
	 * @return
	 */
	public static boolean linuxToken(String path) {
		return true;
	}

	/**
	 * 把创建文件夹的方法提取公共处理
	 * 
	 * @param file
	 * @author zhangcz
	 * @Date 2014-9-4 上午9:50:35
	 */

	public static void createFile(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
			linuxToken(filePath);
		}
	}

	/**
	 * 根据文件路径获取文件的字节大小
	 * 
	 * @param strFilepath
	 * @return
	 */
	public static Long getFileSize(String strFilepath) {
		File file = new File(strFilepath);
		if (file.isFile()) {
			return file.length();
		}
		return 0L;
	}

	/**
	 * 下载网络文件
	 * 
	 * @param strHttpUrl
	 *            网络文件地址
	 * @param strLocalUrl
	 *            下载后的存储地址
	 * @return
	 */
	public static boolean httpDownload(String strHttpUrl, String strLocalUrl) {
		int byteread = 0;
		URL url = null;
		try {
			url = new URL(strHttpUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return false;
		}
		InputStream inStream = null;
		FileOutputStream fs= null;
		try {
			URLConnection conn = url.openConnection();
			inStream = conn.getInputStream();
			fs = new FileOutputStream(strLocalUrl);
			byte[] buffer = new byte[4096];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(null !=inStream){
					inStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(null !=fs){
					fs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: subFileUrlName
	 * @Description: TODO(截取文件名-带后缀)
	 * @param @param strFilePath
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2016年5月27日 上午9:57:12
	 */
	public static String subFileUrlName(String strFilePath) {
		String srcFileName = "";
		try {
			srcFileName = strFilePath.substring(strFilePath.lastIndexOf("/") + 1);
		} catch (Exception e) {
			logger.error("截取文件名异常！异常信息：" + e.getMessage());
		}

		return srcFileName;
	}

	/**
	 * @Title: subFileUrlNameNoSurf
	 * @Description: TODO(截取文件名-不带后缀)
	 * @param @param strFilePath
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2016年5月27日 上午9:56:54
	 */
	public static String subFileUrlNameNoSurf(String strFilePath) {
		String srcFileName = "";
		try {
			srcFileName = strFilePath.substring(strFilePath.lastIndexOf("/") + 1, strFilePath.lastIndexOf("."));
		} catch (Exception e) {
			logger.error("截取文件名异常！异常信息：" + e.getMessage());
		}

		return srcFileName;
	}

	/**
	 * @Title: uploadOssBysdk
	 * @Description: TODO(调用sdk上传文件至OSS)
	 * @param @param strFileUrl
	 * @param @return
	 * @throws
	 * @author lyh
	 * @date 2016年5月27日 下午4:34:02
	 */
	public static String uploadOssBysdk(String strFileUrl) {
		strFileUrl = strFileUrl.replace("\\", "/");
		String strOssImgUrl = "";// 文件访问路径
		String strUserId = "zizhiuserid";
		String strUploadOSSUrl = Configuration.getConfigValue("UPLOADOSSURL");// oss请求地址
		String strOssDomain = Configuration.getConfigValue("STROSSDOMAIN");// 文件访问域名
		String strAppCode = Configuration.getConfigValue("APPCODE");// 应用标识
		String strCompanyId = Configuration.getConfigValue("COMPANYID");// 企业标识
		UploadRequest uploadRequest = new UploadRequest();
		uploadRequest.setAppCode(strAppCode);
		uploadRequest.setCompanyId(strCompanyId);
		uploadRequest.setUserId(strUserId);
		File file = new File(strFileUrl);
		UploadOther uploadOther = new UploadOther();
		uploadOther.setUrl(strUploadOSSUrl);
		uploadOther.setUuidName(FileUtil.subFileUrlNameNoSurf(strFileUrl));
		strOssImgUrl = "http://" + strOssDomain + "/" + strCompanyId + "/" + strAppCode + "/" + strUserId + "/" + FileUtil.subFileUrlName(strFileUrl);
		UploadResponse uploadResponse = Upload.getInstance().upload(file, uploadRequest, uploadOther);
		if (null == uploadResponse || 0 == uploadResponse.getSize()) {
			logger.error("上传oss异常！本地地址：" + strFileUrl + " oss地址：" + strOssImgUrl);
		}
		return strOssImgUrl;
	}

	/**
	 * 压缩文件并下载
	 * 
	 * @param request
	 * @param response
	 * @param mapParams
	 *            文件集合，如：{测试_PC_SHD.mp4=http://123.12.1.3/1234/qwert1234.mp4}
	 * @param strZipName
	 *            压缩包名称，如：测试
	 */
	public static void download4Zip(HttpServletRequest request, HttpServletResponse response, Map<String, String> mapParams, String strZipName) {
		if (null != mapParams) {
			ZipOutputStream zos=null;
			try {
				response.setContentType("APPLICATION/OCTET-STREAM");
				strZipName += ".zip";
				strZipName = (strZipName.trim()).replace(" ", "");
				strZipName = new String(strZipName.getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + strZipName + "\"");
				zos = new ZipOutputStream(response.getOutputStream());
				URL url = null;
				InputStream is = null;
				byte[] b = new byte[Constants.BYTE_LENGTH];
				Set<String> setKeys = mapParams.keySet();
				for (String strKey : setKeys) {
					String strUrls = mapParams.get(strKey);
					zos.putNextEntry(new ZipEntry(strKey));
					url = new URL(strUrls);
					URLConnection urlconn = url.openConnection();
					is = urlconn.getInputStream();
					int intLength = 0;
					while ((intLength = is.read(b)) != -1) {
						zos.write(b, 0, intLength);
					}
					is.close();
				}
				zos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("压缩文件下载异常：" + e.getMessage());
			}finally{
				try {
					if(null !=zos){
						zos.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void download(HttpServletRequest request, HttpServletResponse response, String strUrl, String strTitle) {
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			os = response.getOutputStream();
			bos = new BufferedOutputStream(os);
			URL url = new URL(strUrl);
			URLConnection urlconn = url.openConnection();
			InputStream is = urlconn.getInputStream();
			response.reset();
			strTitle = new String(strTitle.getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + strTitle + "\"");
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentLength(urlconn.getContentLength());
			byte[] b = new byte[Constants.BYTE_LENGTH];
			int intLength = 0;
			while ((intLength = is.read(b)) != -1) {
				bos.write(b, 0, intLength);
				bos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("下载文件时异常：" + e.getMessage());
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
