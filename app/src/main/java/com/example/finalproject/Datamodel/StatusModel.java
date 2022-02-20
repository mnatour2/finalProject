package com.example.finalproject.Datamodel;

public class StatusModel {

    private int numberOfBed;
    private boolean hasBalcony;
    private int numberOfBathrooms;

    public StatusModel(int numberOfBed, boolean hasBalcony, int numberOfBathroom) {
        this.numberOfBed = numberOfBed;
        this.hasBalcony = hasBalcony;
        this.numberOfBathrooms = numberOfBathroom;
    }

    public int getNumberOfBed() {
        return numberOfBed;
    }

    public void setNumberOfBed(int numberOfBed) {
        this.numberOfBed = numberOfBed;
    }

    public boolean isHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public int getNumberOfBathroom() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathroom(int numberOfBathroom) {
        this.numberOfBathrooms = numberOfBathroom;
    }
}
