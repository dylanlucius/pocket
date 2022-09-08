package pocket.system;

import java.io.*;

import org.newdawn.slick.SlickException;

import pocket.world.*;
import pocket.tile.*;
import pocket.creature.*;
import pocket.item.*;

public class MemoryCard {

    public static Ghost[][] ghostDeck = new Ghost[80][48];
    
    public static void setGhostDeck(){
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                ghostDeck[i][j] = new Ghost();
            }
        }
    }

    
    public static void save(){
        try{
            FileOutputStream file = new FileOutputStream("out/save.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);

            packGhostDeck();

            out.writeObject( ghostDeck );

            out.close();
            file.close();
          
            System.out.println("saved file");

        } catch (IOException e) {  e.printStackTrace();}

    }

    public static void packGhostDeck(){
        // for each slot in the ghost deck
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                    setGhost(ghostDeck[i][j], World.space[i][j]);
            }
        }
   }

    public static void setGhost(Ghost ghost, Space space){

        ghost.paused = Main.paused;

        ghost.tileIndex = checkTile(space);

        ghost.creatureIndex = checkCreature(space);
        switch(ghost.creatureIndex){
            default:
                packStats(ghost, space.creatures.get(0));
                break;

            case 99:
                break;
        }
        
        ghost.itemIndex = checkItem(space);
    }

    public static int checkTile(Space space){

        int index = 99;

        if(space.tile != null){

            switch(space.tile.name){
                default:
                    break;

                case "Grass":
                    index = 0;
                    break;

                case "Stone Ground":
                    index = 1;
                    break;
                
                case "Stone Wall":
                    index = 2;
                    break;

                case "Water":
                    index = 3;
                    break;

                case "Lava":
                    index = 4;
                    break;

                case "Medkit":
                    index = 5;
                    break;
            }

        }

        //System.out.println(index);

        return index;

    }

    public static int checkCreature(Space space){

        int index = 99;

        if(space.creatures.size() > 0  && space.creatures.get(0) != null){

            Creature creature = space.creatures.get(0);
            switch(creature.name){
                default:
                    break;

                case "Human Adult":
                    index = 0;
                    break;

                case "Man":
                    index = 1;
                    break;
                
                case "Woman":
                    index = 2;
                    break;

                case "Bug":
                    index = 3;
                    break;

                case "Rodent":
                    index = 4;
                    break;

                case "Dog":
                    index = 5;
                    break;

                case "Cat":
                index = 6;
                break;

                case "Lion":
                    index = 7;
                    break;
                
                case "Tiger":
                index = 8;
                break;

                case "Fish":
                    index = 9;
                    break;

                case "Shark":
                index = 10;
                break;

                case "Lava Shark":
                    index = 11;
                    break;

                case "Red":
                index = 12;
                break;

                case "Blue":
                    index = 13;
                    break;
            }

        }

        //System.out.println(index);

        return index;

    }

    public static void packStats(Ghost ghost, Creature creature){
        ghost.hp = creature.hp;
        ghost.hunger = creature.hunger;
        ghost.number = creature.number;
        ghost.nickname = creature.nickname;
        ghost.creatureCounterStep = creature.counter.step;
        ghost.creatureColorIndex = Screen.checkColor(creature.color);
    }

    public static int checkItem(Space space){

        int index = 99;

        if(space.items.size() > 0 && space.items.get(0) != null){

            switch(space.items.get(0).name){
                default:
                    break;

                case "Corpse":
                    index = 0;
                    break;

                case "Sword":
                    index = 1;
                    break;

                case "Shield":
                    index = 2;
                    break;
                
                
                }

        }

        System.out.println(index);
        return index;

    }


    public static void load(){
        try{

            FileInputStream file = new FileInputStream("out/save.txt");
            ObjectInputStream in = new ObjectInputStream(file);
            
            try{
                ghostDeck = (Ghost[][]) in.readObject();
            } catch (ClassNotFoundException e) { e.printStackTrace(); }
            
            in.close();
            file.close();

            try {
                Program.system.reinit();
            } catch (SlickException e ) { e.printStackTrace();}

            unpackGhostDeck();
            
            System.out.println( "loaded file" );

        } catch (IOException e) {  e.printStackTrace();}
    
    }
    
    public static void unpackGhostDeck(){
        // unpack ghostDeck
        // set each space in world from ghost deck slot
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                // set space
                setSpace(World.space[i][j], ghostDeck[i][j]);
            }
        }
    }

    public static void setSpace(Space space, Ghost ghost){
            
        Main.paused = ghost.paused;

        space.tile = returnTileByIndex(ghost.tileIndex);
        if(space.tile != null)
        space.tile.space = space;

        space.creatures.add(0, returnCreatureByIndex(ghost.creatureIndex) );
        
        if(space.creatures.get(0) == null){
            World.clearcreatures(space.tagX, space.tagY);
        }
        if(space.creatures.size() > 0 && space.creatures.get(0) != null){
            space.creatures.get(0).space = space;
            unpackStats(space.creatures.get(0), ghost);
        }

        space.items.add(0, returnItemByIndex( ghost.itemIndex) );
        if(space.items.get(0) == null){
            World.removeitem(space.tagX, space.tagY);
        }   
    }

    public static Tile returnTileByIndex(int index){
        Tile indexed;

        switch(index){
            default:
                indexed = null;
                break;
            
            case 0:
                indexed = new Grass();
                break;

            case 1:
                indexed = new StoneGround();
                break;

            case 2:
                indexed = new StoneWall();
                break;

            case 3:
                indexed = new Water();
                break;

            case 4:
                indexed = new Lava();
                break;

            case 5:
                indexed = new pocket.tile.Medkit();
                break;
        }

        return indexed;
    }

    public static Item returnItemByIndex(int index){
        Item indexed;

        switch(index){
            default:
                indexed = null;
                break;
            
            case 0:
                indexed = new Corpse();
                break;

            case 1:
                indexed = new Sword();
                break;

            case 2:
                indexed = new Shield();
                break;
        }

        return indexed;
    }

    public static Creature returnCreatureByIndex(int index){
        Creature indexed;

        //System.out.println(index);

        switch(index){
            default:
                indexed = null;
                break;
            
            case 0:
                indexed = new HumanAdult();
                break;

            case 1:
                indexed = new Man();
                break;

            case 2:
                indexed = new Woman();
                break;

            case 3:
                indexed = new Bug();
                break;

            case 4:
                indexed = new Rodent();
                break;

            case 5:
                indexed = new Dog();
                break;
            
            case 6:
            indexed = new Cat();
            break;

            case 7:
                indexed = new Lion();
                break;

            case 8:
                indexed = new Tiger();
                break;

            case 9:
                indexed = new Fish();
                break;

            case 10:
                indexed = new Shark();
                break;

            case 11:
                indexed = new LavaShark();
                break;

            case 12:
            indexed = new Red();
            break;

            case 13:
                indexed = new Blue();
                break;
        }

        //System.out.println(indexed);
        return indexed;
    }

    public static void unpackStats(Creature creature, Ghost ghost){
        creature.hp = ghost.hp;
        creature.hunger = ghost.hunger;
        creature.number = ghost.number;
        creature.nickname = ghost.nickname;
        creature.counter.step = ghost.creatureCounterStep;
        creature.color = Screen.color[ghost.creatureColorIndex];
    }

}
