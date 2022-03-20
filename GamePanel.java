import javax.swing.JPanel;

import java.awt.Image;import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Font;

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
	private Image[] lives;
	private int maxLives = 4;

	private DisintegrateFX bombFX;
	private BrightnessFX goldFX;
	private GrayScaleFX bgFX;

	private boolean bright, disintegrate, gray;

	private int currLife;

	SoundManager soundManager;

	private GameThread gameThread;

	private BufferedImage image;
   	private Image backgroundImage;

	private static int score;

	public GamePanel () {
		player = null;
		bombs = new Bomb[NUM_BOMBS];
		gold = new Gold[NUM_GOLD];
		torches = new Torch[NUM_TORCHES];
		potions = new ArrayList<Potion>();
		lives = new Image[5];
		tempP = null;

		currLife = maxLives;

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
		for (int i=0; i<NUM_BOMBS; i++){
			bombs[i] = new Bomb(this, count, 10, player);
			count+=200;
		}
			
		count = 150;
		for (int i=0; i<NUM_GOLD; i++){
			gold[i] = new Gold(this, count+100, 20, player);
			count += 150;
		}
		for(int j=0; j<5; j++){
			lives[j] = ImageManager.loadImage("images/lives/heart" + j +".png");
		}
		score = 0;

		goldFX = new BrightnessFX(this);
		bright = false;

		bombFX = new DisintegrateFX(this);
		disintegrate = false;

		bgFX = new GrayScaleFX(this);
		gray = false;
	}

	public void movePlayer (int direction) {

		if (player != null) {
			player.move(direction);			
		}

	}

	public void startGame() {				// initialise and start the game thread 

		Thread thread;

		if (gameThread == null) {

			
			createGameEntities();
			soundManager.playClip("start", false);			
			gameThread = new GameThread (this);
			thread = new Thread (gameThread);
			thread.start();
			soundManager.playClip ("background", true);
		}
	}


	public void restartGame() {				// initialise and start a new game thread 

		Thread thread;

		if (gameThread == null || !gameThread.isRunning()) {
			soundManager.playClip ("background", true);
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

	public void gameOver() {

		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		
		soundManager.stopClip("background");
		soundManager.playClip("over", false);
		imageContext.drawImage(backgroundImage, 0, 0 , null);
		Font f = new Font ("Roboto", Font.BOLD, 48);
      	imageContext.setFont (f);
      	imageContext.setColor(Color.RED);
		imageContext.drawString("GAME OVER", 270, 215);
		
		player.draw(imageContext);
		
		Font font = new Font ("Roboto", Font.BOLD, 20);
		imageContext.setColor(Color.WHITE);
		imageContext.setFont (font);
		imageContext.drawString(String.valueOf(score), 390, 20);
		
		imageContext.drawImage(lives[currLife], 374, 35, null);

		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, 800, 400, null);

		imageContext.dispose();
		g2.dispose();
	}


	public void gameUpdate () {
		player.updateSprite();
		
		goldFX.update();
		if(!goldFX.isActive())
			bright = false;

		bombFX.update();
		if(!bombFX.isActive())
			disintegrate = false;
		
		if (gray)
			bgFX.update();
		if(!bgFX.isActive())
			gray = false;

		for(int i=0; i<potions.size(); i++){
			tempP = potions.get(i);
			tempP.move();
			
				
			if(tempP.getX() > this.getWidth() || tempP.getX() < 0)
				potions.remove(tempP);
		}
				

		for (int i=0; i<NUM_TORCHES; i++)
			torches[i].updateSprite();
		
		for (int x=0; x<NUM_BOMBS; x++){
			bombs[x].move();
			
			if(player.collidesWithBomb(bombs[x])){
				gray = true;
				score -= 50;

				if(currLife > 0)
					currLife--;
				
				if (currLife  == 0){
					gameThread.setIsRunning(false);
				}

				if (score < 0){
					score = 0;
					gameThread.setIsRunning(false);
				}
					
				soundManager.playClip("explosion", false);
				bombs[x].setLocation();
				bombs[x].increaseDY();
				

			}

			// collides with potion
			for(int j=0; j<potions.size(); j++){
				if(bombs[x].collidesWithPotion(potions.get(j))){

					disintegrate = true;
					bombFX.setXY(potions.get(j).getX(), potions.get(j).getY());
					score += 5;

					bombs[x].setLocation();
				}
			}
		}

		for(int x=0; x<NUM_GOLD; x++){
			gold[x].move();
			if (gold[x].collidesWithPlayer()){
				soundManager.playClip("collect", false);
				
				bright = true;
				goldFX.setXY(gold[x].getX(), gold[x].getY());
				
				gold[x].setLocation();
				score = score + 50;
			}
		}

	}


	public void gameRender () {				// draw the game objects 

		
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		imageContext.setColor(Color.WHITE);
		player.draw(imageContext);



		imageContext.drawImage(backgroundImage, 0, 0, null);	// draw the background image		
		if (gray)
			bgFX.draw(imageContext);
			
		// draw sprites, or update image animation/effects here
		player.draw(imageContext);

		if(bright)
			goldFX.draw(imageContext);
		
		if(disintegrate)
			bombFX.draw(imageContext);

		Font font = new Font ("Roboto", Font.BOLD, 20);
		imageContext.setFont (font);
		imageContext.drawString(String.valueOf(score), 390, 20);

		for (int i=0; i<NUM_TORCHES; i++)
			torches[i].draw(imageContext);

		
		for (int x=0; x<NUM_BOMBS; x++){
			bombs[x].draw(imageContext);
		}

		for (int j=0; j<NUM_GOLD; j++){
			gold[j].draw(imageContext);
		}

		if (potions != null) {
			for(int x=0; x<potions.size(); x++){
				potions.get(x).draw(imageContext);
			}	
		}
		imageContext.drawImage(lives[currLife], 374, 35, null);

		Graphics2D g2 = (Graphics2D) getGraphics();	// get the graphics context for the panel
		g2.drawImage(image, 0, 0, 800, 400, null);


		imageContext.dispose();
		g2.dispose();

	}

}