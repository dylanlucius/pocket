package pocket.creature;

import java.util.*;

import pocket.system.*;
import pocket.world.*;

public abstract class Participant extends Entity {

    Random random = new Random();

    // combat variables
    public int hp, ac = 10, damage, number, targetTemp;

    public boolean attacking;

    public Counter counter = new Counter(8);

    public Participant(){
        // if there's a world cursor, set this entity's space to the world cursor's space
        if(World.cursor != null){
            space = World.cursor.space;
        }

        // smiley face avatar
        avatar = Screen.spritesheet.getSprite(2, 0);

        hp = random.nextInt(20) + 10; // range 10 to 20
        damage = random.nextInt(3) + 1; // 1 to 3

        attacking = false;

        number = 0;

        setNumber(this);

    }

    @Override
    public void behavior(){

        lifecheck();

        if(!attacking){
            move();
        }
        
        attack();
        
    }

    public void attack(){
            
        // [UP] if the space above them has an entity on it
        if(this.space.up.entities.size() > 0){
            if(this.space.up.entities.get(0).getClass().getSuperclass() == Participant.class){
                // if entity is not on the same team
                if(this.space.up.entities.get(0).team != this.team){
                    attacking = true;
                    roll( (Participant) this.space.up.entities.get(0));
                }
            } 
        }
        else {
            attacking = false;
        }

        // [LEFT] if the space above them has an entity on it
        if(this.space.left.entities.size() > 0){
            if(this.space.left.entities.get(0).getClass().getSuperclass() == Participant.class){
                // if entity is not on the same team
                if(this.space.left.entities.get(0).team != this.team){
                    attacking = true;
                    roll( (Participant) this.space.left.entities.get(0));
                }
            } 
        }
        else {
            attacking = false;
        }

        // [RIGHT] if the space above them has an entity on it
        if(this.space.right.entities.size() > 0){
            if(this.space.right.entities.get(0).getClass().getSuperclass() == Participant.class){
                // if entity is not on the same team
                if(this.space.right.entities.get(0).team != this.team){
                    attacking = true;
                    roll( (Participant) this.space.right.entities.get(0));
                }
            } 
        }
        else {
            attacking = false;
        }

        // [DOWN] if the space above them has an entity on it
        if(this.space.down.entities.size() > 0){
            if(this.space.down.entities.get(0).getClass().getSuperclass() == Participant.class){
                // if entity is not on the same team
                if(this.space.down.entities.get(0).team != this.team){
                    attacking = true;
                    roll( (Participant) this.space.down.entities.get(0));
                }
            } 
        }
        else {
            attacking = false;
        }
        
              
    }

    public void roll(Participant target){  
        // if main counter is at Top of Cycle
        if( counter.over() ){
             // if random roll of "d20" is equal to or larger than enemy AC
            if( World.d20.roll(1) > target.ac){
                damage = World.d6.roll(3);
                targetTemp = target.hp;
                target.hp -=  damage;
                Main.log.add(target.name + " #" + target.number + " (HP: " + targetTemp + ") --> (HP: " + target.hp + ") -" + damage);
                Main.log.add("");
            }
        }
    }

    public void lifecheck(){
        // make sure they're still alive
        if(this.hp <= 0){
            // this is what will run if their hp gets to or below zero
            World.clearEntities(this.space.tagX, this.space.tagY);
            Main.log.add(this.name + " #" + this.number + " eliminated");
            Main.log.add("");
        }
    }

    public void setNumber(Participant particant){
        int number = random.nextInt(3840) + 1;
        boolean duplicate = false;

        // for every space in the world
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                
                // if the space is not null
                if(World.space != null){
                    // if there is an entity on it, and it's a "Participant"
                    if(World.space[i][j].entities.size() > 0 && World.space[i][j].entities.get(0).getClass().getSuperclass() == Participant.class){
                        Participant target = (Participant) World.space[i][j].entities.get(0);
                        if(number == target.number){
                            duplicate = true;
                            System.out.println("duplicate number found");
                        }
                    }
                }            
            }   
        }

        if(!duplicate){
            this.number = number;
            System.out.println("Found a unique ID number");
        }
        else {
            setNumber(this);
            System.out.println("fetching new number");
        }
    }
}
