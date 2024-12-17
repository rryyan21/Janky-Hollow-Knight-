import greenfoot.*;

public class Link extends Actor {
    private GreenfootImage up = new GreenfootImage("knightUp.png");
    private GreenfootImage down = new GreenfootImage("knightDown.png");
    private GreenfootImage right = new GreenfootImage("knightRight.png");
    private GreenfootImage left = new GreenfootImage("knightLeft.png");
    private GreenfootImage dashLeft = new GreenfootImage("dashLeft.png");
    private GreenfootImage dashRight = new GreenfootImage("dashRight.png");
    private String heartImageFull = "maskShard.png";
    private String heartImageEmpty = "brokenMask.png";
    GreenfootSound spiritSound = new GreenfootSound("SpiritSFX.mp3");

    int xmove = 0;
    int xmove2 = 0;
    int ymove = 0;
    int ymove2 = 0;
    int facing; //0 = left 1 = right 2 = up 3 = down
    private static final int dashDistance = 25;
    private boolean isDashing = false;
    boolean redraw = false;
    private int lives = 3; 
    private int maskLives = 3;
    boolean hasDashAbility = false;
    boolean hasSpiritAbility = false;
    int lastShot = 0;

    public void act() {
        handleInput();
        if (isDashing) {
            dash();
        }

        shoot();
        lastShot++;
        if (lives > 0) {
            basicMoving();
            collisionDetection();
            checkLives(); 
        }
        setLocation(getX() + xmove + xmove2, getY() + ymove + ymove2);
        displayLives();
        checkOnScreen();
    }

    public void basicMoving()
    {
        int m=3; //Rate of cells that will be traveled
        //Change movement
        if (Greenfoot.isKeyDown("a") && ymove==0){
            facing = 0;
            setImage(left);
            xmove=-m; 
        }
        if (Greenfoot.isKeyDown("d") && ymove==0){
            facing = 1;
            setImage(right);
            xmove=m; 
        }
        if (Greenfoot.isKeyDown("w") && xmove==0){
            facing = 2;
            setImage(up);
            ymove=-m; 
        }
        if (Greenfoot.isKeyDown("s") && xmove==0){
            facing = 3;
            setImage(down);
            ymove=m; 
        }
        if (!Greenfoot.isKeyDown("a") && !Greenfoot.isKeyDown("d")){
            xmove=0;
        }
        if (!Greenfoot.isKeyDown("w") && !Greenfoot.isKeyDown("s")){
            ymove=0;
        }
    }
    static String direction="up";

    Class[] objects = {Wall.class,Block.class,Lava.class,Water.class};
    int collisionAmount=0;
    public void collisionDetection()
    {
        while (collisionAmount<objects.length){
            //Down check
            for (int i=-getImage().getWidth()/2+2; i<getImage().getWidth()/2-2; i+=4){
                Actor object = getOneObjectAtOffset(i, getImage().getHeight()/2+2,objects[collisionAmount]);
                if (object!=null&&ymove>0){i=-getImage().getWidth()/2+2; ymove=0; setLocation(getX(),object.getY()-object.getImage().getHeight()/2-getImage().getHeight()/2);}
            }
            //Up check
            for (int i=-getImage().getWidth()/2+2; i<getImage().getWidth()/2-2; i+=4){
                Actor object = getOneObjectAtOffset(i, -getImage().getHeight()/2-3,objects[collisionAmount]);
                if (object!=null&&ymove<0){i=-getImage().getWidth()/2+2; ymove=0; setLocation(getX(),object.getY()+object.getImage().getHeight()/2+getImage().getHeight()/2);}
            }
            //Left check
            for (int i=-getImage().getHeight()/2+2; i<getImage().getHeight()/2-2; i+=4){
                Actor object = getOneObjectAtOffset(0-getImage().getWidth()/2-3, i,objects[collisionAmount]);
                if (object!=null&&xmove<0){i=-getImage().getHeight()/2+2; xmove=0; setLocation(object.getX()+object.getImage().getWidth()/2+getImage().getWidth()/2,getY());}
            }
            //Right check
            for (int i=-getImage().getHeight()/2+2; i<getImage().getHeight()/2-2; i+=4){
                Actor object = getOneObjectAtOffset(getImage().getWidth()/2+2, i,objects[collisionAmount]);
                if (object!=null&&xmove>0){i=-getImage().getHeight()/2+2; xmove=0; setLocation(object.getX()-object.getImage().getWidth()/2-getImage().getWidth()/2,getY());}
            }
            collisionAmount++;
        }
        collisionAmount=0;
    }

    public void shoot(){
        if(lastShot >= 180){
            if(Greenfoot.mouseClicked(null) && hasSpiritAbility){
                Actor bullet = new PlayerBullet();
                getWorld().addObject(bullet, getX(), getY());
                spiritSound.play();

                MouseInfo mouse = Greenfoot.getMouseInfo();
                bullet.turnTowards(mouse.getX(), mouse.getY());

                int knockbackDistance = 40;
                int mX = mouse.getX() - getX();
                int mY = mouse.getY() - getY();

                double angle = Math.atan2(mY, mX);
                int knockbackX = (int) (getX() - knockbackDistance * Math.cos(angle));
                int knockbackY = (int) (getY() - knockbackDistance * Math.sin(angle));

                setLocation(knockbackX, knockbackY);
                lastShot = 0;
            }
        }
    }

    private void handleInput() {
        if (hasDashAbility && Greenfoot.isKeyDown("space")) {
            isDashing = true;
        }
        if (Greenfoot.isKeyDown("h")) {
            displayHint("Use your spirit to get through blocks!");
        }
    }

    private void displayHint(String hint) {
        getWorld().showText(hint, getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        Greenfoot.delay(100); 
        getWorld().showText("", getWorld().getWidth() / 2, getWorld().getHeight() / 2); 
    }

    private void dash() {
        if(facing == 0){
            setImage(dashLeft);
            setLocation(getX() - dashDistance, getY());
        }
        if(facing == 1){
            setImage(dashRight);
            setLocation(getX() + dashDistance, getY());
        }
        if(facing == 2){
            setLocation(getX(), getY() - dashDistance);
        }
        if(facing == 3){
            setLocation(getX(), getY() + dashDistance);
        }
        isDashing = false;
    }

    private void displayLives() {
        getWorld().removeObjects(getWorld().getObjects(Heart.class));         // Clear previous heart images

        for (int i = 0; i < maskLives; i++) {
            if (i < lives) {
                getWorld().addObject(new Heart(heartImageFull), 50 + i * 30, 60); 
            } else {
                getWorld().addObject(new Heart(heartImageEmpty), 45 + i * 30, 65); 
            }
        }
    }

    private void checkLives() {
        if (isTouching(Enemy.class) || isTouching(AspidBullet.class) || isTouching(BossBullet.class)) {
            lives--; 
            setLocation(getWorld().getWidth() / 2, getWorld().getHeight() / 2 + 20); 
            if (lives <= 0) {
                displayGameOver();
            }
        }
    }

    public void addLives(int livesToAdd) {
        lives += livesToAdd;
        if (lives > maskLives) {
            lives = maskLives;  // Ensure lives don't exceed the maximum
        }
        displayLives();
    }

    public void checkOnScreen(){
        if(getX() > 680 || getX() < 0 || getY() < 0 || getY() > 680){
            getWorld().removeObject(this);
            displayGameOver();
        }
    }

    public void displayGameOver() {
        getWorld().showText("Game Over", getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        getWorld().removeObject(this);
    }

    public void grantDashAbility() {
        hasDashAbility = true;
    }

    public void grantSpiritAbility() {
        hasSpiritAbility = true;
    }

}//EOF
