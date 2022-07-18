
# 方法 / 步骤

## 服务端测试打包

```batch
# 服务端启动命令
bin/flink run -m localhost:8081 -c com.yanggc.example.CDC4StreamTest ./cdc-connector-1.0-SNAPSHOT-jar-with-dependencies.jar


# 然后在Flink 面板TaskManagers -> Stdout 下面查看相关打印
```

# 参考资料 & 致谢
[1] [Flink CDC 同步mysql数据](https://blog.csdn.net/congge_study/article/details/123694906)  
[2] [从flinkcdc入手剖析DataStream、FlinkSQL两种使用模式](https://www.bilibili.com/video/BV1wL4y1Y7Xu?p=11&vd_source=579d0fc282407045043b5db7f2163295)  
[3] [GitHub-FlinkClub](https://github.com/czy006/FlinkClub)  