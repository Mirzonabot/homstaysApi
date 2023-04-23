package com.example.api_updated;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Booking {
    @Builder.Default
    private  String bookingID = UUID.randomUUID().toString();

    private String checkInDate;
    private String checkOutDate;
    private String nameBookedBy;
    private String homestayID;
    private String homestayName;
    private String userID;
    private String ownerID;
    private String phoneNumberBooker;
    @Builder.Default
    private boolean booked = false;
    @Builder.Default
    private boolean rejected = false;
    @Builder.Default
    private boolean isCancelled = false;
    @Builder.Default
    private long timestampLong = (new Timestamp(System.currentTimeMillis())).getTime();

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID='" + bookingID + '\'' +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", nameBookedBy='" + nameBookedBy + '\'' +
                ", homestayID='" + homestayID + '\'' +
                ", homestayName='" + homestayName + '\'' +
                ", userID='" + userID + '\'' +
                ", ownerID='" + ownerID + '\'' +
                ", booked=" + booked +
                ", rejected=" + rejected +
                ", isCancelled=" + isCancelled +
                ", timestampLong=" + timestampLong +
                '}';
    }

}