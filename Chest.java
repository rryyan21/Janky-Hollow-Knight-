import greenfoot.*;

public class Chest extends Actor {
    protected boolean isOpen = false;
    public boolean bossFight = false;
    static GreenfootSound bossMusic = new GreenfootSound("falseKnightMusic.mp3");
    GreenfootSound bossIntro = new GreenfootSound("bossIntro.mp3");


    public void act() {
        checkForPlayer();
    }

    protected void checkForPlayer() {
        Link player = (Link) getOneIntersectingObject(Link.class);
        if (player != null && !isOpen) { //if chest not opened and player opened it
            openChest(player);
        }
    }

    protected void openChest(Link player) {
        isOpen = true;
        bossIntro.play();
        player.grantDashAbility();
        player.addLives(3);
        showMessage();
    }

    private void showMessage() {
        getWorld().showText("Mothwing Cloak - Click SPACE to Dash!", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
        Greenfoot.delay(100);
        getWorld().showText("", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
        RandomlyGeneratingDungeon.backgroundMusic.stop();
        spawnBoss();
    }

    public void spawnBoss(){
        Boss boss = new Boss();
        getWorld().addObject(boss,100,250);
        Greenfoot.delay(300);
        getWorld().showText("The False Knight...", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
        Greenfoot.delay(300);
        getWorld().showText("", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
        bossIntro.stop();
        bossMusic.play();
    }
}
