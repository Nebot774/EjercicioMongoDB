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

        //String uri = "mongodb://usuario:password@host:puerto";
        String url = "mongodb://adria:Secreto!2021@i-0a4894661ff81acb2.compute-1.amazonaws.com:27017";
         String uri="patata";


        try (MongoClient mongoClient = MongoClients.create(url)) {
            System.out.println("Conexión con MongoClient y CodecRegistry para el trabajo con POJOs");

            // Realizar aquí las operaciones que necesitas en la base de datos

            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            MongoDatabase database = mongoClient.getDatabase("f1-2006").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Driver> collection = database.getCollection("drivers", Driver.class);




            // Si llegamos aquí, la conexión fue exitosa
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (Exception e) {
            // Manejo de excepciones en caso de error de conexión
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }



        }
}