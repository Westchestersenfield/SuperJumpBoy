package Model.inGameObjects;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public abstract class NPC {
	
	protected Sound ded;
	private int hp;
	private long timeSinceLastHit;
	private boolean invincible;
	protected int patrolStrategy;
	
	protected Rectangle body;
	protected SpriteSheet sprite;
	private Animation animatedSpriteRight;
	private Animation animatedSpriteLeft;

	
	public float speed;
	public float vX;
	
	public void updateMovement() {
		
	}

	public Rectangle getBody() {
		return body;
	}

	public void setBody(Rectangle body) {
		this.body = body;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public long getTimeSinceLastHit() {
		return timeSinceLastHit;
	}

	public void setTimeSinceLastHit(long timeSinceLastHit) {
		this.timeSinceLastHit = timeSinceLastHit;
	}

	public abstract Animation getAnimatedSprite();

	public abstract Animation getAnimatedSpriteLeft();
	public abstract Animation getAnimatedSpriteRight();

	public abstract void patrol();


 


}
