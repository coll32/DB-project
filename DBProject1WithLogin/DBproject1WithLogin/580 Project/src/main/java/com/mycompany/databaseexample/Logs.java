/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author jessi
 */
public class Logs {
    private int BookID;
    private String lastAction;
    private String lastActionTime;
    private int timesCheckedOut;

    
    public Logs(int BookID, String lastAction, String lastActionTime, int timesCheckedOut) {
        this.BookID = BookID;
        this.lastAction = lastAction;
        this.lastActionTime = lastActionTime;
        this.timesCheckedOut = timesCheckedOut;
    }
    /**
     * @return the BookID
     */
    public int getBookID() {
        return BookID;
    }

    /**
     * @param BookID the BookID to set
     */
    public void setBookID(int BookID) {
        this.BookID = BookID;
    }

    /**
     * @return the lastAction
     */
    public String getLastAction() {
        return lastAction;
    }

    /**
     * @param lastAction the lastAction to set
     */
    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    /**
     * @return the lastActionTime
     */
    public String getLastActionTime() {
        return lastActionTime;
    }

    /**
     * @param lastActionTime the lastActionTime to set
     */
    public void setLastActionTime(String lastActionTime) {
        this.lastActionTime = lastActionTime;
    }

    /**
     * @return the timesCheckedOut
     */
    public int getTimesCheckedOut() {
        return timesCheckedOut;
    }

    /**
     * @param timesCheckedOut the timesCheckedOut to set
     */
    public void setTimesCheckedOut(int timesCheckedOut) {
        this.timesCheckedOut = timesCheckedOut;
    }
}
