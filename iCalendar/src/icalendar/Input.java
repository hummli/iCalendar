/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icalendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Hummli
 */
public class Input {

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
                    //Ausgabe als Test
                    //System.out.print(/*titel[0] + " " + titel[1] + " " + */dato[2] + dato[1] + dato[0] + "S" + dato2[0] + dato2[1] + "T" + "---" + dato[2] + dato[1] + dato[0] + "S" + dato3[0] + dato3[1] + "T" + "---" + raum);
                    
                    //RückggabeString generieren
                    ordered += titel[0] + "---" + titel[1] + "---" + dato[2] + dato[1] + dato[0] + "S" + dato2[0] + dato2[1] + "T" + "---" + dato[2] + dato[1] + dato[0] + "S" + dato3[0] + dato3[1] + "T" + "---" + raum;
                    if (entfällt) {
                        //System.out.print(" ENTFÄLLT!!!");
                        ordered += ("---ENTFÄLLT!!!");
                    }
                    ordered += "\n\r";
                    //System.out.println();
                    
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
