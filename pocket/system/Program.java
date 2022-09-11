package pocket.system;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

    public class Program extends StateBasedGame {

    public static final int INTRO = 1, MAIN = 0, AFTERWORLD = 2;
    
    public Program() {
        super("Pocket World");
    }

    public void initStatesList(GameContainer gc){
        addState(new Main());
        addState(new Afterworld());
    }

    public static ScalableGame wrapper;
    public static AppGameContainer system;
    public static StateBasedGame stateManager;

    public static final int SCALE_Y = 576;

    public static void main(String[] args) throws SlickException {

        try {
            stateManager = new Program();

            wrapper = new ScalableGame(stateManager, 640, 384, true);

            system = new AppGameContainer(wrapper);
            system.setDisplayMode(960, SCALE_Y, false);
            system.setTargetFrameRate(16);
            system.setShowFPS(false);
            system.start();

        } catch (SlickException e) {e.printStackTrace();}
        
    }

}

