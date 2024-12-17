import greenfoot.*;

public class GlowySpirit extends Chest {
    protected boolean foundSpirit = false;

    public void act() {
        checkForPlayer();
    }

    protected void checkForPlayer() {
        Link player = (Link) getOneIntersectingObject(Link.class);
        if (player != null && !foundSpirit) {  //same thing as chest
            openChest(player);
        }
    }

    protected void openChest(Link player) {
        foundSpirit = true;
        player.grantSpiritAbility(); 
        showMessage();
    }
    
    private void showMessage() {
        getWorld().showText("Vengeful Spirit - click to shoot!", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50);
        Greenfoot.delay(100);
        getWorld().showText("", getWorld().getWidth() / 2, getWorld().getHeight() / 2 - 50); // Clear the message
    }
}
