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

class GameEnd extends BasicGameState {
    
    int stateID = -1;
    
    // Getting the width, height and the scale from Tron.java
    int w = Tron.WIDTH;
    int h = Tron.HEIGHT;
    float s  = Tron.SCALE;  
    
    // Images used in this state
    static Image p1WinImage = null;
    static Image p2WinImage = null;
    static Image tieImage = null;
    static Image p1Score = null;
    static Image p2Score = null;
    static Image games = null;
    static Image mainMenu = null;
    static Image nextGame = null;
    
    public GameEnd(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        p1WinImage = new Image("/resources/background/p1Congratulations.png");
        p2WinImage = new Image("/resources/background/p2Congratulations.png");
        mainMenu = new Image("/resources/button/main_menu.png");
        nextGame = new Image("/resources/button/next_game.png");
        tieImage = new Image("/resources/background/tie.png");
        games = new Image("/resources/numbers/games.png");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        Input input = gc.getInput(); 

        // Getting who won   
        boolean p1Win = Game.p1Win;
        boolean p2Win = Game.p2Win;
        boolean tieGame = Game.gameTie;

        // Getting the number of games and wins
        int numberOfGames = Game.numberOfGames;
        int numberOfP1Wins = Game.numberOfP1Wins;
        int numberOfP2Wins = Game.numberOfP2Wins;

        if (tieGame == true) {
             tieImage.draw(0 ,0 ,w ,h);

             // P1
             player1Score(numberOfP1Wins);

             // P2
             player2Score(numberOfP2Wins);   

             // Number of Games: Left Side
             GamesLeft(numberOfGames);

             // Number of Games: Right Side
             GamesRight(numberOfGames);
        }
        else if (p1Win == true) {
             p1WinImage.draw(0, 0, w, h);

             // P1
             player1Score(numberOfP1Wins);

             // P2
             player2Score(numberOfP2Wins);

             // Number of Games: Left Side
             GamesLeft(numberOfGames);

             // Number of Games: Right Side
             GamesRight(numberOfGames);
        }
        else if (p2Win == true) {
             p2WinImage.draw(0, 0, w, h);

             // P1
             player1Score(numberOfP1Wins);

             // P2
             player2Score(numberOfP2Wins);

             // Number of Games: Left Side
             GamesLeft(numberOfGames);

             // Number of Games: Right Side
             GamesRight(numberOfGames);
        }
        
        nextGame.draw(1450*s, 50*s, s);
        mainMenu.draw(60*s, 50*s, s);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        int numberOfGames = Game.numberOfGames;
        int numberOfP1Wins = Game.numberOfP1Wins;
        int numberOfP2Wins = Game.numberOfP2Wins;
        
        Input input = gc.getInput();
        
        int mX = input.getMouseX();
        int mY = input.getMouseY();
        
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            sbg.enterState(0, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
        }
        
        // Next game check with checking if the game is over for the best of 5 series
        if (input.isKeyPressed(Input.KEY_N)) {
            if (numberOfP1Wins == 3 || numberOfP2Wins == 3) {
                sbg.enterState(4, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
            else if (numberOfGames < 5) {
                sbg.enterState(1, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
            else if (numberOfGames == 5) {
                sbg.enterState(4, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
        }
        
        // Checking mouse hover for next game button
        if (Hover(mX,mY,1450*s,50*s,nextGame.getWidth()*s,nextGame.getHeight()*s) == true) {
            nextGame = new Image("/resources/button/next_game_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                if (numberOfP1Wins == 3 || numberOfP2Wins == 3) {
                sbg.enterState(4, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
                }
                else if (numberOfGames < 5) {
                    sbg.enterState(1, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
                }
                else if (numberOfGames == 5) {
                    sbg.enterState(4, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
                }
            }
        } else {
            nextGame = new Image("/resources/button/next_game.png");
        }
        
        // Checking mouse hover for main menu button
        if (Hover(mX,mY,60*s,50*s,mainMenu.getWidth()*s,mainMenu.getHeight()*s) == true) {
            mainMenu = new Image("/resources/button/main_menu_hover.png");
            
            // Checking to see if the button has been pressed
            if (input.isMousePressed(0)) {
                sbg.enterState(0, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
            }
        } else {
            mainMenu = new Image("/resources/button/main_menu.png");
        }
        
        input.clearControlPressedRecord();
        input.clearKeyPressedRecord();
        input.clearMousePressedRecord();
    }
    
    public void player1Score(int numberOfP1Wins) throws SlickException {
        // P1
        if (numberOfP1Wins == 0) {
            p1Score = new Image("/resources/numbers/Y0.png");
            p1Score.draw(1100*s, 910*s, s);
        }
        else if (numberOfP1Wins == 1) {
            p1Score = new Image("/resources/numbers/Y1.png");
            p1Score.draw(1100*s, 910*s, s);
        }
        else if (numberOfP1Wins == 2) {
            p1Score = new Image("/resources/numbers/Y2.png");
            p1Score.draw(1100*s, 910*s, s);
        }
        else if (numberOfP1Wins == 3) {
            p1Score = new Image("/resources/numbers/Y3.png");
            p1Score.draw(1100*s, 910*s, s);
        }
        else if (numberOfP1Wins == 4) {
            p1Score = new Image("/resources/numbers/Y4.png");
            p1Score.draw(1100*s, 910*s, s);
        }
        else if (numberOfP1Wins == 5) {
            p1Score = new Image("/resources/numbers/Y5.png");
            p1Score.draw(1100*s, 910*s, s);
        }
    }
    
    public void player2Score(int numberOfP2Wins) throws SlickException {
        // P2
        if (numberOfP2Wins == 0) {
            p2Score = new Image("/resources/numbers/R0.png");
            p2Score.draw(790*s, 910*s, s);
        }
        else if (numberOfP2Wins == 1) {
            p2Score = new Image("/resources/numbers/R1.png");
            p2Score.draw(780*s, 910*s, s);
        }
        else if (numberOfP2Wins == 2) {
            p2Score = new Image("/resources/numbers/R2.png");
            p2Score.draw(765*s, 910*s, s);
        }
        else if (numberOfP2Wins == 3) {
            p2Score = new Image("/resources/numbers/R3.png");
            p2Score.draw(765*s, 910*s, s);
        }
        else if (numberOfP2Wins == 4) {
            p2Score = new Image("/resources/numbers/R4.png");
            p2Score.draw(765*s, 910*s, s);
        }
        else if (numberOfP2Wins == 5) {
            p2Score = new Image("/resources/numbers/R5.png");
            p2Score.draw(765*s, 910*s, s);
        }
    }
    
    public void GamesLeft(int numberOfGames) {
        if (numberOfGames == 1) {
            games.draw(50*s ,805*s ,s);
        }
        else if (numberOfGames == 2) {
            games.draw(50*s, 805*s, s);
            games.draw(115*s, 805*s, s);
        }
        else if (numberOfGames == 3) {
            games.draw(50*s, 805*s, s);
            games.draw(115*s, 805*s, s);
            games.draw(180*s, 805*s, s);
        }
        else if (numberOfGames == 4) {
            games.draw(50*s, 805*s, s);
            games.draw(115*s, 805*s, s);
            games.draw(180*s, 805*s, s);
            games.draw(245*s, 805*s, s);
        }
        else if (numberOfGames == 5) {
            games.draw(50*s, 805*s, s);
            games.draw(115*s, 805*s, s);
            games.draw(180*s, 805*s, s);
            games.draw(245*s, 805*s, s);
            games.draw(310*s, 805*s, s);
        }
    }
    
    public void GamesRight(int numberOfGames) {
        if (numberOfGames == 1) {
            games.draw(1785*s ,805*s ,s);
        }
        else if (numberOfGames == 2) {
            games.draw(1785*s, 805*s, s);
            games.draw(1720*s, 805*s, s);
        }
        else if (numberOfGames == 3) {
            games.draw(1785*s, 805*s, s);
            games.draw(1720*s, 805*s, s);
            games.draw(1655*s, 805*s, s);
        }
        else if (numberOfGames == 4) {
            games.draw(1785*s, 805*s, s);
            games.draw(1720*s, 805*s, s);
            games.draw(1655*s, 805*s, s);
            games.draw(1590*s, 805*s, s);
        }
        else if (numberOfGames == 5) {
            games.draw(1785*s, 805*s, s);
            games.draw(1720*s, 805*s, s);
            games.draw(1655*s, 805*s, s);
            games.draw(1590*s, 805*s, s);
            games.draw(1525*s, 805*s, s);
        }
    }
    
    public boolean Hover(int mX, int mY, float x, float y, float width, float height) {
        return (mX >= x) && mX <= x + width && mY >= y && mY <= y + height;
    }
}