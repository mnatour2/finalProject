package com.example.finalproject.Datamodel;

public class ReservationModel {
    String uname;
    String endDate;
    int room_id;

    public ReservationModel(String uname, String endDate, int room_id) {
        this.uname = uname;
        this.endDate = endDate;
        this.room_id = room_id;
    }
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}
