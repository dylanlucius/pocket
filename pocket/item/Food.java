package pocket.item;

import pocket.system.*;

public class Food extends Item {

    boolean rotten;

    public Food(){

        rotten = false;

    }

    public void action(){
        
        if(holder.hunger >= 5){
            
            holder.hunger = 1;

            Main.log.add(holder.nickname + " (" + holder.name  + ") ate " + name );
            Main.log.add("");

            holder.items.remove(this);
        }       
            
    }
}
