package com.example.api_updated;

import java.util.List;

public class SMSParser {
    public static CRUDHomestayService crudService = new CRUDHomestayService();

    public static String parse(String message) {

        // split the message into array by :

        String[] messageArray = message.split(":");

        String result = "";

        if (messageArray[0].equals("ghfs")){
            result = parseAllHometays();
        }


        return result;
    }

    public static String parseAllHometays() {
        List<Homestay> homestays = crudService.getHomestays();
        String result = "";
        for (Homestay homestay : homestays) {
            result += homestay.toString2() + "\n";
        }
        return result;
    }


}
