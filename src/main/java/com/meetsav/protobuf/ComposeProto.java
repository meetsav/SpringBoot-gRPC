package com.meetsav.protobuf;

import com.meetsav.model.*;
import org.w3c.dom.ls.LSOutput;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;

public class ComposeProto {

    public static void main(String[] args) {
        Address address = Address.newBuilder().setCity("Longmont").setPostbox(80501)
                .setStreet("Hover street").build();

        Car car = Car.newBuilder().setMake("toyota").setModel("Rav4").setYear(2022).build();
        Car car2 = Car.newBuilder().setMake("Honda").setModel("CR-v").setYear(2022).build();

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(car);
        cars.add(car2);

        Person meet = Person.newBuilder().setName("Meet Savaliya")
                 .setAge(25)
                 .addAllCar(cars)
                 .setAddress(address).build();

        System.out.println(meet);
    }


}
