package Model.inGameObjects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Coin {
	
	private Rectangle body;
	private Image image;
	private Image staticCoin;
	private Sound grabbed;
	
	private SpriteSheet rotatingCoin;
	private Animation coinAnimation;
	
	public Coin(int x, int y) {
		body = new Rectangle(x,y,32,32);
	

		try {
			rotatingCoin = new SpriteSheet("/assets/art/coins/FullCoins32.png", 32,32);
			setStaticCoin(new Image("/assets/art/coins/coin_01.png"));
			setCoinAnimation(new Animation(rotatingCoin, 100));
			grabbed = new Sound("/assets/sound/coinGrab.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//called when player intersects a coin
	void grabbed() {
		grabbed.play(1, 100);
		getBody().setX(-100);		
	}

	public Animation getCoinAnimation() {
		return coinAnimation;
	}

	public void setCoinAnimation(Animation coinAnimation) {
		this.coinAnimation = coinAnimation;
	}

	public Rectangle getBody() {
		return body;
	}

	public void setBody(Rectangle body) {
		this.body = body;
	}

	public Image getStaticCoin() {
		return staticCoin;
	}

	public void setStaticCoin(Image staticCoin) {
		this.staticCoin = staticCoin;
	}
}	
