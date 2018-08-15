---------------------------------------------------
---------------------部署说明--------------------
---------------------------------------------------
【数据库信息】
1、数据库类型：mysql 5.5以上版本；
2、数据库名称：pmanage（和“CAS”共用同一个）；
3、数据库配置文件的位置：/APP_HOME/WebRoot/WEB-INF/classes/jdbc.properties（修改第7至9行即可）；


【配置信息】
1、文件位置：/APP_HOME/WebRoot/WEB-INF/classes/config.properties；
2、需要提供存储目录/www/portal/logo/（具有读写权限）；
3、修改配置文件中URL的值为“用户管理系统API接口服务”的内网访问地址；
4、修改LOGOHTTPURL的值为用户管理系统的外网访问地址；
5、修改jar.checkAuth.userAPIurl地址为统一用户UserAPI接口地址
6、修改jar.checkAuth.userManageSystemMark为YS_yhgl
7、修改ERRO_CALLBACK_URL为统一门户地址

【单点登录】
1、修改/APP_HOME/WebRoot/WEB-INF/web.xml文件，27行和50行，将“<param-value>”标签中的值，替换为CAS正式环境外网访问的地址（https格式）；
2、修改/APP_HOME/WebRoot/WEB-INF/web.xml文件，31行和54行，将“<param-value>”标签中的值，替换为本系统正式环境外网访问的地址（http格式）；
3、修改/APP_HOME/WebRoot/main.jsp文件，79行，将“<a>”标签中“href”的值，替换为https://[CAS的域名]/logout?service=http://[本系统域名]；
	注意：此处配置的“CAS域名”和“本系统域名”，要和以上的 第1点和第2点的配置完全一样。
【动态适配logo】
本系统会根据用户的所属商信息自动适配logo的信息，具体使用方法和要求如下：
1、/APP_HOME/WebRoot/images/logo/ 目录下存放着待适配的png格式的图片。
2、图片命名规则：顶部logo图片为“所属商名称”+“.png”，顶部背景图片为“background_”+“所属商名称”+“.png”，
	例如：所属商名称为 cdvcloud，则顶部logo图片的名称应为“cdvcloud.png”，顶部背景图片应为“background_cdvcloud.png”;