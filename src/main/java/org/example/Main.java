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
import java.text.SimpleDateFormat;
import java.util.Date;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

            //establecer fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = dateFormat.parse("1980-01-01");

            // Ejemplo de creación de un piloto
            Driver nuevoDriver = new Driver();
            nuevoDriver.setId(new ObjectId());
            nuevoDriver.setDriverid(777);
            nuevoDriver.setCode("BRM");
            nuevoDriver.setForename("Bryant");
            nuevoDriver.setSurname("Myers");
            nuevoDriver.setDob(fechaNacimiento);
            nuevoDriver.setNationality("Boricua");
             //nuevoDriver.setConstructors(/* Objeto Constructor correspondiente */); // Constructor del piloto, si es necesario
            nuevoDriver.setUrl("http://bryantmyers.com");

            operaciones.CrearDriver(nuevoDriver);


            // Leer un piloto específico
            Driver driverLeido = operaciones.LeerDriver(4);

            //mostramos el driver
            if (driverLeido != null) {
                System.out.println("Driver Leído: " + driverLeido);
            } else {
                System.out.println("No se encontró un Driver con el ID proporcionado.");
            }


            // Leer todos los pilotos
            List<Driver> todosLosDrivers = operaciones.LeerDrivers();

            //motstramos todos los pilotos
            if (!todosLosDrivers.isEmpty()) {
                System.out.println("Listado de Pilotos:");
                for (Driver driver : todosLosDrivers) {
                    System.out.println(driver);
                }
            } else {
                System.out.println("No se encontraron pilotos.");
            }



            // Actualizar un piloto
            Driver driverParaActualizar = new Driver();
            driverParaActualizar.setDriverid(777); // ID del piloto a actualizar
            driverParaActualizar.setCode("645"); // Nuevo código del piloto
            driverParaActualizar.setForename("Josevi"); // Nuevo nombre
            driverParaActualizar.setSurname("Honekawa"); // Nuevo apellido

          //Driver driverParaActualizar = ;
          // operaciones.ActualizarDriver(driverParaActualizar);

            System.out.println("Yee anem a borrar");
            // Borrar un piloto
            operaciones.BorrarDriver("BRM"); // Reemplaza "codigoDelPiloto" con el código real del piloto que deseas borrar



            //Llamada a metodos Extras
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