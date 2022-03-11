import javax.swing.JPanel;
import java.awt.Image;import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel {
   
	private static int NUM_ALIENS = 3;

	private Player player;
	SoundManager soundManager;

	private GameThread gameThread;

	private BufferedImage image;
   	private Image backgroundImage;
	private Animation sprite;				// blinking face

	public GamePanel () {
		player = null;
		soundManager = SoundManager.getInstance();

      	backgroundImage = ImageManager.loadImage ("images/BG.png");
		image = new BufferedImage (800, 400, BufferedImage.TYPE_INT_RGB);
		sprite = null;

	}


	private void createGameEntities() {
		// create sprites and effects here
		player = new Player(this, 100, 290);
		sprite = player.getPlayer();
	}

	public void updatePlayer (int direction) {

		if (player != null) {
			if (direction == 0)
				sprite.update();
			else
				player.move(direction);			
		}

	}


	// public boolean isOnBat (int x, int y) {
	// 	return bat.isOnBat(x, y);
	// }


	public void startGame() {				// initialise and start the game thread 

		Thread thread;

		if (gameThread == null) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new GameThread (this);
			thread = new Thread (gameThread);			
			thread.start();
		}
	}


	public void restartGame() {				// initialise and start a new game thread 

		Thread thread;

		if (gameThread == null || !gameThread.isRunning()) {
			//soundManager.playClip ("background", true);
			createGameEntities();
			gameThread = new GameThread (this);
			thread = new Thread (gameThread);			
			thread.start();
			sprite.start();
		}
	}


	public void pauseGame() {				// pause the game (don't update game entities)
		gameThread.pauseGame();
	}


	public void endGame() {					// end the game thread
		gameThread.endGame();
		soundManager.stopClip ("background");
	}


	public void gameUpdate () {

		player.updateSprite();
		// player.move(1)
	}


	public void gameRender () {				// draw the game objects 

		
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		player.draw(imageContext);

		imageContext.drawImage(backgroundImage, 0, 0, null);	// draw the background image		
		
		// draw sprites, or update image animation/effects here
		player.draw(imageContext);

		

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, 800, 400, null);

		g2.setColor(Color.BLACK);
		g2.draw(player.getPlayer().getBoundingRectangle());

		imageContext.dispose();
		g2.dispose();

	}

}