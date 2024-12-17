/****************************************
 * A small remake of Hollow Knight      *
 * int greenfoot. Complete with a boss  *
 * fight and abilities!                 *	
 * ICS4U1			        *
 * Ryan Gupta            	        *
 * January 23 2024    		        *
 * *************************************/


import greenfoot.*;
import java.util.List;

public class RandomlyGeneratingDungeon extends World
{   
    Link player = new Link();
    static GreenfootSound backgroundMusic = new GreenfootSound("ReflectionHK.mp3");
    boolean bgMusic = true;
    public RandomlyGeneratingDungeon()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(680, 680, 1, false); 
        addObject(player,getWidth()/2,getHeight()/2+20);
        addObject(new FadeOverlay(),getWidth()/2,getHeight()/2);
        addObject(new Wall(getWidth()*2,40),getWidth(),20);
        addObject(new Wall(getWidth()*2,40),getWidth(),getHeight()-20);
        addObject(new Wall(40,getHeight()),getWidth()-20,getHeight()-340);
        addObject(new Wall(40,getHeight()),20,getHeight()/2);
        addObject(new Wall(40,getHeight()),getWidth()*2-20,getHeight()/2);
        addObject(new Husk(), 200, 200);
        addObject(new Husk(), 600, 100);
        addObject(new Aspid(), 500,500);
        addObject(new Chest(), 600,600);
        addObject(new GlowySpirit(),100,575);
        testDungeon();
        paintOrder();
    }
    Class[] all = {Wall.class,Block.class,Lava.class,Water.class};
    public void scroll(String direction){
        int v=0;
        int h=0;
        if (direction.equals("left"))h=getWidth();
        if (direction.equals("right"))h=-getWidth();
        if (direction.equals("up"))v=getHeight();
        if (direction.equals("down"))v=-getHeight();
        int a=0;
        while (a<all.length){
            List object = getObjects(all[a]);
            if (!object.isEmpty()){
                for (int i=0; i<object.size(); i++){
                    Actor Object = (Actor) object.get(i);
                    Object.setLocation(Object.getX()+h,Object.getY()+v);
                }
            }
            a++;
        }
    }

    public void paintOrder(){
        setPaintOrder(Heart.class,Link.class,Bullet.class,Boss.class, Enemy.class, Aspid.class, AspidBullet.class,Chest.class,FadeOverlay.class,Wall.class,Block.class,Lava.class,Water.class);
    }

    public void act(){
        if(bgMusic){
        backgroundMusic.play();
    }
        paintOrder();
        if (tileset==0)setBackground(new GreenfootImage("GreenTile.png"));
        if (tileset==1)setBackground(new GreenfootImage("BlueTile.png"));
        if (tileset==2)setBackground(new GreenfootImage("AquaTile.png"));
        if (tileset==3)setBackground(new GreenfootImage("YellowTile.png"));
        if (tileset==4)setBackground(new GreenfootImage("GreyTile.png"));
    }

    public void testDungeon(){
        //block structure left
        block(3,6,true,1,2);
        block(4,8,true);
        block(4,4);
        block(4,6,true,-1);
        block(5,6);
        
        //block structure right
        block(12,4);
        block(12,3);
        block(12,2);
        block(12,1);
        block(13,4);
        block(14,4);
        block(15,4);

        //lavapool
        lava(13,10);
        lava(13,8);
        lava(13,9);
        lava(14,10);
        lava(14,9);
        lava(14,8);
        lava(15,8);
        lava(15,9);
        lava(15,10);

        //waterpool
        water(1,1);
        water(1,2);
        water(1,3);
        water(2,1);
        water(2,3);
        water(3,1);
        water(3,3);
    }

    /**
    Tile Sets
    ---------
    0 = Green
    1 = Blue
    2 = Aqua
    3 = Yellow
    4 = Grey
     */
    int tileset = 0;
    public void changeTileSet(int i){
        tileset=i;
    }

    public void clearDungeonRoom(){
        Class[] objects = {Block.class,Wall.class}; //List of objects that will be cleared
        int object = 0;
        int i = 0;
        while (object<objects.length){
            List Object = getObjects(objects[object]);
            if (! Object.isEmpty() && (Actor) Object.get(0)!=null){
                while (i<Object.size()){
                    removeObject((Actor) Object.get(i));
                    i++;
                }
            }
            object++;
            i=0;
        }
    }

    public void generateDungeonEnemies(){

    }

    //Dungeon Tile Methods

    public void block(int x, int y, boolean movable, int event, int keypos){
      if (x*40+20<0||x*40+20>getWidth()||getHeight()-y*40-20<0||getHeight()-y*40-20>getHeight()){
            System.out.println("ERROR: BLOCK AT ("+x+","+y+") IS OUT OF WORLD RANGE"); return;
        }
        addObject(new Block(movable,event,keypos),x*40+20,getHeight()-y*40-20);
    }

    public void block(int x, int y, boolean movable, int event){
        if (x*40+20<0||x*40+20>getWidth()||getHeight()-y*40-20<0||getHeight()-y*40-20>getHeight()){
            System.out.println("ERROR: BLOCK AT ("+x+","+y+") IS OUT OF WORLD RANGE"); return;
        }
        addObject(new Block(movable,event,-1),x*40+20,getHeight()-y*40-20);
    }

    public void block(int x, int y, boolean movable){
        if (x*40+20<0||x*40+20>getWidth()||getHeight()-y*40-20<0||getHeight()-y*40-20>getHeight()){
            System.out.println("ERROR: BLOCK AT ("+x+","+y+") IS OUT OF WORLD RANGE"); return;
        }
        addObject(new Block(movable,0,-1),x*40+20,getHeight()-y*40-20);
    }

    public void block(int x, int y){
        if (x*40+20<0||x*40+20>getWidth()||getHeight()-y*40-20<0||getHeight()-y*40-20>getHeight()){
            System.out.println("ERROR: BLOCK AT ("+x+","+y+") IS OUT OF WORLD RANGE"); return;
        }
        addObject(new Block(false,0,-1),x*40+20,getHeight()-y*40-20);
    }
    //Liquids
    public void lava(int x, int y){
        if (x*40+20<0||x*40+20>getWidth()||getHeight()-y*40-20<0||getHeight()-y*40-20>getHeight()){
            System.out.println("ERROR: LAVA AT ("+x+","+y+") IS OUT OF WORLD RANGE"); return;
        }
        addObject(new Lava(),x*40+20,getHeight()-y*40-20);
    }

    public void water(int x, int y){
        if (x*40+20<0||x*40+20>getWidth()||getHeight()-y*40-20<0||getHeight()-y*40-20>getHeight()){
            System.out.println("ERROR: WATER AT ("+x+","+y+") IS OUT OF WORLD RANGE"); return;
        }
        addObject(new Water(),x*40+20,getHeight()-y*40-20);
    }
}
