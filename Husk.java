import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Husk here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Husk extends Enemy
{
    private GreenfootImage huskLeft = new GreenfootImage("huskLeft.png");
    private GreenfootImage huskRight = new GreenfootImage("huskRight.png");
    private GreenfootImage huskRunningLeft = new GreenfootImage("huskRunningLeft.png");
    private GreenfootImage huskRunningRight = new GreenfootImage("huskRunningRight.png");

    public void act()
    {
        images();
        enemyTouchPlayer();
        spawnEnemy();
        followPlayer();
        movingEnemy();
    }

    public void images(){
        if(direction){
            setImage(huskLeft);
        }
        if(!direction){
            setImage(huskRight);
        }
    }

    public void enemyTouchPlayer(){
        Actor found = getOneIntersectingObject(Link.class);
        if(isTouching(Link.class)){ //if is touching then remove
            getWorld().removeObject(found);
        }
    }

    public void spawnEnemy(){
        if(Greenfoot.isKeyDown("t")){ //when "t" is clicked spawn multiple enemies (crashes the game)
            Husk clickedEnemy = new Husk();
            getWorld().addObject(clickedEnemy, randomX, randomY);
            clickedEnemy.setRotation(randomX);
        }
    }

    public void followPlayer() {
        List<Link> playersInRange = getObjectsInRange(followRange, Link.class); //make a list of everything in their range

        if (!playersInRange.isEmpty()) { //if the list is not empty
            Link player = playersInRange.get(0);  // Get the first thing in the list(player)
            if(direction){
                setImage(huskRunningLeft); 
            }
            if(!direction){
                setImage(huskRunningRight);
            }
            turnTowards(player.getX(), player.getY());
            move(speed); 
        }
    }
}
