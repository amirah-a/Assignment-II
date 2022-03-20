import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.util.Random;


public class Bomb {

    private JPanel panel;
    private int x;
    private int y;
    private int width;
    private int height;
 
    private int dx;
    private int dy;

    private Image sprite;
    private  Player player;
    private Random random;
    
    public Bomb(JPanel p, int xPos, int yPos, Player player){
        panel = p;
        x = xPos;
        y = yPos;
        
        width = 48;
        height = 48;

        this.player = player;

        sprite = ImageManager.loadImage("images/bomb/bomb.png");
        
        dx = 0;
        dy = 2;

        random = new Random();
    }

    public void setLocation() {
        x = random.nextInt (panel.getWidth() - width);
        y = 0;
     }


     public void draw(Graphics2D g2){
        g2.drawImage(sprite, x, y, width, height, null);
    }
    
    public void move() {
        if (!panel.isVisible())
            return;
        
        x = x + dx;
        y = y + dy;

        if (y > panel.getHeight())
            setLocation();
        
    }
    
    public boolean collidesWithPlayer(){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double playerRect = player.getBoundingRectangle();

        return myRect.intersects(playerRect);
    }
    public boolean collidesWithPotion(Potion p){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double potionRect = p.getBoundingRectangle();

        return myRect.intersects(potionRect);
    }

    public boolean collidesWithGold(Gold g){
        Rectangle2D.Double myRect = getBoundingRectangle();
        Rectangle2D.Double goldrRect = g.getBoundingRectangle();

        return myRect.intersects(goldrRect);
    }


    public Rectangle2D.Double getBoundingRectangle() {
        return new Rectangle2D.Double (x, y, width, height);
    }
        	
	public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void increaseDY(){
        dy += 1;
    }
}
