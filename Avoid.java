//Avoids are entities the player needs to avoid colliding with.
//If a player collides with an avoid, it reduces the players Hit Points (HP).
import java.util.*;

public class Avoid extends Entity implements Consumable, Scrollable {
     
    //Location of image file to be drawn for an Avoid
    private static final String AVOID_IMAGE_FILE = "assets/aviod image.png";
    //Dimensions of the Avoid    
    private static final int AVOID_WIDTH = 75;
    private static final int AVOID_HEIGHT = 75;
    //Speed that the avoid moves each time the game scrolls
    private static final int AVOID_SCROLL_SPEED = 5;
   
    private static ArrayList<String> avoidImages = new ArrayList<String>();
  
		
    public Avoid(){
        this(0, 0);        
    }
    
    public Avoid(int x, int y){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE); 
        avoidImages.add("assets/aviod image.png");
        avoidImages.add("assets/second aviod image.jpg");
        avoidImages.add("assets/third aviod image.jpg");
        setImageName(avoidImages.get(BasicGame.rand.nextInt(3)));
    }
    
    public int getScrollSpeed(){
        return AVOID_SCROLL_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        //setX(getX() - AVOID_SCROLL_SPEED);
        if(getX() >= 195 && getX() <= 225 || getX() >= 315 && getX() <= 345){
        	setY(getY() + AVOID_SCROLL_SPEED);
        }
        
        if(getX() >= 445 && getX() <= 475 || getX() >= 585 && getX() <= 615){
        	setY(getY() - AVOID_SCROLL_SPEED);
        }
    }
    
    //Colliding with an Avoid does not affect the player's score
    public int getPointsValue(){
       //implement me!
       throw new IllegalStateException("Hey 102 Student! You need to implement getPointsValue in Avoid.java!");
  }
    
    //Colliding with an Avoid Reduces players HP by 1
    public int getDamageValue(){
        return -1;
    }
   
}
