import java.awt.*;
import java.awt.event.*;
import java.util.*;

//A Basic version of the scrolling game, featuring Avoids, Gets, and RareGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class BasicGame extends AbstractGame {
           
    //Dimensions of game window
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;  
    
    //Starting Player coordinates
    private static final int STARTING_PLAYER_X = 0;
    private static final int STARTING_PLAYER_Y = 300;
    
    //Score needed to win the game
    private static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    private static final int MAX_GAME_SPEED = 300;
    //Interval that the speed changes when pressing speed up/down keys
    private static final int SPEED_CHANGE = 20;    
 
    private static final String INTRO_SPLASH_FILE = "assets/splash image 03.jpg";        
    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    private static final int SPAWN_INTERVAL = 45;
    //Maximum Entities that can be spawned on a single call to spawnEntities
    private static final int MAX_SPAWNS = 3;

   
    //A Random object for all your random number generation needs!
    public static final Random rand = new Random();

    //Player's current score
    private int score;
    
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    private Player player;
    
    
    public BasicGame(){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public BasicGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void preGame(){
    	this.setBackgroundColor(Color.BLACK);
    	super.setBackgroundImage("assets/background 2.png");
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;     
        super.setSplashImage(INTRO_SPLASH_FILE);
    }
    
    //Called on each game tick
    protected void updateGame(){
        //scroll all scrollable Entities on the game board
        scrollEntities(); 
        //Spawn new entities only at a certain interval
        if (ticksElapsed % SPAWN_INTERVAL <= 0)            
            spawnEntities();
        
            
        //Update the title text on the top of the window
        setTitleText("Score: " + score + "  HP: " + player.getHP());
    }
    
    
    //Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities(){
       for (int i = 0; i < displayList.size(); i++){
       	   System.out.println(displayList.get(i).toString());
           if(!(displayList.get(i) instanceof Player)){
           	   ((Scrollable)displayList.get(i)).scroll();
           }
       }
	   if(super.checkCollision(player.getX() + player.getWidth(), player.getY() + player.getHeight()) != null){
		   Consumable collidingEntity = (Consumable) (super.checkCollision(player));
		   handleCollision((Consumable)collidingEntity);
	   }
           	       
            //How do you know what Entities to scroll?
            //finish me
    }
    
    //Spawn new Entities on the right edge of the game board
    protected void spawnEntities(){
        
        //int maxEntities = rand.nextInt(MAX_SPAWNS);
        //int RG_Determiner = 5;
     
        for(int i=0; i<MAX_SPAWNS; i++){  
        	//int randomInt = rand.nextInt(6);
			//if(randomInt != RG_Determiner){
				/*	
				if(randomInt >= 0 && randomInt <= 1){
					Get greenBox = new Get();
					greenBox.setY(rand.nextInt(super.getWindowHeight()- greenBox.getHeight()));
					greenBox.setX(super.getWindowWidth());
					while(checkCollision(greenBox) != null){
						greenBox.setY(rand.nextInt(super.getWindowHeight()- greenBox.getHeight()));
					}
						displayList.add(greenBox);
				}
				*/
			
				//else{
					Avoid redBoxDOWN = new Avoid();
					redBoxDOWN.setY(0);
					//redBox.setY(super.getWindowHeight());
					redBoxDOWN.setX(rand.nextInt(super.getWindowWidth()- redBoxDOWN.getWidth()));
					
					if(redBoxDOWN.getX() >= 195 && redBoxDOWN.getX() <= 225 || redBoxDOWN.getX() >= 315 && redBoxDOWN.getX() <= 345){
						while(checkCollision(redBoxDOWN) != null){
							//redBox.setX(rand.nextInt(super.getWindowWidth()- redBox.getWidth()));
							redBoxDOWN.setX(rand.nextInt(195, 226));
						}
						displayList.add(redBoxDOWN);
					}
					
					
					Avoid redBoxUP = new Avoid();
					//redBoxUP.setY(0);
					redBoxUP.setY(super.getWindowHeight());
					redBoxUP.setX(rand.nextInt(super.getWindowWidth()- redBoxUP.getWidth()));
					
					if(redBoxUP.getX() >= 445 && redBoxUP.getX() <= 475 || redBoxUP.getX() >= 585 && redBoxUP.getX() <= 615){
						while(checkCollision(redBoxUP) != null){
							redBoxUP.setX(rand.nextInt(445, 475));
						}
						displayList.add(redBoxUP);
					}
					
				}
			}
			//else{
			/*
				RareGet flashingGreenBox = new RareGet();
				flashingGreenBox.setY(rand.nextInt(super.getWindowHeight()- flashingGreenBox.getHeight()));
				flashingGreenBox.setX(super.getWindowWidth());
				while(checkCollision(flashingGreenBox) != null){
					flashingGreenBox.setY(rand.nextInt(super.getWindowHeight()- flashingGreenBox.getHeight()));
				}
				displayList.add(flashingGreenBox);
			}
			*/
		//}
    //}
    
    
    
    //Called whenever it has been determined that the Player collided with a consumable
    protected void handleCollision(Consumable collidedWith){
        	displayList.remove(collidedWith);
        	if(collidedWith instanceof Avoid){
        		int delta = ((Consumable) collidedWith).getDamageValue();
        		player.modifyHP(delta);
        	}
        	/*
        	if(collidedWith instanceof Get){
        		score += ((Consumable) collidedWith).getPointsValue();
        	}
        	if(collidedWith instanceof RareGet){
        		score += ((Consumable) collidedWith).getPointsValue();
        		int delta = ((Consumable) collidedWith).getDamageValue();
        		player.modifyHP(delta);
        	}
        	*/
    }
    
    

    
    
    //Called once the game is over, performs any end-of-game operations
    protected void postGame(){
        if(player.getHP() <= 0){
        	super.setTitleText("Oh no! You got hit by a car :(");
        }
        //if(score >= SCORE_TO_WIN){
        if(player.getX() == super.getWindowWidth() - 110){
        	super.setTitleText("Yay! You crossed the street safely :)");
        }
    }
    
    //Determines if the game is over or not
    //Game can be over due to either a win or lose state
    protected boolean isGameOver(){
    	return(player.getX() == super.getWindowWidth() - 110 || player.getHP() <= 0);
        //return(score >= SCORE_TO_WIN || player.getHP() <= 0);
    }
     
    //Reacts to a single key press on the keyboard
    //Override's AbstractGame's handleKey
    protected void handleKey(int key){
        //first, call AbstractGame's handleKey to deal with any of the 
        //fundamental key press operations
        
        super.handleKey(key);
        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key));
        //if a splash screen is up, only react to the advance splash key
        if (getSplashImage() != null){
            if (key == ADVANCE_SPLASH_KEY){
                super.setSplashImage(null);
            }
            return;
        }
        if(key == KEY_PAUSE_GAME){
			isPaused = !isPaused;
        }
        if(isPaused != true){
			if(key == DOWN_KEY){
				if((player.getY() + 2) < super.getWindowHeight() - 75){
					player.setY(player.getY() + 2);
				}
			}
			if(key == UP_KEY){
				if((player.getY() - 2) > 0){
					player.setY(player.getY() - 2);
				}
			}
			/*
			if(key == LEFT_KEY){
				if((player.getX() - 10) > 0){
					player.setX(player.getX() - 10);
				}
			}
			*/
			if(key == RIGHT_KEY){
				if((player.getX() + 2) < super.getWindowWidth() - 75){
					player.setX(player.getX() + 2);
				}
			}
		}
        if(key == SPEED_DOWN_KEY){
        	if(super.getGameSpeed() > 20){
        		super.setGameSpeed(super.getGameSpeed() - SPEED_CHANGE);
        	}
        }
        if(key == SPEED_UP_KEY){
        	if(super.getGameSpeed() <= MAX_GAME_SPEED){
        		super.setGameSpeed(super.getGameSpeed() + SPEED_CHANGE);
        	}
        }
       
    }    


    
    

    
    
}
