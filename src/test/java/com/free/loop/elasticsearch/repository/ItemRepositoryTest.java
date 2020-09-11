package com.free.loop.elasticsearch.repository;

import com.free.loop.elasticsearch.entity.Item;
import com.free.loop.elasticsearch.reposity.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author free loop
 * @version 1.0
 * @since 2020/9/10 20:52
 */
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void save() {

        long start = System.currentTimeMillis();
        for (long i = 0; i < 100; i++) {
            Item item = new Item(i, "title" +i, "category-test", "brand-test", 100.00, "http://www.baidu.com");
            itemRepository.save(item);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    public void saveList(){
        List<Item> itemList = new ArrayList<>();
        for (long i = 0; i < 5000; i++) {
            Item item = new Item(i, "title", "category-test", "brand-test", 100.00, "http://www.baidu.com");
            itemList.add(item);
        }
        long start = System.currentTimeMillis();
        itemRepository.saveAll(itemList);
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    public void del() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < 100; i++) {
            itemRepository.deleteById(i);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

}
