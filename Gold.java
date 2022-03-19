import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.util.Random;


public class Gold {

    private JPanel panel;
    private int x;
    private int y;
    private int width;
    private int height;
 
    private int dx;
    private int dy;

    private Image sprite;
    private  boolean isMoving;
    private  Player player;
    private Random random;


    
    public Gold(JPanel p, int xPos, int yPos, Player player){
        panel = p;
        x = xPos;
        y = yPos;
        
        width = 48;
        height = 48;

        this.player = player;

        sprite = ImageManager.loadImage("images/gold.png");
        
        dx = 0;
        dy = 5;

        random = new Random();
    }

    public void setLocation() {
        // x = 15;
        // y = random.nextInt (panel.getHeight() - height);
        x = random.nextInt (panel.getWidth() - width);
        y = 15;
     }


     public void draw(Graphics2D g2){
        g2.drawImage(sprite, x, y, width, height, null);
    }
    
    public void move() {
        if (!panel.isVisible())
            return;
        
        x = x + dx;
        y = y + dy;

    // if (collidesWithPlayer())
    //     // play sound
    //     //  IF ITS MOVING DY BUT I UPDATE DX THEN IT MOVES DIAGONAL
    //     dy += 5;    // speed up alien when it is re-generated at top
    
    if (collidesWithPlayer() || y > panel.getHeight())
        setLocation();
        
    }

    public boolean collidesWithPlayer(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();

        return myRect.intersects(playerRect);
    }

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }
    
}
