package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class Flag extends Fixed {
	
	private int sequenceNumber;
	private boolean isSelected;

	public Flag(Point inLocation, int inSequenceNumber) {
		
		// location  of the Flag object; cannot be changed outside of constructor
		super(inLocation);
		
		// size of the Flag object; cannot be changed outside of constructor
		super.generateSize(100, 100); // INITIAL VALUE SUBJECT TO CHANGE
		
		// Initially set to a bright blue color
		super.setColor(255, 0, 203, 235);
		
		// number marker of the Flag; cannot be changed outside of constructor
		this.sequenceNumber = inSequenceNumber;
	}

	public int getSequenceNumber() {
		
		return this.sequenceNumber;
	}

	public void setSequenceNumber(int inSequenceNumber) {
		
	}

	@Override
	public void setColor(int a, int r, int g, int b) {
		
	}

	@Override
	public String toString() {
		
		String outputString = "";
		
		outputString = "Flag: " +
					   "loc=" + this.getLocationString() + 
					   " color=" + "(" + ColorUtil.red(this.getColor()) + ", "
					   				+ ColorUtil.green(this.getColor()) + ", "
					   				+ ColorUtil.blue(this.getColor()) + ")" + 
					   " size=" + this.getSize() +
					   " sequenceNumber=" + this.getSequenceNumber();

		return outputString;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		
		int size = this.getSize();
		
		int xCenter =(int) (pCmpRelPrnt.getX() + this.getLocation().getX());
		int yCenter =(int) (pCmpRelPrnt.getY() + this.getLocation().getY());
		
		int x1 = xCenter;
		int y1 = yCenter - size/2;
		
		int x2 = xCenter - size/2;
		int y2 = yCenter + size/2;
		
		int x3 = xCenter + size/2;
		int y3 = yCenter + size/2;
		
		int[] xPoints = {x1, x2, x3};
		int[] yPoints = {y1, y2, y3};
		
		g.setColor(this.getColor());
		
		if (isSelected()) {
			
			g.drawPolygon(xPoints, yPoints, 3);
		}
		
		else {
			
			g.fillPolygon(xPoints, yPoints, 3);
		}

		g.setColor(ColorUtil.BLACK);
		g.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_LARGE));
		g.drawString(String.valueOf(this.getSequenceNumber()), xCenter - 12, yCenter - 10);
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
