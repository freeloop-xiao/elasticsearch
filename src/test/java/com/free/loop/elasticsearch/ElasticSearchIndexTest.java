package com.free.loop.elasticsearch;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

/**
 * 索引操作测试类
 *
 * @author free loop
 * @version 1.0
 * @since 2020/9/7 13:05
 */
@SpringBootTest
class ElasticSearchIndexTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        String indexName = createIndexResponse.index();
        System.out.println(indexName);
    }

    /**
     * 删除索引
     *
     * @throws IOException
     */
    @Test
    public void delIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("twitter");
        AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(deleteIndexResponse.isAcknowledged());
    }

    /**
     * 判断索引是否存在
     *
     * @throws IOException
     */
    @Test
    public void existsIndex() throws IOException {
        GetIndexRequest getRequest = new GetIndexRequest("twitter");
        getRequest.local(false);
        getRequest.humanReadable(true);
        boolean result = restHighLevelClient.indices().exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("是否存在：" + result);
    }
}
