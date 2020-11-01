package com.example.groupprojectcmi;

public class vehicle_item {
    int mvehicleId;
    int mImageResource;
    String mvehiclePlateNo;

    public vehicle_item(int vehicleId, int imageResource, String vehiclePlateNo) {
        mvehicleId = vehicleId;
        mImageResource = imageResource;
        mvehiclePlateNo = vehiclePlateNo;
    }

    public int getVehicleId() { return mvehicleId; }

    public void setVehicleId(int vehicleId) { mvehicleId = vehicleId; }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int imageResource) { this.mImageResource = imageResource; }

    public String getMvehiclePlateNo() {
        return mvehiclePlateNo;
    }

    public void setMvehiclePlateNo(String vehiclePlateNo) { this.mvehiclePlateNo = vehiclePlateNo; }


    @Override
    public String toString() {
        return mvehiclePlateNo;
    }
}

