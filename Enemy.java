import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Eneemy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemy extends Actor
{
    boolean direction = false;
    int randomX = Greenfoot.getRandomNumber(600);
    int randomY = Greenfoot.getRandomNumber(600);
    int speed = 1;
    int followRange = 150;
    
    public void act()
    {
        movingEnemy();
    }
    
    public void movingEnemy(){
        move(speed);
        if(isAtEdge()){
            direction = !direction;
            speed = -speed;
        }
    }
}
