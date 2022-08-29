package pocket.world;

import pocket.creature.*;
import pocket.system.*;
import pocket.tile.*;

public class World {
    int width, height;
    // depth, volume
    public static Space[][] space;

    public static Cursor cursor;

    public static Space worldCenter, selectionStart, selectionEnd;

    public static Grass grass = new Grass();
    public static StoneGround stoneGround = new StoneGround();
    public static StoneWall stoneWall = new StoneWall();
    public static Water water = new Water();
    public static Lava lava = new Lava();


    public static byte currentTileIndex = 0;
    public static Tile currentTile;
    public static Tile[] tile = {grass, stoneGround, stoneWall, water, lava};

    public static  HumanAdult humanAdult = new HumanAdult();
    public static Man man = new Man();
    public static Woman woman = new Woman();

    public static byte currentEntityIndex = 0;
    public static Entity[] entity = {humanAdult, man, woman};
    public static Entity currentEntity = entity[currentEntityIndex];

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

    }

    // draw every space in the world when called
    public void draw(){
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                space[i][j].draw();
            }
        }
    }

    // place stated entity at stated position, when called
    public static void placeEntity(int x, int y, Entity entity){
        World.space[x][y].entities.add(entity);
    }

    // clear all entities on given space
    public static void clearEntities(int x, int y){
        while(World.space[x][y].entities.size() > 0){
            World.space[x][y].entities.remove(0);
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

    // place given tile at given position
    public static void placeTile(int x, int y, Tile tile){
       
       if(World.space[x][y].tile == null){
            World.space[x][y].tile = tile;
       }

    }

    // clear tile
    public static void clearTile(int x, int y){
        World.space[x][y].tile = null;
    }

    public static void updateCurrentTile(){
        currentTile = tile[currentTileIndex];
    }

    public static void updateCurrentEntity(){
        currentEntity = entity[currentEntityIndex];
    }
}
