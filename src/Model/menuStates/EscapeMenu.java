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

import Model.inGameObjects.Player;
import Model.zones.zoneAbstract.Zone;
import slickMain.Main;


public class EscapeMenu extends Zone  {

	public static int id = 9001; //increment this
	private Image escapeMenu;
	private static Music music;
	private static Music nextMusic;
	private Sound start;
	private Main main;
		
	private Rectangle title; 

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		escapeMenu = new Image("/assets/art/title/title.png");
	
		setMusic(new Music("/assets/sound/music/saveMusic.ogg"));
		start = new Sound("/assets/sound/ui/positive.wav");  //load or positive
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.cyan);
		g.drawImage(escapeMenu, 0, 0);	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		changeState(gc, sbg);
	}

	@Override
	public int getID() {
		return id;
	}
	
	
	 
	
	
	
	//If you are on escape menu, and press the escape key, return to players current game state
	public void changeState(GameContainer gc, StateBasedGame sbg) 
	{
	    Input input = gc.getInput();
	    if(input.isKeyPressed(Keyboard.KEY_ESCAPE))
	    {
			getMusic().stop();
			start.play();
			input.clearKeyPressedRecord();
			Main.getPlayer().getCurrentState().getMusic().play();
	        sbg.enterState(Main.getPlayer().getCurrentState().getID());
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
		
	}

	@Override
	public void createPlatforms() {
		// TODO Auto-generated method stub
		
	}

	
	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
	
	


}
