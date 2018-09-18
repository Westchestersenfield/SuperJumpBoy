package slickMain;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.state.StateBasedGame;

import Model.inGameObjects.Player;
import Model.menuStates.EscapeMenu;
import Model.menuStates.StartScreen;
import Model.zones.*;
import Model.zones.zone1.*;
//import Model.zones.zone1.Level1;
//import Model.zones.zone1.Level2;
//import Model.zones.zone1.Level3;


public class Main extends StateBasedGame {
	private static Player player;
	private static StartScreen startScreen = new StartScreen();
		
	public Main(String name) {
		super(name); 
	} 
	
	public static void main(String [] args) throws LWJGLException {
		AppGameContainer appgc;		
		try {
			
			appgc = new AppGameContainer(new Main("Super Jump Boy"));
			appgc.setDisplayMode(1280,800, false);
			appgc.setAlwaysRender(true);
			player = new Player(startScreen);
			appgc.start();
			appgc.setDisplayMode(1280,800, false);

			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(startScreen);
		addState(new EscapeMenu());
		addState(new Level1());
		addState(new Level2());
		addState(new Level3());

		
//		addState(new TestState());
//		addState(new TestState2());
		
	}

	public static Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}

