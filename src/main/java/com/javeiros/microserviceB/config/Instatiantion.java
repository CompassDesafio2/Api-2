package com.javeiros.microserviceB.config;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class Instatiantion implements CommandLineRunner {


    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void run(String... args) throws Exception {
        MongoDatabase db = mongoTemplate.getDb();

        mongoTemplate.dropCollection("teste");

        Document doc = new Document("nome", "Exemplo")
                .append("idade", 25)
                .append("cidade", "São Paulo");

        mongoTemplate.getCollection("teste").insertOne(doc);
        System.out.println("Documento inserido com sucesso!");



    }
}