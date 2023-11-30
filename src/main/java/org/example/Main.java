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
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Desactivamos los logs de MongoDB
        Logger logger = LoggerFactory.getLogger("org.mongodb.driver");

        //String uri = "mongodb://usuario:password@host:puerto";
        String url = "mongodb://AdminAlejandro:Secreto!2023@3.226.27.197:27017";
        String uri="patata";


        try (MongoClient mongoClient = MongoClients.create(url)) {
            System.out.println("Conexión con MongoClient y CodecRegistry para el trabajo con POJOs");


            CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

            //establecemos contacto con la base de datos de formula 1
            MongoDatabase database = mongoClient.getDatabase("f1-2006").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Driver> collection = database.getCollection("drivers", Driver.class);


            // Crear una instancia de OperacionesCRUDDriver
            OperacionesCRUDPilotos operaciones = new  OperacionesCRUDPilotos(database);

            // Ejemplo de creación de un piloto
            Driver nuevoDriver = new Driver(/* establece los datos del piloto aquí */);
            operaciones.CrearDriver(nuevoDriver);

            // Leer un piloto específico
            Driver driverLeido = operaciones.LeerDriver(123); // reemplaza 123 con un driverId real

            // Leer todos los pilotos
            List<Driver> todosLosDrivers = operaciones.LeerDrivers();

            // Actualizar un piloto
           // Driver driverParaActualizar = /* obtén o crea un objeto Driver con datos actualizados */;
           // operaciones.ActualizarDriver(driverParaActualizar);

            // Borrar un piloto
            operaciones.BorrarDriver(driverLeido); // asumiendo que driverLeido es el que quieres borrar

            //Llamada a metodos
            operaciones.MostrarPilotosOrdenadosPorEdadDescendente();
            operaciones.MostrarPilotosConEdadMayorQue(30);

            // Si llegamos aquí, la conexión fue exitosa
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (Exception e) {
            // Manejo de excepciones en caso de error de conexión
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }



        }
}