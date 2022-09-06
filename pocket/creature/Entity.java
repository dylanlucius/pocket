package pocket.creature;

import java.util.*;

import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

import pocket.system.*;
import pocket.world.*;
import pocket.pickup.*;

public abstract class Entity {
 ///////////////////////////////////////////////
 //                  FIELDS
 //////////////////////////////////////////////
    
    // BASICS
    public Random random = new Random();
    public Space space;

    public String name;
    public Image avatar;
    public Color color, bgColor;

    public byte team;

    // combat variables
    public int hp, ac = 10, damage, number, targetTemp;

    public boolean attacking;

    public Counter counter = new Counter(8);

    // pickups
    public ArrayList<Pickup> pickups = new ArrayList<Pickup>();

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
    int reach, speed, baseSpeed;
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
 public Entity(){

    if(Main.on && World.entityList != null){
        World.entityList.add(this);
    }

    // if there's a world cursor, set this entity's space to the world cursor's space
    if(World.cursor != null){
        space = World.cursor.space;
    }

    // smiley face avatar
    avatar = Screen.spritesheet.getSprite(2, 0);

    hp = World.d10.roll(1) + 10; // range 11 to 20

    damage = random.nextInt(3) + 1; // 1 to 3

    attacking = false;

    number = 0;

    setNumber();

    speed = baseSpeed;

    team = 0; // neutral

    hungerCounter = new Counter(30 * 8); // every 30 seconds, top of cycle | full hunger cycle 5 minutes

    hunger = 1;

    assignNickname();    

}

public void behavior(){

    if(lifecheck()){
        resolveHunger();

        if(!attacking){
            move();
        }
        
        attack();

        pickupAll();

        runPickups();
    }

}

 public void move(){

    movementModifier();

    // MOVEMENT
    //  1/10 chance to move at all
    if(random.nextInt(10) == 9){
        // choose random of 4 directions
        switch(random.nextInt(4)){

            // up
            default:{
                // execute a random number of times between 1 and 8
                for(int i = 0; i < speed ; i++){
                    // if the space above them exists
                    if(space.up != null){
                        // if a tile exists on it
                        if(space.up.tile != null){
                            // if the tile is a ground type
                            if(space.up.tile.ground){
                                // if it has no entities on it
                                if(space.up.entities.size() <= 0){
                                    World.placeEntity(space.up.tagX, space.up.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.up;
                                }       
                            }
                        }
                    }
                }   

                break;    
            }

            // left
            case 1:{
                for(int i = 0; i < speed; i++){
                    if(space.left != null){
                        if(space.left.tile != null){
                            if(space.left.tile.ground){
                                if(space.left.entities.size() <= 0){
                                    World.placeEntity(space.left.tagX, space.left.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.left;
                                }      
                            }
                        }
                    }
                }   

                break;
            }
            
            // right
            case 2:{
                for(int i = 0; i < speed; i++){
                    if(space.right != null){
                        if(space.right.tile != null){
                            if(space.right.tile.ground){
                                if(space.right.entities.size() <= 0){
                                    World.placeEntity(space.right.tagX, space.right.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.right;
                                }      
                            }
                        }
                    }
                }   

                break;
            }

            // down
            case 3:{

                for(int i = 0; i < speed; i++){
                    if(space.down != null){
                        if(space.down.tile != null){
                            if(space.down.tile.ground){
                                if(space.down.entities.size() <= 0){
                                    World.placeEntity(space.down.tagX, space.down.tagY, this);
                                    World.clearEntities(space.tagX, space.tagY);
                                    space = space.down;
                                }       
                            }
                        }
                    }
                }   

                break;
            }

            
        }     
    }      
 }
 
 public void movementModifier(){
    
 }
 
 public void attack(){
            
    // [UP] if the space above them exists and has an entity on it
    if(space.up != null && space.up.entities.size() > 0){
            // if entity is not on the same team
            // OR hunger is at or above 5 and entity is below on foodchain
            // OR hunger is 10 and entity is at or below on foodchain
            if(space.up.entities.get(0).team != team || hunger >= 5 && space.up.entities.get(0).foodchain < foodchain || hunger == 10 && space.up.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.up.entities.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [LEFT] 
    if(space.left != null && space.left.entities.size() > 0){
            if(space.left.entities.get(0).team != team || hunger >= 5 && space.left.entities.get(0).foodchain < foodchain || hunger == 10 && space.left.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.left.entities.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [RIGHT] 
    if(space.right != null && space.right.entities.size() > 0){
            if(space.right.entities.get(0).team != team || hunger >= 5 && space.right.entities.get(0).foodchain < foodchain || hunger == 10 && space.right.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.right.entities.get(0));
            }
    }
    else {
        attacking = false;
    }

    // [DOWN] 
    if(space.down != null && space.down.entities.size() > 0){
            if(space.down.entities.get(0).team != team || hunger >= 5 && space.down.entities.get(0).foodchain < foodchain || hunger ==
             10 && space.down.entities.get(0).foodchain <= foodchain){
                attacking = true;
                roll( space.down.entities.get(0));
            }
    }
    else {
        attacking = false;
    }
    
          
}

public void roll(Entity target){  
    // if main counter is at Top of Cycle
    if( counter.over() ){
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
        World.entityList.remove(this);

        //System.out.println("corpse name:" + World.returnCorpse(this).name);
        World.placePickup( space.tagX, space.tagY, World.returnCorpse(this) );
        //System.out.println("space item name: " + space.pickups.get(0).name);


        World.clearEntities(space.tagX, space.tagY);
        //Main.log.add(name + " #" + number + " eliminated");
        //Main.log.add("");

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
            if(World.space != null && World.space[i][j].entities.size() > 0){
                // if there is an entity on it, and it's a "Participant"
                    Entity target = World.space[i][j].entities.get(0);
                    if(number == target.number){
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
    if( hungerCounter.over() ){
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

public void pickupAll(){
    if(space.pickups.size() > 0){   // if there are pickups on the space
        for(int i = 0; i < space.pickups.size(); i++){
            System.out.println("pickup added to inventory");
            pickups.add( space.pickups.get(0) );
            World.removePickup(space.tagX, space.tagY);
            pickups.get(0).holder = this;
            System.out.println("item holder: " + pickups.get(0).holder);
        }
    }
}

public void runPickups(){
    if(pickups.size() > 0){
        for(int i = 0; i < pickups.size(); i++){
            pickups.get(i).behavior();
        }
    }
}

public void assignNickname(){
    nickname = Names.returnProposal(this);
}

}
