package com.NiceView;

import Fastmoney.CreditCardInfoType;
import com.NiceView.DateRange;
import com.NiceView.Hotel;

 public class HotelReservation {

        public int bookingNumber;
        public Hotel hotel;
        public DateRange timePeriod;
        public CreditCardInfoType creditCardInfoType = null;

        public HotelReservation(int bookingNumber, Hotel hotel, DateRange timePeriod) {
            this.bookingNumber = bookingNumber;
            this.hotel = hotel;
            this.timePeriod = timePeriod;
        }

        public int getBookingNumber() {
            return bookingNumber;
        }

        public Hotel getHotel() {
            return hotel;
        }

        public DateRange getTimePeriod() {
            return timePeriod;
        }

        public void setCreditCardInfoType(CreditCardInfoType creditCardInfoType) {
            this.creditCardInfoType = creditCardInfoType;
        }
        
        
 }