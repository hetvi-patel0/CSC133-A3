package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Spider extends Movable {

	public Spider() 
	{
		super.generateSize(50, 100);
		super.setHeading(generateInitialHeading());
		super.setSpeed(this.generateSpeed());
		super.setLocation(this.generateLocation());
		super.setColor(255, 36, 36, 36);
	}
	
	public void changeHeadingSlightly() {
		
		Random random = new Random();
		
		this.setHeading(this.getHeading() + random.nextInt(15 + 15) - 15);
	}

	private int generateSpeed() {
		
		Random random = new Random();
		
		return random.nextInt(10 - 5) + 5;
	}

	private int generateInitialHeading() {
		
		Random random = new Random();
		
		return random.nextInt(359 - 0) + 0;
	}

	@Override
	public void setColor(int a, int r, int g, int b) {
		
	}

	@Override
	public void setSpeed(int inSpeed) {
		
	}

	@Override
	public String toString() {
		
		String outputString = "";
		
		outputString = "Spider: " +
					   "loc=" + this.getLocationString() + 
					   " color=" + "(" + ColorUtil.red(this.getColor()) + ", "
					   				+ ColorUtil.green(this.getColor()) + ", "
					   				+ ColorUtil.blue(this.getColor()) + ")" + 
					   " heading=" + this.getHeading() + 
					   " speed=" + this.getSpeed() +
					   " size=" + this.getSize();
				
		return outputString;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		
		int size = this.getSize();
		
		int x1 = (int) (pCmpRelPrnt.getX() + this.getLocation().getX());
		int y1 = (int) (pCmpRelPrnt.getY() + this.getLocation().getY()) - size/2;
		
		int x2 = (int) (pCmpRelPrnt.getX() + this.getLocation().getX()) - size/2;
		int y2 = (int) (pCmpRelPrnt.getY() + this.getLocation().getY()) + size/2;
		
		int x3 = (int) (pCmpRelPrnt.getX() + this.getLocation().getX()) + size/2;
		int y3 = (int) (pCmpRelPrnt.getY() + this.getLocation().getY()) + size/2;
		
		int[] xPoints = {x1, x2, x3};
		int[] yPoints = {y1, y2, y3};
		
		int centerX = x1;
		int centerY = y1 + size/2;
		
		g.setColor(this.getColor());
				
		g.drawPolygon(xPoints, yPoints, 3);
	}



	@Override
	public Boolean collidesWith(GameObject otherObject) {
		
		return null;
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		
	}
}
