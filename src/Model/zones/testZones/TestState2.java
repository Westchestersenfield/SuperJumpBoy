package Model.zones.testZones;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Model.inGameObjects.*;
import Model.zones.zoneAbstract.Zone;
import slickMain.Main;

public class TestState2 extends Zone{
	
	public static int id = 992; //increment this
	
	private Sound footStepType;
		
	private Rectangle collide;
	private Rectangle jumpBox;
	private Rectangle platform1; 
	private Rectangle platform2;
	private Rectangle platform3;
	private Rectangle platform4;
	private Rectangle platform5;
	private Rectangle platform6;
	private Rectangle platform7;
	
	private ArrayList<Rectangle> ded = new ArrayList<Rectangle>();
	private Rectangle ded1;
	private Rectangle ded2;

	private ArrayList<NPC> enemies = new ArrayList<NPC>();
	private ScrubPatrol testMob1 = new ScrubPatrol();

	private ArrayList<Coin> coins;
	
	private Rectangle floor;
	private ArrayList<Rectangle> collides = new ArrayList<Rectangle>();
	boolean collision = false;
	private Image background;
	private Rectangle sideWest;
	private Rectangle sideEast;
	private Player player;
	 
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("/assets/forestBackAltered.png");
		player = Main.getPlayer();
		floor = new Rectangle(700, 0, 110, 1280);

		coins = new ArrayList<Coin>();
		Coin coin1 = new Coin(500, 400);
		coins.add(coin1);
	} 

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("WOW!", 50, 50);
		g.setColor(Color.cyan);
		
		//RECTANGLE: X Pos, Y Pos, X size, Y size
		sideEast = new Rectangle(1280,0, 50, 1300);
		sideWest = new Rectangle(-50,0, 50, 1300);
		
		collide = new Rectangle(180,680, 24, 48);
		jumpBox = new Rectangle(300,650, 100, 24);
		platform1 = new Rectangle(40, 300, 150, 24);
		platform2 = new Rectangle(50, 575, 230, 24);
		platform3 = new Rectangle(400, 400, 24, 350);
		platform4 = new Rectangle(50, 490, 100, 24);
		platform5 = new Rectangle(200, 400, 100, 24);  
		platform6 = new Rectangle(500, 600, 24, 150);
		platform7 = new Rectangle(600, 50, 24, 550); 
		
		floor = new Rectangle(0, 730, 1280, 100);
		
		if(enemies.size() > 0) {
			enemies.remove(testMob1);
		}
		enemies.add(testMob1);
		
		if(collides.size() > 0) {
			collides.clear();
		}
		
		collides.add(sideWest); 
		collides.add(sideEast);
		
	//	collides.add(testMob1.getBody());
		//collides.add(jumpBox);
		collides.add(platform2);
		collides.add(platform3);
		//collides.add(platform4);
		collides.add(platform5);
				
		//ded.add(ded1);

		g.drawImage(background, 0, 0);
		g.draw(player.getBody());
		g.draw(floor);
		g.drawString("HP: " + Integer.toString(player.getHp()), 50, 150);

		for(int i = 0; i < collides.size(); i++) {
			g.draw(collides.get(i));
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
		
		player.updateMovement( coins, enemies, ded, collides, floor, input.isKeyDown(Input.KEY_W), input.isKeyDown(Input.KEY_S), input.isKeyDown(Input.KEY_A), input.isKeyDown(Input.KEY_D));		
		if(input.isKeyDown(Input.KEY_R)) {
			System.out.println("Here's a break");
		}
		
		
	}


	@Override
	public int getID() {
		return TestState2.id;
	}
	public void changeState(GameContainer gc, StateBasedGame sbg)
	{
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

	@Override
	public Sound getFootStepType() {
		// TODO Auto-generated method stub
		return null;
	}
}
