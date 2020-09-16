package com.free.loop.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * @author free loop
 * @version 1.0
 * @since 2020/9/5 15:25
 */
@Data
@Document(indexName = "es-user", shards = 1, replicas = 0)
public class User {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String userName;

    @Field(type = FieldType.Keyword)
    private String birthday;

    @Field(type = FieldType.Keyword)
    private String nickName;

    @Field(index = false, type = FieldType.Keyword)
    private String images;

    @Field(index = false)
    private List<String> roles;

    public User() {
    }

}
