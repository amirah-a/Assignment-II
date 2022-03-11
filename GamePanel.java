import javax.swing.JPanel;
import java.awt.Image;import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel {
   
	private static int NUM_TORCHES = 2;

	private Player player;
	private Torch[] torches;
	SoundManager soundManager;

	private GameThread gameThread;

	private BufferedImage image;
   	private Image backgroundImage;

	public GamePanel () {
		player = null;
		torches = new Torch[NUM_TORCHES];

		soundManager = SoundManager.getInstance();

      	backgroundImage = ImageManager.loadImage ("images/BG.png");
		image = new BufferedImage (800, 400, BufferedImage.TYPE_INT_RGB);

	}


	private void createGameEntities() {
		// create sprites and effects here
		player = new Player(this, 100, 290);

		torches[0] = new Torch(this, 10, 40);
		torches[1] = new Torch(this, 750, 40);
	}

	public void updatePlayer (int direction) {

		if (player != null) {
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
			Player.sprite.start();
			Torch.torch.start();
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
		for (int i=0; i<NUM_TORCHES; i++)
			torches[i].updateSprite();

	}


	public void gameRender () {				// draw the game objects 

		
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		player.draw(imageContext);

		imageContext.drawImage(backgroundImage, 0, 0, null);	// draw the background image		
		
		// draw sprites, or update image animation/effects here
		player.draw(imageContext);
		for (int i=0; i<NUM_TORCHES; i++)
			torches[i].draw(imageContext);

		

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, 800, 400, null);

		g2.setColor(Color.BLACK);
		g2.draw(player.getPlayer().getBoundingRectangle());

		imageContext.dispose();
		g2.dispose();

	}

}