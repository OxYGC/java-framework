# 配置打包docker镜像（通常用于本地,dockerhub等镜像名称在流水线配置文件中设置）
# 注意：下面的行必须是Tab，而不是空格！
IMAGE_NAME = boot-270-img
VERSION = 1.0.0

build:
	docker build -t $(IMAGE_NAME):$(VERSION) .
push:
	docker push $(IMAGE_NAME):$(VERSION)