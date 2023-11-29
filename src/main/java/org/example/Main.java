package org.example;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
//import org.mongo.entities.Driver;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {

        // Desactivamos los logs de MongoDB
        Logger logger = LoggerFactory.getLogger("org.mongodb.driver");





    }
}