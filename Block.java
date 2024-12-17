import greenfoot.*;

public class Block extends Actor
{
    int xmove=0;
    int ymove=0;
    int blockMoves=0;
    int event=0;//Determinds which event activate apon moving.
    int keypos=-1;
    /**
    -1 means the block is infinitely movable
    0 means no event is triggered
    1 means a key is dropped from the ceiling in a random location
    2 means a closed door is opened
    3 means an item appears
    */
    boolean infiniteMovable=false;//If false, it will not let move from being true
    boolean allowedMove=false;//If true, it will allow the block to be pushed
    public Block(boolean b, int e, int k){
        infiniteMovable=b;
        allowedMove=b;
        event=e;
        keypos=k;
    }
    public void act(){
        if (infiniteMovable==false){allowedMove=false;}
        if (allowedMove==true){collisionDetection();}
        
        if (event==-1&&blockMoves==0){allowedMove=true;}
        
        if (blockMoves != 0){
            setLocation(getX()+xmove,getY()+ymove);
            blockMoves--;
        }else{
            xmove=0;
            ymove=0;
        }
    }
    public void collisionDetection()
    {
        Class[] objects = {Link.class}; //All classes in this array are able to push this block
        int collisionAmount=0;
        int m=2; //How many cells the block travels every movement
        while (collisionAmount<objects.length){
            int range=13;//range of cells the object has to be within in a givin axis order to push the object
            //Down check
            Actor down = getOneObjectAtOffset(0, getImage().getHeight()/2+2,objects[collisionAmount]);
            if (down != null && down.getX() > getX() - range && down.getX() < getX() + range && Greenfoot.isKeyDown("w")){ymove=-m;}
            //Up check
            Actor up = getOneObjectAtOffset(0, -getImage().getHeight()/2-2,objects[collisionAmount]);
            if (up != null && up.getX() > getX() - range && up.getX() < getX() + range && Greenfoot.isKeyDown("s")){ymove=m;}
            //Left check
            Actor left = getOneObjectAtOffset(-getImage().getWidth()/2-2, 0,objects[collisionAmount]);
            if (left != null && left.getY() > getY() - range && left.getY() < getY() + range && Greenfoot.isKeyDown("d")){xmove=m;}
            //Right check
            Actor right = getOneObjectAtOffset(getImage().getWidth()/2+2, 0,objects[collisionAmount]);
            if (right != null && right.getY() > getY() - range && right.getY() < getY() + range && Greenfoot.isKeyDown("a")){xmove=-m;}
            collisionAmount++;
        }
        collisionAmount=0;
        
        /**This checks if there is an object in the blocks way when it moves*/
        
        Class[] blocks = {Wall.class,Block.class,Lava.class,Water.class,Water.class}; //All classes in this array can prevent the block from moving
        while (collisionAmount<blocks.length){
            //Down check
            for (int i = -getImage().getWidth()/2+2; i<getImage().getWidth()/2-2; i++){
                Actor object = getOneObjectAtOffset(i, getImage().getHeight()/2+20,blocks[collisionAmount]);
                if (object != null && ymove > 0){negateMoving();}
            }
            //Up check
            for (int i=-getImage().getWidth()/2+2; i<getImage().getWidth()/2-2; i++){
                Actor object = getOneObjectAtOffset(i, -getImage().getHeight()/2-20,blocks[collisionAmount]);
                if (object != null && ymove < 0){negateMoving();}
            }
            //Left check
            for (int i=-getImage().getHeight()/2+2; i<getImage().getHeight()/2-2; i++){
                Actor object = getOneObjectAtOffset(-getImage().getWidth()/2-20, i,blocks[collisionAmount]);
                if (object != null && xmove < 0){negateMoving();}
            }
            //Right check
            for (int i=-getImage().getHeight()/2+2; i<getImage().getHeight()/2-2; i++){
                Actor object = getOneObjectAtOffset(getImage().getWidth()/2+20, i,blocks[collisionAmount]);
                if (object != null && xmove > 0){negateMoving();}
            }
            collisionAmount++;
        }
        if (xmove!=0||ymove!=0){allowedMove=false; blockMoves=20;} //Moves is the amount of times the block will move before stopping
    }
    public void negateMoving(){
        xmove=0;
        ymove=0;
    }
}
