/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author jessi
 */
public class LibraryRoom {
    private int roomNumber;
    private int reservedBy;
    private String reservedOn;
    private String dateReserved;
    
    public LibraryRoom(int roomNumber, int reservedBy, String reservedOn, String dateReserved) {
        this.roomNumber = roomNumber;
        this.reservedBy = reservedBy;
        this.reservedOn = reservedOn;
        this.dateReserved = dateReserved;
    }

    /**
     * @return the roomNumber
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the reservedBy
     */
    public int getReservedBy() {
        return reservedBy;
    }

    /**
     * @param reservedBy the reservedBy to set
     */
    public void setReservedBy(int reservedBy) {
        this.reservedBy = reservedBy;
    }

    /**
     * @return the reservedOn
     */
    public String getReservedOn() {
        return reservedOn;
    }

    /**
     * @param reservedOn the reservedOn to set
     */
    public void setReservedOn(String reservedOn) {
        this.reservedOn = reservedOn;
    }

    /**
     * @return the dateReserved
     */
    public String getDateReserved() {
        return dateReserved;
    }

    /**
     * @param dateReserved the dateReserved to set
     */
    public void setDateReserved(String dateReserved) {
        this.dateReserved = dateReserved;
    }
}
