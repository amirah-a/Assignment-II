import javax.swing.JPanel;

/**
   The thread that manages each game.
*/

public class GameThread implements Runnable {

	private GamePanel gamePanel;
	private boolean isRunning;
	private boolean isPaused;

	public GameThread (GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		isRunning = false;
		isPaused = false;
	}

	
	boolean isRunning () {
		return isRunning;
	}


	public void pauseGame() {

		if (isRunning) {
			if (isPaused)
				isPaused = false;
			else
				isPaused = true;
		}
	}


	public void endGame() {
		isRunning = false;
	}


	private void gameUpdate() {
		gamePanel.gameUpdate();
	}


	private void gameRender() {
		gamePanel.gameRender();
	}


	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				if (!isPaused)
					gameUpdate();
				gameRender();
				Thread.sleep (50);	
			}

			if(!isRunning){
				gamePanel.gameOver();
			}
		}
		catch(InterruptedException e) {}
	}


    public void setIsRunning(boolean b) {
		this.isRunning = b;
    }

}