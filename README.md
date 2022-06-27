# java-framework
> SpringBoot 2.7.0 整合相关服务

# 方法 / 步骤
## SpringBoot 2.7.0 整合 Alibaba Canal
- [下载并解压 canal.deployer-x.x.x.tar.gz](https://github.com/alibaba/canal/releases)  

  在deployer项目里面的conf\example\instance.properties改写自己的配置
```java
# position info
canal.instance.master.address = 192.168.11.14:3306(这里换成自己的主数据库地址)

# 这里填写自己的数据库账号密码
canal.instance.dbUsername=canal
canal.instance.dbPassword=canal
```
其他默认即可，改完后进行启动






# 参考资料 & 致谢
[1] [【Win平台开发-基础服务】Alibaba Canal 安装和配置](https://blog.csdn.net/YangCheney/article/details/122118469?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522165629962416781667841723%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=165629962416781667841723&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-1-122118469-null-null.nonecase&utm_term=canal&spm=1018.2226.3001.4450)
[2] [基于SpringBoot bootstrap.yml配置未生效的解决](https://www.jb51.net/article/197013.htm)



