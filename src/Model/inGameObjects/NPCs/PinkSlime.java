package Model.inGameObjects.NPCs;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import slickMain.Main;


public class PinkSlime extends NPC{
	
	float initialX;
	int flag = 0;
	private long timeSinceLastHit = 0;
	private SpriteSheet left;
	private SpriteSheet right;
	private Animation animatedSpriteRight;
	private Animation animatedSpriteLeft;
	private Animation animatedSprite;




 
	public PinkSlime(int x, int y, int patrolStrat) {
		this.setBody(new Rectangle(x, y, 64, 64));
		try {
			this.setDed(new Sound("/assets/sound/combat/hitSlime.ogg"));
			left = new SpriteSheet("/assets/art/characters/enemies/pink blob/pinkSlimeIdle.png", 64, 58);
			right = new SpriteSheet("/assets/art/characters/enemies/pink blob/pinkSlimeIdleRight.png", 64, 58);
			animatedSpriteLeft = new Animation(left, 150);
			animatedSpriteRight = new Animation(right, 150);
			patrolStrategy = patrolStrat;


			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initialX = getBody().getX();
		this.speed = 1.3f;
		this.setHp(2);
	} 
	
	
	public PinkSlime(Sound dedSound, int hp, Rectangle body, Image texture ) {
		this.setDed(dedSound);
		this.setHp(hp);
		this.setBody(body);
		this.speed = 0.6f; 
		this.initialX = body.getX();
	} 
	
	
	public void patrol() { 
		
		if(patrolStrategy == 1) {	
			if(getBody().getX() == initialX && flag == 0) {
				vX = -speed;
				flag = 1;
				animatedSprite = animatedSpriteLeft;
			}
			
			if(getBody().getX() > (this.initialX + 60)) { // moving left
				animatedSprite = animatedSpriteLeft;
	
				vX = -speed;
			}
			
			
			if(getBody().getX() < (this.initialX - 30)) { //moving right
				animatedSprite = animatedSpriteRight;
	
				vX = speed;
		
			}
			getBody().setX(getBody().getX() + vX);
		}
		else if(patrolStrategy == 2){
			if(getBody().getX() == initialX && flag == 0) {
				vX = speed;
				flag = 1;
				animatedSprite = animatedSpriteRight;
			}
			
			if(getBody().getX() > (this.initialX + 30)) { // moving left
				animatedSprite = animatedSpriteLeft;

				vX = -speed;
			}
			
			
			if(getBody().getX() < (this.initialX - 60)) { //moving right
				animatedSprite = animatedSpriteRight;

				vX = speed;
		
			}
			getBody().setX(getBody().getX() + vX);

		}

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
