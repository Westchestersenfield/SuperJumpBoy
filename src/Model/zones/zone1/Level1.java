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
import Model.inGameObjects.NPCs.NPC;
import Model.inGameObjects.NPCs.ScrubPatrol;
import Model.menuStates.EscapeMenu;
import Model.zones.zoneAbstract.Zone;
import slickMain.Main;

public class Level1 extends Zone{
	
	public static int id = 1; //increment this
	
	private EscapeMenu escapeMenu = new EscapeMenu();
	private Sound footStepType;
	private static Music music;
		
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

	private ArrayList<Rectangle> ded;

	private ArrayList<NPC> enemies;
	private ScrubPatrol testMob1;
	private ScrubPatrol testMob2;


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
		background = new Image("/assets/art/levels/zone1/level1.png");
		
		player = Main.getPlayer();
		music = new Music("/assets/sound/music/level1.ogg");
		
		//Coins init
		createCoins();
		
		//Mobs init
		createMobs();
		
		//Platform init
		createPlatforms();
		
		//Ded init
		createDed();
		
	} 

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("WOW!", 50, 50);
		g.setColor(Color.white);
		
		//ded.add(ded1);

		g.drawImage(background, 0, 0);
		player.getAnimatedSprite().draw(player.getBody().getX(), player.getBody().getY());
		g.drawImage(player.getHearts().getCurrent(), 100, 100);
		g.drawImage(player.getStaticCoin().getStaticCoin(), 100, 140);
		//g.draw(floor);
		
		g.drawString(" x " + Integer.toString(player.getGold()), 130, 150 );


		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).getAnimatedSprite().draw(enemies.get(i).getBody().getX(), enemies.get(i).getBody().getY());
		}
			
		for(int i = 0; i < collides.size(); i++) {
		//	g.draw(collides.get(i));
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
		player.setCurrentState(this);		

		Input input = gc.getInput(); //obtain keyboard input
		
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getHp() > 0) {
				enemies.get(i).patrol();
			}
			else {
				enemies.get(i).getBody().setX(-100);
				enemies.remove(enemies.get(i));
			}
		}
			
		
		player.update(coins, enemies, ded, collides, floor, input.isKeyDown(Input.KEY_W), input.isKeyDown(Input.KEY_S), input.isKeyDown(Input.KEY_A), input.isKeyDown(Input.KEY_D));
		if(input.isKeyDown(Input.KEY_R)) {
			 nextLevel3(gc, sbg);
		}
		if(input.isKeyDown(Input.KEY_ESCAPE)) {
			changeState(gc, sbg);
		}
		
		if(player.isLoadNext() == true) {nextLevel(gc, sbg);}
	}


	@Override
	public int getID() {
		return id;
	}
	
		
	
	public void changeState(GameContainer gc, StateBasedGame sbg)
	{
		//music.stop();
	    Input input = gc.getInput();
	    if(input.isKeyPressed(Keyboard.KEY_ESCAPE))
	    {
	        escapeMenu.getMusic().play();
	        sbg.enterState(9001);
	    }
	}
	
	
	public void nextLevel(GameContainer gc, StateBasedGame sbg)
	{
        sbg.enterState(2);
        player.getBody().setX(20);
	}
	public void nextLevel3(GameContainer gc, StateBasedGame sbg)
	{
        sbg.enterState(3);
        player.getBody().setX(20);
	}
	
	
	
	
	
	
	public Sound getFootStepType(){
		return footStepType;
	}
		
	public void createMobs() {
		enemies = new ArrayList<NPC>();
		testMob1 = new ScrubPatrol(800,690, 1);
		
		enemies.add(testMob1);
	}
	
	public void createCoins() {
		coins = new ArrayList<Coin>();
		coin1 = new Coin(500, 400);
		coin2 = new Coin(550, 400);
		coin3 = new Coin(170, 400);

		coins.add(coin1);
		coins.add(coin2);
		coins.add(coin3);
	}
	
	public void createPlatforms() {

		platform1 = new Rectangle(100, 700, 135, 24);
		platform2 = new Rectangle(305, 640, 135, 24);		
		platform4 = new Rectangle(85, 535, 135, 24);
		platform5 = new Rectangle(300, 459, 135, 24);  
		
		platform3 = new Rectangle(455, 465, 45, 290);
		
		platform6 = new Rectangle(500, 600, 24, 150);
		platform7 = new Rectangle(600, 50, 24, 550); 
			
		
		sideWest = new Rectangle(-24,0, 24, 1000);
		sideEast = new Rectangle(1280, 0, 24, 1000);
		floor = new Rectangle(0,750, 1280, 50);
		
		collides.add(sideWest); 
		collides.add(sideEast);
		collides.add(floor);
		 
		collides.add(testMob1.getBody());
		collides.add(platform1);
		collides.add(platform2);
		collides.add(platform3);
		collides.add(platform4);
		collides.add(platform5);
		//collides.add(platform6);
	}
	
	public void createDed() {
		ded = new ArrayList<Rectangle>();
	}

	@Override
	public Music getMusic() {
		return music;
	}
	
}
