package com.example.api_updated.controller;


import com.example.api_updated.Booking;
import com.example.api_updated.CRUDHomestayService;
import com.example.api_updated.Homestay;
import com.example.api_updated.SendSMS;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Controller
public class EmployeeController {


    public static CRUDHomestayService crudService;

    public EmployeeController(CRUDHomestayService crudService) {
        this.crudService = crudService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String testing() {
        return "testing";
    }

    @PostMapping("/sms-receiver")
    public ResponseEntity smsReceiver(@RequestBody String sms) {
        System.out.println("sms receiver");
        return ResponseEntity.ok("sms receiver");
    }


    @GetMapping("/get-all-homestays")
    public ResponseEntity<List<Homestay>> getAllHomestays() {
        return ResponseEntity.ok(crudService.getHomestays());
    }

    @GetMapping("/get-homestay-by-id/{homestayID}")
    public ResponseEntity<List<Homestay>> getHomestayById(@PathVariable String homestayID) {
        return ResponseEntity.ok(crudService.getHomestayById(homestayID));
    }

    @GetMapping("/get-homestay-by-ownerID/{ownerID}")
    public ResponseEntity<List<Homestay>> getHomestayByOwnerId(@PathVariable String ownerID) {
        return ResponseEntity.ok(crudService.getHomestaysByOwnerId(ownerID));
    }

    @GetMapping("/get-all-bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        System.out.println("get all bookings");
        return ResponseEntity.ok(crudService.getAllBookings());
    }

    @GetMapping("/get-booking-by-id/{bookingID}")
    public ResponseEntity<List<Booking>> getBookingById(@PathVariable String bookingID) {
        return ResponseEntity.ok(crudService.getBookingById(bookingID));
    }

    @GetMapping("/get-bookings-by-homestayID/{homestayID}")
    public ResponseEntity<List<Booking>> getBookingsByHomestayId(@PathVariable String homestayID) {
        return ResponseEntity.ok(crudService.getBookingsByHomestayId(homestayID));
    }

    @GetMapping("/get-bookings-by-userID/{userID}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable String userID) {
        return ResponseEntity.ok(crudService.getBookingsByUserId(userID));
    }

    @GetMapping("/get-bookings-by-ownerID/{ownerID}")
    public ResponseEntity<List<Booking>> getBookingsByOwnerId(@PathVariable String ownerID) {
        return ResponseEntity.ok(crudService.getBookingsByOwnerId(ownerID));
    }

    @PostMapping("/create-homestay")
    public String createHomestay(@RequestBody Homestay homestay) {
        System.out.println(homestay);
        return crudService.createHomestay(homestay);
    }

    @PutMapping("/update-homestay")
    public String update(@RequestBody Homestay homestay) {
        return crudService.updateObject("homestay", homestay.getId(), homestay);
    }


    @PostMapping("/create-booking")
    public String createBooking(@RequestBody Booking booking) {
        return crudService.createBooking(booking);
    }

    @PutMapping("/update-booking")
    public String updateBooking(@RequestBody Booking booking) {
        return crudService.updateObject("booking", booking.getBookingID(), booking);
    }

    @PostMapping("/send-sms/{phoneNumber}")
    public String sendSMS(@PathVariable String phoneNumber) {
        return SendSMS.sendSms(phoneNumber,"Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                "\n" +
                "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.");
    }



    @PutMapping("/set-availability/{homestayID}/{availability}")
    public String setAvailability(@PathVariable String homestayID, @PathVariable boolean availability) {
        return crudService.setAvailability(homestayID, availability);
    }

    @PutMapping("/set-booking-acceptance/{bookingID}/{status}")
    public String setBookingAcceptance(@PathVariable String bookingID, @PathVariable boolean status) {
        return crudService.setBookingAcceptance(bookingID, status);
    }

    @PutMapping("/set-booking-cancelled/{bookingID}/{status}")
    public String setBookingCancelled(@PathVariable String bookingID, @PathVariable boolean status) {
        return crudService.setBookingCancelled(bookingID, status);
    }

    @PutMapping("/set-booking-rejected/{bookingID}/{status}")
    public String setBookingRejected(@PathVariable String bookingID, @PathVariable boolean status) {
        return crudService.setBookingRejected(bookingID, status);
    }

    @PutMapping("/set-checkin-date/{bookingID}/{checkinDate}")
    public String setCheckinDate(@PathVariable String bookingID, @PathVariable String checkinDate) {
        return crudService.setCheckinDate(bookingID, checkinDate);
    }

    @PutMapping("/set-checkout-date/{bookingID}/{checkoutDate}")
    public String setCheckoutDate(@PathVariable String bookingID, @PathVariable String checkoutDate) {
        return crudService.setCheckoutDate(bookingID, checkoutDate);
    }

    @PutMapping("/set-checkin-checkout-date/{bookingID}/{checkinDate}/{checkoutDate}")
    public String setCheckinCheckoutDate(@PathVariable String bookingID, @PathVariable String checkinDate, @PathVariable String checkoutDate) {
        return crudService.setCheckinCheckoutDate(bookingID, checkinDate, checkoutDate);
    }

//    @GetMapping("/capacity-in-date/{homestayID}/{checkinDate}")
//    public int getCapacityInDate(@PathVariable String homestayID, @PathVariable String checkinDate) {
//        return crudService.getCapacityInDate(homestayID, checkinDate);
//    }

    @GetMapping("/capacity-in-one-date-range/{homestayID}/{date}")
    public int getCapacityInOneDateRange(@PathVariable String homestayID, @PathVariable String date) {
        return crudService.getCapacityInOneDateRange(homestayID, date);
    }

    @GetMapping("/get-free-capacity-in-one-date-range/{homestayID}/{date}")
    public int getFreeCapacityInOneDateRange(@PathVariable String homestayID, @PathVariable String date) {
        return crudService.getFreeCapacityInOneDateRange(homestayID, date);
    }

    @GetMapping("get-homestay-capacity/{homestayID}")
    public int getHomestayCapacity(@PathVariable String homestayID) {
        return crudService.getHomestayCapacity(homestayID);
    }

    @PutMapping("/sms-received/{sms}")
    public String smsReceived(@PathVariable String sms) {

        String[] strArr = sms.split(":"); // split the string by space
        System.out.println("SMS: " + sms);
        List<String> strList = Arrays.asList(strArr); // convert array to list
        System.out.println("first: " + strList.get(0));
        System.out.println("second:" + strList.get(1));
        if (strList.get(0).equals("nb")) {
            RecieveSMS.makeNewBooking(strList.get(0), strList.get(1));
        }

        System.out.println(strList.get(0));
        System.out.println(strList.get(1));


        return "sms received";
    }

    public static class RecieveSMS {

        public RecieveSMS() {
        }

        public static void makeNewBooking(String phoneNumber, String message){
            System.out.println("phoneNumber: " + phoneNumber);
            String[] strArr = message.split(","); // split the string by space
            List<String> strList = Arrays.asList(strArr); // convert array to list



            Booking booking = new Booking();
            booking.setBookingID(strList.get(0));
            booking.setHomestayID(strList.get(1));
            booking.setUserID(strList.get(2));
            booking.setCheckInDate(strList.get(3));
            booking.setCheckOutDate(strList.get(4));

            crudService.createBooking(booking);

        }
    }
}
