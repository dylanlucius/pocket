package pocket.creature;

import pocket.system.*;

public class Red extends Participant {
    
    public Red(){

        name = "Red";
        color = Screen.PINK;

        team = 1;

        if(Main.on){
            Main.log.add("#" + this.number + " joined team " + this.name);
            Main.log.add("");
        }

    }
}
