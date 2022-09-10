package pocket.item;

import pocket.system.Screen;

public class Corpse extends Food {

    public Corpse(){
        name = "Corpse";
        icon = Screen.spritesheet.getSprite(15, 3); // '?'
        // icon = Screen.skull;

    }
}
