import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Aspidd here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Aspid extends Enemy
{
    private GreenfootImage AspidLeft = new GreenfootImage("AspidLeft.png");
    private GreenfootImage AspidRight = new GreenfootImage("AspidRight.png");
    int followRange = 150;
    int speed = 2;

    public void act()
    {
        movingEnemy();
        images();
        shootPlayer();
    }
    
      public void images(){
        if(direction){
            setImage(AspidLeft);
        }
        if(!direction){
            setImage(AspidRight);
        }
    }
    
    public void shootPlayer() {
        List<Link> playersInRange = getObjectsInRange(followRange, Link.class);

        if (!playersInRange.isEmpty()) {
            Link player = playersInRange.get(0);  
            turnTowards(player.getX(), player.getY());
            Actor bullet = new AspidBullet();
            getWorld().addObject(bullet, getX(), getY());
            bullet.turnTowards(player.getX(), player.getY());
        }
    }
}
