package Model.inGameObjects;


import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import Model.zones.zoneAbstract.Zone;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;
import java.lang.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

//TODO: Encapsulate, text ur baboo, clean up combat, text boxes? start screen / pause screen, projectiles?, art, idleAnimation, 

//DONE: player hp, mob & player delayed damage, compounding distance, collision angle on mob, Encapsulate, no damage on top of mob, state switching, coins,. move zones into abstract


@SuppressWarnings("unused")
public class Player {
	
	private Zone currentState;
	
	private Sound ded;
	private Sound hitSound;
	
	private SpriteSheet left;
	private SpriteSheet right;
	private SpriteSheet idle;
	private Animation animatedSpriteRight;
	private Animation animatedSpriteLeft;
	private Animation animatedIdle;
	private Animation animatedSprite;
	
	private boolean loadNext = false;
	private boolean invincible = false;
	private boolean mute = false;
	
	private float timeSinceLastHit = 0.0f;
	private float timeSinceLastSound = 0.0f;
	
	private int hp;
	private int gold = 0;
	 
	private Rectangle body;
	private Image image;
	
	private float speed = 2.4f;   
	private static float gravity = 0.3f;
	private static float jumpStrength = -8;
	 
	private static int iterations = 10;	 
	private float vX = 0;
	private float vY = 0;

	public Player(Zone zone)  {
		setCurrentState(zone);
		body = new Rectangle(0, 700, 64, 64);
	}
	

	public void init() {
		try {
			right = new SpriteSheet("/assets/art/characters/hero/playerWalk.png", 58, 64);
			left = new SpriteSheet("/assets/art/characters/hero/playerWalkLeft.png", 58, 64);
			idle = new SpriteSheet("/assets/art/characters/hero/idle.png", 58, 64);

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		animatedSpriteRight = new Animation(right, 100);
		animatedSpriteLeft = new Animation(left, 100);
		animatedIdle = new Animation(idle, 100);
	}
	public void updateMovement(ArrayList<Coin> coins, ArrayList<NPC> enemies,ArrayList<Rectangle> ded, ArrayList<Rectangle> collisions, Rectangle floor, boolean up, boolean down, boolean left, boolean right) {
		setLoadNext(false);
		if(this.getHp() <= 0) {
			body.setX(20);
			this.setHp(3);
		}
		
		if(down == true) {body.setX(550);}
		
		if(System.nanoTime() - timeSinceLastHit >  1000000000) { invincible = false;}
		if(System.nanoTime() - timeSinceLastSound >  250000000) { mute = false;}     //ALSO STOP WHILE IN THE AIR
		
		
		//Y 
		vY += gravity; 
		if(body.getY() > 1280) {body.setY(700);}
		
		if(up == true) {
			makeVulnerable(enemies);
			jump( collisions, floor);
		}  
				
		iterativeYCollision(floor, collisions, enemies);
		
		// X 
		move(left, right, up, enemies);
		body.setX(body.getX() + vX);
		checkxCollision(collisions, enemies); 	
		checkCoinCollision(coins);
		checkDeadCollision(ded);		
	}
	

		
	
	
	
	
	
	
	
	
//\\             AUXILIARY FUNCTIONS       		 //\\
//\\=============================================//\\	
	
	
	
	
	
	
	
	
	
	
	

	
	/*
	 * Offsets body slightly so that it is intersecting the floor. Then checks if it is intersecting the floor or a collision object (including enemies).
	 * Sets gravity to jumpStrength value
	 * Then resets offset from first step.
	 */
	public void jump( ArrayList<Rectangle>collisions, Rectangle floor) {  

		mute = true;
		body.setY(body.getY() + 0.2f);
		for(int i = 0; i < collisions.size(); i++) { 
			if(body.intersects(floor) || body.intersects(collisions.get(i))) {
				setvY(Player.getJumpStrength());
			}
		}
		body.setY(body.getY() - 0.21f ); 
	}
	
	
	
	/*
	 * Simple function which checks players position against spots on the map which kill them. ie; pits, spikes, etc 
	 */
	public void checkDeadCollision(ArrayList<Rectangle>ded) {
		for(int i = 0; i < ded.size(); i++){ 
			if(ded.get(i).intersects(body)) {
				this.setHp(0);
				//Show dead screen, play sound, etc
			//	body.setX(100);
			}
		}
	}
	
	
	
	/*
	 * Function for simple left right movement. Also covers standing still, as well as collision while standing still
	 */
	public void move(boolean left, boolean right, boolean up, ArrayList<NPC> enemies) {
		animatedSprite = animatedIdle;
		if(left == true) {
			if(mute == false) {
		//		currentState.getFootStepType().play();
				timeSinceLastSound = System.nanoTime();
				mute = true;
			}
			vX = -speed;
			animatedSprite = animatedSpriteLeft;
		}
		else if(right == true) {
			if(mute == false) {
			//	currentState.getFootStepType().play();
				timeSinceLastSound = System.nanoTime();
				mute = true;
			}
			vX = speed;
			animatedSprite = animatedSpriteRight;

		} 
		else { 
			animatedSprite = animatedIdle;
			vX = 0;		
			xCollisionZeroMovement(enemies);
		}
	}
	
	
	
	/*
	 * Checks still players collision against moving enemies
	 */
	public void xCollisionZeroMovement(ArrayList<NPC> enemies) {
		
		for(int i = 0; i < enemies.size(); i++) {
			
			if(enemies.get(i).getBody().getCenterX() > body.getCenterX()) { //enemy is on players right
				if(enemies.get(i).getBody().getCenterX() - body.getCenterX() < 40 && enemies.get(i).vX < 0 &&  enemies.get(i).getBody().getMaxY() < body.getMinY()) { //and enemy is within range and moving left
					body.setX(body.getX() - 0.1f );
					enemies.get(i).getBody().setX(enemies.get(i).getBody().getX() + 1.5f);
					if(invincible == false) { 
						this.setHp(this.getHp() - 1);
						timeSinceLastHit = System.nanoTime();
						System.out.println("HP: " + this.getHp());
					}
					invincible = true;
					return;}}
			
			else if(enemies.get(i).getBody().getCenterX() < body.getCenterX()) { //enemy is on players left
				if(body.getCenterX() - enemies.get(i).getBody().getCenterX()  < 40 && enemies.get(i).vX > 0 &&  enemies.get(i).getBody().getMaxY() < body.getMinY()) {
					body.setX(body.getX() + 0.1f );
					enemies.get(i).getBody().setX(enemies.get(i).getBody().getX() - 1.5f);
					if(invincible == false) { 
						this.setHp(this.getHp() - 1);
						timeSinceLastHit = System.nanoTime();
						System.out.println("HP: " + this.getHp());
					}
					invincible = true;
					return;	}}}
		}

	
	
	
	
	//Checks moving players collision against other collision objects.
	public void checkxCollision(ArrayList<Rectangle> collisions, ArrayList<NPC> enemies) {
		
		for(int i = 0; i < collisions.size(); i++){ 
			if(collisions.get(i).intersects(body)) {
				if(collisions.get(i).getX() == 1280 && enemies.size() == 0) {
					setLoadNext(true);
				}
				
				if(vY < 0) {vY = gravity;}
				
				//right collision
				if(vX > 0){	checkXCollisionRight(collisions, enemies, i);}		
				
				//left collision
				else if(vX < 0) {checkXCollisionLeft(collisions, enemies, i);} 	
		}}}
	
	
	
	
	
	//checks right collision of +X moving player, used in X movement collision function
	public void checkXCollisionRight(ArrayList<Rectangle>collisions, ArrayList<NPC>enemies, int i) {
		body.setX(body.getX() - vX); 
		setvX(0.0f); 
		for(int k = 0; k < enemies.size(); k++) {
			if(enemies.get(k).getBody() == collisions.get(i)) {
				body.setX((float) (body.getX() - 0.5));
				enemies.get(k).getBody().setX(enemies.get(k).getBody().getX() + 1.5f);
				if(invincible == false) { 
					this.setHp(this.getHp() - 1);
					timeSinceLastHit = System.nanoTime();
				}
				invincible = true;
				vX = 0;	}}
	}
	
	
	
	//Checks left collision of -X moving player
	public void checkXCollisionLeft(ArrayList<Rectangle>collisions, ArrayList<NPC>enemies, int i) {
		
		body.setX(body.getX() + (vX*-1));
		setvX(0.0f);
		
		for(int k = 0; k < enemies.size(); k++) {
			if(enemies.get(k).getBody() == collisions.get(i) ) {
				body.setX((float) (body.getX() + 0.5f));
				enemies.get(k).getBody().setX(enemies.get(k).getBody().getX() - 1.5f );
				if(invincible == false) { 
					this.setHp(this.getHp() - 1);
					timeSinceLastHit = System.nanoTime();
				}
				invincible = true;
				vX = 0;}}
	}


	
	
	
	public void iterativeYCollision(Rectangle floor, ArrayList<Rectangle> collisions, ArrayList<NPC> enemies) {  //COULD MAKE THIS MORE EFFICIENT BY MAKING ENEMY ARRAY RECT ARR AND USING BoDY
		float vYtemp = vY/iterations;
		for(int i = 0; i < iterations; i++) {
			
			
			body.setY(body.getY() + vYtemp);
			
			for(int j = 0; j < collisions.size(); j++) {
				if(body.intersects(floor) || body.intersects( collisions.get(j))) {					
					//jumping on an enemy
					if(body.intersects(collisions.get(j))) {
						for(int k = 0; k < enemies.size(); k++) {
							if(enemies.get(k).getBody() == collisions.get(j) && (enemies.get(k).getBody().getY() + 12 ) > body.getY() ) { //check if player is ontop of the enemy
								invincible = true;
							//	timeSinceLastHit = System.nanoTime();
								if(enemies.get(k).isInvincible() == false) {
									enemies.get(k).ded.play(1,200.0f);
									enemies.get(k).setHp(enemies.get(k).getHp() - 1);
									enemies.get(k).setTimeSinceLastHit(System.nanoTime());
									enemies.get(k).setInvincible(true);
								}
								enemies.get(k).setInvincible(true);
							} 
						}
					}
					
				body.setY(body.getY() - vYtemp); //resistance to gravity to keep player from falling through the floor.
				vY = 0; 		
				}
			} 
		}	
	}
	
	public void makeVulnerable(ArrayList<NPC> enemies) {
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).setInvincible(false);
		}
	}
	
	public void checkCoinCollision(ArrayList<Coin> coins) {
		for(int i = 0; i < coins.size(); i++){ 
			if(coins.get(i).getBody().intersects(body)) {
				this.setGold(this.getGold() + 1);
				coins.get(i).grabbed();
			}
		}
	}
	
	
	
	
	//GETTERS AND SETTERS
	public Rectangle getBody() {
		return body;
	}
	
	public void setBody(Rectangle body) {
		this.body = body;
	}

	public float getvY() {
		return vY;
	}

	public void setvY(float vY) {
		this.vY = vY;
	}

	public float getvX() {
		return vX;
	}
	
	public void setvX(float vX) {
		this.vX = vX;
	}


	public static float getJumpStrength() {
		return jumpStrength;
	}


	public static void setJumpStrength(float jumpStrength) {
		Player.jumpStrength = jumpStrength;
	}

	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}


	public boolean isLoadNext() {
		return loadNext;
	}


	public void setLoadNext(boolean loadNext) {
		this.loadNext = loadNext;
	}


	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}



	public int getGold() {
		return gold;
	}



	public void setGold(int gold) {
		this.gold = gold;
	}



	public Zone getCurrentState() {
		return currentState;
	}



	public void setCurrentState(Zone currentState) {
		this.currentState = currentState;
	}



	public Animation getAnimatedSprite() {
		return animatedSprite;
	}



	public void setAnimatedSprite(Animation animatedSprite) {
		this.animatedSprite = animatedSprite;
	}



	
}
