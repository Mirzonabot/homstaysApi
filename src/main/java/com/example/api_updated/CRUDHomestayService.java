package com.example.api_updated;

import com.google.firebase.database.*;
import com.google.firebase.internal.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class CRUDHomestayService {
    private final DatabaseReference homestayDatabaseReference = FirebaseDatabase.getInstance("https://homestaybooking-f8308-default-rtdb.europe-west1.firebasedatabase.app").getReference("homestays");
    private final DatabaseReference bookingsDatabaseReference = FirebaseDatabase.getInstance("https://homestaybooking-f8308-default-rtdb.europe-west1.firebasedatabase.app").getReference("booking");
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://homestaybooking-f8308-default-rtdb.europe-west1.firebasedatabase.app").getReference();


    public List<Homestay> getHomestays() {

        return updateList(homestayDatabaseReference, Homestay.class);
    }

    public List<Homestay> getHomestaysInTheDistanceOf(String latitude, String longitude, String distance) {
        List<Homestay> homestays = getHomestays();
        List<Homestay> result = new ArrayList<>();
        for (Homestay homestay : homestays) {
            if (DistanceBetweenLocations.distance(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(homestay.getLatitude()), Double.parseDouble(homestay.getLongitude()),'K') <= Double.parseDouble(distance)) {
                result.add(homestay);


            }
        }
        return result;
    }


    public List<Homestay> getHomestayById(String homestayId) {

        Query query = homestayDatabaseReference.orderByChild("id").equalTo(homestayId);

        return updateList(query, Homestay.class);
    }

    public List<Homestay> getHomestaysByOwnerId(String ownerId) {

        Query query = homestayDatabaseReference.orderByChild("ownerId").equalTo(ownerId);

        return updateList(query, Homestay.class);
    }

    public List<Booking> getAllBookings() {
        return updateList(bookingsDatabaseReference, Booking.class);
    }

    public List<Booking> getBookingById(String bookingId) {
        Query query = bookingsDatabaseReference.orderByChild("bookingID").equalTo(bookingId);
        return updateList(query, Booking.class);
    }

    public List<Booking> getBookingsByHomestayId(String homestayId) {
        Query query = bookingsDatabaseReference.orderByChild("homestayID").equalTo(homestayId);
        return updateList(query, Booking.class);
    }

    public List<Booking> getBookingsByUserId(String userId) {
        Query query = bookingsDatabaseReference.orderByChild("userID").equalTo(userId);
        return updateList(query, Booking.class);
    }

    public List<Booking> getBookingsByOwnerId(String ownerId) {
        Query query = bookingsDatabaseReference.orderByChild("ownerID").equalTo(ownerId);
        return updateList(query, Booking.class);
    }


    public String createHomestay(Homestay homestay) {
        databaseReference.child("homestays").push().setValue(homestay, null);
        return homestay.getId();
    }


    public String createBooking(Booking booking) {

        databaseReference.child("booking").push().setValue(booking, null);
        return booking.getBookingID();
    }


    public <T> String updateObject(String objectType, String objectId, T object) {

        DatabaseReference databaseReference;
        String childId;

        if (objectType.equals("homestay")) {
            databaseReference = homestayDatabaseReference;
            childId = "id";
        } else if (objectType.equals("booking")) {
            databaseReference = bookingsDatabaseReference;
            childId = "bookingID";
        } else {
            throw new IllegalArgumentException("Invalid object type");
        }
        Query query = databaseReference.orderByChild(childId).equalTo(objectId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().setValue(object, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return objectId;
    }
    private <T> ArrayList<T> updateList(Query query, Class<T> valueType) {
//        System.out.println("1");
        ArrayList<T> list = new ArrayList<>();
//        System.out.println("2");
        CountDownLatch latch = new CountDownLatch(1); // Initialize CountDownLatch
//        System.out.println("3");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("4");
                for (DataSnapshot homestaySnapshot : snapshot.getChildren()) {
//                    System.out.println("4.5");
//                    System.out.println(valueType);
//                    System.out.println("_________________");
//                    System.out.println(homestaySnapshot);
//                    System.out.println("_________________");
//                    System.out.println(homestaySnapshot.getValue(valueType));
//                    System.out.println("_________________");
                    list.add(homestaySnapshot.getValue(valueType));
//                    System.out.println("5");
                }

                latch.countDown();
//                System.out.println("6");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await(); // Block the current thread until the latch count reaches 0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("7");
        return list;
    }

    public String setAvailability(String homestayId, boolean availability) {
        Query query = homestayDatabaseReference.orderByChild("id").equalTo(homestayId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().child("availability").setValue(availability, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return homestayId;
    }

    public String setBookingAcceptance(String bookingId, boolean acceptance) {
        Query query = bookingsDatabaseReference.orderByChild("bookingID").equalTo(bookingId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().child("booked").setValue(acceptance, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    public String setBookingCancelled(String bookingId, boolean cancelled) {
        Query query = bookingsDatabaseReference.orderByChild("bookingID").equalTo(bookingId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().child("cancelled").setValue(cancelled, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    public String setBookingRejected(String bookingId, boolean rejected) {
        Query query = bookingsDatabaseReference.orderByChild("bookingID").equalTo(bookingId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().child("rejected").setValue(rejected, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    public String setCheckinDate(String bookingId, String checkinDate) {
        Query query = bookingsDatabaseReference.orderByChild("bookingID").equalTo(bookingId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().child("checkInDate").setValue(checkinDate, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    public String setCheckoutDate(String bookingId, String checkOutDate) {
        Query query = bookingsDatabaseReference.orderByChild("bookingID").equalTo(bookingId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().child("checkOutDate").setValue(checkOutDate, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

    public String setCheckinCheckoutDate(String bookingId, String checkinDate, String checkOutDate) {
        Query query = bookingsDatabaseReference.orderByChild("bookingID").equalTo(bookingId);
        CountDownLatch latch = new CountDownLatch(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    objectSnapshot.getRef().child("checkInDate").setValue(checkinDate, null);
                    objectSnapshot.getRef().child("checkOutDate").setValue(checkOutDate, null);
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bookingId;
    }

//    public int getCapacityInDate(String homestayId, String checkinDate) {
//        Query query = bookingsDatabaseReference.orderByChild("homestayID").equalTo(homestayId);
//        CountDownLatch latch = new CountDownLatch(1);
//        final int[] capacity = {0};
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
//                    String checkInDate = objectSnapshot.child("checkInDate").getValue(String.class);
//                    System.out.println("checkInDate: " + checkInDate);
//                    if (checkInDate != null) {
//                        System.out.println("not null");
//                        if (checkInDate.equals(checkinDate)) {
//                            capacity[0] += 1;
//                            System.out.println("capacity: " + capacity[0]);
//                        }
//                    }
//                }
//                latch.countDown();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                latch.countDown();
//            }
//        });
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return capacity[0];
//    }

    public int getCapacityInOneDateRange(String homestayId, String date) {
        Query query = bookingsDatabaseReference.orderByChild("homestayID").equalTo(homestayId);
        CountDownLatch latch = new CountDownLatch(1);
        final int[] capacity = {0};
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    String checkInDate = objectSnapshot.child("checkInDate").getValue(String.class);
                    String checkOutDate = objectSnapshot.child("checkOutDate").getValue(String.class);
                    System.out.println("checkInDate: " + checkInDate);
                    if (checkInDate != null && checkOutDate != null) {
                        //convert string to date
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date1 = LocalDate.parse(checkInDate, formatter);
                        LocalDate date2 = LocalDate.parse(checkOutDate, formatter);
                        LocalDate date3 = LocalDate.parse(date, formatter);

                        System.out.println("not null");
                        if ((date3.isBefore(date2) && date3.isAfter(date1))||(date3.isEqual(date1))) {
                            capacity[0] += 1;
                            System.out.println("capacity: " + capacity[0]);
                        }
                    }
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return capacity[0];
    }

    public int getFreeCapacityInOneDateRange(String homestayId, String date){
        int capacity = getCapacityInOneDateRange(homestayId, date);
        int homestayCapacity = getHomestayCapacity(homestayId);

        return capacity - homestayCapacity;

    }

    public int getHomestayCapacity(String homestayId){
        Query query = homestayDatabaseReference.orderByChild("id").equalTo(homestayId);
        CountDownLatch latch = new CountDownLatch(1);
        final int[] capacity = {0};
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("snapshot: " + snapshot);
                for (DataSnapshot objectSnapshot : snapshot.getChildren()) {
                    capacity[0] = objectSnapshot.child("homestayCapacity").getValue(Integer.class);
                    break;
                }
                latch.countDown();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return capacity[0];
    }


}