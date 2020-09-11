package com.free.loop.elasticsearch;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.seqno.RetentionLeaseActions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 索引操作CRUD测试类
 *
 * @author free loop
 * @version 1.0
 * @since 2020/9/7 13:05
 */
@SpringBootTest
public class ElasticSearchOptTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 保存索引文档
     *
     * @throws IOException
     */
    @Test
    public void save() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("name", "free loop");
        map.put("age", "27");
        IndexRequest request = new IndexRequest("twitter").id("1").source(map);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response));
    }

    /**
     * 删除文档索引
     *
     * @throws IOException
     */
    @Test
    public void del() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest().index("twitter").id("1");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(deleteResponse));
    }

    /**
     * 修改索引文档内容
     *
     * @throws IOException
     */
    @Test
    public void update() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest().index("twitter").id("1").doc("age", "30", "name", "颜卓希");
        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void query() throws IOException {
        GetRequest getRequest = new GetRequest().index("twitter").id("1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(getResponse));
    }


}
