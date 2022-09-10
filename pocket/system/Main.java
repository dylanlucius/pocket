package pocket.system;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


import org.newdawn.slick.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.*;
import pocket.creature.*;
import pocket.item.*;
import pocket.world.*;

public class Main extends BasicGameState {
 
    public static final int ID  = 0;
    public static final boolean PLAY_INTRO    = true;

    public static boolean
    colortest, on, paused, startupPlayed, animationOver, characterTest,
    spectateMode, builderMode, itemMode, selection, creatureMode, logMode, restart;

    static int
    titleColors, menuPlacement;

    public static Screen screen;
    public static Log log;
    static Input input; 
    static World world;
    Counter animationCounter = new Counter(21);
    Sound startup;
    Cursor cursor;
    Creature currentCreature;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        

        // restart = false;
        screen = new Screen();
        input = new Input(640);
        colortest = false;
        animationOver = false;
        paused = false;
        log = new Log();

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
        titleColors = 0;

        spectateMode = false;
        builderMode = false;
        selection = false;
        creatureMode = false;
        itemMode = false;
        logMode = false;

    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException{
       
        //  PLAY ANIMATION
        if(!animationOver){
            if(animationCounter.over()){
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

        //  CYCLE COMPANY SPLASH COLORS
        if(titleColors < 5){
            titleColors++;
        }
        else {
            titleColors = 0;
        }

        // restart flag (F5) response
        // if(restart){
        //     system.reinit();
        // }

        // GAMEPLAY (if "on" and not paused)
        if(on && !paused){

            // BEHAVIOR check for and run any behavior on all spaces
            for(int j = 0; j < World.space.length; j++){
                for(int k = 0; k < World.space[0].length; k++){
                    World.space[j][k].behavior();
                }
            }

            // next game event ...
        }

        //  PLACEMENT OF SPECTATE MENU
        // if the cursor is on the right of the screen
        if(World.cursor.space.tagX >= 39){
            menuPlacement = 8;
        }
        // if it's on the left
        else {
            menuPlacement = 8 * 39;
        }

        if(builderMode || creatureMode || itemMode || spectateMode){
            int mouseX = (int) ( Mouse.getX() / 1.5) / 8,
                mouseY = (int) ( (-Mouse.getY() + Program.SCALE_Y) / 1.5) / 8;

            World.removeCursor(World.cursor.space);
            World.updateCursor(World.space[mouseX][mouseY]);

            //System.out.println("\nx: " + mouseX + ", y: " + mouseY);
        }
    }
 
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException{

        // MENU BOX RULER    "12345678901234567890123456789012"
        // GAME BOX RULER    "123456789012345612345678901234561234567890123456"
        // FULL SCREEN RULER "12345678901234561234567890123456123456789012345612345678901234567890123456789012"

        //  INITIAL SCREEN CLEAR
        Screen.screen.draw(0, 0, Screen.BLACK);

        //  INTRO ANIMATION
        if(PLAY_INTRO){

            if(!animationOver && !on){
                screen.font.drawString(8, animationCounter.step*8,   "                                 Software Toy", Screen.rainbow[titleColors]);
                screen.font.drawString(8, 8+animationCounter.step*8, "                                   presents", Screen.WHITE);
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

            // PERIODIC SCREEN CLEAR 
            Screen.screen.draw(0, 0, Screen.BLACK);

            // OPTIONAL COLOR TEST
            screen.colortest();

            // OPTIONAL CHARACTER TEST
            if(characterTest){
                Screen.tileset.draw(144, 8, Screen.WHITE);
            }

            // DRAW THE WORLD
            world.draw();
            
            // DRAW PAUSE INDICATOR
            if(paused){
                screen.font.drawString(0, 0,            "                                     Paused", Screen.YELLOW);
            }

            
            // DRAW BUILDER MODE
            if(builderMode){
                screen.font.drawString(0, 0,            "            Builder Mode", Screen.BLUE);
                
                screen.font.drawString(0, 0,            "                                                        " + World.currentTile.name, Screen.WHITE);

            }

            // DRAW CREATURE MODE
            if(creatureMode){
                screen.font.drawString(0, 0,            "            Creature Mode", Screen.RED);
                
                screen.font.drawString(0, 0,            "                                                        " + World.currentCreature.name, Screen.WHITE);

            }

            // DRAW SPECTATE MODE
            if(spectateMode){

                screen.font.drawString(0, 0,            "            Spectate Mode", Screen.YELLOW);
                
                // Creature target = World.cursor.space.creatures.get(0);
                // FULL SCREEN RULER                    "12345678901234561234567890123456123456789012345612345678901234567890123456789012"
                
                World.drawPopulation();
                World.drawdatboi();

                // if cursor space has creature OR item
                if(World.cursor.space.creatures.size() > 0){

                    // if(target != null){
                    //         // ENTITY INFO
                    //     // draw creature nickname
                    //     screen.font.drawString(menuPlacement, 16,    "Name: " + target.nickname, Screen.WHITE);

                    //     // draw creature name
                    //     screen.font.drawString(menuPlacement, 24,    "Type: " + target.name, Screen.WHITE);

                    //     // draw number
                    //     //screen.font.drawString(menuPlacement, 24,    "#: " + target.number, Screen.WHITE);

                    //     // draw HP
                    //     screen.font.drawString(menuPlacement, 32,   "HP: " + target.hp, Screen.WHITE);

                    //     // draw hunger
                    //     screen.font.drawString(menuPlacement, 40,   "Hunger: " + target.hunger, Screen.WHITE);

                    //     // draw foodchain
                    //     //screen.font.drawString(menuPlacement, 48,   "Foodchain #: " + target.foodchain, Screen.WHITE);

                    //     // draw items
                    //     if(target.items.size() > 0)
                    //     screen.font.drawString(menuPlacement, 48,   "Item: " + target.items.get(0).name, Screen.WHITE);

                    // }
                    
                }
                // IF THERE ARE NO creatures BUT THERE ARE ITEMS
                else if(World.cursor.space.items.size() > 0){
                    Item target = World.cursor.space.items.get(0);

                    // ITEM INFO
                    // draw item name
                    screen.font.drawString(menuPlacement, 16,    "Item: " + target.name, Screen.WHITE);
                }


            }              
        
            // DRAW item MODE
            if(itemMode){
                screen.font.drawString(0, 0,            "            item Mode", Screen.DARK_PURPLE);
                
                screen.font.drawString(0, 0,            "                                                        " + World.currentitem.name, Screen.WHITE);

            }      
            
            // DRAW LOG
            if(logMode){  
                log.draw();
            }
        }
    }

    @Override
    public int getID(){
        return ID;
    }

    public void keyPressed(int key, char c){

        if(Main.on &&  key == Keyboard.KEY_F3){
            Program.stateManager.enterState(2);
        }
        
        // RESTART   [F5]
        if(key == Keyboard.KEY_F5){
            restart = true;
        }
        
        // TITLE SCREEN [ENTER]
        if(Main.animationOver && !Main.on &&key == Keyboard.KEY_RETURN){
            Main.on = true;
        }

        // PAUSE [SPACE]
        if(Main.on && !Main.logMode &&key == Keyboard.KEY_SPACE){
            Main.paused = !Main.paused;
        }

        // STEP THROUGH
        if(Main.paused &&key == Keyboard.KEY_PERIOD){
            for(int j = 0; j < World.space.length; j++){
                for(int k = 0; k < World.space[0].length; k++){
                    World.space[j][k].behavior();
                }
            }
        }

        // COLOR TEST    [1]
        if(Main.on &&key == Keyboard.KEY_1){
            Main.colortest = !Main.colortest;
        }

        // CHARACTER TEST   [2]
        if(Main.on &&key == Keyboard.KEY_2){
            Main.characterTest = !Main.characterTest;
        }

        //[SAVE]  [F1]

        if(Main.on &&key == Keyboard.KEY_F1){
            MemoryCard.setGhostDeck();
            MemoryCard.save();
        }

        // LOAD  [F2]
        if(Main.on &&key == Keyboard.KEY_F2){
            MemoryCard.load();
        }
        
        // Main.log  MODE [TAB]
        if(Main.on &&key == Keyboard.KEY_TAB){

            if(Main.builderMode && World.cursor.space.cursorOn || Main.creatureMode && World.cursor.space.cursorOn || Main.itemMode && World.cursor.space.cursorOn || Main.spectateMode && World.cursor.space.cursorOn){
                World.cursor.space.cursorOn = Main.on;
            }
            else {
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
            }

            Main.builderMode = false;
            Main.creatureMode = false;
            Main.itemMode = false;
            Main.spectateMode = false;

            System.out.println("\nlog mode " + Main.spectateMode);

            Main.logMode = !Main.logMode;

            Main.paused = true;

            Main.log.currentLogIndex = 0;

        }

        // CYCLE Main.log

        // [ + ] Main.log
        if(Main.on &&key == Keyboard.KEY_ADD && Main.logMode){
            if(Main.log.currentLogIndex < Main.log.history.size() - 44){
                Main.log.currentLogIndex++;
            }
        }
        // [ - ] Main.log
        if(Main.on &&key == Keyboard.KEY_SUBTRACT && Main.logMode){
            if(Main.log.currentLogIndex > 0){
                Main.log.currentLogIndex--;
            }
        }


        ///////////////////////////////////////////////
        //          CURSOR INPUT MODES
        ///////////////////////////////////////

        // [K] SPECTATE MODE   
        if(Main.on &&key == Keyboard.KEY_K){

            if(Main.builderMode && World.cursor.space.cursorOn || Main.creatureMode && World.cursor.space.cursorOn || Main.itemMode && World.cursor.space.cursorOn || Main.logMode && World.cursor.space.cursorOn){
                World.cursor.space.cursorOn = Main.on;
            }
            else {
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
            }
            
            Main.builderMode = false;
            Main.creatureMode = false;
            Main.itemMode = false;
            Main.logMode = false;


                if(World.cursor.space.cursorOn){
                    if(World.datboi == null){
                        World.removeCursor(World.cursor.space);
                        World.updateCursor(World.space[39][23]);
                    }
                }

                Main.selection = false;

                Main.spectateMode = !Main.spectateMode;

                System.out.println("\nspectate mode " + Main.spectateMode);
        }

        // BUILDER MODE  [B]
        if(Main.on &&key == Keyboard.KEY_B){

            if(Main.creatureMode && World.cursor.space.cursorOn || Main.spectateMode && World.cursor.space.cursorOn || Main.itemMode && World.cursor.space.cursorOn || Main.logMode && World.cursor.space.cursorOn){
                World.cursor.space.cursorOn = Main.on;
            }
            else {
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
            }

            Main.spectateMode = false;
            Main.creatureMode = false;
            Main.itemMode = false;
            Main.logMode = false;


                if(World.cursor.space.cursorOn){
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.space[39][23]);
                }

                Main.selection = false;
                
                Main.builderMode = !Main.builderMode;

                System.out.println("\nbuilder mode " + Main.builderMode);
        }

        // CYCLE TILES in BUILDER MODE

        // [ + ] Builder
        if(Main.on &&key == Keyboard.KEY_ADD && Main.builderMode){
            if(World.currentTileIndex < World.tile.length - 1){
                World.currentTileIndex++;
                World.updateCurrentTile();
            }
            else {
                World.currentTileIndex = 0;
                World.updateCurrentTile();
            }
        }
        // [ - ] Builder
        if(Main.on &&key == Keyboard.KEY_SUBTRACT && Main.builderMode){
            if(World.currentTileIndex > 0){
                World.currentTileIndex--;
                World.updateCurrentTile();
            }
            else {
                World.currentTileIndex = (byte) (World.tile.length - 1);
                World.updateCurrentTile();
            }
        }

        // CREATURE MODE  [C]
        if(Main.on &&key == Keyboard.KEY_C){


            if(Main.builderMode && World.cursor.space.cursorOn || Main.spectateMode && World.cursor.space.cursorOn || Main.itemMode && World.cursor.space.cursorOn || Main.logMode && World.cursor.space.cursorOn){
                World.cursor.space.cursorOn = Main.on;
            }
            else {
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
            }

            Main.spectateMode = false;
            Main.builderMode = false;
            Main.itemMode = false;
            Main.logMode = false;


                if(World.cursor.space.cursorOn){
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.space[39][23]);
                }
                Main.selection = false;

                
                Main.creatureMode = !Main.creatureMode;

                System.out.println("\ncreature mode " + Main.creatureMode);

        }

        // CYCLE CREATURES in CREATURE MODE

        // [ + ] Creature
        if(Main.on && key == Keyboard.KEY_ADD && Main.creatureMode){
            if(World.currentCreatureIndex < World.creature.length - 1){
                World.currentCreatureIndex++;
                World.updateCurrentCreature();
            }
            else {
                World.currentCreatureIndex = 0;
                World.updateCurrentCreature();
            }
        }
        // [ - ] Creature
        if(Main.on && key == Keyboard.KEY_SUBTRACT && Main.creatureMode){
            if(World.currentCreatureIndex > 0){
                World.currentCreatureIndex--;
                World.updateCurrentCreature();
            }
            else {
                World.currentCreatureIndex = (byte) (World.creature.length - 1);
                World.updateCurrentCreature();
            }
        }

        // item MODE  [P]
        if(Main.on && key == Keyboard.KEY_P){

            if(Main.creatureMode && World.cursor.space.cursorOn || Main.spectateMode && World.cursor.space.cursorOn || Main.builderMode && World.cursor.space.cursorOn || Main.logMode && World.cursor.space.cursorOn){
                World.cursor.space.cursorOn = Main.on;
            }
            else {
                World.cursor.space.cursorOn = !World.cursor.space.cursorOn;
            }

            Main.spectateMode = false;
            Main.creatureMode = false;
            Main.builderMode = false;
            Main.logMode = false;


            //if(!Main.spectateMode && !Main.creatureMode){
                if(World.cursor.space.cursorOn){
                    World.removeCursor(World.cursor.space);
                    World.updateCursor(World.space[39][23]);
                }

                Main.selection = false;
                
                Main.itemMode = !Main.itemMode;

                System.out.println("\nitem mode " + Main.itemMode);
            //}  
        }

        // CYCLE TILES in ITEM MODE

        // [ + ] Builder
        if(Main.on && key == Keyboard.KEY_ADD && Main.itemMode){
            if(World.currentitemIndex < World.item.length - 1){
                World.currentitemIndex++;
                World.updateCurrentitem();
            }
            else {
                World.currentitemIndex = 0;
                World.updateCurrentitem();
            }
        }
        // [ - ] Builder
        if(Main.on && key == Keyboard.KEY_SUBTRACT && Main.itemMode){
            if(World.currentitemIndex > 0){
                World.currentitemIndex--;
                World.updateCurrentitem();
            }
            else {
                World.currentitemIndex = (byte) (World.item.length - 1);
                World.updateCurrentitem();
            }
        }

        // CYCLE Dat Boi in Spectate mode

        // [ + ] Spectate
        if(Main.on && key == Keyboard.KEY_ADD && Main.spectateMode){
            if(World.datboiIndex < World.allCreatures.size() - 1){
                World.datboiIndex++;
            }
            else {
                World.datboiIndex = 0;
            }
        }
        // [ - ] Spectate
        if(Main.on &&key == Keyboard.KEY_SUBTRACT && Main.spectateMode){
            if(World.datboiIndex > 0){
                World.datboiIndex--;
            }
            else {
                World.datboiIndex = (byte) (World.allCreatures.size() - 1);
            }
        }

        //
        //  UNIVERSAL CURSOR MODE CONTROLS
        //

        // UP [MOVE CURSOR UP]
        if(World.cursor.space.cursorOn &&key == Keyboard.KEY_UP){
            
            if(World.cursor.space.up != null){
                //  IF SHIFT
                if(key == Keyboard.KEY_UP && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_LSHIFT) ||key == Keyboard.KEY_UP && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_RSHIFT)){
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
        if(World.cursor.space.cursorOn &&key == Keyboard.KEY_LEFT){
            
            if(World.cursor.space.left != null){
                // IF SHIFT
                if(key == Keyboard.KEY_LEFT && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_LSHIFT) ||key == Keyboard.KEY_LEFT && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_RSHIFT)){
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
        if(World.cursor.space.cursorOn &&key == Keyboard.KEY_RIGHT){
            
            if(World.cursor.space.right != null){

                //  IF SHIFT
                if(key == Keyboard.KEY_RIGHT && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_LSHIFT) ||key == Keyboard.KEY_RIGHT && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_RSHIFT)){
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
        if(World.cursor.space.cursorOn &&key == Keyboard.KEY_DOWN){
            
            if(World.cursor.space.down != null){
                // IF SHIFT
                if(key == Keyboard.KEY_DOWN && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_LSHIFT) ||key == Keyboard.KEY_DOWN && Main.input.isKeyDown(org.newdawn.slick.Input.KEY_RSHIFT)){

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
        if(Main.on &&key == Keyboard.KEY_RETURN){
            
            // [ENTER] BUILDER MODE
            if(builderMode){
                
                // set world's "Main.selection start" to cursor, set current world's "[current] Main.selection" to true
                if(!Main.selection){
                    World.selectionStart = World.cursor.space;
                    Main.selection = true;
                }
                
                // set world's "Main.selection end" to cursor, set current world's "[current] Main.selection" to false
                else{
                    World.selectionEnd = World.cursor.space;
                    Main.selection = false;
                }

                // place tiles from world Main.selection start to world Main.selection end
                if(World.cursor.space.tile == null && World.selectionEnd != null){
                    
                    // cursor ends to LEFT and ABOVE start select  --  from right to LEFT and bottom to TOP
                    if(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }

                    // cursor ends to RIGHT and ABOVE start select  --  from left to RIGHT and bottom to TOP
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }

                    // cursor ends to LEFT and BELOW of start select  --  print from RIGHT TO LEFT and TOP TO BOTTOM
                    if(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }

                    // cursor ends to RIGHT and BELOW of start select  --  print LEFT TO RIGHT and TOP TO BOTTOM
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }
                    
                    // edge cases -- above, below, left, right, and center
                    if( !(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY) &&
                        !(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY) ){

                            // center
                            if(World.selectionEnd == World.selectionStart){
                                World.placeTile(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentTile());
                            }

                            // above
                            if(World.selectionEnd.tagY < World.selectionStart.tagY){
                                    for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                        World.placeTile(World.selectionStart.tagX, j, World.returnCurrentTile());
                                    }
                            }

                            // left
                            if(World.selectionEnd.tagX < World.selectionStart.tagX){
                                for(int j = World.selectionEnd.tagX; j < World.selectionStart.tagX + 1; j++){
                                    World.placeTile(j, World.selectionStart.tagY, World.returnCurrentTile());
                                }
                            }


                            // right
                            if(World.selectionEnd.tagX > World.selectionStart.tagX){
                                for(int j = World.selectionStart.tagX; j < World.selectionEnd.tagX + 1; j++){
                                    World.placeTile(j, World.selectionStart.tagY, World.returnCurrentTile());
                                }
                            }


                            // below
                            if(World.selectionEnd.tagY > World.selectionStart.tagY){
                                for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                    World.placeTile(World.selectionStart.tagX, j, World.returnCurrentTile());
                                }
                            }
                        }

                    // reset the selectors
                    World.selectionStart = null;
                    World.selectionEnd = null;
                }
                
                //  clear tiles from world Main.selection start to end
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
                
                World.placeCreature(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentCreature());
                
                // if(World.cursor.space.creatures.size() <= 0){
                
                //     World.placeCreature(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentCreature());
                // } 
                // else {
                //     World.clearcreatures(World.cursor.space.tagX, World.cursor.space.tagY);
                // }
            }
            
            // [ENTER] item MODE
            if(itemMode){
                if(World.cursor.space.items.size() <= 0){
                
                    World.placeitem(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentitem());
                } 
                else {
                    World.removeitem(World.cursor.space.tagX, World.cursor.space.tagY);
                }
            }
        
            if(spectateMode){
                if(World.space[(int) ( Mouse.getX() / 1.5) / 8][(int) ( (-Mouse.getY() + Program.SCALE_Y) / 1.5) / 8].creatures.size() > 0){
                    World.datboi = World.space[(int) ( Mouse.getX() / 1.5) / 8][(int) ( (-Mouse.getY() + Program.SCALE_Y) / 1.5) / 8].creatures.get(0);
                    World.datboi.isdatboi = true;
                    World.datboiChosen = true;
    
                }
            }
        }

        if(spectateMode && Main.on &&key == Keyboard.KEY_ESCAPE){
            World.datboi.isdatboi = false;
            World.datboiChosen = false;
        }
    
    }

    public void mousePressed(int button, int x, int y){
        
        // LEFT MOUSE

        if(Mouse.isButtonDown(0)){
            // System.out.println("left mouse clicked "  + (int) (Mouse.getX() / 1.5) + ", " + (int) ((-Mouse.getY() + Program.SCALE_Y) / 1.5) );
            System.out.println("\nleft mouse clicked at space "  + ((int) (Mouse.getX() / 1.5)) / 8 + ", " + ((int) ((-Mouse.getY() + Program.SCALE_Y) / 1.5)) / 8 );
        
            // IN BUILD MODE
            if(builderMode){
                // set world's "Main.selection start" to cursor, set current world's "[current] Main.selection" to true
                if(!Main.selection){
                    World.selectionStart = World.cursor.space;
                    Main.selection = true;
                }
                
                // set world's "Main.selection end" to cursor, set current world's "[current] Main.selection" to false
                else{
                    World.selectionEnd = World.cursor.space;
                    Main.selection = false;
                }

                if(World.cursor.space.tile == null && World.selectionEnd != null){
                    
                    // cursor ends to LEFT and ABOVE start select  --  from right to LEFT and bottom to TOP
                    if(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }
    
                    // cursor ends to RIGHT and ABOVE start select  --  from left to RIGHT and bottom to TOP
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }
    
                    // cursor ends to LEFT and BELOW of start select  --  print from RIGHT TO LEFT and TOP TO BOTTOM
                    if(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionEnd.tagX; i < World.selectionStart.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }
    
                    // cursor ends to RIGHT and BELOW of start select  --  print LEFT TO RIGHT and TOP TO BOTTOM
                    if(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY){
                        for(int i = World.selectionStart.tagX; i < World.selectionEnd.tagX + 1; i++){
                            for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                World.placeTile(i, j, World.returnCurrentTile());
                            }
                        }
                    }
                    
                    // edge cases -- above, below, left, right, and center
                    if( !(World.selectionStart.tagX > World.selectionEnd.tagX  && World.selectionStart.tagY > World.selectionEnd.tagY) &&
                        !(World.selectionStart.tagX < World.selectionEnd.tagX  && World.selectionStart.tagY < World.selectionEnd.tagY) ){
    
                            // center
                            if(World.selectionEnd == World.selectionStart){
                                World.placeTile(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentTile());
                            }
    
                            // above
                            if(World.selectionEnd.tagY < World.selectionStart.tagY){
                                    for(int j = World.selectionEnd.tagY; j < World.selectionStart.tagY + 1; j++){
                                        World.placeTile(World.selectionStart.tagX, j, World.returnCurrentTile());
                                    }
                            }
    
                            // left
                            if(World.selectionEnd.tagX < World.selectionStart.tagX){
                                for(int j = World.selectionEnd.tagX; j < World.selectionStart.tagX + 1; j++){
                                    World.placeTile(j, World.selectionStart.tagY, World.returnCurrentTile());
                                }
                            }
    
    
                            // right
                            if(World.selectionEnd.tagX > World.selectionStart.tagX){
                                for(int j = World.selectionStart.tagX; j < World.selectionEnd.tagX + 1; j++){
                                    World.placeTile(j, World.selectionStart.tagY, World.returnCurrentTile());
                                }
                            }
    
    
                            // below
                            if(World.selectionEnd.tagY > World.selectionStart.tagY){
                                for(int j = World.selectionStart.tagY; j < World.selectionEnd.tagY + 1; j++){
                                    World.placeTile(World.selectionStart.tagX, j, World.returnCurrentTile());
                                }
                            }
                        }
    
                    // reset the selectors
                    World.selectionStart = null;
                    World.selectionEnd = null;
                }
                
                //  clear tiles from world Main.selection start to end
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
    
            if(Main.creatureMode){
                
                World.placeCreature(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentCreature());
                
                // if(World.cursor.space.creatures.size() <= 0){
                
                //     World.placeCreature(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentCreature());
                // } 
                // else {
                //     World.clearcreatures(World.cursor.space.tagX, World.cursor.space.tagY);
                // }
            }
            
            // item MODE
            if(Main.itemMode){
                if(World.cursor.space.items.size() <= 0){
                
                    World.placeitem(World.cursor.space.tagX, World.cursor.space.tagY, World.returnCurrentitem());
                } 
                else {
                    World.removeitem(World.cursor.space.tagX, World.cursor.space.tagY);
                }
            }
        
            if(spectateMode){
                if(World.space[(int) ( Mouse.getX() / 1.5) / 8][(int) ( (-Mouse.getY() + Program.SCALE_Y) / 1.5) / 8].creatures.size() > 0){
                    World.datboi = World.space[(int) ( Mouse.getX() / 1.5) / 8][(int) ( (-Mouse.getY() + Program.SCALE_Y) / 1.5) / 8].creatures.get(0);
                    World.datboi.isdatboi = true;
                    World.datboiChosen = true;
    
                }
            }
        }

        // RIGHT MOUSE
        if(Mouse.isButtonDown(1)){
            // System.out.println("right mouse clicked " + (int) (Mouse.getX() / 1.5) + ", " + (int) ((-Mouse.getY() + Program.SCALE_Y) / 1.5) );
            System.out.println("\nright mouse clicked at space "  + ((int) (Mouse.getX() / 1.5)) / 8 + ", " + ((int) ((-Mouse.getY() + Program.SCALE_Y) / 1.5)) / 8 );
        }
    }
}