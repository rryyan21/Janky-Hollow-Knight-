import greenfoot.*;

public class Boss extends Actor {
    private GreenfootImage falseKnightLeft = new GreenfootImage("falseKnightLeft.png");
    private GreenfootImage falseKnightRight = new GreenfootImage("falseKnightRight.png");
    private GreenfootImage deadBoss = new GreenfootImage("deadBoss.png");

    private int Timer = 0;   // Timer to control movement
    private int speed = 1;
    private int moveDuration = 180;   // 5 seconds (60 frames per second)
    private boolean direction = true;
    private int hits = 0;
    private int enrageMessage = 1; // 0 off 1 on
    private GreenfootSound endingSong = new GreenfootSound("endingSong.mp3");

    public void act() {
        moveBoss();
        images();
        if (Greenfoot.getRandomNumber(100) < 2) { // Adjust the probability as needed
            shoot();
        }
        checkProjectileCollision();
    }

    private void moveBoss() {
        if (Timer < moveDuration) {
            setLocation(getX() + speed, getY());
            Timer++;
        } else {
            // Change direction and reset the timer
            speed = -speed;
            direction = !direction;
            Timer = 0;
        }
    }

    private void images() {
        if (direction) {
            setImage(falseKnightRight);
        } else {
            setImage(falseKnightLeft);
        }
    }

    private void shoot() {
        BossBullet bulletUp = new BossBullet();
        BossBullet bulletDown = new BossBullet();
        BossBullet bulletLeft = new BossBullet();
        BossBullet bulletRight = new BossBullet();

        getWorld().addObject(bulletUp, getX(), getY() - 20);
        getWorld().addObject(bulletDown, getX(), getY() + 20);
        getWorld().addObject(bulletLeft, getX() - 20, getY());
        getWorld().addObject(bulletRight, getX() + 20, getY());

        bulletUp.setSpeed(0, -5);
        bulletDown.setSpeed(0, 5);
        bulletLeft.setSpeed(-5, 0);
        bulletRight.setSpeed(5, 0);

        if (hits >= 3) {
            if (enrageMessage == 1) {
                getWorld().showText("the boss is ENRAGED", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
                Greenfoot.delay(100);
                getWorld().showText("", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
                enrageMessage = 0;
                moveDuration = 90; // Move faster during enraged state
                speed += 2; // Increase speed by 2
            }
            // Boss is enraged, shoot in 8 directions
            BossBullet bulletUpLeft = new BossBullet();
            BossBullet bulletUpRight = new BossBullet();
            BossBullet bulletDownLeft = new BossBullet();
            BossBullet bulletDownRight = new BossBullet();

            getWorld().addObject(bulletUpLeft, getX() - 15, getY() - 15);
            getWorld().addObject(bulletUpRight, getX() + 15, getY() - 15);
            getWorld().addObject(bulletDownLeft, getX() - 15, getY() + 15);
            getWorld().addObject(bulletDownRight, getX() + 15, getY() + 15);

            bulletUpLeft.setSpeed(-3, -3);
            bulletUpRight.setSpeed(3, -3);
            bulletDownLeft.setSpeed(-3, 3);
            bulletDownRight.setSpeed(3, 3);
        }
    }

    private void checkProjectileCollision() {
        if (isTouching(PlayerBullet.class)) {
            removeTouching(PlayerBullet.class); // Remove the projectile
            hits++;
            if (hits == 6) {
                // Boss has been hit three times, implement your logic here
                handleBossDefeat();
            }
        }
    }

    private void handleBossDefeat() {
        getWorld().showText("You beat the False Knight", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
        Greenfoot.delay(100);
        getWorld().showText("", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
        victoryScreen();
        getWorld().removeObject(this);
    }

    public void victoryScreen() {
        Greenfoot.delay(100);
        getWorld().showText("You Win!!!", 300, 300);
        Chest.bossMusic.stop();
        endingSong.play();
    }
}
