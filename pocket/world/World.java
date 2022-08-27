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

    public World(){
        space = new Space[80][48];
        
        System.out.println("Creating world");
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                space[i][j] = new Space(i * 8, j * 8);
                space[i][j].tagX = i;
                space[i][j].tagY = j;

                //System.out.println("Creating space: x = " + i + ", y = " + j );
            }   

        }

        worldCenter = World.space[39][23];

        System.out.println("Adding cursor");
        cursor = new Cursor(World.worldCenter);   // World center

    }

    public void draw(){
        for(int i = 0; i < 80; i++){
            for(int j = 0; j < 48; j++){
                space[i][j].draw();
            }
        }
    }

    public static void placeEntity(int x, int y, Entity entity){
        World.space[x][y].entities.add(entity);
    }

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

    public static void placeTile(int x, int y, Tile tile){
       
       if(World.space[x][y].tile == null){
            World.space[x][y].tile = tile;
       }

    }

    public static void clearTile(int x, int y){
        World.space[x][y].tile = null;
    }
}
