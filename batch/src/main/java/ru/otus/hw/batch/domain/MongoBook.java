package ru.otus.hw.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class MongoBook {
    @Id
    @Field("id")
    private String id;
    @Field("name")
    private String name;
    @Field("author")
    private MongoAuthor author;
    @Field("genre")
    private String genre;
}
