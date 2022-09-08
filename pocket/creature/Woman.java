package pocket.creature;

import pocket.system.Screen;
import java.util.Random;

public class Woman extends HumanAdult {
    transient Random random = new Random();

    public Woman(){

        name                = "Woman";
        avatar              = Screen.spritesheet.getSprite(12, 0);

        assignNickname();

        age                 = 18;
        height              = 67;   // inches
        weight              = 125;  // lb;

        sight               = true;
        hearing             = true;
        smell               = true;
        taste               = true;
        touch               = true;
        canWalk             = true;
        canSwim             = true;
    //  canFly              = false;
        sightRange          = 100;  // spaces
        hearingRange        = 20;   // spaces
        smellRange          = 5;    // spaces
        reach               = 1;    // space
        damage              = 1;    // one is standard 

        awake               = true;
        alert               = true;
        focused             = false;
        hostile             = false;
        languageCapacity    = true;
        intelligenceLevel   = 10;   // ten is standard   

        carnivore           = false;
        vegetarian          = false;
        omnivore            = true;

        sexual              = true;
        asexual             = false;
        puberty             = true;

        canBreath           = true;
        hydrated            = true;
        sleepy              = false;
        dizzy               = false;
        nauseous            = false;
        sexuallyAroused     = false;
        hasToUrinate        = false;
        hasToDeficate       = false;
        bleeding            = false;
        temperature         = 98;

        interested          = false;
        confused            = false;
        proud               = false;
        ashamed             = false;
        excited             = false;
        agitated            = false;
        happy               = true;
        sad                 = false;
        angry               = false;
        afraid              = false;
        disgusted           = false;
        overwhelmed         = false;
    }
}
