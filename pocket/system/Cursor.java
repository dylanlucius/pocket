package pocket.system;

import org.newdawn.slick.Image;

import pocket.world.Space;


    public class Cursor {
        public transient Image icon = Screen.spritesheet.getSprite(1, 11);
        public transient Image bg = Screen.bg;
        public Space space;

        public Cursor(Space space){
            this.space = space;
        }

    }
