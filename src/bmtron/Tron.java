// By: Akshay Pathak & Jason Dias
package bmtron;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import java.awt.*;

public class Tron extends StateBasedGame{

    public static final String NAME = "TRON L E G A C Y: THE GAME";
    public static int WIDTH;
    public static int HEIGHT;
    
    /*
        ***NOTE: IT IS HIGHLY RECOMMENDED THAT THIS GAME IS RUN ON A 1920 X 1080 RESOLUTION MONITOR***
        IF RUNNING ON 1920 X 1080 RESOLUTION MONITOR, LEAVE SCALE AT 1.0
        IF NOT RUNNING ON 1920 X 1080 RESOLUTION MONITOR, THE SCALE IS CALCULATED BY:
        
        WIDTH OF YOUR MONITOR / 1920
    
        FOR EXAMPLE (IF YOUR MONITOR WIDTH IS 1440):
        1440 / 1920 = 0.75
        SCALE SHOULD BE SET TO 0.75
    */
    public static final float SCALE = 1.0f;
    
    
    public static final int MAINMENUSTATE = 0;
    public static final int GAMESTATE = 1;
    public static final int OPTIONSSTATE = 2;
    public static final int ABOUTSTATE = 3;
    public static final int GAMEFINISHSTATE = 4;
    public static final int GAMEENDSTATE = 5;
    
    public Tron() {
        super(NAME);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new MainMenu(MAINMENUSTATE));
        this.addState(new Game(GAMESTATE));
        this.addState(new Options(OPTIONSSTATE));
        this.addState(new About(ABOUTSTATE));
        this.addState(new Finish(GAMEFINISHSTATE));
        this.addState(new GameEnd(GAMEENDSTATE));
    }
    
    public static void main(String[] args) throws SlickException{
        AppGameContainer app = new AppGameContainer(new Tron());
        
        // To optimize the game for the screen resolution the system is running at.
        Toolkit toolkit = Toolkit.getDefaultToolkit();    
        Dimension dim = toolkit.getScreenSize();
        WIDTH = dim.width;
        HEIGHT = dim.height;
        
        app.setDisplayMode(WIDTH, HEIGHT, true);
        app.setTargetFrameRate(60);
        app.setShowFPS(false);
        app.start();
    }
}