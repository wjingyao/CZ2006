package com.example.groupprojectcmi;

public class booking_item {
    int bookingId;
    String bookingActive;
    String bookingDateTime;
    String vehiclePlate;
    String carParkName;
    String address;

    public booking_item(int ibookingId, String ibookingActive, String ibookingDateTime, String ivehiclePlate, String icarParkName, String iaddress) {
        bookingId = ibookingId;
        bookingActive = ibookingActive;
        bookingDateTime = ibookingDateTime;
        vehiclePlate = ivehiclePlate;
        carParkName = icarParkName;
        address = iaddress;
    }

    public int getBookingId() { return bookingId; }

    public void setBookingId(int ibookingId) { bookingId = ibookingId; }

    public String getBookingActive() { return bookingActive; }

    public void setBookingActive(String ibookingActive) { bookingActive = ibookingActive; }

    public String getBookingDateTime() { return bookingDateTime; }

    public void setBookingDateTime(String ibookingDateTime) { bookingDateTime = ibookingDateTime; }

    public String getVehiclePlate() { return vehiclePlate; }

    public void setVehiclePlate(String ivehiclePlate) { vehiclePlate = ivehiclePlate; }

    public String getCarParkName() { return carParkName; }

    public void setCarParkName(String icarParkName) { carParkName = icarParkName; }

    public String getAddress() { return address; }

    public void setAddress(String iaddress) { address = iaddress; }
}
