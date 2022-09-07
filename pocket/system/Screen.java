
package pocket.system;

import java.util.Random;
import org.newdawn.slick.*;

public class Screen {

    Random random = new Random();

    public static final Color BLACK        = Color.decode("#000000");
    public static final Color DARK_GRAY    = Color.decode("#5F574F");
    public static final Color GRAY         = Color.decode("#C2C3C7");
    public static final Color WHITE        = Color.decode("#FFF1E8");

    public static final Color PINK         = Color.decode("#FF77A8");
    public static final Color RED          = Color.decode("#FF004D");
    public static final Color ORANGE       = Color.decode("#FFA300");
    public static final Color YELLOW       = Color.decode("#FFEC27");
    
    public static final Color GREEN        = Color.decode("#00E436");
    public static final Color DARK_GREEN   = Color.decode("#008751");
    public static final Color BLUE         = Color.decode("#29ADFF");
    public static final Color DARK_BLUE    = Color.decode("#1D2B53");
    
    public static final Color PURPLE       = Color.decode("#83769C");
    public static final Color DARK_PURPLE  = Color.decode("#7E2553"); 
    public static final Color TAN          = Color.decode("#FFCCAA");
    public static final Color BROWN        = Color.decode("#AB5236");

    public static final Color[] color = {BLACK, DARK_GRAY, GRAY, WHITE, PINK, RED, ORANGE, YELLOW,
    GREEN, DARK_GREEN, BLUE, DARK_BLUE, PURPLE, DARK_PURPLE, TAN, BROWN};

    public static final Color[] rainbow = {RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE};

    public static final Color[] people = {PINK, YELLOW, TAN, BROWN, RED, BLUE, DARK_PURPLE};


    public static Image tileset, menu, bg, cbg, clear, screen;
    public static SpriteSheet spritesheet;
    public SpriteSheetFont font;
    public Graphics graphics;
    
    public Screen(){

        try{

            tileset = new Image("res/tileset.png", Color.magenta);
            menu = new Image("res/taskbar.png");
            bg = new Image("res/bg.png");
            clear = new Image("res/clear.png");
            screen = new Image("res/screen.png");
            tileset.setFilter(Image.FILTER_NEAREST);
            
            spritesheet = new SpriteSheet(tileset, 8, 8);
            font = new SpriteSheetFont(spritesheet, '\0');
            graphics = new Graphics();                     

        } catch (SlickException e) {    }
    }

    public static void clear(Color color){
        Screen.clear.draw(0, 0, color);
    }

    public void colortest(){
        if(Main.on && Main.colortest == true){
            for(int i = 0; i < 16; i++){
                for(int j = 0; j < 16; j++){
                        Screen.bg.draw(8 + 8*i, 8 + 8*j, Screen.color[j]);
                        Screen.spritesheet.getSprite(random.nextInt(16), random.nextInt(16)).draw(8 + 8*i, 8 + 8*j, Screen.color[i]);;
                }
            }
        }
    }
}
