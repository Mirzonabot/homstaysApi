package com.example.api_updated;


import lombok.*;

import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class Homestay {

    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String homestayName;
    private int homestayCapacity;
    private String ownerId;
    private String address;
    private String latitude;
    private String longitude;
    @Builder.Default
    private boolean availability = true;
    private String village;
    private String district;
    private String city;
    private String street;
    private String homestayPhoneNumber;


    @Override
    public String toString() {
        return "Homestay{" +
                    "id='" + id + '\'' +
                    ", homestayName='" + homestayName + '\'' +
                    ", homestayCapacity=" + homestayCapacity +
                    ", ownerId='" + ownerId + '\'' +
                    ", address='" + address + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", availability=" + availability +
                    ", village='" + village + '\'' +
                    ", district='" + district + '\'' +
                    ", city='" + city + '\'' +
                    ", street='" + street + '\'' +
                '}';
    }

    public String toString2() {
        return id + ',' +
                    homestayName + ',' +
                    homestayCapacity + ','+
                    ownerId + ',' +
                    address + ',' +
                    latitude + ',' +
                    longitude + ',' +
                    availability + ',' +
                    village + ',' +
                    district + ',' +
                    city + ',' +
                    street + ',' +
                    homestayPhoneNumber;
    }
}
