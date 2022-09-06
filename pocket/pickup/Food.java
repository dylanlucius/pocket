package pocket.pickup;

import pocket.system.*;

public class Food extends Pickup {

    boolean rotten;

    public Food(){

        rotten = false;

    }

    public void action(){
        holder.hunger = 1;

        Main.log.add(holder.nickname + " (" + holder.name  + ") ate " + name );
        Main.log.add("");

        holder.pickups.remove(this);
                

    }
}
