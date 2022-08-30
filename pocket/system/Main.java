package pocket.system;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;

import pocket.creature.*;
import pocket.world.*;

public class Main extends BasicGame{
    
    //////////////////////////////////////
    //           PROGRAM SETUP
    //////////////////////////////////////

    public Main(){
        super("Pocket World");
    }

        final boolean PLAY_INTRO = false;

        public static AppGameContainer system;
        Screen screen;
        static Input input; 
        public static World world;

        public static ScalableGame wrapper;

    public static void main(String[] args) throws SlickException {

        wrapper = new ScalableGame(new Main(), 640, 384, true);

        system = new AppGameContainer(wrapper);
        system.setDisplayMode(960, 576, false);
        system.setTargetFrameRate(8);
        system.setShowFPS(false);
        system.start();
        
    }

    
    //////////////////////////////////////
    //              INIT
    //////////////////////////////////////

        public static boolean colortest;
        public static boolean on;
        public static boolean startupPlayed, animationOver, characterTest, cursorMode, builderMode, selection, creatureMode;
        int titleColors, y, key;
        Sound startup;
        Cursor cursor;
        public boolean paused;

    @Override
    public void init(GameContainer gc) throws SlickException{
        
        restart = false;
        screen = new Screen();
        input = new Input(640);
        y = 0;
        colortest = false;
        key = 0;
        animationOver = false;
        paused = false;

        if(!PLAY_INTRO){
            on = true;
        }
        else {
            on = false;
        }

        world = new World();
        World.setNeighbors();
        World.setNeighbors8();
        cursor = World.cursor;
        startup = new Sound("res/startup.ogg");
        startupPlayed = false;
        menu = false;
        titleColors = 0;

        cursorMode = false;
        builderMode = false;
        selection = false;
        creatureMode = false;

    }


    //////////////////////////////////////
    //              UPDATE
    //////////////////////////////////////

    @Override
    public void update(GameContainer gc, int i) throws SlickException{
       
        //  PLAY ANIMATION
        if(!animationOver){
            y++;
            
            if(y > 21){
                animationOver = true;
            }
        }

        //  STARTUP SOUND
        if(PLAY_INTRO){
            if(!startupPlayed){
                startup.play();
                startupPlayed = true;
            }
        }  

        //  TITLE COLORS
        if(titleColors < 5){
            titleColors++;
        }
        else {
            titleColors = 0;
        }

        // restart flag (F5) response
        if(restart){
            system.reinit();
        }

        // if not paused
        if(!paused){
            // check for and run any behavior on all spaces
            for(int j = 0; j < World.space.length; j++){
                for(int k = 0; k < World.space[0].length; k++){
                    World.space[j][k].behavior();
                }
            }
        }   
    }

 
    //////////////////////////////////////
    //             RENDER
    //////////////////////////////////////

    @Override
    public void render(GameContainer gc, Graphics graphics) throws SlickException{

        // MENU BOX RULER    "12345678901234567890123456789012"
        // GAME BOX RULER    "123456789012345612345678901234561234567890123456"
        // FULL SCREEN RULER "12345678901234561234567890123456123456789012345612345678901234567890123456789012"

        //  CLEAR SCREEN
        Screen.screen.draw(0, 0, Screen.BLACK);

        //  INTRO ANIMATION
        if(PLAY_INTRO){

            if(!animationOver && !on){
                screen.font.drawString(8, y*8,   "                                 Software Toy", Screen.rainbow[titleColors]);
                screen.font.drawString(8, 8+y*8, "                                   presents", Screen.WHITE);
            }
    
            if(animationOver && !on){
                Screen.screen.draw(0, 0, Screen.DARK_BLUE);
                screen.font.drawString(8, 48,    "                                 Pocket World", Screen.rainbow[titleColors]);
                screen.font.drawString(8, 23*8,  "                             Press Enter to Start", Screen.WHITE);
            }    
        }
        else {
            animationOver = true;
        }
    
        //  PLAY STATE
        if(on && animationOver){

            Screen.screen.draw(0, 0, Screen.BLACK);

            screen.colortest();

            if(characterTest){
                Screen.tileset.draw(144, 8, Screen.WHITE);
            }

            // DRAW THE WORLD
            world.draw();
            
            if(paused){
                screen.font.drawString(0, 16,            "                                     Paused", Screen.YELLOW);
            }

             // FULL SCREEN RULER                        "12345678901234561234567890123456123456789012345612345678901234567890123456789012"
            if(builderMode){
                screen.font.drawString(0, 16,            "            Builder Mode", Screen.BLUE);
                
                screen.font.drawString(0, 16,            "                                                        " + World.currentTile.name, Screen.WHITE);

            }

            if(creatureMode){
                screen.font.drawString(0, 16,            "            Creature Mode", Screen.RED);
                
                screen.font.drawString(0, 16,            "                                                        " + World.currentEntity.name, Screen.WHITE);

            }
            
        }

    }

    
    ////////////////////////////////////
    //          GLOBAL INPUT
    //////////////////////////////////////

    boolean menu;
    boolean restart;

    public void keyPressed(int key, char c){
        
         // F5   [RESTART]
        if(key == Keyboard.KEY_F5){
            restart = true;
        }
        
        // TITLE SCREEN ENTER
        if(animationOver && !on && key == Keyboard.KEY_RETURN){
            on = true;
        }

        // PAUSE [SPACE]
        if(on && key == Keyboard.KEY_SPACE){
            paused = !paused;
        }

        // 1    [COLOR TEST]
        if(on && key == Keyboard.KEY_1){
            colortest = !colortest;
        }

        // 2    [CHARACTER TEST]
        if(on && key == Keyboard.KEY_2){
            characterTest = !characterTest;
        }

        // // TAB  [MENU]
        // if(on && key == Keyboard.KEY_TAB){
        //     menu = !menu;
        // }

        ///////////////////////////////////////////////
        //          CURSOR INPUT MODES
        ///////////////////////////////////////

        // K    [CURSOR]
        if(on && key == Keyboard.KEY_K){
            
            if(!builderMode && !creatureMode){
                if(World.cursor.space.cursorOn){
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.space[39][23]);
                }
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
    
                
                cursorMode = !cursorMode;
                System.out.println("\ncursor mode " + cursorMode);
            }
        }

        // B    [CURSOR]
        if(on && key == Keyboard.KEY_B){

            if(!cursorMode && !creatureMode){
                if(World.cursor.space.cursorOn){
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.space[39][23]);
                }
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
                selection = false;
                
                builderMode = !builderMode;

                System.out.println("\nbuilder mode " + builderMode);
            }  
        }

        // CYCLE TILES FORWARD in BUILDER MODE

        // [ + ]
        if(on && key == Keyboard.KEY_ADD && builderMode){
            if(World.currentTileIndex < World.tile.length - 1){
                World.currentTileIndex++;
                World.updateCurrentTile();
            }
            else {
                World.currentTileIndex = 0;
                World.updateCurrentTile();
            }
        }
        // [ - ]
        if(on && key == Keyboard.KEY_SUBTRACT && builderMode){
            if(World.currentTileIndex > 0){
                World.currentTileIndex--;
                World.updateCurrentTile();
            }
            else {
                World.currentTileIndex = (byte) (World.tile.length - 1);
                World.updateCurrentTile();
            }
        }

        // P    [CURSOR]
        if(on && key == Keyboard.KEY_P){

            if(!cursorMode && !builderMode){
                if(World.cursor.space.cursorOn){
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.space[39][23]);
                }
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
                selection = false;
    
                
                creatureMode = !creatureMode;

                System.out.println("\ncreature mode " + creatureMode);
            }  

        }

        // CYCLE CREATURES in CREATURE MODE

        // [ + ]
        if(on && key == Keyboard.KEY_ADD && creatureMode){
            if(World.currentEntityIndex < World.entity.length - 1){
                World.currentEntityIndex++;
                World.updateCurrentEntity();
            }
            else {
                World.currentEntityIndex = 0;
                World.updateCurrentEntity();
            }
        }
        // [ - ]
        if(on && key == Keyboard.KEY_SUBTRACT && creatureMode){
            if(World.currentEntityIndex > 0){
                World.currentEntityIndex--;
                World.updateCurrentEntity();
            }
            else {
                World.currentEntityIndex = (byte) (World.entity.length - 1);
                World.updateCurrentEntity();
            }
        }

        //
        //      CURSOR MODE CONTROLS
        //

        // UP [MOVE CURSOR UP]
        if(World.cursor.space.cursorOn && key == Keyboard.KEY_UP){
            
            if(World.cursor.space.up != null){
                //  IF SHIFT
                if(key == Keyboard.KEY_UP && input.isKeyDown(Input.KEY_LSHIFT) || key == Keyboard.KEY_UP && input.isKeyDown(Input.KEY_RSHIFT)){
                    if(World.cursor.space.up8 != null){
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.cursor.space.up8);
                    }
                    else {
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.space[World.cursor.space.tagX][0]);
                    }

                }
                //   NO SHIFT
                else {
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.cursor.space.up);

                }
            }    
        }

        // LEFT [MOVE CURSOR LEFT]
        if(World.cursor.space.cursorOn && key == Keyboard.KEY_LEFT){
            
            if(World.cursor.space.left != null){
                // IF SHIFT
                if(key == Keyboard.KEY_LEFT && input.isKeyDown(Input.KEY_LSHIFT) || key == Keyboard.KEY_LEFT && input.isKeyDown(Input.KEY_RSHIFT)){
                    if(World.cursor.space.left8 != null){
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.cursor.space.left8);
                    }
                    else {
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.space[0][World.cursor.space.tagY]);
                    }
                }
                //  NO SHIFT
                else{
                      
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.cursor.space.left);
                }
            }   
        }

        // RIGHT [MOVE CURSOR RIGHT]
        if(World.cursor.space.cursorOn && key == Keyboard.KEY_RIGHT){
            
            if(World.cursor.space.right != null){

                //  IF SHIFT
                if(key == Keyboard.KEY_RIGHT && input.isKeyDown(Input.KEY_LSHIFT) || key == Keyboard.KEY_RIGHT && input.isKeyDown(Input.KEY_RSHIFT)){
                    if(World.cursor.space.right8 != null){
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.cursor.space.right8);
                    }
                    else {
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.space[79][World.cursor.space.tagY]);
                    }
                }
                //  NO SHIFT
                else {

                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.cursor.space.right);
                    
                }
            }
        }

        // DOWN [MOVE CURSOR DOWN]
        if(World.cursor.space.cursorOn && key == Keyboard.KEY_DOWN){
            
            if(World.cursor.space.down != null){
                // IF SHIFT
                if(key == Keyboard.KEY_DOWN && input.isKeyDown(Input.KEY_LSHIFT) || key == Keyboard.KEY_DOWN && input.isKeyDown(Input.KEY_RSHIFT)){

                    if(World.cursor.space.down8 != null){
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.cursor.space.down8);
                    }
                    else {
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.space[World.cursor.space.tagX][47]);
                    }
                    
                }
                //  NO SHIFT
                else{

                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.cursor.space.down);

                }
            }  
        }
     
        //////////////////////////////////////
        //  ENTER [CURSOR MODES]
        ///////////////////////////////
        if(on && key == Keyboard.KEY_RETURN){
            
            // BUILDER MODE
            if(builderMode){
                
                // set world's "selection start" to cursor, set current world's "[current] selection" to true
                if(!selection){
                    World.selectionStart = World.cursor.space;
                    selection = true;
                }
                
                // set world's "selection end" to cursor, set current world's "[current] selection" to false
                else{
                    World.selectionEnd = World.cursor.space;
                    selection = false;
                }

                // place tiles from world selection start to world selection end
                if(World.cursor.space.tile == null && World.selectionEnd != null){
                    
                    // cursor ends to LEFT and ABOVE start select  --  from right to LEFT and bottom to TOP
                    if(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.placeTile(i, j, World.currentTile);
                            }
                        }
                    }

                    // cursor ends to RIGHT and ABOVE start select  --  from left to RIGHT and bottom to TOP
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.placeTile(i, j, World.currentTile);
                            }
                        }
                    }

                    // cursor ends to LEFT and BELOW of start select  --  print from RIGHT TO LEFT and TOP TO BOTTOM
                    if(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.placeTile(i, j, World.currentTile);
                            }
                        }
                    }

                    // cursor ends to RIGHT and BELOW of start select  --  print LEFT TO RIGHT and TOP TO BOTTOM
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.placeTile(i, j, World.currentTile);
                            }
                        }
                    }
                    
                    // edge cases -- above, below, left, right, and center
                    if( !(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY) &&
                        !(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY) ){

                            // center
                            if(World.selectionEnd == World.selectionStart){
                                World.placeTile(World.cursor.space.tagX, World.cursor.space.tagY, World.currentTile);
                            }

                            // above
                            if(World.selectionEnd.tagY < World.selectionStart.tagY){
                                    for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                        World.placeTile(World.selectionStart.tagX, j, World.currentTile);
                                    }
                            }

                            // left
                            if(World.selectionEnd.tagX < World.selectionStart.tagX){
                                for(int j = World.selectionEnd.tagX; j < World.selectionStart.tagX + 1; j++){
                                    World.placeTile(j, World.selectionStart.tagY, World.currentTile);
                                }
                            }


                            // right
                            if(World.selectionEnd.tagX > World.selectionStart.tagX){
                                for(int j = World.selectionStart.tagX; j < World.selectionEnd.tagX + 1; j++){
                                    World.placeTile(j, World.selectionStart.tagY, World.currentTile);
                                }
                            }


                            // below
                            if(World.selectionEnd.tagY > World.selectionStart.tagY){
                                for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                    World.placeTile(World.selectionStart.tagX, j, World.currentTile);
                                }
                            }
                        }

                    // reset the selectors
                    World.selectionStart = null;
                    World.selectionEnd = null;
                }
                
                //  clear tiles from world selection start to end
                else if(World.cursor.space.tile != null && World.selectionEnd != null){

                    // UP LEFT
                    if(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.clearTile(i, j);
                            }
                        }
                    }

                    // UP RIGHT
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.clearTile(i, j);
                            }
                        }
                    }

                    //  DOWN LEFT
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.clearTile(i, j);
                            }
                        }
                    }

                    //  DOWN RIGHT
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.clearTile(i, j);
                            }
                        }
                    }

                    // edge cases
                    if( !(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY) &&
                        !(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY) ){

                            // center
                            if(World.selectionEnd == World.selectionStart){
                                World.clearTile(World.cursor.space.tagX, World.cursor.space.tagY);
                            }

                            // above
                            if(World.selectionEnd.tagY < World.selectionStart.tagY){
                                    for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                        World.clearTile(World.selectionStart.tagX, j);
                                    }
                            }

                            // left
                            if(World.selectionEnd.tagX < World.selectionStart.tagX){
                                for(int j = World.selectionEnd.tagX; j < World.selectionStart.tagX + 1; j++){
                                    World.clearTile(j, World.selectionStart.tagY);
                                }
                            }


                            // right
                            if(World.selectionEnd.tagX > World.selectionStart.tagX){
                                for(int j = World.selectionStart.tagX; j < World.selectionEnd.tagX + 1; j++){
                                    World.clearTile(j, World.selectionStart.tagY);
                                }
                            }


                            // below
                            if(World.selectionEnd.tagY > World.selectionStart.tagY){
                                for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                    World.clearTile(World.selectionStart.tagX, j);
                                }
                            }
                        }
                    
                    //  reset selectors
                    World.selectionStart = null;
                    World.selectionEnd = null;
                }
            }
        
            // [ENTER] CREATURE MODE
            if(creatureMode){
                if(World.cursor.space.entities.size() <= 0){

                    switch(World.currentEntityIndex){
                        
                        default:
                            World.placeEntity(World.cursor.space.tagX, World.cursor.space.tagY, new Red() );
                            break;

                        case 1:
                        World.placeEntity(World.cursor.space.tagX, World.cursor.space.tagY, new Blue() );
                        break;

                        case 2:
                        World.placeEntity(World.cursor.space.tagX, World.cursor.space.tagY, new HumanAdult() );
                        break;

                        case 3:
                        World.placeEntity(World.cursor.space.tagX, World.cursor.space.tagY, new Woman() );
                        break;

                        case 4:
                        World.placeEntity(World.cursor.space.tagX, World.cursor.space.tagY, new Man() );
                        break;
                    }
                }
                
            }


        }

        

    }
}