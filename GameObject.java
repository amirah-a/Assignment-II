import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;


public abstract class GameObject {

    protected JPanel panel;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
 
    protected int dx;
    protected int dy;

    public static Animation spriteA;
    public static Image spriteI;
    public static boolean isMoving;

    public GameObject(JPanel p, int xPos, int yPos, int width, int height){
        panel = p;
        x = xPos;
        y = yPos;
        this.width = width;
        this.height = height;
        
        spriteA = null;
        spriteI = null;
    }

    public abstract void loadAnimation();

    public void updateSprite(){
        spriteA.update();
    }

    public void draw(Graphics2D g2){
        g2.drawImage(spriteA.getImage(), x, y, width, height, null);
    }

    public abstract void move();

    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
     }
    
}
