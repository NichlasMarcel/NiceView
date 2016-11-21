/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NiceView;

import Fastmoney.AccountType;
import Fastmoney.BankService;
import Fastmoney.CreditCardFaultMessage;
import Fastmoney.CreditCardInfoType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Nichlas
 */
@WebService(serviceName = "NiceViewService")
public class NiceView {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;


  

    /**
     * This is a sample web service operation
     */
    ArrayList<Hotel> hotelList = new ArrayList<Hotel>();
    ArrayList<Reservation> bookings = new ArrayList<Reservation>();

    int groupNumber = 23;
    AccountType niceViewAccount = new AccountType();

    public NiceView() {
        service = new BankService();
        initialize();
    }

    private void initialize() {
        niceViewAccount.setName("NiceView");
        niceViewAccount.setNumber("50308815");
        Hotel copenhagen = new Hotel("ChipAndChap", "Copenhagen", 18000, true, "AndersAnd");
        // "ChipAndChap", "Copenhagen", 18000, true, "AndersAnd"
//        copenhagen.setAddress("Whatup");
//        copenhagen.setPrice(300);
        Hotel amsterdam = new Hotel("RipRapRup", "Amsterdam", 200, true, "AndersSine");

//        Date arrival = new SimpleDateFormat("dd/MM/yyyy").parse("20/12/2016");
//        Date departure = new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2017");
        try {
            Reservation copenhagen23122016;
            copenhagen23122016 = new Reservation(
                    1,
                    copenhagen,
                    new DateRange(
                            new SimpleDateFormat("dd/MM/yyyy").parse("15/12/2016"),
                            new SimpleDateFormat("dd/MM/yyyy").parse("27/12/2016")),
                    false
            );

            Reservation amsterdam10112016;
            amsterdam10112016 = new Reservation(
                    3,
                    amsterdam,
                    new DateRange(
                            new SimpleDateFormat("dd/MM/yyyy").parse("10/11/2016"),
                            new SimpleDateFormat("dd/MM/yyyy").parse("22/11/2016")),
                    false
            );
            hotelList.add(copenhagen);
            hotelList.add(amsterdam);
            bookings.add(copenhagen23122016);
            bookings.add(amsterdam10112016);

        } catch (Exception e) {
            //System.out.println("initialize: " + e.getMessage());
        }
    }

    private boolean isWithinRange(Date testDate, DateRange range) {
        return !(testDate.before(range.getStart()) || testDate.after(range.getEnd()));
    }

    @WebMethod(operationName = "getHotels")
    public ArrayList<Hotel> getHotels(@WebParam(name = "city") String city,
            @WebParam(name = "arrival") Date arrival,
            @WebParam(name = "departure") Date departure) {
        ArrayList<Hotel> hotelListFiltered = new ArrayList<Hotel>();
        hotelListFiltered.addAll(hotelList);

        for (Reservation res : bookings) {
            // Bestemt start og slut tidspunkt
            if (!res.hotel.getAddress().equals(city)
                    || !res.timePeriod.getStart().equals(arrival)
                    || !res.timePeriod.getEnd().equals(departure)) {
                if (hotelListFiltered.contains(res.hotel)) {
                    hotelListFiltered.remove(res.hotel);
                }
            }
        }

        return hotelListFiltered;
    }

    // Booking nummeret burde være relateret til perioden som hotellet bliver booket
// og skal derfor ikke være fast på hotellet.
    @WebMethod(operationName = "bookHotel")
    public Boolean bookHotel(@WebParam(name = "bookingNumber") int bookingNumber,
            @WebParam(name = "creditcard") CreditCardInfoType creditCardInfo) throws Exception {
        try {
            for (Reservation booking : bookings) {
                int price = (int) (booking.timePeriod.Days() * booking.hotel.getPrice());
                if (booking.bookingNumber == bookingNumber && !booking.reserved) {
                    if (booking.hotel.isCreditcard() == true) {
                        // We expect it to validate if there is enough money on the account.
                        System.out.println("name: " + creditCardInfo.getName() + "\n price: " + price);
                        System.out.println("-----------------");
                        if (!validateCreditCard(groupNumber, creditCardInfo, price)) {
                            throw new Exception();
                        }
                    }
                    System.out.println("BN reservation: " + booking.bookingNumber);
                    System.out.println("BN Input: " + bookingNumber);
                    chargeCreditCard(groupNumber, creditCardInfo, price, niceViewAccount);
                    booking.reserved = true;
                    booking.creditCardInfoType = creditCardInfo;
                    return true;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    @WebMethod(operationName = "cancelHotel")
    public Boolean cancelHotel(@WebParam(name = "bookingNumber") int bookingNumber) throws Exception {
        // Tag et booking nummer og fjern reservationen
        // PT har hotellerne et booking nummer og det skal ændres så 
        // at reservationen har et booking nummer i stedet.
        for (Reservation res : bookings) {
            if (res.bookingNumber == bookingNumber && res.reserved == true) {
                res.reserved = false;
                int price = (int) (res.timePeriod.Days() * res.hotel.getPrice());
                refundCreditCard(groupNumber, res.creditCardInfoType, price, niceViewAccount);
                return true;
            }

        }
        throw new Exception();
    }

    public class Reservation {

        int bookingNumber;
        Hotel hotel;
        DateRange timePeriod;
        boolean reserved;
        CreditCardInfoType creditCardInfoType = null;

        public Reservation(int bookingNumber, Hotel hotel, DateRange timePeriod, boolean reserved) {
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

    private boolean chargeCreditCard(int group, Fastmoney.CreditCardInfoType creditCardInfo, int amount, Fastmoney.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        Fastmoney.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean refundCreditCard(int group, Fastmoney.CreditCardInfoType creditCardInfo, int amount, Fastmoney.AccountType account) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        Fastmoney.BankPortType port = service.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, Fastmoney.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        Fastmoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }


  

}
