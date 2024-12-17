import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AspidBulle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AspidBullet extends Bullet
{
   int speed = 3;
    public void act()
    {
        move(speed);
    }
    public void touchingPlayer(){
        if(isTouching(Link.class)){
            removeTouching(Link.class);
            getWorld().removeObject(this);
        }
    }
}
