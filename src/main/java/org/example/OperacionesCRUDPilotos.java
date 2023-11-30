package org.example;

import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import com.mongodb.client.model.Filters;

public class OperacionesCRUDPilotos {

    private MongoCollection<Driver> collection;

    public OperacionesCRUDPilotos(MongoDatabase database) {
        this.collection = database.getCollection("drivers", Driver.class);
    }

    public void CrearDriver(Driver driver) {
        collection.insertOne(driver);
    }

    public Driver LeerDriver(int driverId) {
        return collection.find(Filters.eq("driverid", driverId)).first();
    }


    public List<Driver> LeerDrivers() {
        List<Driver> drivers = new ArrayList<>();
        collection.find().forEach(drivers::add);
        return drivers;
    }

    public void ActualizarDriver(Driver driver) {
        collection.replaceOne(Filters.eq("driverid", driver.getDriverid()), driver);
    }


    public void BorrarDriver(String code) {
        collection.deleteMany(Filters.eq("code", code));
    }



    //metodos adicionales
    public void MostrarPilotosOrdenadosPorEdadDescendente() {
        List<Driver> drivers = LeerDrivers();
        drivers.stream()
                .sorted(Comparator.comparing(driver -> getEdadEn2006(driver.getDob()), Comparator.reverseOrder()))
                .forEach(driver -> System.out.println(driver + " - Edad en 2006: " + getEdadEn2006(driver.getDob())));
    }

    public void MostrarPilotosConEdadMayorQue(int edad) {
        List<Driver> drivers = LeerDrivers();
        drivers.stream()
                .filter(driver -> getEdadEn2006(driver.getDob()) >= edad)
                .sorted(Comparator.comparing(driver -> getEdadEn2006(driver.getDob()), Comparator.reverseOrder()))
                .forEach(driver -> System.out.println(driver + " - Edad en 2006: " + getEdadEn2006(driver.getDob())));
    }

    private int getEdadEn2006(Date dob) {
        LocalDate localDob = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(localDob, LocalDate.of(2006, 1, 1)).getYears();
    }



}
