package pocket.pickup;

import pocket.creature.*;

public class Corpse extends Food {

    public Corpse(Entity entity){
        name = "the corpse of " + entity.nickname + " (" + entity.name + ")";
        icon = entity.avatar;
    }
}
