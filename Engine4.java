//Game engine developed 08/29/14 20:58 BCN

package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import characters.*;
import levels.*;

public class Engine4 extends Canvas implements Runnable{
	
	//Optimizacion:  Tue 26 Aug 2014, 17:18pm
	
	//http://zetcode.com/tutorials/javagamestutorial/movingsprites/
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Player 		player1;
	private Player 		player2;
	private Level1		level1;
	private Level2		level2;
	private Room[][] 	rooms;
	private int 		velocity = 10;
	
	private int 		level;
	
	Thread objGame;
	private boolean 	execute = true;
	
	private BufferedImage 		imageBackground;
	private BufferedImage 		imageDialog1;
	private BufferedImage 		imageYesDialog1;
	
	
	private BufferedImage		imageBackgroundMenu;
	private BufferedImage 		imageBackgroundSetup;
	
	private BufferedImage 		imageKeys;
	private BufferedImage 		imageSpaceKey;
	private BufferedImage 		imageNext;
	
	private Graphics bufferGraphics;
	private Image offScreen;
	
	
	private int[][] map = null;
	
	//Image Strings
	private String 		brickImage, brickImage2, doorImage;
	
	//standar font style
	//private Font sansSerif =  new Font("SanSerif", Font.BOLD, 30);
	
	private File typeFile;
	private Font f;
	private Bgplayer p1Menu;
	private Bgplayer p2Menu;
	private Bgplayer p3Menu;
	private Bgplayer p4Menu;
	private Bgplayer p5Menu;
	private Bgplayer p6Menu;
	
	//Timers
	private int clockMenu;
	private int sec, milsec, totalTimer;
	
	private boolean isGameStarting = false;
	
	public Engine4() {		
		//We create 2 player, One per Wordl
		
		System.out.println("Engine 2");
		
		LevelsManager lm = new LevelsManager();
		//lm.setLevel(1);
		level = lm.getLevel();
		
		System.out.println("first run and level: "+lm.getLevel());
		System.out.println("first run and level 2: "+level);
		
		player1 = new Player("res/player.png", 30, 550);
		player2 = new Player("res/player.png", 540, 550);
		
		//Strings
		brickImage = "res/brick.png";
		brickImage2 = "res/brick2.png";
		doorImage = "res/door.png";	
		
		//Starting menu icons
		p1Menu = new Bgplayer("res/player.png", 10, 10);
		p2Menu = new Bgplayer("res/player.png", 550, 550);
		p3Menu = new Bgplayer("res/player.png", 200, 350);
		p4Menu = new Bgplayer("res/player.png", 30, 30);
		p5Menu = new Bgplayer("res/player.png", 520, 300);
		p6Menu = new Bgplayer("res/player.png", 200, 200);
		
		this.requestFocusInWindow();	
	
		
		
		try {
			imageBackground = ImageIO.read(new File("res/background.png"));
			imageDialog1 = ImageIO.read(new File("res/dotdialog1.png"));
			imageYesDialog1 = ImageIO.read(new File("res/yesdialog1.png"));
			typeFile = new File("res/editundo.ttf");
			
			imageBackgroundMenu = ImageIO.read(new File("res/background0.png"));
			imageBackgroundSetup = ImageIO.read(new File("res/backgroundSetup.png"));
			
			imageKeys = ImageIO.read(new File("res/keys.png"));
			imageSpaceKey = ImageIO.read(new File("res/spacebarkey.png"));
			
			imageNext = ImageIO.read(new File("res/backgroundNext.png"));
			
			FileInputStream fonStream = new FileInputStream(typeFile);
			f = Font.createFont(Font.TRUETYPE_FONT, fonStream);
			f = f.deriveFont(35f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		//Loading Level Map
		//loadMap();
		
		
		
		//like message start game
		//like PRESS Intro when ready
		//in method pz
		
		Thread t = new Thread(this);
		t.start();
		
	}

	
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println("RUN 1");
					
		addKeyListener(player1);
		//addKeyListener(player2);
		
		//tutorialTimer = new Timer();
		//int delay = 1000;
		//int period = 5000;
		clockMenu = 0;
		
		while(execute) {
			try {
			//	System.out.println("RUN 2");
				Thread.sleep(60);
				this.move();
				
				System.out.println("SHOW ME LEVEL: "+level);

				
				if (level == -1) {
					movingAnimes();
					if (player1.getStart()) {
						level = 0;
						player1.setStartGame(false);
						Thread.sleep(500);
					}
				}
				
				if (level == 0) {
					System.out.println("We are Here");
					if (player1.getStart()) {
						level = -2;
						player1.setStartGame(false);
						Thread.sleep(500);					
					}
				}
				
				if (level == 1) {
					
					move();
					countTimer();
					clockMenu = 0;
					isGameStarting = true;
					System.out.println("GOALS: "+playersAreGoal());
					if (playersAreGoal()) {
						System.out.println("STARRTS: "+player1.getStart());
						if (player1.getStart()) {
							player1.setStartGame(false);
							level = -2;			
						}
					}
				}
				
				if (level == 2) {
					move();
					countTimer();
					clockMenu = 0;
					isGameStarting = true;
					if (playersAreGoal()) {
						if (player1.getStart()) {
							level = -2;		
						}
					}
				}
				
				if (level == -2) {				
					if (countdownMethod()) {
						if (!isGameStarting) {
							level = 1;
							//Loading Level Map
							loadMap();
						}
						else{		
							level = 2;
							restartingPlayersPositions();
							
							//Loading Level Map
							loadMap();
						}

					}
					
				}
				
				repaint();
				
				if (playersAreGoal()) {
					//
					Thread.sleep(300);			
					//Message level Up
					//load new map
					//reset positions
					//sleep for a while
					//message i'm ready
				}
			}

		catch (InterruptedException e){}
		}
	}
	
	public void countTimer() {
		totalTimer++;
	}
	
	public boolean countdownMethod() {
		System.out.println("IM COUNT DOWN");
		clockMenu++;
		System.out.println(clockMenu);
		if (clockMenu > 50) {
			return true;
		}
		else
			return false;
	}
	
	public void restartingPlayersPositions() {
		player2.setPosY(520);
		player1.resetPositions(30, 520);
		player2.resetPositions(520, 520);
		System.out.println("P1X: "+player1.getPosX());
		System.out.println("P1Y: "+player1.getPosY());
		System.out.println("P2X: "+player2.getPosX());
		System.out.println("P2Y: "+player2.getPosY());
		
		player1.setGoal(false);
		player2.setGoal(false);
		
		totalTimer = 0;
		
		playersAreGoal();
		
	}
	
	public void move() {
		//System.out.println("MOVEMENT");
		if (player1.getLeft()) {
			//Check left character
			if(checkWall(player1.getPosX()-10, player1.getPosY())) {
			//	System.out.println("Wall found!");
				player1.setDialog1(true);
			}
			else {
				player1.setPosX(-velocity);
				player1.setDialog1(false);
				if (checkGoal(player1.getPosX(), player1.getPosY())) {
					player1.setGoal(true);
				}
				else {
					player1.setGoal(false);
				}
			}
			
			//Check player right
			if(checkWall(player2.getPosX()+10, player2.getPosY())) {
			//	System.out.println("Wall found!");
				player2.setDialog1(true);
			}
			else {
				player2.setPosX(+velocity);
				player2.setDialog1(false);
				if (checkGoal(player2.getPosX(), player2.getPosY())) {
					player2.setGoal(true);
				}
				else {
					player2.setGoal(false);
				}
			}
		}
		
		if (player1.getRight()) {		
			//Check left character
			if(checkWall(player1.getPosX()+10, player1.getPosY())) {
			//	System.out.println("Wall found!");
				player1.setDialog1(true);
			}
			else {
				player1.setPosX(velocity);
				player1.setDialog1(false);
				if (checkGoal(player1.getPosX(), player1.getPosY())) {
					player1.setGoal(true);
				}
				else {
					player1.setGoal(false);
				}
			}
			
			//Check player right
			if(checkWall(player2.getPosX()-10, player2.getPosY())) {
			//	System.out.println("Wall found!");
				player2.setDialog1(true);
			}
			else {
				player2.setPosX(-velocity);
				player2.setDialog1(false);
				if (checkGoal(player2.getPosX(), player2.getPosY())) {
					player2.setGoal(true);
				}
				else {
					player2.setGoal(false);
				}
			}
		}
		
		if (player1.getUp()) {
			//Check left character
			if(checkWall(player1.getPosX(), player1.getPosY()-10)) {
			//	System.out.println(" P1 :Wall found!");
				player1.setDialog1(true);
			}
			else {
				player1.setPosY(-velocity);
				player1.setDialog1(false);
				if (checkGoal(player1.getPosX(), player1.getPosY())) {
					player1.setGoal(true);
				}
				else {
					player1.setGoal(false);
				}
			}
			
			//Check player right
			if(checkWall(player2.getPosX(), player2.getPosY()-10)) {
			//	System.out.println("P2: Wall found!");
				player2.setDialog1(true);
			}
			else {
				player2.setPosY(-velocity);
				player2.setDialog1(false);
				if (checkGoal(player2.getPosX(), player2.getPosY())) {
					player2.setGoal(true);
				}
				else {
					player2.setGoal(false);
				}	
		}
		}
		if (player1.getDown()) {
			//Check left character
			if(checkWall(player1.getPosX(), player1.getPosY()+10)) {
				//System.out.println("P1: Wall found!");
				player1.setDialog1(true);
			}
			else {
				player1.setPosY(+velocity);
				player1.setDialog1(false);
				if (checkGoal(player1.getPosX(), player1.getPosY())) {
					player1.setGoal(true);
				}
				else {
					player1.setGoal(false);
				}
			}
		
			
			//Check player right
			if(checkWall(player2.getPosX(), player2.getPosY()+10)) {
			//	System.out.println("P2: Wall found!");
				player2.setDialog1(true);
			}
			else {
				player2.setPosY(+velocity);
				player2.setDialog1(false);
				if (checkGoal(player2.getPosX(), player2.getPosY())) {
					player2.setGoal(true);
				}
				else {
					player2.setGoal(false);
				}
			}
		}

	}
	
	
	public void loadMap() {
		
		if (level == 1) {
			System.out.println("LOADING MAP "+level);
			//Load Map1 class
			level1 = new Level1();
			map = level1.getLevelArray();
			
			rooms = new Room[map.length][map[0].length];
			
		//BRICKS
				for (int i = 0; i < map.length; i++) {
						for (int j = 0; j < map[0].length; j++) {
							if (map[i][j]==0) {
								//	System.out.print("X");
								rooms[i][j] = new Room();
							}
							else if (map[i][j]==2) {
								rooms[i][j] = new Door(doorImage, i, j);
							}
							else {
								if ((i+1)>(map.length/2)) {
									rooms[i][j] = new Wall(brickImage2, i, j);
								}
								else {
									rooms[i][j] = new Wall(brickImage, i, j);
								//	System.out.print(" ");
								}
							}
						}
				}	
		}
		
		if (level == 2) {
			System.out.println("LOADING MAP "+level);
			level2 = new Level2();
			
			map = null;
			rooms = null; 
			
			map = level2.getLevelArray();
			
			
			rooms = new Room[map.length][map[0].length];
			
			//BRICKS
					for (int i = 0; i < map.length; i++) {
							for (int j = 0; j < map[0].length; j++) {
								if (map[i][j]==0) {
									//	System.out.print("X");
									rooms[i][j] = new Room();
								}
								else if (map[i][j]==2) {
									rooms[i][j] = new Door(doorImage, i, j);
								}
								else {
									if ((i+1)>(map.length/2)) {
										rooms[i][j] = new Wall(brickImage2, i, j);
									}
									else {
										rooms[i][j] = new Wall(brickImage, i, j);
									//	System.out.print(" ");
									}
								}
							}
					}	
		}
	}
		
	
	
	public void paint (Graphics g) {	
		if (offScreen == null) {
			offScreen = createImage(600, 600);
			bufferGraphics = offScreen.getGraphics();
		}
		
		if (level == -1) {
			//movingAnimes();
			
			//DOUBLE BUFFER TECHNIQUE COOOOOOOOOOOOL
			
			
		//	bufferGraphics.clearRect(0, 0, 600, 600);
			
			bufferGraphics.drawImage(imageBackgroundMenu, 0, 0, null);
			
			bufferGraphics.drawImage(p1Menu.getCharacterImage(), p1Menu.getPosX(), p1Menu.getPosY(), null);
			bufferGraphics.drawImage(p2Menu.getCharacterImage(), p2Menu.getPosX(), p2Menu.getPosY(), null);
			bufferGraphics.drawImage(p3Menu.getCharacterImage(), p3Menu.getPosX(), p3Menu.getPosY(), null);
			bufferGraphics.drawImage(p4Menu.getCharacterImage(), p4Menu.getPosX(), p4Menu.getPosY(), null);
			bufferGraphics.drawImage(p5Menu.getCharacterImage(), p5Menu.getPosX(), p5Menu.getPosY(), null);
			bufferGraphics.drawImage(p6Menu.getCharacterImage(), p6Menu.getPosX(), p6Menu.getPosY(), null);
			
			
			Color rectTitle = new Color(255, 178, 102);
			Color rectColor = new Color(204,104,0);
			Color rectColor2 = new Color(153, 76, 0);
			bufferGraphics.setColor(rectColor);
			bufferGraphics.drawRect(30, 40, 545, 80);
			bufferGraphics.fillRect(30, 40, 545, 80);
			
			bufferGraphics.setColor(rectTitle);
			bufferGraphics.drawRect(20, 20, 540, 80);
			bufferGraphics.fillRect(20, 20, 540, 80);
			
			bufferGraphics.setColor(Color.BLACK);
			f = f.deriveFont(60f);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("Parallels Worlds", 60, 80);
						
			
			bufferGraphics.setColor(rectColor2);
			bufferGraphics.drawRect(115, 240, 400, 90);
			bufferGraphics.fillRect(115, 240, 400, 90);
	
			bufferGraphics.setColor(rectColor);		
			
			bufferGraphics.drawRect(100, 225, 400, 90);
			bufferGraphics.fillRect(100, 225, 400, 90);
		
			
			f = f.deriveFont(40f);
			bufferGraphics.setColor(Color.BLACK);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("SPACEBAR TO START", 120, 280);
			
			
			bufferGraphics.setColor(Color.BLACK);
			f = f.deriveFont(40f);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("ENJOY IT!", 200, 480);
			f = f.deriveFont(20f);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("Game Design by Jessica F. Martinez", 110, 540);
			f = f.deriveFont(25f);
			bufferGraphics.drawString("@JessicaFzMz", 210, 560);
			
			g.drawImage(offScreen, 0, 0, this);
		}
		
		if (level == 0) {
			bufferGraphics.drawImage(imageBackgroundSetup, 0, 0, null);
			bufferGraphics.setColor(Color.BLACK);
			f = f.deriveFont(50f);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("CONTROLS", 150, 80);
			f = f.deriveFont(30f);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("PLAYER MOVEMENT ", 50, 200);
			bufferGraphics.drawImage(imageKeys, 300, 180, null);
			bufferGraphics.drawImage(imageKeys, 350, 180, null);
			bufferGraphics.drawImage(imageKeys, 400, 180, null);
			bufferGraphics.drawImage(imageKeys, 350, 130, null);
			bufferGraphics.drawString("A", 318, 210);
			bufferGraphics.drawString("S", 368, 210);
			bufferGraphics.drawString("D", 418, 210);
			bufferGraphics.drawString("W", 368, 160);
			
			bufferGraphics.drawString("MAIN MENU", 50, 350);
			bufferGraphics.drawString("START GAME", 50, 500);
			bufferGraphics.drawImage(imageKeys, 300, 320, null);
			f = f.deriveFont(20f);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("Esc", 310, 350);
			
			bufferGraphics.drawImage(imageSpaceKey, 300, 480, null);
			bufferGraphics.drawString("SPACEBAR", 330, 505);
	
			g.drawImage(offScreen, 0, 0, this);
		}
		
		if (level == -2) {
			bufferGraphics.drawImage(imageBackgroundSetup, 0, 0, null);
			bufferGraphics.setColor(Color.BLACK);
			f = f.deriveFont(50f);
			bufferGraphics.setFont(f);
			bufferGraphics.drawString("LOADING  GAME . . .", 100, 300);
			g.drawImage(offScreen, 0, 0, this);
		}
		
		
		if (level >= 1 ) {
		//System.out.println("PAINT");
			bufferGraphics.drawImage(imageBackground, 0, 0, null);
		
		//BRICKS
		for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					if (rooms[i][j] instanceof Wall) {
					//	System.out.print("W");					
						if ((i+1)>(map.length/2)) {
							bufferGraphics.drawImage(rooms[i][j].getRoomImage(), i*10, j*10, null);
						}
						else {
							bufferGraphics.drawImage(rooms[i][j].getRoomImage(), i*10, j*10, null);
						}
					}
					else if (rooms[i][j] instanceof Door) {
						bufferGraphics.drawImage(rooms[i][j].getRoomImage(), i*10, j*10, null);
					}
				}
		}
		
		//Player painting the last!!!
		bufferGraphics.drawImage(player1.getCharacterImage(), player1.getPosX(), player1.getPosY(), null);
		bufferGraphics.drawImage(player2.getCharacterImage(), player2.getPosX(), player2.getPosY(), null);
		
		if (player1.getDialog1()) {
			bufferGraphics.drawImage(imageDialog1, player1.getPosX(), player1.getPosY()-10, null);
		}
		if (player2.getDialog1()) {
			bufferGraphics.drawImage(imageDialog1, player2.getPosX(), player2.getPosY()-10, null);
		}
		
		if (player1.getGoal()) {
			bufferGraphics.drawImage(imageYesDialog1, player1.getPosX(), player1.getPosY()-10, null);
		}
		if (player2.getGoal()) {
			bufferGraphics.drawImage(imageYesDialog1, player2.getPosX(), player2.getPosY()-10, null);
		}
		
		//LEVEL TEXT	
		f = f.deriveFont(30f);
		bufferGraphics.setFont(f);
		bufferGraphics.setColor(Color.BLACK);
		bufferGraphics.drawString("LEVEL: "+level, 225, 25);
		
		f = f.deriveFont(20f);
		bufferGraphics.setFont(f);
		bufferGraphics.drawString(sec+" : "+milsec, 100, 15);
		
		if (showNext()) {
			bufferGraphics.drawImage(imageNext, 110, 100, null);
			f = f.deriveFont(30f);
			bufferGraphics.setFont(f);
			bufferGraphics.setColor(Color.WHITE);
			bufferGraphics.drawString("LEVEL UP ! ! !", 160, 180);
			bufferGraphics.drawString("PRESS SPACEBAR ", 160, 260);
			bufferGraphics.drawString("TO NEXT MAP", 160, 340);
			//g.drawImage(offScreen, 0, 0, this);
		}	
		g.drawImage(offScreen, 0, 0, this);
		}
	}
	
	public void update (Graphics g) {
		//System.out.println("Update");
		sec = totalTimer/60;
		milsec = totalTimer%60;
	
		if (playersAreGoal()) {
			
				
			//level++;
		}			
		paint(g);	
	}
	
	public boolean playersAreGoal() {
		if (player1.getGoal() && player2.getGoal()) {
			return true;
		}
		else
			return false;
	}

	public boolean checkWall(int px, int py) {
		px = px/10;
		py = py/10;
		
		//System.out.println(px+", "+py);
		if (rooms[px][py] instanceof Wall) {
			return true;
		}
		else
			return false;	
	}
	
	public boolean showNext() {
		if (playersAreGoal()) 
			return true;
		else
			return false;
	}
	
	public boolean checkGoal(int px, int py) {	
		px = px/10;
		py = py/10;
		
		if (rooms[px][py] instanceof Door) {
			return true;
		}
		else
			return false;	
	}
	
	public BufferedImage cImage() {
		return player2.getCharacterImage();
	}
	
	public int getLevel() {
		return level;
	}
	
	
	public void movingAnimes() {
		if (p1Menu.getPosX() == 580) {
			p1Menu.setDirection(-1);
		}
		else if (p1Menu.getPosX() == 0) {
			p1Menu.setDirection(1);
		}	
		
		if (p2Menu.getPosX() == 580) {
			p2Menu.setDirection(-1);
		}
		else if (p2Menu.getPosX() == 0) {
			p2Menu.setDirection(1);
		}
		
		if (p3Menu.getPosY() == 580) {
			p3Menu.setDirection(-1);
		}
		else if (p3Menu.getPosY() == 0) {
			p3Menu.setDirection(1);
		}
		
		
		if (p4Menu.getPosY() == 580) {
			p4Menu.setDirection(-1);
		}
		else if (p4Menu.getPosY() == 0) {
			p4Menu.setDirection(1);
		}
		
		if (p5Menu.getPosY() == 580) {
			p5Menu.setDirection(-1);
		}
		else if (p5Menu.getPosY() == 0) {
			p5Menu.setDirection(1);
		}
		if (p6Menu.getPosY() == 580) {
			p6Menu.setDirection(-1);
		}
		else if (p6Menu.getPosY() == 0) {
			p6Menu.setDirection(1);
		}

		p1Menu.setPosX(10, p1Menu.getDirection());
		p2Menu.setPosX(10, p2Menu.getDirection());
		p3Menu.setPosX(10, p3Menu.getDirection());
		p4Menu.setPosY(10, p4Menu.getDirection());
		p5Menu.setPosY(10, p5Menu.getDirection());
		p6Menu.setPosY(10, p6Menu.getDirection());			
	}

}
