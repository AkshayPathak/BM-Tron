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

class About extends BasicGameState {

    int stateID = -1;
    
    int w = Tron.WIDTH;
    int h = Tron.HEIGHT;
    float s  = Tron.SCALE;
    
    Image aboutBackground = null;
    Image back = null;
    
    public About(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        aboutBackground = new Image("/resources/background/aboutbackground.png");
        back = new Image("/resources/button/back.png");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        aboutBackground.draw(0,0,w,h);
        back.draw(756*s, 920*s, s);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();
        
        int mX = input.getMouseX();
        int mY = input.getMouseY();
        
        // Checking mouse hover for back button
        if (Hover(mX,mY,756*s,920*s,back.getWidth()*s,back.getHeight()*s) == true) {
            back = new Image("/resources/button/back_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                sbg.enterState(0, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
        } else {
            back = new Image("/resources/button/back.png");
        }
        
        // Escape button to exit the game instantly
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