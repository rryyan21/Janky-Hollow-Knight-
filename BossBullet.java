import greenfoot.*;

public class BossBullet extends Bullet {
    private int speedX;
    private int speedY;

    public void act() {
        move();
        touchingPlayer();
    }

    public void setSpeed(int speedX, int speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    private void move() {
        setLocation(getX() + speedX, getY() + speedY);
    }

    public void touchingPlayer(){
        if(isTouching(Link.class)){  //if touching player then remove both
            removeTouching(Link.class);
            getWorld().removeObject(this);

        }
    }
}
