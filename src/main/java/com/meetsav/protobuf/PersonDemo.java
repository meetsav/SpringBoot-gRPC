package com.meetsav.protobuf;
import com.google.protobuf.Int32Value;
import com.meetsav.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonDemo {

    public static void main(String[] args) throws IOException {

//       Person meet =  Person.newBuilder()
//                .setName("meet")
//                .setAge(25).build();

//        System.out.println(meet.toString());
//
//        Person bhavin = Person.newBuilder()
//                .setName("Bhavin")
//                .setAge(30)
//                .build();
//        System.out.println(bhavin);
//        System.out.println(meet.equals(bhavin));

        //example using wrapper type:
   //             Person personUsingWrapperType = Person.newBuilder()
    //            .setName("Bhavin")
         //TODO main difference is here
      //                  .setAge(Int32Value.newBuilder().setValue(25).build())
      //          .build();
        //System.out.println(bhavin);
       // System.out.println(meet.equals(bhavin));
        Path path = Paths.get("meet.ser");
       // Files.write(path, meet.toByteArray());
        byte[] bytes = Files.readAllBytes(path);

        Person deser = Person.parseFrom(bytes);
        System.out.println(deser);




    }
}
