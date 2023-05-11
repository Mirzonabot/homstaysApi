package com.example.api_updated;

import java.util.List;

public class SMSParser {
    public static CRUDHomestayService crudService = new CRUDHomestayService();

    public static String parse(String message) {

        // split the message into array by :

        String[] messageArray = message.split(":");
        System.out.println("message1:"+messageArray[0]);
        System.out.println("message2:"+messageArray[1]);
        System.out.println(messageArray.toString());

        String result = "";

        if (messageArray[0].equals("ghfs")){
            result = parseAllHometays();
        }


        return result;
    }

    public static String parseAllHometays() {
        List<Homestay> homestays = crudService.getHomestays();
        String result = "h:";
        for (Homestay homestay : homestays) {
            result += homestay.toString2() + "\n";
        }
        return result;
    }


}
