package pocket.creature;

import java.util.*;
import pocket.system.*;
import pocket.world.*;

public abstract class Participant extends Human {

    Random random = new Random();

    // combat variables
    public int hp, ac = 980, damage;

    public boolean attacking;

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
            // if entity is not on the same team
            if(this.space.up.entities.get(0).team != this.team){
                attacking = true;
                roll( (Participant) this.space.up.entities.get(0));
            }
        }
        else {
            attacking = false;
        }

        // [LEFT] if the space above them has an entity on it
        if(this.space.left.entities.size() > 0){
            // if entity is not on the same team
            if(this.space.left.entities.get(0).team != this.team){
                attacking = true;
                roll( (Participant) this.space.left.entities.get(0));
            }
        }
        else {
            attacking = false;
        }

        // [RIGHT] if the space above them has an entity on it
        if(this.space.right.entities.size() > 0){
            // if entity is not on the same team
            if(this.space.right.entities.get(0).team != this.team){
                attacking = true;
                roll( (Participant) this.space.right.entities.get(0));
            }
        }
        else {
            attacking = false;
        }

        // [DOWN] if the space above them has an entity on it
        if(this.space.down.entities.size() > 0){
            // if entity is not on the same team
            if(this.space.down.entities.get(0).team != this.team){
                attacking = true;
                roll( (Participant) this.space.down.entities.get(0));
            }
        }
        else {
            attacking = false;
        }
        
              
    }

    public void roll(Participant target){


        // if random roll of "d20" is equal to or larger than enemy AC
        if( World.d1000.roll(1) >= target.ac){
            damage = World.d6.roll(3);
            System.out.println("\n" + this.name + " (HP: " + this.hp + ") did " + damage + " damage to " + target.name + " (HP: " + target.hp + ")");
            target.hp -=  damage;
            System.out.println(target.name + " remaining HP: " + target.hp);

        }

    }

    public void lifecheck(){
        // make sure they're still alive
        if(this.hp <= 0){
            // this is what will run if their hp gets to or below zero
            World.clearEntities(this.space.tagX, this.space.tagY);
            System.out.println("\n" + this.name + " eliminated");
        }
    }
}
