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

        if (messageArray[0].equals("ghfc")){
            System.out.println("in ghfs if statement");
            String[] query = messageArray[1].split(",");
            if (query[0].isEmpty()){
                result = parseInTheDistanceOf(query[2], query[3], query[1]);
            }
            result = parseAllHometays();
        }


        return result;
    }

    private static String parseInTheDistanceOf(String s, String s1, String s2) {
        List<Homestay> homestays = crudService.getHomestaysInTheDistanceOf(s, s1, s2);
        String result = "h:";
        for (Homestay homestay : homestays) {
            result += homestay.toString2() + "\n";
            if (result.length() > 1500) {
                break;
            }
        }
        return result;
    }

    public static String parseAllHometays() {
        System.out.println("in parseAllHometays");
        List<Homestay> homestays = crudService.getHomestays();
        String result = "h:";
        for (Homestay homestay : homestays) {
            result += homestay.toString2() + "\n";
            if (result.length() > 1500) {
                break;
            }
        }
        System.out.println("______________________");
        System.out.println("______________________");
        System.out.println("result: " + result);
        System.out.println("______________________");
        System.out.println("______________________");
        return result;
    }


}
