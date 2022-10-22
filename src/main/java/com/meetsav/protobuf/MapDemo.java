package com.meetsav.protobuf;

import com.meetsav.model.*;


public class MapDemo {

    public static void main(String[] args) {
        Car car = Car.newBuilder().setMake("toyota").setModel("Rav4").setYear(2022).setBodyStyle(BodyStyle.SEDAN).build();
        Car car2 = Car.newBuilder().setMake("Honda").setModel("CR-v").setYear(2022).setBodyStyle(BodyStyle.SUV).build();

        Dealer dealer = Dealer.newBuilder().putModel(2005, car).putModel(2004,car2).build();
        System.out.println(
                dealer.getModelMap() //model because variable "model" is in proto file
        );
        System.out.println(
                dealer.getModelOrThrow(2005).getBodyStyle()
        );
    }
}
