import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Image;

public class Torch {
    private JPanel panel;
    private int x;
    private int y;
    private int width;
    private int height;
 
    private int dx;
    private int dy;

    public static Animation torch;
 
    public Torch(JPanel p, int xPos, int yPos){
        panel = p;
        x = xPos;
        y = yPos;
  
        dx = 5;
        dy = 0;
  
        width = 50;
        height = 136;

        loadAnimation();
    }

    public void loadAnimation( ){
        String prefix = "images/torch/torch_";

        torch = new Animation(panel);

        for (int i=1; i<14; i++) {
            String fullPath = prefix + i + ".png";
            Image animImage = ImageManager.loadImage(fullPath);
            torch.addFrame(animImage, 150);
        }
    }

    public Animation getPlayer(){
        return torch;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(torch.getImage(), x, y, width, height, null);
    }

    public void updateSprite(){
        torch.update();
    }

    // public void move(int direction){

    // }

    // public boolean isOnPlayer (int x, int y) {
    //     if (sprite == null)
    //           return false;
  
    //     Rectangle2D player = sprite.getBoundingRectangle();
    //     return player.contains(x, y);
    // }

    
}
