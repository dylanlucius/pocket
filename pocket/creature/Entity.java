package pocket.creature;

import java.util.Random;
import org.newdawn.slick.Image;
import org.newdawn.slick.Color;
import pocket.world.*;

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


    // SPECIFICS
    short age, height, weight;
    String nickname; 

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

    // SEXUAL
    boolean sexual, asexual, male, female, puberty;

    //byte attractiveness;
    // ... deprecated | replace with mate seleection system:
    // ... things they're attracted to: appearance, personality, ... feather color

    // PHYSICAL
    boolean canWalk, canSwim, canFly, fight, flee;;
    byte reach, movementSpeed;
    short damage; // how much damage if attacks -- dangerousness

    /////////////////////////////////
    //          HEALTH
    ///////////////////////////////////

    // VITALS

    boolean canBreath, hydrated, hungry, sleepy, dizzy, nauseous, sexuallyAroused, hasToUrinate, hasToDeficate, bleeding;
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


 public void behavior(){
        move();

 }

 public void move(){
        if(this.space != null){
                if(random.nextInt(10) == 9){
                    switch(random.nextInt(4)){
                        case 1:{
            
                            for(int i = 0; i < random.nextInt(8); i++){
                                if(this.space.left != null){
                                    if(this.space.left.tile != null){
                                        if(this.space.left.tile.ground){
                                            if(this.space.left.entities.size() <= 0){
                                                World.placeEntity(this.space.left.tagX, this.space.left.tagY, this);
                                                World.clearEntities(this.space.tagX, this.space.tagY);
                                                this.space = this.space.left;
                                            }      
                                        }
                                    }
                                }
                            }   
            
                            break;
                        }
            
                        case 2:{
            
                            for(int i = 0; i < random.nextInt(8); i++){
                                if(this.space.right != null){
                                    if(this.space.right.tile != null){
                                        if(this.space.right.tile.ground){
                                            if(this.space.right.entities.size() <= 0){
                                                World.placeEntity(this.space.right.tagX, this.space.right.tagY, this);
                                                World.clearEntities(this.space.tagX, this.space.tagY);
                                                this.space = this.space.right;
                                            }      
                                        }
                                    }
                                }
                            }   
            
                            break;
                        }
            
                        case 3:{
            
                            for(int i = 0; i < random.nextInt(8); i++){
                                if(this.space.down != null){
                                    if(this.space.down.tile != null){
                                        if(this.space.down.tile.ground){
                                            if(this.space.down.entities.size() <= 0){
                                                World.placeEntity(this.space.down.tagX, this.space.down.tagY, this);
                                                World.clearEntities(this.space.tagX, this.space.tagY);
                                                this.space = this.space.down;
                                            }       
                                        }
                                    }
                                }
                            }   
            
                            break;
                        }
            
                        default:{
            
                            for(int i = 0; i < random.nextInt(8); i++){
                                if(this.space.up != null){
                                    if(this.space.up.tile != null){
                                        if(this.space.up.tile.ground){
                                            if(this.space.up.entities.size() <= 0){
                                                World.placeEntity(this.space.up.tagX, this.space.up.tagY, this);
                                                World.clearEntities(this.space.tagX, this.space.tagY);
                                                this.space = this.space.up;
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
 }
 
}
