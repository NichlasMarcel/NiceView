/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NiceView;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nichlas
 */
public class Hotel {
    private String name;
    private String address;
    
    private double price;
    private boolean creditcard;
    private String hotelReservationService;

    public Hotel(String name, String address, double price, boolean creditcard, String hotelReservationService) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.creditcard = creditcard;
        this.hotelReservationService = hotelReservationService;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public boolean isCreditcard() {
        return creditcard;
    }

    public void setCreditcard(boolean creditcard) {
        this.creditcard = creditcard;
    }

    
    public String getHotelReservationService() {
        return hotelReservationService;
    }

    public void setHotelReservationService(String hotelReservationService) {
        this.hotelReservationService = hotelReservationService;
    }
    
   
    
    
}
