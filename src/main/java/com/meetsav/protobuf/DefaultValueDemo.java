package com.meetsav.protobuf;

import com.meetsav.model.Person;

public class DefaultValueDemo {
    public static void main(String[] args) {

        Person person = Person.newBuilder().build();

        System.out.println(
                "City : " + person.getAddress().getCity()
        );
        System.out.println(
                person.hasAddress() // has method is only available for non-primitive data types, for primitive data
                //types in this examples are person's name and age, they dont have has method. To get has method for them
                // we need to use wrapper class
        );
    }
}
