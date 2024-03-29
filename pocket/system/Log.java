package pocket.system;

import java.util.*;


public class Log {
    public ArrayList<String> history = new ArrayList<String>();
    public int currentLogIndex;

    public Log(){

        currentLogIndex = 0;
    }

    public void add(String string){
        history.add(0, string);
    }

    public void print(){
        for(int i = currentLogIndex; i < history.size(); i++){
            System.out.println(history.get(i));
        }
    }

    public void draw(){

        Screen.clear(Screen.TAN);

        for(int i = 0; i < history.size(); i++){
         // FULL SCREEN RULER                 "12345678901234561234567890123456123456789012345612345678901234567890123456789012"

            if(i < 44){
                Main.screen.font.drawString(8 * 12, 8 + (i * 8), " " + history.get(i), Screen.DARK_BLUE);
            }
        }
    }
}
