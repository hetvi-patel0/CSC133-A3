package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class FoodStation extends Fixed {
	
	private int capacity;
	private boolean isSelected;
	
	public FoodStation() {
		
		// size of the FoodStation, randomly generated
		super.generateSize(70, 140);
		
		// location of the FoodStation
		super.setLocation(this.generateLocation());
		
		// amount of food a FoodStation contains, equal to its size/7
		// initially capacity = size, but the size had to be scaled up by 7 for display purposes
		this.setCapacity(this.getSize()/7);
		
		// color of the FoodStation, a bright green color
		setColor(255, 103, 234, 13);
	}
	
	public int getCapacity() {
		
		return capacity;
	}

	public void setCapacity(int inCapacity) {
		
		this.capacity = inCapacity;
	}

	public void lightenColor() {
		
		setColor(255, 196, 255, 168);
	}
	
	@Override
	public String toString() {
		
		String outputString = "";
		
		outputString = "FoodStation: " +
					   "loc=" + this.getLocationString() + 
					   " color=" + "(" + ColorUtil.alpha(this.getColor()) + ", "
						   			   + ColorUtil.red(this.getColor()) + ", "
						   			   + ColorUtil.green(this.getColor()) + ", "
						   			   + ColorUtil.blue(this.getColor()) + ")" + 
					   " size=" + this.getSize() +
					   " capacity=" + this.getCapacity();
				
		return outputString;
	}



	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		
		int size = this.getSize();
		
		int xCenter = (int) (pCmpRelPrnt.getX() + this.getLocation().getX());
		int yCenter = (int) (pCmpRelPrnt.getY() + this.getLocation().getY());
		
		int x1 = xCenter + size/2;
		int y1 = yCenter - size/2;
		
		int x2 = xCenter + size/2;
		int y2 = yCenter + size/2;
		
		int x4 = xCenter - size/2;
		int y4 = yCenter - size/2;
		
		int x3 = xCenter - size/2;
		int y3 = yCenter + size/2;
		
		int[] xPoints = {x1, x2, x3, x4};
		int[] yPoints = {y1, y2, y3, y4};
		
		g.setColor(this.getColor());
		
		if (isSelected()) {
			
			g.drawPolygon(xPoints, yPoints, 4);
		}
		
		else {
			
			g.fillPolygon(xPoints, yPoints, 4);
		}
		
		
		g.setColor(ColorUtil.BLACK);
		g.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
		g.drawString(String.valueOf(capacity), xCenter - size/4, yCenter - size/4);
	}



	@Override
	public Boolean collidesWith(GameObject otherObject) {
		return null;
	}



	@Override
	public void handleCollision(GameObject otherObject) {
		
	}



	@Override
	public void setSelected(boolean b) {
		
		isSelected = b;
	}



	@Override
	public boolean isSelected() {
		
		return isSelected;
	}

	

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		
		float px = pPtrRelPrnt.getX() + this.getSize()/2; // pointer location relative to
		float py = pPtrRelPrnt.getY() + this.getSize()/2; // parent’s origin
		float xLoc = pCmpRelPrnt.getX()+ this.getLocation().getX();// shape location relative
		float yLoc = pCmpRelPrnt.getY()+ this.getLocation().getY();// to parent’s origin
		
		return((px >= xLoc) && (px <= xLoc+this.getSize()) && (py >= yLoc) && (py <= yLoc+this.getSize()));
	}
}
