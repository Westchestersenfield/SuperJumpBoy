package Model.inGameObjects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import slickMain.Main;


public class ScrubPatrol extends NPC{
	
	float initialX;
	int flag = 0;
	private long timeSinceLastHit = 0;
	private SpriteSheet left;
	private SpriteSheet right;
	private Animation animatedSpriteRight;
	private Animation animatedSpriteLeft;
	private Animation animatedSprite;




 
	public ScrubPatrol() {
		this.setBody(new Rectangle(700,690,64,64));
		try {
			this.ded = new Sound("/assets/sound/combat/hitSlime.ogg");
			left = new SpriteSheet("/assets/art/characters/enemies/greenMonster/greenMonsterLeft.png", 64, 64);
			right = new SpriteSheet("/assets/art/characters/enemies/greenMonster/greenMonsterRight.png", 64, 64);
			animatedSpriteLeft = new Animation(left, 100);
			animatedSpriteRight = new Animation(right, 100);


			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initialX = getBody().getX();
		this.speed = 0.6f;
		this.setHp(3);
	} 
	
	
	public ScrubPatrol(Sound dedSound, int hp, Rectangle body, Image texture ) {
		this.ded = dedSound;
		this.setHp(hp);
		this.setBody(body);
		this.speed = 0.6f; 
		this.initialX = body.getX();
	} 
	
	
	public void patrol() { 
		 		
		if(getBody().getX() == initialX && flag == 0) {
			vX = -speed;
			flag = 1;
			animatedSprite = animatedSpriteLeft;
		}
		
		if(getBody().getX() > (this.initialX + 100)) { // moving left
			animatedSprite = animatedSpriteLeft;

			vX = -speed;
		}
		
		
		if(getBody().getX() < (this.initialX - 100)) { //moving right
			animatedSprite = animatedSpriteRight;

			vX = speed;
	
		}
		getBody().setX(getBody().getX() + vX);

	}


	@Override
	public Animation getAnimatedSpriteLeft() {
		// TODO Auto-generated method stub
		return animatedSpriteLeft;
	}


	@Override
	public Animation getAnimatedSprite() {
		// TODO Auto-generated method stub
		return animatedSprite;
	}


	@Override
	public Animation getAnimatedSpriteRight() {
		// TODO Auto-generated method stub
		return animatedSpriteRight;
	}	
}
