package com.cdvcloud.rochecloud.mongodao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public class MongoJdbc {
	/** 客户端连接池 */
	private static MongoClient mongoClient;
	/** 线程安全的数据库链接 */
	private static MongoDatabase mongoDatabase = null;
	/** 线程安全的数据库链接 */
	private static DB db = null;
	/** 服务器信息 */
	private String repset;
	/** 数据库信息 */
	private String database;
	/** 用户名 */
	private String user;
	/** 密码 */
	private String password;
	/** 单个host允许链接的最大链接数 */
	private String connectionsPerHost;
	/** 线程队列数 */
	private String threadsAllowedToBlockForConnectionMultiplier;

	public void setRepset(String repset) {
		this.repset = repset;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConnectionsPerHost(String connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(String threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	private void init() {
		List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
		if (this.repset == null || "".equals(this.repset)) {
			throw new MongodbException("未配置ip地址和端口号！");
		}
		if (this.connectionsPerHost == null || "".equals(this.connectionsPerHost)) {
			throw new MongodbException("未配置单个host允许链接的最大链接数！");
		}
		if (this.threadsAllowedToBlockForConnectionMultiplier == null || "".equals(this.threadsAllowedToBlockForConnectionMultiplier)) {
			throw new MongodbException("未配置线程队列数！");
		}
		if (this.user == null || "".equals(this.user)) {
			throw new MongodbException("未配置用户名！");
		}
		if (this.password == null || "".equals(this.password)) {
			throw new MongodbException("未配置密码！");
		}
		if (this.database == null || "".equals(this.database)) {
			throw new MongodbException("未配置库名！");
		}
		String[] hostPorts = this.repset.split(",");
		for (int i = 0; i < hostPorts.length; i++) {
			String[] hostPortArr = hostPorts[i].split(":");
			if (hostPortArr.length != 2) {
				throw new MongodbException("mongodb主机配置异常");
			}
			String host = hostPortArr[0];
			String port = hostPortArr[1];
			ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
			serverAddressList.add(serverAddress);
		}
		MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder().connectionsPerHost(Integer.parseInt(this.connectionsPerHost))
				.threadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(this.threadsAllowedToBlockForConnectionMultiplier)).build();
		MongoCredential credential = MongoCredential.createCredential(this.user, this.database, this.password.toCharArray());
		mongoClient = new MongoClient(serverAddressList, Arrays.asList(credential), mongoClientOptions);
		mongoDatabase = mongoClient.getDatabase(database);
	}

	private void initDB() {
		List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
		if (this.repset == null || "".equals(this.repset)) {
			throw new MongodbException("未配置ip地址和端口号！");
		}
		if (this.connectionsPerHost == null || "".equals(this.connectionsPerHost)) {
			throw new MongodbException("未配置单个host允许链接的最大链接数！");
		}
		if (this.threadsAllowedToBlockForConnectionMultiplier == null || "".equals(this.threadsAllowedToBlockForConnectionMultiplier)) {
			throw new MongodbException("未配置线程队列数！");
		}
		if (this.user == null || "".equals(this.user)) {
			throw new MongodbException("未配置用户名！");
		}
		if (this.password == null || "".equals(this.password)) {
			throw new MongodbException("未配置密码！");
		}
		if (this.database == null || "".equals(this.database)) {
			throw new MongodbException("未配置库名！");
		}
		String[] hostPorts = this.repset.split(",");
		for (int i = 0; i < hostPorts.length; i++) {
			String[] hostPortArr = hostPorts[i].split(":");
			if (hostPortArr.length != 2) {
				throw new MongodbException("mongodb主机配置异常");
			}
			String host = hostPortArr[0];
			String port = hostPortArr[1];
			ServerAddress serverAddress = new ServerAddress(host, Integer.parseInt(port));
			serverAddressList.add(serverAddress);
		}
		MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder().connectionsPerHost(Integer.parseInt(this.connectionsPerHost))
				.threadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(this.threadsAllowedToBlockForConnectionMultiplier)).build();
		MongoCredential credential = MongoCredential.createCredential(this.user, this.database, this.password.toCharArray());
		mongoClient = new MongoClient(serverAddressList, Arrays.asList(credential), mongoClientOptions);
		db = mongoClient.getDB(database);
	}

	public MongoDatabase getMongodb() {
		if (null == mongoDatabase) {
			init();
		}
		return mongoDatabase;
	}

	public DB getDB() {
		if (null == db) {
			initDB();
		}
		return db;
	}

	public DBCollection getCollection(String collection) throws Exception {
		return getDB().getCollection(collection);
	}

	/**
	 * 插入(参数map)
	 * 
	 * @param collection
	 *            集合名称
	 * @param map
	 *            插入参数(map)
	 * @return 成功返回0 失败返回 >0
	 */
	public String insert(String collection, Map<String, Object> map) throws Exception {
		DBObject dbObject = this.mapToObj(map);
		getCollection(collection).insert(dbObject, WriteConcern.SAFE);
		String id = dbObject.get("_id").toString();
		return id;
	}

	/**
	 * 插入 (参数json)
	 * 
	 * @param collection
	 *            集合名称
	 * @param jsonStr
	 *            插入参数 eg:{'name':'tt',"age":"12"} (json)
	 * @return 成功返回0 失败返回 >0
	 * @throws Exception
	 */
	public String insert(String collection, String jsonStr) throws Exception {
		DBObject dbObject = this.jsonToObj(jsonStr);
		getCollection(collection).insert(dbObject, WriteConcern.SAFE);
		String id = dbObject.get("_id").toString();
		return id;
	}

	/**
	 * 批量插入
	 * 
	 * @param collection
	 *            集合名称
	 * @param list
	 *            插入参数列表
	 * @return 成功返回0 失败返回>0
	 */
	public int insertBatch(String collection, List<Map<String, Object>> list) throws Exception {
		if (list == null || list.isEmpty()) {
			return 0;
		}
		List<DBObject> listDB = new ArrayList<DBObject>();
		for (int i = 0; i < list.size(); i++) {
			DBObject dbObject = this.mapToObj(list.get(i));
			listDB.add(dbObject);
		}
		WriteResult ret = getCollection(collection).insert(listDB, WriteConcern.SAFE);
		return ret.getN();
	}

	/**
	 * 删除集合中所有数据
	 * 
	 * @param collection
	 * @param map
	 * @throws Exception
	 */
	public int deleteAll(String collection) throws Exception {
		DBObject obj = new BasicDBObject();
		WriteResult ret = getCollection(collection).remove(obj, WriteConcern.SAFE);
		return ret.getN();
	}

	/**
	 * 删除操作(参数map)
	 * 
	 * @param collection
	 *            集合名称
	 * @param map
	 *            删除条件
	 * @return 返回影响行数
	 * 
	 */
	public int delete(String collection, Map<String, Object> map) throws Exception {
		DBObject obj = this.mapToObj(map);
		if (!map.isEmpty()) {
			WriteResult ret = getCollection(collection).remove(obj, WriteConcern.SAFE);
			return ret.getN();
		} else {
			return 0;
		}
	}

	/**
	 * 删除操作 (参数json)
	 * 
	 * @param collection
	 *            集合名称
	 * @param jsonStr
	 *            删除条件 eg:{'name':'tt',"age":"12"} (json)
	 * @return 返回影响行数
	 * 
	 */
	public int delete(String collection, String jsonStr) throws Exception {
		DBObject obj = this.jsonToObj(jsonStr);
		if (!jsonStr.equals("") && jsonStr != null) {
			WriteResult ret = getCollection(collection).remove(obj, WriteConcern.SAFE);
			return ret.getN();
		} else {
			return 0;
		}
	}

	/**
	 * 批量删除操作
	 * 
	 * @param collection
	 *            集合名称
	 * @param list
	 *            删除参数列表
	 */
	public void deleteBatch(String collection, List<Map<String, Object>> list) throws Exception {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			getCollection(collection).remove(this.mapToObj(list.get(i)));
		}
	}

	/**
	 * 查询满足条件的条数(参数map)
	 * 
	 * @param collection
	 *            集合名称
	 * @param map
	 *            查询参数集合
	 */
	public long getCount(String collection, Map<String, Object> map) throws Exception {
		return getCollection(collection).getCount(this.mapToObj(map));
	}

	/**
	 * 查询满足条件的条数(参数json)
	 * 
	 * @param collection
	 *            集合名称
	 * @param jsonStr
	 *            查询参数集合 eg:{'name':'tt',"age":"12"} (json)
	 */
	public long getCount(String collection, String jsonStr) throws Exception {
		long count = getCollection(collection).getCount(this.jsonToObj(jsonStr));
		return count;
	}

	/**
	 * 更新
	 * 
	 * @param collection
	 *            集合名称
	 * @param setFields
	 *            修改参数
	 * @param whereFields
	 *            修改条件
	 * @return 返回更新行数
	 */
	public int update(String collection, Map<String, Object> setFields, Map<String, Object> whereFields) throws Exception {
		DBObject whereValue = this.mapToObj(whereFields);
		DBObject setValue = this.mapToObj(setFields);
		DBObject updateSetValue = new BasicDBObject("$set", setValue);
		WriteResult ret = getCollection(collection).update(whereValue, updateSetValue, false, false, WriteConcern.SAFE);
		return ret.getN();
	}

	/**
	 * 批量更新
	 * 
	 * @param collection
	 *            集合名称
	 * @param setFields
	 *            修改参数
	 * @param whereFields
	 *            修改条件
	 * @return 返回更新行数
	 */
	public int updateBatch(String collection, Map<String, Object> setFields, Map<String, Object> whereFields) throws Exception {
		DBObject whereValue = this.mapToObj(whereFields);
		DBObject setValue = this.mapToObj(setFields);
		DBObject updateSetValue = new BasicDBObject("$set", setValue);
		WriteResult ret = getCollection(collection).update(whereValue, updateSetValue, false, true, WriteConcern.SAFE);
		return ret.getN();
	}

	/**
	 * 更新or插入
	 * 
	 * @param collection
	 *            集合名称
	 * @param setFields
	 *            修改参数 map
	 * @param whereFields
	 *            修改条件 map
	 * @param sbl
	 *            ture：更新数据不存在插入数据 false： 更新数据不存在不进行操作
	 * @param ubl
	 *            ture：更新所有符合条件数据 false：只更新首条数据
	 * @return 返回影响行数
	 * @throws Exception
	 */
	public int update(String collection, Map<String, Object> setFields, Map<String, Object> whereFields, boolean sbl, boolean ubl) throws Exception {
		DBObject whereValue = this.mapToObj(whereFields);
		DBObject setValue = this.mapToObj(setFields);
		DBObject updateSetValue = new BasicDBObject("$set", setValue);
		WriteResult ret = getCollection(collection).update(whereValue, updateSetValue, sbl, ubl, WriteConcern.SAFE);
		return ret.getN();
	}

	/**
	 * 更新(参数json)
	 * 
	 * @param collection
	 *            集合名称
	 * @param setJson
	 *            修改参数 eg:{'name':'tt',"age":"12"} (json)
	 * @param whereJson
	 *            修改条件 eg:{'name':'tt',"age":"123"} (json)
	 * @return 返回更新行数
	 * @throws Exception
	 */
	public int update(String collection, String setJson, String whereJson) throws Exception {
		DBObject setValue = this.jsonToObj(setJson);
		DBObject whereValue = this.jsonToObj(whereJson);
		DBObject updateSetValue = new BasicDBObject("$set", setValue);
		WriteResult ret = getCollection(collection).update(whereValue, updateSetValue, false, false, WriteConcern.SAFE);
		return ret.getN();
	}

	/**
	 * 更新or插入
	 * 
	 * @param collection
	 *            集合名称
	 * @param setJson
	 *            修改参数 eg:{'name':'tt',"age":"12"} (json)
	 * @param whereJson
	 *            修改条件 eg:{'name':'tt',"age":"123"} (json)
	 * @param sbl
	 *            ture：更新数据不存在插入数据 false： 更新数据不存在不进行操作
	 * @param ubl
	 *            ture：更新所有符合条件数据 false：只更新首条数据
	 * @return 返回影响行数
	 * @throws Exception
	 */
	public int update(String collection, String setJson, String whereJson, boolean sbl, boolean ubl) throws Exception {
		DBObject setValue = this.jsonToObj(setJson);
		DBObject whereValue = this.jsonToObj(whereJson);
		DBObject updateSetValue = new BasicDBObject("$set", setValue);
		WriteResult ret = getCollection(collection).update(whereValue, updateSetValue, sbl, ubl, WriteConcern.SAFE);
		return ret.getN();
	}

	/**
	 * 查询单条数据
	 * 
	 * @param collection
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DBObject findOne(String collection, Map<String, Object> map) throws Exception {
		DBCollection coll = getCollection(collection);
		if (!map.isEmpty()) {
			DBObject basicDBObject = coll.findOne(this.mapToObj(map));
			if (null != basicDBObject) {
				basicDBObject.put("_id", basicDBObject.get("_id").toString());
			}
			return basicDBObject;
		} else {
			return null;
		}
	}

	/**
	 * 查询单条数据
	 * 
	 * @param collection
	 *            集合名称
	 * @param jsonStr
	 *            查询条件 eg:{'name':'tt',"age":"12"} (json)
	 * @return
	 * @throws Exception
	 */
	public DBObject findOne(String collection, String jsonStr) throws Exception {
		DBCollection coll = getCollection(collection);
		if (!jsonStr.equals("") && jsonStr != null) {
			DBObject basicDBObject = coll.findOne(this.jsonToObj(jsonStr));
			if (null != basicDBObject) {
				basicDBObject.put("_id", basicDBObject.get("_id").toString());
			}
			return basicDBObject;
		} else {
			return null;
		}
	}

	/**
	 * 查找（返回一个List<DBObject>）
	 * 
	 * @param collection
	 *            集合名称
	 * @param map
	 *            查询参数集合
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> find(String collection, Map<String, Object> map) throws Exception {
		DBCollection coll = getCollection(collection);
		DBCursor cur;
		if (map.isEmpty()) {
			cur = coll.find();
		} else {
			cur = coll.find(this.mapToObj(map));
		}
		return this.dbCursorTolist(cur);
	}

	/**
	 * 查找(参数json)（返回一个List<DBObject>）
	 * 
	 * @param collection
	 *            集合名称
	 * @param jsonStr
	 *            查询参数集合(json) eg:{'name':'tt',"age":"12"}
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> find(String collection, String jsonStr) throws Exception {
		DBCollection coll = getCollection(collection);
		DBCursor cur;
		if (jsonStr.equals("") || jsonStr == null) {
			cur = coll.find();
		} else {
			cur = coll.find(this.jsonToObj(jsonStr));
		}
		return this.dbCursorTolist(cur);
	}

	/**
	 * 查找对象（根据主键_id）
	 * 
	 * @param collection
	 *            集合名称
	 * @param _id
	 */
	public DBObject findById(String collection, String _id) throws Exception {
		DBObject obj = new BasicDBObject();
		obj.put("_id", new ObjectId(_id));
		DBObject basicDBObject = getCollection(collection).findOne(obj);
		if (null != basicDBObject) {
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
		}
		return basicDBObject;
	}

	/**
	 * 排序
	 * 
	 * @param collection
	 *            集合名称
	 * @param queryMap
	 *            查询条件（map）
	 * @param param
	 *            根据此参数排序
	 * @param sortnum
	 *            1:asc / -1:desc
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> sortDocument(String collection, Map<String, Object> queryMap, String param, int sortnum) throws Exception {
		DBCollection coll = getCollection(collection);
		DBObject query = this.mapToObj(queryMap);
		DBCursor cur;
		if (query != null) {
			cur = coll.find(query);
		} else {
			cur = coll.find();
		}
		cur.sort(new BasicDBObject(param, sortnum));
		return this.dbCursorTolist(cur);
	}

	/**
	 * 排序
	 * 
	 * @param collection
	 *            集合名称
	 * @param queryJson
	 *            查询条件(json) 查询条件 eg:{'name':'tt',"age":"12"} (
	 * @param param
	 *            根据此参数排序
	 * @param sortnum
	 *            1:asc -1:desc
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> sortDocument(String collection, String queryJson, String param, int sortnum) throws Exception {
		DBCollection coll = getCollection(collection);
		DBObject query = this.jsonToObj(queryJson);
		DBCursor cur;
		if (query != null) {
			cur = coll.find(query);
		} else {
			cur = coll.find();
		}
		cur.sort(new BasicDBObject(param, sortnum));
		return this.dbCursorTolist(cur);
	}

	/**
	 * 分页
	 * 
	 * @param collection
	 *            集合名称
	 * @param queryMap
	 *            查询条件
	 * @param sortMap
	 *            排序条件
	 * @param start
	 *            分页开始
	 * @param limit
	 *            分页结束
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> find(String collection, Map<String, Object> queryMap, Map<String, Object> sortMap, int start, int limit) throws Exception {
		DBCollection coll = getCollection(collection);
		DBObject query = this.mapToObj(queryMap);
		DBObject sort = this.mapToObj(sortMap);
		DBCursor cur;
		if (query != null) {
			cur = coll.find(query);
		} else {
			cur = coll.find();
		}
		if (sort != null) {
			cur.sort(sort);
		}
		cur.skip(start).limit(limit);
		return this.dbCursorTolist(cur);
	}

	/**
	 * 分页(参数json)
	 * 
	 * @param collection
	 *            集合名称
	 * @param queryJson
	 *            查询条件 eg:{'name':'tt',"age":"12"} (json)
	 * @param sortJson
	 *            排序条件 eg:{'name':'tt',"age":"12"} (json)
	 * @param start
	 *            分页开始
	 * @param limit
	 *            分页结束
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> find(String collection, String queryJson, String sortJson, int start, int limit) throws Exception {
		DBCollection coll = getCollection(collection);
		DBObject query = this.jsonToObj(queryJson);
		DBObject sort = this.jsonToObj(sortJson);
		DBCursor cur;
		if (query != null) {
			cur = coll.find(query);
		} else {
			cur = coll.find();
		}
		if (sort != null) {
			cur.sort(sort);
		}
		cur.skip(start).limit(limit);
		return this.dbCursorTolist(cur);
	}

	/***
	 * MongoDB编程模式（用于统计）
	 * 
	 * @param collection
	 *            目标集合
	 * @param map
	 *            映射函数 (生成键值对序列，作为 reduce 函数参数)。
	 * @param reduce
	 *            统计函数
	 * @param outputTarget
	 *            统计结果存放集合
	 * @param query
	 *            条件
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> mapReduce(String collection, String map, String reduce, String outputTarget, String query) throws Exception {
		DBObject dbObject = this.jsonToObj(query);
		DBCollection coll = getCollection(collection);
		coll.mapReduce(map, reduce, outputTarget, dbObject);
		DBCollection collTemp = getCollection(outputTarget);
		DBCursor cur = collTemp.find();
		return this.dbCursorTolist(cur);
	}

	/**
	 * 删除集合
	 * 
	 * @param collection
	 * @throws Exception
	 */
	public void drop(String collection) throws Exception {
		DBCollection coll = getCollection(collection);
		coll.drop();
	}

	/**
	 * MongoDB编程模式（用于统计，得出数据自动删除临时集合）
	 * 
	 * @param collection
	 *            目标集合
	 * @param map
	 *            映射函数 (生成键值对序列，作为 reduce 函数参数)。
	 * @param reduce
	 *            统计函数
	 * @param outputTarget
	 *            统计结果存放集合
	 * @param query
	 *            条件
	 * @return
	 * @throws Exception
	 */
	public List<DBObject> mapReduceAndDrop(String collection, String map, String reduce, String outputTarget, String query) throws Exception {
		List<DBObject> mapList = mapReduce(collection, map, reduce, outputTarget, query);
		DBCollection coll = getCollection(outputTarget);
		coll.drop();
		return mapList;
	}

	/**
	 * DBCursor 转换list
	 * 
	 * @param cur
	 * @return
	 */
	public List<DBObject> dbCursorTolist(DBCursor cur) {
		List<DBObject> list = new ArrayList<DBObject>();
		while (cur.hasNext()) {
			DBObject basicDBObject = cur.next();
			basicDBObject.put("_id", basicDBObject.get("_id").toString());
			list.add(basicDBObject);
		}
		return list;
	}

	/**
	 * map转换DBObject对象
	 * 
	 * @param map
	 * @return
	 */
	public DBObject mapToObj(Map<String, Object> map) {
		DBObject obj = new BasicDBObject();
		obj.putAll(map);
		return obj;
	}

	/**
	 * json转换为DBObject
	 * 
	 * @param json
	 * @return
	 */
	public DBObject jsonToObj(String json) {
		DBObject obj = (DBObject) JSON.parse(json);
		return obj;
	}

	/**
	 * 查找（返回一个List<DBObject>）
	 * 
	 * @param collection
	 *            集合名称
	 * @param map
	 *            查询参数集合
	 * @return
	 * @throws Exception
	 */
	public DBObject group(DBObject key, DBObject cond, DBObject initial, String reduce, String collection) throws Exception {
		DBCollection coll = getCollection(collection);
		return coll.group(key, cond, initial, reduce);
	}
	
	public int updateNoCommand(String collection, Map<String, Object> setFields,
			Map<String, Object> whereFields) throws Exception {
		DBObject whereValue = this.mapToObj(whereFields);
		DBObject setValue = this.mapToObj(setFields);
		DBObject updateSetValue = new BasicDBObject();
		updateSetValue.putAll(setValue);
		WriteResult ret = getCollection(collection).update(whereValue,
				updateSetValue,false,true);
		return ret.getN();
	}
}