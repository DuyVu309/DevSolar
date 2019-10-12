package com.solarapp.filtersearch.utils;

public class Validator {
    public static boolean isEmpty(String text){
        boolean isEmpty = false;
        if (text.isEmpty()){
            isEmpty = true;
        }
        return isEmpty;
    }
}
