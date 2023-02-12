package com.meetsav.server;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountDB {

    private static final Map<Integer, Integer> DB = IntStream
            .rangeClosed(1,10)
            .boxed()
            .collect(Collectors.toMap(Function.identity(), v -> v * 10)
            );
    public static int getBalance(int acoountID){
        return DB.get(acoountID);
    }
    public static int addBalance(int accountID, int amount){

       return DB.computeIfPresent(accountID,(k,v) -> v+amount);
    }
    public static int deductBalance(int accountID, int amount){
        return DB.computeIfPresent(accountID, (k,v) -> v- amount);
    }
}
