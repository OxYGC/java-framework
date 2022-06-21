package com.yanggc.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Description:
 * 对常用常用连接断开的抽取
 *
 * @author: YangGC
 */
public class ConnectElasticsearch {

    public static void connect(ElasticsearchTask task) {
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("10.10.10.124", 9200, "http")));
        try {
            task.doSomething(client);
            // 关闭客户端连接
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
