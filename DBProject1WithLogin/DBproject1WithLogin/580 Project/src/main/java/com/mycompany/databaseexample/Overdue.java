/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author jessi
 */
public class Overdue {
    private int LibraryNumber;
    private int BookID;
    private String dueDate;
    
    public Overdue (int LibraryNumber, int BookID, String dueDate) {
        this.LibraryNumber = LibraryNumber;
        this.BookID = BookID;
        this.dueDate = dueDate;
    }

    /**
     * @return the LibraryNumber
     */
    public int getLibraryNumber() {
        return LibraryNumber;
    }

    /**
     * @param LibraryNumber the LibraryNumber to set
     */
    public void setLibraryNumber(int LibraryNumber) {
        this.LibraryNumber = LibraryNumber;
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
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
