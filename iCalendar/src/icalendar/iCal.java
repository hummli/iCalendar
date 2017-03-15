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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class iCal {

    final private String calBegin = "BEGIN:VCALENDAR\r\n";
    final private String prodid = "PRODID:-//Hummli Inc//THI Stundenplan//GER\r\n";
    final private String version = "VERSION:2.0\r\n";
    final private String calscale = "CALSCALE:GREGORIAN\r\n";
    final private String calname = "X-WR-CALNAME:Stundenplan\r\n";
    final private String timezone = "X-WR-TIMEZONE:Europe/Berlin\r\n";
    final private String calEnd = "END:VCALENDAR\r\n";

//Aktuelle Zeit wird in String dateTimeStamp gespeichert
    public void iCal() {

    }

    public void write(String name,String events) {

        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(".ics");

        try {
            File file = new File(builder.toString());

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(calBegin);
            bw.write(prodid);
            bw.write(version);
            bw.write(calscale);
            bw.write(calname);
            bw.write(timezone);
            bw.write(events);
            bw.write(calEnd);

            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
