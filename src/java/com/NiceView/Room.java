/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.NiceView;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nichlas
 */
public class Room {

    private int roomNumber;
    ArrayList<DateRange> bookedDates = new ArrayList<DateRange>();

    public Boolean isAvailable(DateRange range) {

        for (DateRange bookedRange : bookedDates) {
            if (isWithinRange(range.getStart(), bookedRange) || isWithinRange(range.getEnd(), bookedRange)) {
                return false;
            }
        }

        return true;
    }

    private boolean isWithinRange(Date testDate, DateRange range) {
        return !(testDate.before(range.getStart()) || testDate.after(range.getEnd()));
    }

}
