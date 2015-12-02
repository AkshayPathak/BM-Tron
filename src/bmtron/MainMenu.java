package bmtron;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

class MainMenu extends BasicGameState {
 
    int stateID = -1;
    
    int w = Tron.WIDTH;
    int h = Tron.HEIGHT;
    float s  = Tron.SCALE;
    
    Image menuBackground = null;
    Image start = null;
    Image options = null;
    Image about = null;
    Image exit = null;
    
    public MainMenu(int stateID) {
        this.stateID = stateID;
    }
    
    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        menuBackground = new Image("/resources/background/menubackground.png");
        start = new Image("/resources/button/start.png");
        options = new Image("/resources/button/options_cs.png");
        about = new Image("/resources/button/about.png");
        exit = new Image("/resources/button/exit.png");
    }
  
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        menuBackground.draw(0, 0, w, h);
        start.draw(142*s, 381*s, s);
        options.draw(142*s, (start.getHeight() + 430)*s, s);
        about.draw(1361*s,381*s, s);
        exit.draw(1361*s, (about.getHeight() + 430)*s, s);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();
        
        int mX = input.getMouseX();
        int mY = input.getMouseY();
        
        // Checking mouse hover for start button
        if (Hover(mX,mY,142*s,381*s,start.getWidth()*s,start.getHeight()*s) == true) {
            start = new Image("/resources/button/start_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                sbg.enterState(1, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
        } else {
            start = new Image("/resources/button/start.png");
        }
        
        // Checking mouse hover for about button
        if (Hover(mX,mY,1361*s,381*s,about.getWidth()*s,about.getHeight()*s) == true) {
            about = new Image("/resources/button/about_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                sbg.enterState(3, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
        } else {
            about = new Image("/resources/button/about.png");
        }
        
        // Checking mouse hover for exit button
        if (Hover(mX,mY,1361*s,(about.getHeight() + 430)*s,exit.getWidth()*s,exit.getHeight()*s) == true) {
            exit = new Image("/resources/button/exit_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                System.exit(0);
            } 
        } else {
            exit = new Image("/resources/button/exit.png");
        }
        
        // Escape button to exit the game instantly
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            System.exit(0);
        }
        
        input.clearControlPressedRecord();
        input.clearKeyPressedRecord();
        input.clearMousePressedRecord();
    }
    
    public boolean Hover(int mX, int mY, float x, float y, float width, float height) {
        return (mX >= x) && mX <= x + width && mY >= y && mY <= y + height;
    }
}