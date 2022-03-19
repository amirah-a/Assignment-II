import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import java.awt.Image;import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;

/**
   A component that displays all the game entities
*/

public class GamePanel extends JPanel {
   
	private static int NUM_TORCHES = 2;
	private static int NUM_BOMBS = 4;
	private static int NUM_GOLD = 2;

	private Player player;
	private Torch[] torches;
	private Bomb[] bombs;
	private Gold[] gold;
	public static ArrayList <Potion> potions;
	private Potion tempP;
	RotateFX rotateFX;
	SoundManager soundManager;

	private GameThread gameThread;

	private BufferedImage image;
   	private Image backgroundImage;

	public GamePanel () {
		player = null;
		bombs = new Bomb[NUM_BOMBS];
		gold = new Gold[NUM_GOLD];
		torches = new Torch[NUM_TORCHES];
		potions = new ArrayList<Potion>();
		tempP = null;

		soundManager = SoundManager.getInstance();

      	backgroundImage = ImageManager.loadImage ("images/BG.png");
		image = new BufferedImage (800, 400, BufferedImage.TYPE_INT_RGB);

	}


	private void createGameEntities() {
		// create sprites and effects here
		player = new Player(this, 100, 290);

		torches[0] = new Torch(this, 10, 40);
		torches[1] = new Torch(this, 750, 40);	

		int count = 100;
		for (int i=0; i<NUM_BOMBS; i++)
			bombs[i] = new Bomb(this, count+50, 10, player);

		count = 150;
		for (int i=0; i<NUM_GOLD; i++){
			gold[i] = new Gold(this, count+100, 20, player);

		}
		rotateFX = new RotateFX(this);
	
	}

	public void movePlayer (int direction) {

		if (player != null) {
			player.move(direction);			
		}

	}

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
		
		for(int i=0; i<potions.size(); i++){
			tempP = potions.get(i);
			tempP.move();
			
				
			if(tempP.getX() > this.getWidth() || tempP.getX() < 0)
				potions.remove(tempP);
		}
				

		for (int x=0; x<NUM_GOLD; x++){
			gold[x].move();
		}

		for (int i=0; i<NUM_TORCHES; i++)
			torches[i].updateSprite();
		
			for (int x=0; x<NUM_BOMBS; x++){
				bombs[x].move();
				
				// collides with potion
				for(int j=0; j<potions.size(); j++){

					if(bombs[x].collidesWithPotion(potions.get(j))){
						bombs[x].setLocation();
					}
				}
		}

		for(int x=0; x<NUM_GOLD; x++){
			if (gold[x].collidesWithPlayer()){
				gold[x].setLocation();
			}
		}

	}


	public void gameRender () {				// draw the game objects 

		
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		player.draw(imageContext);

		imageContext.drawImage(backgroundImage, 0, 0, null);	// draw the background image		
		
		// draw sprites, or update image animation/effects here
		player.draw(imageContext);
		for (int i=0; i<NUM_TORCHES; i++)
			torches[i].draw(imageContext);

		
		for (int x=0; x<NUM_BOMBS; x++){
			bombs[x].draw(imageContext);
		}

		for (int x=0; x<NUM_GOLD; x++){
			gold[x].draw(imageContext);
		}

		if (potions != null) {
			for(int x=0; x<potions.size(); x++){
				potions.get(x).draw(imageContext);
			}	
		}

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, 800, 400, null);


		imageContext.dispose();
		g2.dispose();

	}

}