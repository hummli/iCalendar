/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icalendar;

/**
 *
 * @author Hummli
 */
public class ICalendar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        iCal a = new iCal();
        a.write("name",events.ordnen((Input.scan())));
        
        System.out.println(events.ordnen((Input.scan())));
           
    }
}
