package slickMain;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class BasicGame extends BasicGameState{
	
	public static int id = 1;
	private Rectangle square;
	private Rectangle collide; 
	private float x = 50; 
	private float y = 650;
	boolean collision = false;
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		square = new Rectangle(x, y, 50, 50);	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("WOW!", 50, 50);
		g.setColor(Color.green);
		square = new Rectangle(x, y, 50, 50);
		collide = new Rectangle(350,350, 50, 50);
		g.draw(square);
		g.draw(collide);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//y += .13 * delta;
		Input input = gc.getInput(); //obtain keyboard input
		if(input.isKeyDown(input.KEY_A)) {
			x -= 200/1000.0f*delta;
		}
		if(input.isKeyDown(input.KEY_D)) {
			x += 200/1000.0f*delta;
		}
		if(input.isKeyDown(input.KEY_W)) {
			y -= 200/1000.0f*delta;
		}
		if(input.isKeyDown(input.KEY_S)) {
			y += 200/1000.0f*delta;
		}
		
		
		if(square.intersects(collide)) {
			collision = true;
		}
		else {
			collision = false;
		}
		
		if(square.intersects(collide)) {
			if(x < 400) {				//value should be half of window width
				x =(collide.getX()-53); //value should be size of object width + a few pixels 
			}
			else {
				x =(collide.getX()+53); //
			}
		}
	}


	@Override
	public int getID() {
		return BasicGame.id;
	}
	
}
