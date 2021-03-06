package com.free.loop.elasticsearch.reposity;

import com.free.loop.elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author free loop
 * @version 1.0
 * @since 2020/9/10 20:50
 */
public interface UserRepository extends ElasticsearchRepository<User,Long> {
}