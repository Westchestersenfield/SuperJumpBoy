package Model.zones.zone1;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Model.inGameObjects.*;
import Model.zones.zoneAbstract.Zone;
import slickMain.Main;

public class Level2 extends Zone{
	
	public static int id = 2; //increment this
	
	private Sound footStepType;
	private Music music;
		
	private Rectangle floor;
	private Rectangle sideWest;
	private Rectangle sideEast;
	private Rectangle platform1; 
	private Rectangle platform2;
	private Rectangle platform3;
	private Rectangle platform4;
	private Rectangle platform5;
	private Rectangle platform6;
	private Rectangle platform7;

	private ArrayList<Rectangle> ded = new ArrayList<Rectangle>();

	private ArrayList<NPC> enemies = new ArrayList<NPC>();
	private ScrubPatrol testMob1 = new ScrubPatrol();

	private ArrayList<Coin> coins;
	private Coin coin1;
	private Coin coin2;
	private Coin coin3;

	private ArrayList<Rectangle> collides = new ArrayList<Rectangle>();
	
	boolean collision = false;
	
	private Image background;
	private Player player;
	 
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		footStepType = new Sound("/assets/sound/footsteps/stepdirt_3.ogg");
		background = new Image("/assets/art/levels/zone1/level2.png");
		
		player = Main.getPlayer();
		player.setCurrentState(this);
		
		System.out.println(this);
		System.out.println(player.getCurrentState());

		
		
		floor = new Rectangle(700, 0, 110, 1280);
		sideEast = new Rectangle(1280,0, 50, 1300);
		sideWest = new Rectangle(-50,0, 50, 1300);
		
		coins = new ArrayList<Coin>();
		coin1 = new Coin(500, 400);
		coin2 = new Coin(550, 400);
		coin3 = new Coin(170, 400);

		coins.add(coin1);
		coins.add(coin2);
		coins.add(coin3);
	} 

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("WOW!", 50, 50);
		g.setColor(Color.cyan);
		
		//RECTANGLE: X Pos, Y Pos, X size, Y size
		
		//horizontal platforms
		platform1 = new Rectangle(125, 675, 135, 24);
		platform2 = new Rectangle(240, 610, 505, 24);	
		platform3 = new Rectangle(765, 550, 135, 24);
		platform4 = new Rectangle(920, 470, 135, 24);
		platform5 = new Rectangle(695, 425, 135, 24);  
		platform6 = new Rectangle(30, 435, 510, 24);
		
		platform7 = new Rectangle(600, 50, 24, 550); 
		
		floor = new Rectangle(0, 750, 1280, 100);
		
		if(enemies.size() > 0) {
			enemies.remove(testMob1);
		}
		enemies.add(testMob1);
		
		if(collides.size() > 0) {
			collides.clear();
		}
		
		collides.add(sideWest); 
		collides.add(sideEast);
		
		collides.add(testMob1.getBody());
		collides.add(platform1);
		collides.add(platform2);
		collides.add(platform3);
		collides.add(platform4);
		collides.add(platform5);
		collides.add(platform6);

				
		//ded.add(ded1);

		g.drawImage(background, 0, 0);
		player.getAnimatedSprite().draw(player.getBody().getX(), player.getBody().getY());
		g.draw(floor);
		
		g.drawString("HP: " + Integer.toString(player.getHp()), 50, 150);
		g.drawString("MOB HP: " + Integer.toString(testMob1.getHp()), 950, 175);
		g.drawString("COINS: " + Integer.toString(player.getGold()), 50, 200 );


		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).getAnimatedSprite().draw(enemies.get(i).getBody().getX(), enemies.get(i).getBody().getY());
		}
		for(int i = 0; i < collides.size(); i++) {
			g.draw(collides.get(i));
		}
		
		for(int i =0; i < coins.size(); i++) {
			coins.get(i).getCoinAnimation().draw(coins.get(i).getBody().getX(), coins.get(i).getBody().getY());
		}
		for(int i = 0; i < ded.size(); i++) {
			g.draw(ded.get(i));
		} 
		
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput(); //obtain keyboard input
		
		if(testMob1.getHp() > 0) {
			testMob1.patrol();
		}
		else { testMob1.getBody().setX(-100);}
	
		testMob1.patrol();
		
		player.updateMovement(coins, enemies, ded, collides, floor, input.isKeyDown(Input.KEY_W), input.isKeyDown(Input.KEY_S), input.isKeyDown(Input.KEY_A), input.isKeyDown(Input.KEY_D));		
		if(input.isKeyDown(Input.KEY_R)) {
			System.out.println("Here's a break");
		}
		
		if(player.isLoadNext() == true) {
			nextLevel(gc, sbg);
		}
		
	}


	@Override
	public int getID() {
		return id;
	}
	
	
	
	
	public void changeState(GameContainer gc, StateBasedGame sbg)
	{
		music.stop();
	    Input input = gc.getInput();
	    if(input.isKeyPressed(Keyboard.KEY_ESCAPE))
	    {
	        sbg.enterState(2);
	    }
	    else if(input.isKeyPressed(Keyboard.KEY_SPACE))
	    {
	        sbg.enterState(3);
	    }
	}
	
	
	public void nextLevel(GameContainer gc, StateBasedGame sbg)
	{
        sbg.enterState(2);
        player.getBody().setX(20);
	}
	
	public Sound getFootStepType(){
		return footStepType;
		
	}
}
