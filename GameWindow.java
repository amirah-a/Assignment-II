import javax.swing.*;			// need this for GUI objects
import java.awt.*;			// need this for Layout Managers
import java.awt.event.*;		// need this to respond to GUI events
	
public class GameWindow extends JFrame 
				implements ActionListener,
					   KeyListener,
					   MouseListener
{
	// declare instance variables for user interface objects

	// declare labels 
    private JButton start, exit;
    private JPanel buttonPanel;

	private Container c;

	private JPanel mainPanel;
	private GamePanel gamePanel;
	private SoundManager soundManager;

	@SuppressWarnings({"unchecked"})
	public GameWindow() {
 
		setTitle("The Mines");
		setSize(1000, 480);
		setLocationRelativeTo(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		soundManager = SoundManager.getInstance();

		// set up buttons
		start = new JButton("Start Game");
		exit = new JButton("Exit Game");
		start.addActionListener(this);
		exit.addActionListener(this);
		
		// create mainPanel

		mainPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		mainPanel.setLayout(flowLayout);

		GridLayout gridLayout;

		// create the gamePanel for game entities

		gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(800, 400));

		// create infoPanel

		JPanel infoPanel = new JPanel();
		gridLayout = new GridLayout(3, 2);
		
		// create button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));

		buttonPanel.add(start);
		buttonPanel.add(exit);

		
		// create mainPanel and add subpanels 
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(gamePanel);
		mainPanel.add(buttonPanel);
		mainPanel.setBackground(new Color(255,195,0));
		
		
		// add sub-panels with GUI objects to mainPanel and set its colour

		mainPanel.add(gamePanel);
		mainPanel.add(buttonPanel);
		mainPanel.setBackground(Color.PINK);

		// set up mainPanel to respond to keyboard and mouse

		gamePanel.addMouseListener(this);
		mainPanel.addKeyListener(this);

		// add mainPanel to window surface

		c = getContentPane();
		c.add(mainPanel);

		// set properties of window
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}


	// implement single method in ActionListener interface

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		

		if (command.equals(start.getText())) {
			gamePanel.startGame();
		}

		// if (command.equals(exit.getText())) {
		// 	gamePanel.endGame();
		// }

		// if (command.equals(restartB.getText()))
		// 	gamePanel.restartGame();

		if (command.equals(exit.getText()))
			System.exit(0);

		mainPanel.requestFocus();
	}


	// implement methods in KeyListener interface

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		Player.isWalking = true;
		if (keyCode == KeyEvent.VK_LEFT) {
			gamePanel.movePlayer(1);			//gamePanel.drawGameEntities();
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			gamePanel.movePlayer(2);
			//gamePanel.drawGameEntities();
		}

		if (keyCode == KeyEvent.VK_SPACE){
			// Player.isShooting = true;
            // Player.playerShoot.start();
			
			try {
                Thread.sleep(600);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            int x = Player.x; //+ Player.width;
            int y = Player.y; //+ Player.height;
            GamePanel.potions.add(new Potion(gamePanel, x+15, y+20));
			soundManager.playClip("shoot", false);
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		Player.isWalking = false;
		if (keyCode == KeyEvent.VK_LEFT) {
			Player.sprite = Player.animationLB;
			gamePanel.movePlayer(0);
			//gamePanel.drawGameEntities();
		}

		if (keyCode == KeyEvent.VK_RIGHT) {
			Player.sprite = Player.animationRB;
			gamePanel.movePlayer(0);
			//gamePanel.drawGameEntities();
		}

	}

	public void keyTyped(KeyEvent e) {

	}

	// implement methods in MouseListener interface

	public void mouseClicked(MouseEvent e) {

	}


	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
	
	}

	public void mouseReleased(MouseEvent e) {
	
	}

}