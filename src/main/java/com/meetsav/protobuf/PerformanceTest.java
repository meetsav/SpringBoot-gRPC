package com.meetsav.protobuf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetsav.json.Jperson;
import com.meetsav.model.Person;

import java.io.IOException;

public class PerformanceTest {

    public static void main(String[] args) throws IOException {

        //json data
        Jperson jperson = new Jperson();
        jperson.setAge(25);
        jperson.setName("meet");
        ObjectMapper mapper = new ObjectMapper();


        Runnable runnable1 = () -> {

            try {
                byte[] bytes = mapper.writeValueAsBytes(jperson);
                Jperson person1 = mapper.readValue(bytes, Jperson.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        };

        //proto data

        Person proto_person = Person.newBuilder().setAge(23).setName("meetsav").build();



        Runnable runnable2 = () -> {
            try{
                byte[] bytes = proto_person.toByteArray();
                Person meet = Person.parseFrom(bytes);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        };
        RunnablePerformanceTest(runnable1, "JSON");
        RunnablePerformanceTest(runnable2, "PROTO");



    }
    private static void RunnablePerformanceTest(Runnable runnable, String method){
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            runnable.run();
            
        }
        long time2 = System.currentTimeMillis();
        System.out.println("time taken by : "+ method +" "+ (time2-time1)+ "ms");
    }
}
