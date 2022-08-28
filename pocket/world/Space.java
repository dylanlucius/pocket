package pocket.world;
import java.util.ArrayList;
import org.newdawn.slick.Image;

import pocket.creature.*;
import pocket.system.*;
import pocket.tile.*;

public class Space {
    Image image;
    public int tagX, tagY;
    boolean walk;
    public ArrayList<Entity> entities;
    public Tile tile;
    public boolean cursorOn, buildCursorOn;
    public Space placeholder, up, left, down, right, up8, left8, right8, down8;

    int x, y;

    public Space(int x, int y){
        entities = new ArrayList<Entity>();
        this.x = x;
        this.y = y;
    }

    public void draw(){
        
        //
        //  BELOW
        //

        //  TILE BG
        if(tile != null){
            Screen.bg.draw(x, y, tile.bgColor);
        }

        // ENTITY OR TILE ICON
        if (this.entities.size() != 0){
            this.entities.get(0).avatar.draw(this.x, this.y, this.entities.get(0).color);
        }
        else{
            if(tile != null){
                tile.icon.draw(x, y, tile.iconColor);
            }
        }

        //
        //  DRAW CURSOR
        //
        if(cursorOn){
           
            // BUILDER MODE [CURSOR w/ BOX]
            if(Main.builderMode){

                if(Main.selection){

                    // if cursor is up left of starting selection
                    if(World.cursor.space.tagY < World.selectionStart.tagY && World.cursor.space.tagX < World.selectionStart.tagX ){
                        for(int i = World.cursor.space.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.cursor.space.tagY; j < World.selectionStart.tagY + 1; j++){
                                placeholder = World.space[i][j];
                                World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);
                            }
                        }
                    }


                    // if up right
                    if(World.selectionStart.tagX < World.cursor.space.tagX  && World.selectionStart.tagY > World.cursor.space.tagY){
                        for(int i = World.selectionStart.tagX; i < World.cursor.space.tagX + 1; i++){
                            for(int j = World.cursor.space.tagY; j < World.selectionStart.tagY + 1; j++){
                                placeholder = World.space[i][j];
                                World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);
                            }
                        }
                    }

                    // if down left
                    if(World.cursor.space.tagY > World.selectionStart.tagY && World.cursor.space.tagX < World.selectionStart.tagX ){
                        for(int i = World.cursor.space.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.cursor.space.tagY + 1; j++){
                                placeholder = World.space[i][j];
                                World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);
                            }
                        }


                        
                    }

                    // down right
                    if(World.cursor.space.tagY > World.selectionStart.tagY && World.cursor.space.tagX > World.selectionStart.tagX ){
                        for(int i = World.selectionStart.tagX; i < World.cursor.space.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.cursor.space.tagY + 1; j++){
                                placeholder = World.space[i][j];
                                World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);                           }
                        }


                    }

                    // edge cases -- above, below, left, right, and center
                    if( !(World.selectionStart.tagX > World.cursor.space.tagX  && World.selectionStart.tagY > World.cursor.space.tagY) &&
                        !(World.selectionStart.tagX < World.cursor.space.tagX  && World.selectionStart.tagY < World.cursor.space.tagY) ){

                            // above
                            if(World.cursor.space.tagY < World.selectionStart.tagY){
                                    for(int j = World.cursor.space.tagY; j < World.selectionStart.tagY + 1; j++){
                                        placeholder = World.space[World.cursor.space.tagX][j];
                                        World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);
                                    }
                            }

                            // left
                            if(World.cursor.space.tagX < World.selectionStart.tagX){
                                for(int j = World.cursor.space.tagX; j < World.selectionStart.tagX + 1; j++){
                                    placeholder = World.space[j][World.cursor.space.tagY];
                                    World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);                                }
                            }


                            // right
                            if(World.cursor.space.tagX > World.selectionStart.tagX){
                                for(int j = World.selectionStart.tagX; j < World.cursor.space.tagX + 1; j++){
                                    placeholder = World.space[j][World.cursor.space.tagY];
                                    World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);                                }
                            }


                            // below
                            if(World.cursor.space.tagY > World.selectionStart.tagY){
                                for(int j = World.selectionStart.tagY; j < World.cursor.space.tagY + 1; j++){
                                    placeholder = World.space[World.cursor.space.tagX][j];
                                    World.cursor.icon.draw(placeholder.x, placeholder.y, Screen.DARK_BLUE);                                }
                            }
                        }
                }

                World.cursor.icon.draw(this.x, this.y, Screen.BLUE);

            }

            if(Main.creatureMode)
            World.cursor.icon.draw(this.x, this.y, Screen.RED);

            if(Main.cursorMode)
            World.cursor.icon.draw(this.x, this.y, Screen.YELLOW);
        }

        ///
        //  ABOVE
        //

        
    }

    public String checkNeighbors(){

        String up = "", left = "", right = "", down = "";

        if(this.up != null){
            up = "\nUP \u001b[32mtrue\u001b[37m";
        }
        else {
            up = "\nUP false";
        }

        if(this.left != null){
            left = "\nLEFT \u001b[32mtrue\u001b[37m";
        }
        else {
            left = "\nLEFT false";
        }


        if(this.right != null){
            right = "\nRIGHT \u001b[32mtrue\u001b[37m";
        }
        else {
            right = "\nRIGHT false";
        }


        if(this.down != null){
            down = "\nDOWN \u001b[32mtrue\u001b[37m";
        }
        else {
            down = "\nDOWN false";
        }


        return  up + left + right + down;
    }

    public String checkNeighbors8(){

        String up = "", left = "", right = "", down = "";

        if(this.up8 != null){
            up = "\nUP8 \u001b[32mtrue\u001b[37m";
        }
        else {
            up = "\nUP8 false";
        }

        if(this.left8 != null){
            left = "\nLEFT8 \u001b[32mtrue\u001b[37m";
        }
        else {
            left = "\nLEFT8 false";
        }


        if(this.right8 != null){
            right = "\nRIGHT8 \u001b[32mtrue\u001b[37m";
        }
        else {
            right = "\nRIGHT8 false";
        }


        if(this.down8 != null){
            down = "\nDOWN8 \u001b[32mtrue\u001b[37m";
        }
        else {
            down = "\nDOWN8 false";
        }


        return  up + left + right + down;
    }

    public void behavior(){
        if(entities != null){
            // for (Entity entity : entities){
            //     entity.behavior();
            // }

            for(int i = 0; i < entities.size(); i++){
                entities.get(i).behavior();
            }
        }
        
    }
}
