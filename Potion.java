import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Potion {
    private GamePanel panel;

    private int x;

    private int y;
    private int width;

    private int height;
    private int dy;
    private Image sprite;
    private static RotateFX rotateFX;

    public Potion(GamePanel p, int x, int y){
        panel = p;
        this.x = x;
        this.y = y;

        sprite = ImageManager.loadImage("images/potion.png");
        width = 20;
        height = 33;

        dy = 25;
    }


    public void move(){
        rotateFX = new RotateFX(panel);
        y -= dy;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(sprite, x, y, width, height, null);
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


}
