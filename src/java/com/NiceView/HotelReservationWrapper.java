/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NiceView;


import java.util.ArrayList;

/**
 *
 * @author Nichlas
 */
public class HotelReservationWrapper {
    public ArrayList<HotelReservation> list = new ArrayList<HotelReservation>();
    public HotelReservationWrapper(ArrayList<HotelReservation> list){
        this.list = list;
    }

    public ArrayList<HotelReservation> getList() {
        return list;
    }
    
    
}
