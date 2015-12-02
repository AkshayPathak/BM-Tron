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

class Finish extends BasicGameState {
    
    int stateID = -1;
    
    // Getting the width, height and the scale from Tron.java
    int w = Tron.WIDTH;
    int h = Tron.HEIGHT;
    float s  = Tron.SCALE;
    
    // Images used in this state
    static Image p1WinImage = null;
    static Image p2WinImage = null;
    static Image tieImage = null;
    static Image mainMenu = null;
    static Image exit = null;
    
    public Finish(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        p1WinImage = new Image("/resources/background/finishp1.png");
        p2WinImage = new Image("/resources/background/finishp2.png");
        tieImage = new Image("/resources/background/tiefinish.png");
        mainMenu = new Image("/resources/button/main_menu_center.png");
        exit = new Image("/resources/button/exit_center.png");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        int numberOfP1Wins = Game.numberOfP1Wins;
        int numberOfP2Wins = Game.numberOfP2Wins;
        
        if (numberOfP1Wins == numberOfP2Wins) {
            tieImage.draw(0 ,0 ,w ,h);
        }
        else if (numberOfP1Wins > numberOfP2Wins) {
            p1WinImage.draw(0 ,0 ,w ,h);
        }
        else if (numberOfP2Wins > numberOfP1Wins) {
            p2WinImage.draw(0 ,0 ,w ,h);
        }
        
        
        mainMenu.draw(1150*s, 600*s, s);
        exit.draw(1150*s, 800*s, s);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();
        
        int mX = input.getMouseX();
        int mY = input.getMouseY();
        
        // Checking mouse hover for main menu button
        if (Hover(mX,mY,1150*s,600*s,mainMenu.getWidth()*s,mainMenu.getHeight()*s) == true) {
            mainMenu = new Image("/resources/button/main_menu_center_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                sbg.enterState(0, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
        } else {
            mainMenu = new Image("/resources/button/main_menu_center.png");
        }
        
        // Checking mouse hover for exit button
        if (Hover(mX,mY,1150*s,800*s,exit.getWidth()*s,exit.getHeight()*s) == true) {
            exit = new Image("/resources/button/exit_center_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                System.exit(0);
            }
        } else {
            exit = new Image("/resources/button/exit_center.png");
        }
        
        // Escape button 
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            sbg.enterState(0, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
        }
        
        input.clearControlPressedRecord();
        input.clearKeyPressedRecord();
        input.clearMousePressedRecord();
    }
    
    public boolean Hover(int mX, int mY, float x, float y, float width, float height) {
        return (mX >= x) && mX <= x + width && mY >= y && mY <= y + height;
    }
}