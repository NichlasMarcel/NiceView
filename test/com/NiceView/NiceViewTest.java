/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NiceView;

import Fastmoney.CreditCardInfoType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Nichlas
 */
public class NiceViewTest {

    CreditCardInfoType card = new CreditCardInfoType();
    CreditCardInfoType card2 = new CreditCardInfoType();

    public NiceViewTest() {
        card.setName("Anne Strandberg");
        card.setNumber("50408816");
        CreditCardInfoType.ExpirationDate exp = new CreditCardInfoType.ExpirationDate();
        exp.setMonth(5);
        exp.setYear(9);
        card.setExpirationDate(exp);

        card2.setName("Thor-Jensen Claus");
        card2.setNumber("50408825");
        CreditCardInfoType.ExpirationDate exp2 = new CreditCardInfoType.ExpirationDate();
        exp2.setMonth(5);
        exp2.setYear(9);
        card2.setExpirationDate(exp2);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getHotels method, of class NiceView.
     */
    @Test
    public void testGetHotels() throws ParseException {
        System.out.println("getHotels");
        String city = "Copenhagen";
        DateRange timePeriod = new DateRange(
                new SimpleDateFormat("dd/MM/yyyy").parse("15/12/2016"),
                new SimpleDateFormat("dd/MM/yyyy").parse("27/12/2016"));

        NiceView instance = new NiceView();
        //instance.initialize();

        // Expects 1 result
//        HotelReservationWrapper expResult = null;
//        HotelReservationWrapper result = instance.getHotels(city, timePeriod.getStart(), timePeriod.getEnd());
//        assertEquals(1, result.list.size());
//
//        // Expects 0
//        timePeriod.setStart(new SimpleDateFormat("dd/MM/yyyy").parse("19/11/2016"));
//        result = instance.getHotels(city, timePeriod.getStart(), timePeriod.getEnd());
//        assertEquals(0, result.list.size());

        // TODO review the generated test code and remove the default call to fail.        
    }

    /**
     * Test of bookHotel method, of class NiceView.
     */
    @Test
    public void testBookHotel() throws ParseException {
        int bookingNumber = 1;

        NiceView instance = new NiceView();
        //instance.initialize();

        Boolean expResult = false;
        Boolean result;
        try {
            result = instance.bookHotel(bookingNumber, card2);
        } catch (Exception ex) {
            result = false;
        }

        assertEquals(expResult, result);

        expResult = true;
        try {
            result = instance.bookHotel(bookingNumber, card);
        } catch (Exception ex) {
            result = false;
        }
        assertEquals(expResult, result);

        bookingNumber = 2;
        expResult = false;
        try {
            result = instance.bookHotel(bookingNumber, card);
        } catch (Exception ex) {
            result = false;
        }
        assertEquals(expResult, result);

        bookingNumber = 1;
        expResult = false;
        try {
            result = instance.bookHotel(bookingNumber, card);
        } catch (Exception ex) {
            result = false;
        }
        assertEquals(expResult, result);

        
        
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of cancelHotel method, of class NiceView.
     */
    @Test
    public void testCancelHotel() throws ParseException {
        System.out.println("cancelHotel");
        int bookingNumber = 1;
        NiceView instance = new NiceView();
        //instance.initialize();

        try {
            instance.bookHotel(bookingNumber, card);
        } catch (Exception ex) {

        }

        /*
         Cancel a reservation that exist.
         */
        Boolean expResult = true;
        Boolean result;
        try {
//            result = instance.cancelHotel(bookingNumber);
        } catch (Exception ex) {
            result = false;
        }
//        assertEquals(expResult, result);

        /*
         Cancel a reservation that has already been cancelled
         */
        expResult = false;
        try {
//            result = instance.cancelHotel(bookingNumber);
        } catch (Exception ex) {
            result = false;
        }
//        assertEquals(expResult, result);

        /*
         Cancel a reservation that does not exist.
         */
        expResult = false;
        bookingNumber = 3;
        try {
//            result = instance.cancelHotel(bookingNumber);
        } catch (Exception ex) {
            result = false;
        }
//        assertEquals(expResult, result);
        
        /*
            Check if its possible to book the reservation.
        */
        expResult = true;
        bookingNumber = 1;
        try {
            result = instance.bookHotel(bookingNumber, card);
        } catch (Exception ex) {
            result = false;
        }
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
}
