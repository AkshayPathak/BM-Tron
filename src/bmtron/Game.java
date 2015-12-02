package bmtron;
 
import java.awt.Rectangle;
import java.util.ArrayList;
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
 
class Game extends BasicGameState {
 
    int stateID = -1;
   
    // Getting the width, height and the scale from Tron.java
    int w = Tron.WIDTH;
    int h = Tron.HEIGHT;
    float s  = Tron.SCALE;
   
    // Variable declaration and initialization
    float player1x, player1y;
    float player2x, player2y;
   
    static int numberOfP1Wins = 0;
    static int numberOfP2Wins = 0;
    static int numberOfGames = 0;
    
    int direction1x, direction1y;
    int direction2x, direction2y;
   
    boolean isDead1, isDead2; 
    boolean right1, left1, up1, down1;
    boolean right2, left2, up2, down2;
    
    static boolean p1Win, p2Win, gameTie;
   
    int ctr;
    int place;
   
    ArrayList<Integer> p1X;
    ArrayList<Integer> p1Y;
    ArrayList<Integer> p2X;
    ArrayList<Integer> p2Y;
    
    ArrayList<Image> p1Trail; 
    ArrayList<Image> p2Trail; 
    
    ArrayList<Rectangle> p1Collision;
    ArrayList<Rectangle> p2Collision; 
    
    // Images that are going to be used in this state
    static Image gameBackground = null;
    static Image player1 = null;
    static Image player1Trail = null;
    static Image player2 = null;
    static Image player2Trail = null;
   
    // Bounding boxes
    Rectangle p1;
    Rectangle p2;
    
    public Game(int stateID) {
        this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gameBackground = new Image("/resources/background/gamebackground.jpg");
        player1 = new Image("/resources/images/player1.png");
        player1Trail = new Image("/resources/images/player1.png");
        player2 = new Image("/resources/images/player2.png");
        player2Trail = new Image("/resources/images/player2.png");
    }
 
    // Initialize Method
    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        // Variable declaration and initialization
        player1x = (1440*s);
        player1y = (540*s);
        player2x = (480*s);
        player2y = (540*s);
         
        direction1x = 5;
        direction1y = 0;
        direction2x = -5;
        direction2y = 0;

        isDead1 = false;
        isDead2 = false;
        
        p1Win = false;
        p2Win = false;
        gameTie = false;
        
        right1 = false;
        left1 = true;
        up1 = false; 
        down1 = false;
        
        right2 = true; 
        left2 = false; 
        up2 = false; 
        down2 = false;

        ctr = 0;
        place = 0;

        p1X = new ArrayList<>();
        p1Y = new ArrayList<>();
        p2X = new ArrayList<>();
        p2Y = new ArrayList<>();

        p1Trail = new ArrayList<>();
        p2Trail = new ArrayList<>();

        p1Collision = new ArrayList<>();
        p2Collision = new ArrayList<>();   
        
        p1 = new Rectangle((int)(player1x),(int)(player1y),(int)(20*s),(int)(20*s));
        p2 = new Rectangle((int)(player2x),(int)(player2y),(int)(20*s),(int)(20*s));
        
        gameBackground = new Image("/resources/background/gamebackground.jpg");
        player1 = new Image("/resources/images/player1.png");
        player1Trail = new Image("/resources/images/player1.png");
        player2 = new Image("/resources/images/player2.png");
        player2Trail = new Image("/resources/images/player2.png");
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        gameBackground.draw(0, 0, w, h);
        
        drawAndCollision();
    }
   
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();
       
        // Getting the movement of the players
        getMovement(gc);
        
        // Adding and updating all the values
        addValues();
        
        // Checking to see if the players went out of bounds
        boundsCheck();
        
        // Exit using escape
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            sbg.enterState(0, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
        }
       
        // Checking who has won
        if (isDead1 == true && isDead2 == true) {
            gameTie = true;
            numberOfGames ++;
            
            input.clearControlPressedRecord();
            input.clearKeyPressedRecord();
            input.clearMousePressedRecord();
            
            sbg.enterState(5, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
        }
        else if (isDead2 == true) {
            numberOfGames ++;
            numberOfP1Wins ++;
            p1Win = true;
            
            input.clearControlPressedRecord();
            input.clearKeyPressedRecord();
            input.clearMousePressedRecord();

            sbg.enterState(5, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));   
        }
        else if (isDead1 == true) {
            numberOfGames ++;
            numberOfP2Wins ++;
            p2Win = true;
            
            input.clearControlPressedRecord();
            input.clearKeyPressedRecord();
            input.clearMousePressedRecord();
            
            sbg.enterState(5, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 1000));
        }    
    }
    
    private void getMovement(GameContainer gc) {
        Input input = gc.getInput();
       
        // Player 1 controls
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            if (left1==false){
                right1 = true;
                left1 = false;
                up1 = false;
                down1 = false;
            }else{
                isDead1=true;
            }
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            if (right1==false){
                right1 = false;
                left1 = true;
                up1 = false;
                down1 = false;
            }else{
                isDead1=true;
            }
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            if (down1==false){
                right1 = false;
                left1 = false;
                up1 = true;
                down1 = false;
            }else{
                isDead1=true;
            }
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            if (up1==false){
                right1 = false;
                left1 = false;
                up1 = false;
                down1 = true;
            }else{
                isDead1=true;
            }
        }
        // Movement of Player 1
        if (right1 == true && player1y % (20*s) == 0){
            direction1x = 5;
            direction1y = 0;
        }else if (left1 == true && player1y % (20*s) == 0){
            direction1x = -5;
            direction1y = 0;          
        }else if (up1 == true && player1x % (20*s) == 0){
            direction1x = 0;
            direction1y = -5;          
        }else if (down1 == true && player1x  % (20*s) == 0){
            direction1x = 0;
            direction1y = 5;
        }
       
        // Player 2 controls
        if (input.isKeyDown(Input.KEY_D)) {
            if (left2==false){
                right2 = true;
                left2 = false;
                up2 = false;
                down2 = false;
            }else{
                isDead2=true;
            }
        }
        if (input.isKeyDown(Input.KEY_A)) {
            if (right2==false){
                right2 = false;
                left2 = true;
                up2 = false;
                down2 = false;
            }else{
                isDead2=true;
            }
        }
        if (input.isKeyDown(Input.KEY_W)) {
            if (down2==false){
                right2 = false;
                left2 = false;
                up2 = true;
                down2 = false;
            }else{
                isDead2=true;
            }
        }
        if (input.isKeyDown(Input.KEY_S)) {
            if (up2==false){
                right2 = false;
                left2 = false;
                up2 = false;
                down2 = true;
            }else{
                isDead2=true;
            }
        }
       
        // Movement of Player 2
        if (right2 == true && player2y % (20*s) == 0){
            direction2x = 5;
            direction2y = 0;
        }else if (left2 == true && player2y % (20*s) == 0){
            direction2x = -5;
            direction2y = 0;          
        }else if (up2 == true && player2x % (20*s) == 0){
            direction2x = 0;
            direction2y = -5;          
        }else if (down2 == true && player2x % (20*s) == 0){
            direction2x = 0;
            direction2y = 5;
        }
    }
    
    public void addValues() {
        // Updating the location of the bounding boxes
        p1.setLocation((int)(player1x),(int)player1y);
        p2.setLocation((int)(player2x),(int)player2y);
        
        // Adding Player 1 X and Y values to ArrayLists
        p1X.add((int)(player1x));
        p1Y.add((int)(player1y));
       
        // Adding Player 2 X and Y values to ArrayLists
        p2X.add((int)player2x);
        p2Y.add((int)player2y);
        
        // Adding trails for Player 1 and Player 2 to ArrayLists
        p1Trail.add(player1Trail);
        p2Trail.add(player2Trail); 
    }
    
    public void boundsCheck() throws SlickException {
        // Checking to see if Player 1 went out of bounds
        if (player1x>(1920*s)||player1x<1){
            isDead1 = true;
            player1.destroy();
        }else{
            player1x +=direction1x;
        }
        if (player1y>(1080*s)||player1y<1){
            isDead1 = true;
            player1.destroy();
        }else{
            player1y +=direction1y;
        }
        
        // Checking to see if Player 2 went out of bounds
        if (player2x>(1920*s)||player2x<1){
            isDead2 = true;
            player2.destroy();
        }else{
            player2x +=direction2x;
        }
        if (player2y>(1080*s)||player2y<1){
            isDead2 = true;
            player2.destroy();
        }else{
            player2y +=direction2y;
        }
    }
    
    public void drawAndCollision() throws SlickException {
        // Drawing the players to the screen
        if (isDead1 == false){
            player1.draw(player1x,player1y,s);
        }else{
            player1.destroy();
        }
       
        if (isDead2 == false){
            player2.draw(player2x,player2y,s);
        }else{
            player2.destroy();
        }
        
        // Checking if the two players' head collide with each other
        if (p1.intersects(p2)) {
            isDead1 = true;
            isDead2 = true;
        }
        
        // Drawing Player 1 trail
        for (int i = 0; i < p1Trail.size(); i++) {
            if((p1X.get(i)%(20*s)==0) && (p1Y.get(i)%(20*s)==0)){
                p1Trail.get(i).draw(p1X.get(i), p1Y.get(i), (20*s), (20*s));
            }         
        }
        
        // Drawing Player 1 trail
        for (int i = 0; i < p1Trail.size(); i++) {
            if((p2X.get(i)%(20*s)==0) && (p2Y.get(i)%(20*s)==0)){
                p2Trail.get(i).draw(p2X.get(i), p2Y.get(i), (20*s), (20*s));
            }        
        }
        
        // Adding collision boxes for both players
        for (int i = 0; i < p1Trail.size(); i++) {
            if(i%(4) == 0 && i > 4) {
                p1Collision.add(new Rectangle(p1X.get(i-4),p1Y.get(i-4),(int)(20*s),(int)(20*s)));
                p2Collision.add(new Rectangle(p2X.get(i-4),p2Y.get(i-4),(int)(20*s),(int)(20*s)));
            }
            if(i==5){
                p1Collision.add(new Rectangle((int)(1440*s),(int)(540*s),(int)(20*s),(int)(20*s)));
                p2Collision.add(new Rectangle((int)(480*s),(int)(540*s),(int)(20*s),(int)(20*s)));
            }
        }
        
        // Trail Collision
        for (int i = 0; i < p1Collision.size(); i++) {
            if(p2.intersects(p1Collision.get(i))){
                isDead2 = true;
            }
            if(p1.intersects(p2Collision.get(i))){
                isDead1 = true;
            }
            ctr = i -1;
            if (ctr>1){
                if(p1.intersects(p1Collision.get(ctr))){
                    isDead1 = true;
                }
                if(p2.intersects(p2Collision.get(ctr))){
                    isDead2 = true;
                }
            }
        }        
    }
}