package Model.menuStates;

import java.awt.Font;
import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Model.zones.zoneAbstract.Zone;
import slickMain.Main;


public class StartScreen extends Zone  {

	public static int id = 0; //increment this
	private Image startScreen;
	private Music music;
	private Music nextMusic;
	private Sound start;
	private Main main;
	
	private SpriteSheet pressStart;
	private Animation pressStartBlink;
	
	private Rectangle title; 

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		pressStart = new SpriteSheet("/assets/art/title/pressStart.png", 271,47);
		pressStartBlink = new Animation(pressStart, 750);
		
		startScreen = new Image("/assets/art/title/title.png");
		
		music = new Music("/assets/sound/music/title.ogg");
		nextMusic = new Music("/assets/sound/music/level1.ogg");
		
		start = new Sound("/assets/sound/ui/positive.wav");  //load or positive
		music.play();

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.cyan);
		g.drawImage(startScreen, 0, 0);	
		pressStartBlink.draw(500, 550);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		changeState(gc, sbg);
	}

	@Override
	public int getID() {
		return id;
	}
	
	
	 
	
	
	
	public void changeState(GameContainer gc, StateBasedGame sbg) 
	{
	    Input input = gc.getInput();
	    if(input.isKeyPressed(Keyboard.KEY_SPACE))
	    {
			music.stop();
			start.play();
			nextMusic.play(1,50.0f);
			input.clearKeyPressedRecord();
			Main.getPlayer().getBody().setX(10);
			Main.getPlayer().init();
	        sbg.enterState(1);
	    }
	}

	@Override
	public Sound getFootStepType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createMobs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createCoins() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createPlatforms() {
		// TODO Auto-generated method stub
		
	}
	
	


}
