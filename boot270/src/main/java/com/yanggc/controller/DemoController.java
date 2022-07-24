package com.yanggc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author: YangGC
 * DateTime: 07-17
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/sign")
    public void sign() throws IOException {
        String cookie = "*";

        String signInURL = "https://www.52pojie.cn/home.php?mod=task&do=apply&id=2";

        String USER_AGENT_TEMPLATE = "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) miHoYoBBS/%s";

        Map<String, String> header = new HashMap<>();


        header.put("Cookie", cookie);
        header.put("User-Agent", USER_AGENT_TEMPLATE);
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("x-rpc-channel", "appstore");
        header.put("accept-language", "zh-CN,zh;q=0.9,ja-JP;q=0.8,ja;q=0.7,en-US;q=0.6,en;q=0.5");
        header.put("accept-encoding", "gzip, deflate");
        header.put("accept-encoding", "gzip, deflate");
        header.put("x-requested-with", "com.mihoyo.hyperion");
        header.put("Host", "api-takumi.mihoyo.com");

        List<Header> list = new ArrayList<>();
        for (String key : header.keySet()) {
            list.add(new BasicHeader(key, header.get(key)));
        }
        Header[] headers = list.toArray(new Header[0]);
        doPost(signInURL, headers, null);
    }

    public static JSONObject doPost(String url, Header[] headers, Map<String, Object> data) throws IOException {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject resultJson = null;
        try {
            StringEntity entity = new StringEntity(JSON.toJSONString(data), StandardCharsets.UTF_8);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            if (headers != null && headers.length != 0) {
                for (Header header : headers) {
                    httpPost.addHeader(header);
                }
            }
//            httpPost.setConfig(REQUEST_CONFIG);
            response = httpClient.execute(httpPost);
            System.out.println(response);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                resultJson = JSON.parseObject(result);
            }
            return resultJson;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
        return resultJson;
    }

}
