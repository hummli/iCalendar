/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Hummli
 */
public class events {

    public static String scan() {
        String inhalt = "";
        File inputFile = new File("input.txt");

        try {
            if (!inputFile.exists()) {
                inputFile.createNewFile();
                System.out.println("inputFile wurde nicht gefunden und deshalb generiert!");

            } else {
                Scanner s = new Scanner(inputFile, "Windows-1252");
                while (s.hasNextLine()) {
                    inhalt += s.nextLine() + "%";
                    //System.out.println(s.nextLine());
                }

                s.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return inhalt;
    }

    public static String ordnen(String inhalt) {

        String ordered = "";
        String[] gesamt = inhalt.split("%");

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HHmmss");
        final String dateTimeStamp = time.format(formatter1) + "T" + time.format(formatter2);

        for (int i = 0; i < gesamt.length; i++) {
            boolean block = false;
            String gesamtTitel = gesamt[i];
            String[] titelArray = gesamtTitel.split("_", 2);
            String[] titel = titelArray[1].split(" ", 2);
            titel[1] = titel[1].replace("(", "");
            titel[1] = titel[1].replace(")", "");
            if (i < gesamt.length - 3) {
                i++;
                i++;
                i++;
            };

            block = true;
            while (block) {
                if (!gesamt[i].equals("")) {

                    String[] gesamtSplit = gesamt[i].split(" ", 6);
                    gesamtSplit[1] = gesamtSplit[1].replace(":", "");
                    String[] dato = gesamtSplit[1].split("\\.");
                    String[] dato2 = gesamtSplit[2].split("\\.");
                    String[] dato3 = gesamtSplit[4].split("\\.");
                    String[] räume = gesamtSplit[5].split(" ");
                    String raum;
                    boolean entfällt = false;
                    if (gesamtSplit[5].contains("findet nicht statt")) {
                        raum = räume[räume.length - 4];
                        entfällt = true;
                    } else {
                        raum = räume[räume.length - 1];
                    }
                    //LocalDateTime des Events zum vergleichen erstellen
                    String vergleichsDatum = dato[2] + dato[1] + dato[0] + dato2[0] + dato2[1];
                    DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                    LocalDateTime vergleichsDatumDate = LocalDateTime.parse(vergleichsDatum, formatter3);
                    //RückggabeString generieren
                    if (vergleichsDatumDate.isAfter(time)) {
                        ordered += "BEGIN:VEVENT\r\n"
                                + "DTSTART:" + dato[2] + dato[1] + dato[0] + "T" + dato2[0] + dato2[1] + "00\r\n"
                                + "DTEND:" + dato[2] + dato[1] + dato[0] + "T" + dato3[0] + dato3[1] + "00\r\n"
                                + "DTSTAMP:" + dateTimeStamp + "\r\n"
                                + "UID:" + dato[2] + dato[1] + dato[0] + dato2[0] + dato2[1] + dato[2] + dato[1] + dato[0] + dato3[0] + dato3[1] + "hummli@hummli.com\r\n"
                                + "CREATED:" + dateTimeStamp + "\r\n"
                                + "DESCRIPTION:" + titel[1] + "\r\n"
                                + "LAST-MODIFIED:" + dateTimeStamp + "\r\n"
                                + "LOCATION:" + raum + "\r\n";
                        if (entfällt) {
                            ordered += ("SUMMARY:ENTFÄLLT!!!" + titel[0] + "\r\n");
                        } else {
                            ordered += "SUMMARY:" + titel[0] + "\r\n";
                        }
                        ordered += "END:VEVENT\r\n";
                    }
                    if (i == gesamt.length - 1) {
                        block = false;
                    } else {
                        i++;
                    }
                } else {
                    block = false;
                }
            }

        }

        return ordered;
    }

}
