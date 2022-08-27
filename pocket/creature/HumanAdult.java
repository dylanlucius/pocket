package pocket.creature;

import pocket.system.Screen;

public class HumanAdult extends Human {
    
    public HumanAdult(){
        name                = "Human Adult";
        avatar              = Screen.spritesheet.getSprite(8, 4);
        color               = Screen.WHITE;
        nickname            = "";
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
        movementSpeed       = 1;    // block per tick

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
        hungry              = false;
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
