package com.free.loop.elasticsearch;

import com.free.loop.elasticsearch.entity.Item;
import com.free.loop.elasticsearch.reposity.ItemRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引聚合查询操作类
 *
 * @author free loop
 * @version 1.0
 * @since 2020/9/7 14:40
 */
@SpringBootTest
public class ElasticSearchAggregationsTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void buildData() {

        List<Item> itemList = new ArrayList<>();
        for (long i = 0; i < 5000; i++) {
            Item item = new Item(i, "apple", "category-test", "brand-test", 100.00 + i, "http://www.baidu.com");
            itemList.add(item);
        }
        itemRepository.saveAll(itemList);
        itemList = new ArrayList<>();
        for (long i = 5000; i < 5200; i++) {
            Item item = new Item(i, "banana", "category-banana-big", "brand-banana", 10.00 + i, "http://www.baidu.com");
            itemList.add(item);
        }
        itemRepository.saveAll(itemList);

        itemList = new ArrayList<>();
        for (long i = 5200; i < 5300; i++) {
            Item item = new Item(i, "banana", "category-banana-small", "brand-banana", 10.00 + i, "http://www.baidu.com");
            itemList.add(item);
        }
        itemRepository.saveAll(itemList);
    }


    @Test
    public void groupBy() throws IOException {

        // 1级分组条件字段
        TermsAggregationBuilder aggregation1 = AggregationBuilders.terms("group_title").field("title");
        // 2级分组条件字段
        TermsAggregationBuilder aggregation2 = AggregationBuilders.terms("group_category").field("category");
        // 组合分组条件
        aggregation1 = aggregation1.subAggregation(aggregation2);

        // 查询source构建
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(aggregation1);
        searchSourceBuilder.size(10);

        SearchRequest request = new SearchRequest("item");
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        Aggregations aggregations = response.getAggregations();
        ParsedStringTerms terms = aggregations.get("group_title");
        List<Terms.Bucket> buckets = (List<Terms.Bucket>) terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            long count = bucket.getDocCount();
            System.out.println(key + " ===> " + count);
            ParsedStringTerms subTerms = bucket.getAggregations().get("group_category");
            List<Terms.Bucket> subBuckets = (List<Terms.Bucket>) subTerms.getBuckets();
            for (Terms.Bucket subBucket : subBuckets) {
                String subKey = subBucket.getKeyAsString();
                long subCount = subBucket.getDocCount();
                System.out.println("   " + subKey + " ===> " + subCount);
            }
        }

    }


}