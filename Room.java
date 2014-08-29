package engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Room {
	
	private BufferedImage 		rImage; 
	private int 				posX, posY;
	private int[][]				roomMap = null;
	
	public Room (String bImage, int x, int y) {
		try {
			rImage = ImageIO.read(new File(bImage));
			this.posX = x;
			this.posY = y;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Room() {
		
	}
	
	public void setRoomMap(int[][] m) {
		this.roomMap = m;
	}
	
	public int[][] getRoomMap() {
		return roomMap;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setRoomImage (BufferedImage newImage){
		this.rImage = newImage;
	}
	
	public BufferedImage getRoomImage() {
		return rImage;
	}

}
