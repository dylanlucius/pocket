package pocket.system;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Afterworld extends BasicGameState {

    public static final int ID = 2;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Screen.clear(Screen.DARK_PURPLE);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }

    @Override
    public int getID() {
        return Afterworld.ID;
    }

    public void keyPressed(int key, char c){
        if(key == Keyboard.KEY_F3){
            Program.stateManager.enterState(0);
        }
    }
    
    
}
