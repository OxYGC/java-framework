# java-framework
> SpringBoot 2.7.0 整合相关服务

# 方法 / 步骤
## SpringBoot 2.7.0 整合 Alibaba Canal

### MySQL配置
- mysql需要开启binlog，修改my.cnf文件，添加如下配置：
```properties
[mysqld]  
log-bin=mysql-bin #添加这一行就ok  
binlog-format=ROW #选择row模式  
server_id=1 #配置mysql replaction需要定义，不能和canal的slaveId重复  
```

- 连接到数据库，执行如下语句：
```sql

CREATE USER canal IDENTIFIED BY 'canal'; 
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
-- GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' ;
FLUSH PRIVILEGES;
```

- 查看配置是否可用
```sql
-- 运行以下sql (如果显示OFF则代表未开启。在MySQL8以前，这个功能是默认关闭的，需要手动开启。)
show variables like 'log_bin';
-- 可以查看账号相关信息
select host, user, authentication_string, plugin from mysql.user;
```





### 中间件配置

- [下载并解压 canal.deployer-x.x.x.tar.gz](https://github.com/alibaba/canal/releases)


  在deployer项目里面的conf\example\instance.properties改写自己的配置
```properties
# position info

canal.instance.master.address = 192.168.11.14:3306(这里换成自己的主数据库地址)
# username/password
canal.instance.dbUsername=canal                    #用授权后的canal账号
canal.instance.dbPassword=canal                    #用授权后的canal密码
canal.instance.defaultDatabaseName = test1         #数据库库名
canal.instance.connectionCharset=UTF-8             #数据库字符集

# table regex
# 正则匹配默认.*\\..*，其中前面.*代表库名，后面.*代表表名
canal.instance.filter.regex=test1\\..*


```
其他默认即可，改完后进行启动






# 参考资料 & 致谢
[1] [Win平台开发-基础服务- Alibaba Canal 安装和配置](https://blog.csdn.net/YangCheney/article/details/122118469?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522165629962416781667841723%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=165629962416781667841723&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-1-122118469-null-null.nonecase&utm_term=canal&spm=1018.2226.3001.4450)  
[2] [基于SpringBoot bootstrap.yml配置未生效的解决](https://www.jb51.net/article/197013.htm)  
[3] [Windows部署canal报错：Should be either .groovy or .xml](https://blog.csdn.net/Brave_heart4pzj/article/details/123717059)  
[4] [SpringBoot整合canal实现数据同步的示例代码](https://www.jb51.net/article/241266.htm)  
[5] [数据库发生改变收不到订阅消息](http://t.zoukankan.com/LQBlog-p-14582469.html)  
[6] [[脚本之家] SpringBoot整合canal实现数据同步的示例代码](https://www.jb51.net/article/241266.htm)






