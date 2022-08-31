package pocket.creature;

import pocket.system.*;

public class Blue extends Participant {
    
    public Blue(){
        name = "Blue";
        color = Screen.BLUE;

        team = 2;

        if(Main.on){
            Main.log.add("#" + this.number + " joined team " + this.name);
            Main.log.add("");
        }
    }
}
