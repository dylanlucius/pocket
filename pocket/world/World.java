package pocket.world;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import pocket.creature.*;
import pocket.item.*;
import pocket.system.*;
import pocket.tile.*;

public class World {

    public static Dice d2 = new Dice(2);
    public static Dice d4 = new Dice(4);
    public static Dice d6 = new Dice(6);
    public static Dice d8 = new Dice(8);
    public static Dice d10 = new Dice(10);
    public static Dice d12 = new Dice(12);
    public static Dice d20 = new Dice(20);
    public static Dice d100 = new Dice(100);
    public static Dice d1000 = new Dice(1000);

    int width, height;

    public static Space[][] space;
    public static Cursor cursor;
    public static Space worldCenter, selectionStart, selectionEnd;

    public static Grass grass = new Grass();
    public static StoneGround stoneGround = new StoneGround();
    public static StoneWall stoneWall = new StoneWall();
    public static Water water = new Water();
    public static Lava lava = new Lava();
    public static pocket.tile.Medkit medkit = new pocket.tile.Medkit();

    public static byte currentTileIndex = 0;
    public static Tile currentTile;
    public static Tile[] tile = {grass, stoneGround, stoneWall, water, lava, medkit};

    public static Player player = new Player();
    public static boolean isPlayer;

    public static Red red = new Red();
    public static Blue blue = new Blue();
    public static  HumanAdult humanAdult = new HumanAdult();
    public static Woman woman = new Woman();
    public static Man man = new Man();
    public static Fish fish = new Fish();
    public static Shark shark = new Shark();
    public static Tiger tiger = new Tiger();
    public static Lion lion = new Lion();
    public static Cat cat = new Cat();
    public static Dog dog = new Dog();
    public static Rodent rodent = new Rodent();
    public static Bug bug = new Bug();
    public static LavaShark lavashark = new LavaShark();

    public static byte currentCreatureIndex = 0;
    
    public static Creature[] creature = {player, humanAdult, man, woman, bug, rodent, dog, cat, lion, tiger, fish, shark,
                                     lavashark, red, blue};


    public static Creature currentCreature = creature[currentCreatureIndex];
    public static ArrayList<Creature> allCreatures;
    public static Creature datboi;
    public static int datboiIndex;
    public static boolean datboiChosen;

    //public static Creature playerOne;
    public static Space playerSpace;

    public static Sword sword = new Sword();
    public static Shield shield = new Shield();
    public static Corpse corpse = new Corpse();

    public static byte currentitemIndex = 0;

    public static Item[] item = {corpse, sword, shield};
    public static Item currentitem = item[currentitemIndex];

    public World(){

        space = new Space[80][48];
        
        // create world, World.space[][]
        System.out.println("Creating world");
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                space[i][j] = new Space(i * 8, j * 8);
                space[i][j].tagX = i;
                space[i][j].tagY = j;
            }   

        }

        // define world center
        worldCenter = World.space[39][23];

        // assign world center as default cursor location
        cursor = new Cursor(World.worldCenter);

        // set default current tile
        currentTile = tile[currentTileIndex];

        allCreatures = new ArrayList<Creature>();
        datboiIndex = 0;

    }

    // draw every space in the world when called
    public void draw(){
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                space[i][j].draw();
            }
        }
    }

    // return current creature based on currentCreatureIndex
    public static Creature returnCurrentCreature(){
        switch(currentCreatureIndex){
            default:
                return new Player();
            
            case 1:

                return new HumanAdult();

            case 2:
                return new Man();

            case 3:
                return new Woman();

            case 4:
                return new Bug();

            case 5:
                return new Rodent();

            case 6:
                return new Dog();

            case 7:
                return new Cat();

            case 8:
                return new Lion();

            case 9:
                return new Tiger();

            case 10:
                return new Fish();

            case 11:
                return new Shark();

            case 12:
                return new LavaShark();

            case 13:
                return new Red();

            case 14:
                return new Blue();

        }
    }

    // place stated creature at stated position, when called
    public static void placeCreature(int x, int y, Creature creature){
        
        if(creature.name == "Player"){
            if(!isPlayer){
                space[x][y].creatures.add(0, creature);
                playerSpace = creature.space;
                isPlayer = true;    
            }
            else {
                World.space[x][y].creatures.add(0, creature);
                World.clearcreatures(playerSpace.tagX, playerSpace.tagY);
                playerSpace = creature.space;
            }
        }
        else {
            space[x][y].creatures.add(0, creature); 
        }

        
    }

    // clear all creatures on given space
    public static void clearcreatures(int x, int y){
        while(World.space[x][y].creatures.size() > 0){
            World.space[x][y].creatures.remove(0);
            if(World.space[x][y].creatures.size() > 0 && World.space[x][y].creatures.get(0).name == "Player")
            World.isPlayer = false;
        }
    }

    public static void updateCursor(Space space){
        
        cursor = new Cursor(space);
        cursor.space.cursorOn = true;
        cursor.space = space;
        
    }

    public static void removeCursor(Space space){
        space.cursorOn = false;
    }

    // set each spaces neighbors (graph edges)
    public static void setNeighbors(){
        
        for(int i = 0; i < 80; i++){

            for(int j = 0; j < 48; j++){
            
                // UP
                if(j - 1 >= 0){
                    space[i][j].up = space[i][j - 1];
                }

                // LEFT
                if(i - 1 >= 0){
                    space[i][j].left = space[i - 1][j];
                }

                // RIGHT
                if(i + 1 <= space.length - 1){
                    space[i][j].right = space[i + 1][j];
                }

                // DOWN
                if(j + 1 <= space[0].length - 1){
                    space[i][j].down = space[i][j + 1];
                }

            }

        }  
                
        
        
    }

    // second pair of neighbors to check 8 spaces out
    public static void setNeighbors8(){
        
        for(int i = 0; i < 80; i++){

            for(int j = 0; j < 48; j++){
            
                // UP
                if(j - 8 >= 0){
                    space[i][j].up8 = space[i][j - 8];
                }

                // LEFT
                if(i - 8 >= 0){
                    space[i][j].left8 = space[i - 8][j];
                }

                // RIGHT
                if(i + 8 <= 79){
                    space[i][j].right8 = space[i + 8][j];
                }

                // DOWN
                if(j + 8 <= 47){
                    space[i][j].down8 = space[i][j + 8];
                }

            }

        }  
                
        
        
    }

    // returns current tile based on currentTileIndex, for use with placeTile()
    public static Tile returnCurrentTile(){
        switch(currentTileIndex){
            default:
                return new Grass();
            case 1:
                return new StoneGround();
            case 2:
                return new StoneWall();
            case 3:
                return new Water();
            case 4:
                return new Lava();
            case 5:
                return new pocket.tile.Medkit();
        }
    }

    // place given tile at given position
    public static void placeTile(int x, int y, Tile tile){
       
       if(World.space[x][y].tile == null){
            World.space[x][y].tile = tile;
            World.space[x][y].tile.space = World.space[x][y];
       }

    }

    // clear tile
    public static void clearTile(int x, int y){
        World.space[x][y].tile = null;
    }

    public static void updateCurrentTile(){
        currentTile = tile[currentTileIndex];
    }

    public static void updateCurrentCreature(){
        currentCreature = creature[currentCreatureIndex];
    }

    public static void drawPopulation(){
        // FULL SCREEN RULER                "00000000011111111112222222222333333333344444444445555555555666666666677777777778"
        // FULL SCREEN RULER                "12345678901234567890123456789012345678901234567890123456789012345678901234567890"

        Main.screen.font.drawString(0, 0,  "                                                        Population: " + allCreatures.size(), Screen.WHITE);
    }

    // place stated creature at stated position, when called
    public static void placeitem(int x, int y, Item item){
        World.space[x][y].items.add(item);
    }

    // place stated creature at stated position, when called
    public static void removeitem(int x, int y){
        World.space[x][y].items.remove(0);
    }

    public static void updateCurrentitem(){
        currentitem = item[currentitemIndex];
    }

    // returns current tile based on currentTileIndex, for use with placeTile()
    public static Item returnCurrentitem(){
        Item index;
        
        switch(currentitemIndex){
            default:
                index = null;
                break;

            case 0:
                index = new Corpse();
                break;

            case 1:
                index = new Sword();
                break;

            case 2:
                index = new Shield();
                break;
        }

        return index;
    }

    // return corpse
    public static Corpse returnCorpse(Creature creature){
        return new Corpse();
    }

    public static void drawdatboi(){
        Space space = World.space[(int) ( Mouse.getX() / 1.5) / 8][(int) ( (-Mouse.getY() + Program.SCALE_Y) / 1.5) / 8];

        if(datboiChosen){
            Main.screen.font.drawString(0, 16, " Name: " + datboi.nickname + " Type: " + datboi.name + " HP: " + datboi.hp + " Hunger: " + datboi.hunger, Screen.WHITE);    
        }
        else {
            if(space.creatures.size() > 0){
                datboi = space.creatures.get(0);
                Main.screen.font.drawString(0, 16, " Name: " + datboi.nickname + " Type: " + datboi.name + " HP: " + datboi.hp + " Hunger: " + datboi.hunger, Screen.WHITE);    
            }
        }


        
    }

}
