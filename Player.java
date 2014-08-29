package characters;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Player extends Character implements KeyListener {
	
	private BufferedImage playerImage;
	
	private boolean isAlive = false;
	
	private boolean dialog1 = false;
	private boolean isGoal = false;
	
	private int		velocity = 10;
	
	private boolean leftPress = false;
	private boolean rightPress = false;
	private boolean upPress = false;
	private boolean downPress = false;
	
	private boolean startGame = false;
	
	public Player(String name, int x, int y) {
		super(name, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void setDialog1(boolean d) {
		this.dialog1 = d;
	}
	
	public boolean getDialog1() {
		return dialog1;
	}
	
	public void setGoal(boolean g) {
		this.isGoal = g;
	}
	
	public boolean getGoal() {
		return isGoal;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
			
	}
	
	public boolean getLeft() {
		return leftPress;
	}
	public boolean getRight() {
		return rightPress;
	}
	public boolean getUp() {
		return upPress;
	}
	public boolean getDown() {
		return downPress;
	}
	
	
	public boolean getStart() {
		return startGame;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		int keys = e.getKeyCode();
		
		System.out.println("KEYS");
		
		if (keys == e.VK_A) {
			//System.out.println("KEY A");
			leftPress = true;
		}
		else
			leftPress = false;
		
		if (keys == e.VK_D) {
			//System.out.println("KEY A");
			rightPress = true;
		}
		else
			rightPress = false;
		
		if (keys == e.VK_W) {
			//System.out.println("KEY A");
			upPress = true;
		}
		else
			upPress = false;
		
		if (keys == e.VK_S) {
		//{System.out.println("KEY A");
			downPress = true;
		}
		else
			downPress = false;	
		
		if (keys == e.VK_SPACE) {
			System.out.println("SPACE");
			startGame = true;
		}
		
	}
	
	public void setStartGame(boolean s) {
		this.startGame = false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
