import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


import javax.swing.JPanel;
import java.awt.Image;

public class Player {
    private JPanel panel;
    public static int x;
    public static int y;
    public static int width;
    public static int height;
 
    private int dx;
    private int dy;

    public static Animation animationLR, animationRR, animationLB, animationRB, sprite;
    public static boolean isWalking;
 
    public Player(JPanel p, int xPos, int yPos){
        panel = p;
        x = xPos;
        y = yPos;
  
        dx = 5;
        dy = 0;
  
        width = 80;
        height = 80;

        loadAnimation(1);
        loadAnimation(2);
        loadAnimation(3);
        loadAnimation(4);

        sprite = animationLB;

        isWalking = false;

    }

    public void loadAnimation(int direction){
        String prefix = "images/player/";
        String suffix = "/0_Reaper_Man_";
        String action;

        if (direction == 1){
            animationLR = new Animation(panel);
            action = "Running_";

            for (int i=0; i<11; i++) {
                String fullPath = prefix + "left/Running/" + suffix + action + i + ".png";
                Image animImage = ImageManager.loadImage(fullPath);
                animationLR.addFrame(animImage, 100);
            }
        }

        if (direction == 2){
            animationRR = new Animation(panel);
            action = "Running_";
            for (int i=0; i<11; i++) {
                String fullPath = prefix + "right/Running/" + suffix + action+ i + ".png";
                Image animImage = ImageManager.loadImage(fullPath);
                animationRR.addFrame(animImage, 100);
            }
        }

        if (direction == 3){
            animationLB = new Animation(panel);
            action = "IdleBlinking_";
            for (int i=0; i<17; i++) {
                String fullPath = prefix + "left/IdleBlinking/" + suffix + action + i + ".png";
                Image animImage = ImageManager.loadImage(fullPath);
                animationLB.addFrame(animImage, 100);
            }
        }

        if (direction == 4){
            animationRB = new Animation(panel);
            action = "IdleBlinking_";
            for (int i=0; i<17; i++) {
                String fullPath = prefix + "right/IdleBlinking" + suffix + action + i + ".png";
                Image animImage = ImageManager.loadImage(fullPath);
                animationRB.addFrame(animImage, 100);
            }
        }
    }

    public Animation getPlayer(){
        return sprite;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(sprite.getImage(), x, y, width, height, null);
    }

    public void updateSprite(){
        sprite.update();
    }

    public void move(int direction){
        if (!panel.isVisible ()) 
            return;

        if (!isWalking)
            return;
      
        if (direction == 1) {		// move left
            sprite = animationLR;
            x = x - dx;
            
            if (x < 0)
              x = -38;
        }	
        else				// move right
        if (direction == 2) {
            sprite = animationRR;
            x = x + dx;
            
            if (x+75 > panel.getWidth())
              x = panel.getWidth() - 75;
        }

    }

    public boolean isOnPlayer (int x, int y) {
        if (sprite == null)
              return false;
  
        Rectangle2D player = getBoundingRectangle();
        return player.contains(x, y);
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }
    
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean collidesWithBomb(Bomb bomb){
        Rectangle2D.Double player = getBoundingRectangle();
        Rectangle2D.Double bombRect = bomb.getBoundingRectangle();
        return player.intersects(bombRect);
    }
}
