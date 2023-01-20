package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Random;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public abstract class GameObject implements IDrawable, ICollider {
	
	private int size;
	private Point location;
	private int color;
	
	private ArrayList<GameObject> objectsCurrentlyCollidingWith = new ArrayList<>();

	public int getSize() {
		
		return size;
	}

	public int getColor() {
		
		return color;
	}
	
	public void setColor(int a, int r, int g, int b) {
		
		color = ColorUtil.argb(a, r, g, b);
	}
	
	public String getLocationString() {
		
		return "(" + location.getX() + ", " + location.getY() + ")";
	}

	public Point getLocation() {
		
		return location;
	}
	
	public void setLocation(float x, float y) {
		
		// rounds the x and y coordinates to one decimal place
		x = (float) (Math.round(x * 10.0) / 10.0);
		y = (float) (Math.round(y * 10.0) / 10.0);
		
		location = new Point(x, y);
	}
	
	public void setLocation(Point inLocation) {
		
		// rounds the x and y coordinates to one decimal place
		float x = (float) (Math.round(inLocation.getX() * 10.0) / 10.0);
		float y = (float) (Math.round(inLocation.getY() * 10.0) / 10.0);
		
		location = new Point(x, y);
	}
	
	public ArrayList<GameObject> getObjectsCurrentlyCollidingWith() {
		
		return objectsCurrentlyCollidingWith;
	}

	public void setObjectsCurrentlyCollidingWith(ArrayList<GameObject> objectsCurrentlyCollidingWith) {
		
		this.objectsCurrentlyCollidingWith = objectsCurrentlyCollidingWith;
	}

	protected Boolean outsideBounds(Point inPoint, int size) {
		
		float worldHeight = GameWorld.getHeight();
		float worldWidth = GameWorld.getWidth();
		
		// rounds the x and y coordinates to one decimal place
		float x = (float) (Math.round(inPoint.getX() * 10.0) / 10.0);
		float y = (float) (Math.round(inPoint.getY() * 10.0) / 10.0);
		
		int boundaryOffset = size/2;
		
		return (x + boundaryOffset >= worldWidth ||
				x - boundaryOffset <= 0 ||
				y + boundaryOffset >= worldHeight ||
				y - boundaryOffset <= 0);
	}

	protected void generateSize(int lowerBound, int upperBound) {
		
		Random random = new Random();
		
		if (lowerBound == upperBound) {
			this.size = lowerBound;
		}
		
		else {
			this.size = random.nextInt(upperBound - lowerBound) + lowerBound;
		}
	}

	protected Point generateLocation() {
		
		Random random = new Random();
		
		float x = (float) (Math.round((random.nextFloat() * GameWorld.getWidth()) * 10.0) / 10.0);
		float y = (float) (Math.round((random.nextFloat() * GameWorld.getHeight()) * 10.0) / 10.0);
		
		Point generatedPoint = new Point(x, y);
		
		if (outsideBounds(generatedPoint, size)) {
			
			return generateLocation();
		}
		
		else {
			
			return generatedPoint;
		}
	}
}
