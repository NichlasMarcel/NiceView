package com.NiceView;

import Fastmoney.CreditCardInfoType;
import com.NiceView.DateRange;
import com.NiceView.Hotel;

 public class HotelReservation {

        public int bookingNumber;
        public Hotel hotel;
        public DateRange timePeriod;
        public boolean reserved;
        public CreditCardInfoType creditCardInfoType = null;

        public HotelReservation(int bookingNumber, Hotel hotel, DateRange timePeriod, boolean reserved) {
            this.bookingNumber = bookingNumber;
            this.hotel = hotel;
            this.timePeriod = timePeriod;
            this.reserved = reserved;
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

        public boolean isReserved() {
            return reserved;
        }
 }