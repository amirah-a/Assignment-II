import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.util.Random;

public class Bomb extends GameObject {
    
    private Player player;
    private boolean exploding;
    private Random random;

    public Bomb(JPanel p, int xPos, int yPos, Player player){
        super(p, xPos, yPos, 80, 80);
        
        this.player = player;
        exploding = false;
        random = new Random();

        dx = 0;
        dy = 5;

        loadAnimation();
    }

    public void setLocation() {
        // x = 15;
        // y = random.nextInt (panel.getHeight() - height);
        x = random.nextInt (panel.getWidth() - width);
        y = 15;
     }

    @Override
    public void loadAnimation() {
        String prefix = "images/bomb/expl-";

        spriteA = new Animation(panel);
        
        Image animImage = ImageManager.loadImage("images/bomb/bomb.png");
        spriteA.addFrame(animImage, 300);
        
        for (int i=1; i<=4; i++) {
            String fullPath = prefix + i + ".png";
            animImage = ImageManager.loadImage(fullPath);
            spriteA.addFrame(animImage, 300);
        }
        
    }

    public Animation getAnimation(){
        return spriteA;
    }

    @Override
    public void move() {
        if (!panel.isVisible()) 
            return;
        x = x + dx;
        y = y + dy;
        // if (collidesWithPlayer())
            // play sound
        
        if (collidesWithPlayer() || y > panel.getHeight()){
            setLocation();
            //  IF ITS MOVING DY BUT I UPDATE DX THEN IT MOVES DIAGONAL
            dy += 5;    // speed up alien when it is re-generated at top
        }    
    }

    public boolean collidesWithPlayer(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();

        return myRect.intersects(playerRect);
    }
    
}
