# java-framework
> SpringBoot 2.7.0 整合相关服务

# 方法 / 步骤
## Docker目录说明
假设分布式项目包含多个服务（如 service-a 和 service-b），一个典型的目录结构可能是：
project-root/
├── service-a/
│   ├── Dockerfile
│   ├── src/
│   ├── target/
│   └── config/
├── service-b/
│   ├── Dockerfile
│   ├── src/
│   ├── target/
│   └── config/
├── docker-compose/
│   ├── docker-compose.yml
│   └── override.yml
├── README.md
└── scripts/
└── build-and-push.sh


###  Dockerfile
	•	位置: 每个服务的根目录下，例如 service-a/Dockerfile。
	•	内容: 定义该服务的镜像构建细节。
	•	基础镜像
	•	项目文件复制
	•	环境变量
	•	容器启动命令
### docker-compose.yml

	•	位置: 集中放在根目录下的 docker-compose/ 目录中。
	•	内容:
	•	包含多个服务的定义、网络配置、共享卷和其他资源。
	•	override.yml 文件用于本地开发或调试时覆盖配置（如端口映射）。
	•	管理方式:
	•	在 docker-compose.yml 中引用各服务的 Dockerfile。
	•	可以为开发、测试和生产环境分别维护不同的 docker-compose 文件。

示例 docker-compose.yml:
```yml
version: "3.8"
services:
  service-a:
    build:
      context: ../service-a
    ports:
      - "8081:8080"
  service-b:
    build:
      context: ../service-b
    ports:
      - "8082:8080"
```









# 参考资料 & 致谢
[1] [参考资料](https://www.baidu.com/)
