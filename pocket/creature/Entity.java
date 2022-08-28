package pocket.creature;
import org.newdawn.slick.Image;
import org.newdawn.slick.Color;
import pocket.world.Space;

public abstract class Entity {
 ///////////////////////////////////////////////
 //                  FIELDS
 //////////////////////////////////////////////
    
    // BASICS
    public Space space;

    public String name;
    public Image avatar;
    public Color color, bgColor;


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
                
        }
   
}
