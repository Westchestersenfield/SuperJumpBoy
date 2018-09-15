package view.uiElements;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class HeartModule {

	private Rectangle body;
	private Image current;
	private Image full;
	private Image two;
	private Image one;
	private Image empty;
	
	public HeartModule(){
		init();
	}
	
	private void init() {
		try {
			full = new Image("/assets/art/UIElements/heartsFull.png");
			two = new Image("/assets/art/UIElements/heartsTwo.png");
			one = new Image("/assets/art/UIElements/heartsOne.png");
			empty = new Image("/assets/art/UIElements/heartsEmpty.png");
			
			setCurrent(full);
		
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setBody(new Rectangle(96, 32, 100, 250 ));
	}
	
	public void update(int x) {
		if( x == 3) {
			setCurrent(full);
		}
		else if( x == 2 ) {
			setCurrent(two);
		}
		else if( x == 1){
			setCurrent(one);
		}
		else if ( x == 0 ) {
			setCurrent(empty);
		}
		
	}

	public Image getCurrent() {
		return current;
	}

	public void setCurrent(Image current) {
		this.current = current;
	}

	public Rectangle getBody() {
		return body;
	}

	public void setBody(Rectangle body) {
		this.body = body;
	}



	
}
