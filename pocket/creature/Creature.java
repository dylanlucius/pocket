package pocket.creature;

import java.util.*;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import pocket.item.*;
import pocket.system.*;
import pocket.world.*;

public abstract class Creature {
 ///////////////////////////////////////////////
 //                  FIELDS
 //////////////////////////////////////////////
    
    public final int HUNGER_UNIT = 30;
    // BASICS
    public boolean isdatboi;
    public transient Random random = new Random();
    public Space space;

    public String name;
    public transient Image avatar;
    public Color color;

    public byte team;

    // combat variables
    public int hp, ac = 10, damage, number, targetTemp;

    public boolean attacking;

    public Counter counter = new Counter(15);

    // items
    public ArrayList<Item> items = new ArrayList<Item>();

    // SPECIFICS
    short age, height, weight;
    public String nickname;

    ///////////////////////////////////////
    //        TAXONOMIC TRAITS
    ///////////////////////////////////////

    // BODY : skeleton, muscles, organs
    // HashMap<String, Boolean[]> skeleton = new HashMap<String, Boolean[]>();
    // ... skull, back bones, etc
    
    // HashMap<String, Boolean[]> muscle   = new HashMap<String, Boolean[]>();
    // ... all muscles
    
    // HashMap<String, Boolean[]> organs   = new HashMap<String, Boolean[]>();
    // ... sense organs: eyes, ears, ... | internal organs

    // SENSES
    boolean sight, hearing, smell, taste, touch;
    short sightRange, hearingRange, smellRange;

    // MENTAL
    boolean focused, awake, alert, hostile, languageCapacity;
    short intelligenceLevel;

    // DIET
    boolean carnivore, vegetarian, omnivore;
    public int foodchain, baseFoodchain, hunger;
    Counter hungerCounter;

    // SEXUAL
    boolean sexual, asexual, male, female, puberty;

    //byte attractiveness;
    // ... deprecated | replace with mate seleection system:
    // ... things they're attracted to: appearance, personality, ... feather color

    // PHYSICAL
    boolean canWalk, canSwim, canFly, fight, flee;;
    int reach;
    //short damage; // how much damage if attacks -- dangerousness

    /////////////////////////////////
    //          HEALTH
    ///////////////////////////////////

    // VITALS
    boolean canBreath, hydrated, sleepy, dizzy, nauseous, sexuallyAroused, hasToUrinate, hasToDeficate, bleeding;
    //byte painLevel, bloodVolume;
    short heartrate, temperature;
    // EMOTIONAL STATE

    boolean interested, confused, proud, ashamed, excited, agitated, overwhelmed, alarmed,
            happy, sad, angry, afraid, disgusted;

    // DISABILITIES
    // sense impairment: blind, deaf...
    // mental impairment: 
    // physical impairment:
    // mental illness
    // ... anxiety disorder
    // ... mood disorder
    // ... personality disorder
    // ... psychotic disorder
    // ... addiction

    // DISEASES
    // infectious
    // ... bacterial, viral
    // physiological
    // ... cancer
    // hereditary
    // ... *-degenerative
    // deficiency
    // ... vitamin*
    
 /////////////////////////////////////////////   
 //                  METHODS
 ///////////////////////////////////////////////////////
 public Creature(){

    if(Main.on && World.allCreatures != null){
        World.allCreatures.add(this);
    }

    // if there's a world cursor, set this creature's space to the world cursor's space
    if(World.cursor != null){
        space = World.cursor.space;
    }

    // smiley face avatar
    isdatboi = false;

    avatar = Screen.spritesheet.getSprite(2, 0);

    hp = World.d10.roll(1) + 10; // range 11 to 20

    damage = random.nextInt(3) + 1; // 1 to 3

    attacking = false;

    number = 0;

    setNumber();

    team = 0; // neutral

    hungerCounter = new Counter(HUNGER_UNIT * 16); // every 30 seconds, top of cycle | full hunger cycle 5 minutes

    hunger = 1;

    assignNickname();    

}

public void behavior(){

    //System.out.println("\n" + counter.step);

    counter.update();
    hungerCounter.update();

    if(lifecheck()){
        resolveHunger();

        if(!attacking){
            move();
        }
        
        attack();

        itemAll();

        runitems();
    }

}

 public void move(){

    movementModifier();

    // MOVEMENT
    //  1 2 3 4 5 6 7 8
    //                ^      <-- threshold   |  executes 1 time

    if(Speedcheck.fullspeed()){
        
        if(random.nextInt(10) == 0){
            switch(random.nextInt(4)){

                // up
                default:{
                    // if the space above them exists, a tile exists on it, the tile is a ground type and has no creatures on it
                    if(space.up != null && space.up.tile != null && space.up.tile.ground && space.up.creatures.size() <= 0){
                        World.placeCreature(space.up.tagX, space.up.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.up;
                    }
                    break;    
                }

                // left
                case 1:{
                    if(space.left != null && space.left.tile != null && space.left.tile.ground && space.left.creatures.size() <= 0){
                        World.placeCreature(space.left.tagX, space.left.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.left;
                    }
                    break;
                }
                
                // right
                case 2:{
                    if(space.right != null && space.right.tile != null && space.right.tile.ground && space.right.creatures.size() <= 0){
                        World.placeCreature(space.right.tagX, space.right.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.right;
                    }
                    break;
                }

                // down
                case 3:{
                    if(space.down != null && space.down.tile != null && space.down.tile.ground && space.down.creatures.size() <= 0){
                        World.placeCreature(space.down.tagX, space.down.tagY, this);
                        World.clearcreatures(space.tagX, space.tagY);
                        space = space.down;
                    }
                    break;
                }
        }     
  

        }
            // choose random of 4 directions
    }      
 }
 
 public void movementModifier(){
    
 }
 
 public void attack(){
            
    // [UP] if the space above them exists and has an creature on it
    if(space.up != null && space.up.creatures.size() > 0 && space.up.creatures.get(0) != null){
            // if creature is not on the same team
            // OR hunger is at or above 5 and creature is below on foodchain
            // OR hunger is 10 and creature is at or below on foodchain
            if(space.up.creatures.get(0).team != team || hunger >= 5 && space.up.creatures.get(0).foodchain < foodchain || hunger == 10 && space.up.creatures.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.up.creatures.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [LEFT] 
    if(space.left != null && space.left.creatures.size() > 0 && space.left.creatures.get(0) != null){
            if(space.left.creatures.get(0).team != team || hunger >= 5 && space.left.creatures.get(0).foodchain < foodchain || hunger == 10 && space.left.creatures.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.left.creatures.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [RIGHT] 
    if(space.right != null && space.right.creatures.size() > 0 && space.right.creatures.get(0) != null){
            if(space.right.creatures.get(0).team != team || hunger >= 5 && space.right.creatures.get(0).foodchain < foodchain || hunger == 10 && space.right.creatures.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.right.creatures.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [DOWN] 
    if(space.down != null && space.down.creatures.size() > 0 && space.down.creatures.get(0) != null){
            if(space.down.creatures.get(0).team != team || hunger >= 5 && space.down.creatures.get(0).foodchain < foodchain || hunger ==
             10 && space.down.creatures.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.down.creatures.get(0));
            }
    }
    else {
        attacking = false;
    }
    
          
}

public void roll(Creature target){  
    // if main counter is at Top of Cycle
    if( counter.trigger ){
         // if random roll of "d20" is equal to or larger than enemy AC
        if( World.d20.roll(1) > target.ac){
            damage = World.d4.roll(2);
            targetTemp = target.hp;
            target.hp -=  damage;
            // Main.log.add(target.name + " #" + target.number + " (HP: " + targetTemp + ") --> (HP: " + target.hp + ") -" + damage);
            // Main.log.add("");

            Main.log.add(nickname + " (" + name  + ") did " + damage + " damage to " + target.nickname + " (" + target.name  + ")" );
            Main.log.add("");        

            
            if(target.hp - damage < 0){
                Main.log.add(nickname + " (" + name  + ") killed " + target.nickname + " (" + target.name  + ")" );
                Main.log.add("");        
            }

        }
    }
}

public boolean lifecheck(){
    // make sure they're still alive
    if(hp < 0){
        
        World.allCreatures.remove(this);

        World.placeitem( space.tagX, space.tagY, World.returnCorpse(this) );
        
        //space.items.get(0).icon = this.avatar;

        if(this.isdatboi){
            World.datboiChosen = false;
        }

        World.clearcreatures(space.tagX, space.tagY);


        return false; // is dead
    }
    else {
        return true; // is alive
    }
}

public void setNumber(){
    int number = random.nextInt(3840) + 1;
    boolean duplicate = false;

    // for every space in the world
    for(int i = 0; i < 80; i++){
        for(int j = 0; j < 48; j++){
            
            // if the space is not null
            if(World.space != null && World.space[i][j].creatures.size() > 0){
                // if there is an creature on it, and it's a "Participant"
                    Creature target = World.space[i][j].creatures.get(0);
                    if(target != null && number == target.number){
                        duplicate = true;
                        //System.out.println("duplicate number found");
                    }
            }            
        }   
    }

    if(!duplicate){
        this.number = number;
        //System.out.println("Found a unique ID number");
    }
    else {
        setNumber();
        //System.out.println("fetching new number");
    }
}

public void resolveHunger(){
    if( hungerCounter.trigger ){
        if(hunger < 10){
            hunger++;
        }
        else {

            if(hp - 5 < 0){
                Main.log.add(nickname + " (" + name  + ") starved to death");
                Main.log.add("");        
            }

            hp -= 5;


        }
    }
}

public void itemAll(){
    if(space.items.size() > 0 &&  space.items.get(0) != null){   // if there are items on the space
        for(int i = 0; i < space.items.size(); i++){
            Main.log.add(this.nickname + " (" + this.name + ") picked up " + space.items.get(0).name);
            items.add( space.items.get(0) );
            World.removeitem(space.tagX, space.tagY);
            if(items.size() > 0){
                for(Item item : items){
                    item.holder = this;
                }
            }
            
            //System.out.println("item holder: " + items.get(0).holder);
        }
    }
}

public void runitems(){
    if(items.size() > 0){
        for(int i = 0; i < items.size(); i++){
            items.get(i).behavior();
        }
    }
}

public void assignNickname(){
    nickname = Names.returnProposal(this);
}

public void dropItem(){
    if(this.items.size() > 0){
        space.items.add(items.remove(0));
    }
}

}
