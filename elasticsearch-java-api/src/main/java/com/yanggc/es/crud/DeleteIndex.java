package com.yanggc.es.crud;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Description:
 *
 * @author: YangGC
 */
public class DeleteIndex {
    public static void main(String[] args) throws IOException {
        // 创建客户端对象
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("10.10.10.124", 9200, "http")));
        try {
            // 删除索引 - 请求对象
            DeleteIndexRequest request = new DeleteIndexRequest("user2");
            // 发送请求，获取响应
            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
            // 操作结果
            System.out.println("操作结果 ： " + response.isAcknowledged());
            System.out.println(client);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端连接
            client.close();
        }

    }
}
