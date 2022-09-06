package pocket.system;

import java.io.*;
import java.util.Scanner;

public class Temp {
    
    public void save(){
        try{
            PrintWriter save = new PrintWriter(new File ("put/save.txt") );
            save.println((int) 854.);
            save.close();
            System.out.println("Saved game");
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }

    public void load() {
        try{
            Scanner load = new Scanner("save.txt");

            int newInt = Integer.parseInt(load.nextLine());
            System.out.println(newInt);
            load.close();

        } catch (NumberFormatException f) {
            System.out.println("Error loading file");
        }
    } 
}
