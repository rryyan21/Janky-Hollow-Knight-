import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlayerBullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerBullet extends Bullet
{
    
    public void act()
    {
        move(speed);
        touchingEnemy();
    }
    
    public void touchingEnemy(){
        if(isTouching(Enemy.class)){
            removeTouching(Enemy.class);
            getWorld().removeObject(this);
        } else if(isTouching(Aspid.class)){
            removeTouching(Aspid.class);
            getWorld().removeObject(this);
        } else if(isTouching(AspidBullet.class)){
            removeTouching(AspidBullet.class);
        } else if(isTouching(Boss.class)){
            
        }
    }
}
