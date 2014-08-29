package characters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character {
	
	private String cName = null;
	private BufferedImage cImage;
	
	private int posX, posY;
	
	public Character(String name, int x, int y) {
		this.cName = name;
		this.posX = x;
		this.posY = y;
		try {
			cImage = ImageIO.read(new File(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void setPosX(int x) {
		//System.out.println("SET X: "+posX);
		//if ((posX >= 10) && (posX <= 570))
			posX += x;
	}
	
	public void setPosX(int x, int v) {
		int pos = x*(v);
		posX += pos;
	}
	
	public void setPosY(int y, int v) {
		int pos = y*(v);
		posY += pos;
	}
	
	public void setPosY(int y) {
		//if ((posY <= 500) && (posY >= 0))
			posY += y;
	}
	
	public void resetPositions(int px, int py) {
		this.posX = px;
		this.posY = py;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public BufferedImage getCharacterImage() {
		return cImage;
	}
	
	public String getName() {
		return cName;
	}

}
