package com.mycompany.a3;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Ant extends Movable implements ISteerable, IDrawable {
	
	private static Ant INSTANCE;
	private final int maximumSpeed = 10, maximumHealth = 10, maximumFoodLevel = 30;
	private int healthLevel, lastFlagReached, currentMaximumSpeed;
	private float currentFoodLevel, foodConsumptionRate;
	private GameWorld gw;
	
	private Ant(Point inLocation, int tickRate, GameWorld gw) {
		
		// size of the Ant object; cannot be changed outside of constructor
		super.generateSize(100, 100); // INITIAL VALUE SUBJECT TO CHANGE
		
		// current maximum speed of the ant, which can be affected by the Ant's healthLevel and foodLevel
		this.currentMaximumSpeed = maximumSpeed;
		
		// Ant starts with speed = maximumSpeed
		super.setSpeed(maximumSpeed);
		
		// hunger level of the Ant; the smaller the number, the hungrier the Ant is 
		this.currentFoodLevel = maximumFoodLevel; // INITIAL VALUE SUBJECT TO CHANGE
		
		// current health level of the Ant
		this.healthLevel = this.maximumHealth;
		
		// indicates the sequence number of the last flag that the Ant has reached in the increasing order
		this.lastFlagReached = 1;
		
		this.foodConsumptionRate = tickRate * 0.001f;
		
		// Ant set to initially head North/up (zero degrees)
		super.setHeading(0);
		
		// initial location of the Ant
		super.setLocation(inLocation);
		
		// initially a bright red color
		setColor(255, 255, 0, 21);
		
		this.gw = gw;
	}
	
	public static Ant getInstance(int tickRate, GameWorld gw) {
		
		if (INSTANCE == null) {
			
			INSTANCE = new Ant(new Point(0, 0), tickRate, gw);
		}
		
		return INSTANCE;
	}
	
	public static Ant getInstance(Point inLocation, int tickRate, GameWorld gw) {
		
		if (INSTANCE == null) {
			
			INSTANCE = new Ant(inLocation, tickRate, gw);
		}
		
		return INSTANCE;
	}
	
	public void resetAnt(Point inLocation) {
		
		INSTANCE.setCurrentMaximumSpeed(maximumSpeed);
		INSTANCE.setSpeed(maximumSpeed);
		INSTANCE.setColor(255, 255, 0, 21);
		INSTANCE.setCurrentFoodLevel(maximumFoodLevel);
		INSTANCE.setHeading(0);
		INSTANCE.setHealthLevel(maximumHealth);
		INSTANCE.setLocation(inLocation);
	}
	
	public int getMaximumSpeed() {
		
		return this.maximumSpeed;
	}
	
	public void setMaximumSpeed(int inSpeed) {
		
	}

	public int getCurrentMaximumSpeed() {
		
		return currentMaximumSpeed;
	}

	public void setCurrentMaximumSpeed(int newMaxSpeed) {
		
		this.currentMaximumSpeed = newMaxSpeed;
	}

	public float getFoodConsumptionRate() {
		
		return foodConsumptionRate;
	}
	
	public void setFoodConsumptionRate(float inFoodConsumptionRate) {

	}

	public int getMaximumHealth() {
		
		return maximumHealth;
	}

	public void setMaximumHealth(int inMaximumHealth) {

	}

	public int getMaximumFoodLevel() {
		
		return maximumFoodLevel;
	}

	public float getCurrentFoodLevel() {
		
		return currentFoodLevel;
	}

	public void setCurrentFoodLevel(float f) {
		
		// foodLevel cannot be negative
		if (f <= 0) {
			
			f = 0;
		}
		
		this.currentFoodLevel = f;
	}

	public int getHealthLevel() {
		
		return healthLevel;
	}

	public void setHealthLevel(int inHealthLevel) {
		
		// healthLevel can never be negative
		if (inHealthLevel <= 0) {
			
			healthLevel = 0;
		}
		
		this.healthLevel = inHealthLevel;
		
		// currentMaximumSpeed decreases linearly as health decreases
		this.setCurrentMaximumSpeed((int)Math.round((((double)healthLevel / (double)this.getMaximumHealth()) * this.getMaximumSpeed())));
		
		// if current speed > currentMaximumSpeed, decrease it to currentMaximumSpeed
		if (this.getSpeed() > this.getCurrentMaximumSpeed()) {
			
			this.setSpeed(this.getCurrentMaximumSpeed());
		}
		
	}

	public int getLastFlagReached() {
		
		return lastFlagReached;
	}

	public void setLastFlagReached(int inLastFlagReached) {
		
		this.lastFlagReached = inLastFlagReached;
	}

	public void lightenColorSlightly(int health) {
		
		switch(health) {
			case 9:
				setColor(255, 255, 40, 40);
				break;
			case 8:
				setColor(255, 255, 60, 60);
				break;
			case 7:
				setColor(255, 250, 80, 80);
				break;
			case 6:
				setColor(255, 255, 100, 100);
				break;
			case 5:
				setColor(255, 255, 120, 120);
				break;
			case 4:
				setColor(255, 255, 140, 140);
				break;
			case 3:
				setColor(255, 255, 160, 160);
				break;
			case 2:
				setColor(255, 255, 180, 180);
				break;
			case 1:
				setColor(255, 255, 200, 200);
				break;
			default:
				setColor(255, 255, 0, 0);
				break;
		}
			
	}

	@Override
	public void changeHeading(int inHeading) {

		this.setHeading(inHeading);
	}

	public String toString() {
		
		String outputString = "";
		
		outputString = "Ant: " +
					   "loc=" + this.getLocationString() + 
					   " color=" + "(" + ColorUtil.alpha(this.getColor()) + ", "
					   				   + ColorUtil.red(this.getColor()) + ", "
					   				   + ColorUtil.green(this.getColor()) + ", "
					   				   + ColorUtil.blue(this.getColor()) + ")" + 
					   " heading=" + this.getHeading() + 
					   " speed=" + this.getSpeed() +
					   " size=" + this.getSize() +
					   " maxSpeed=" + this.getMaximumSpeed() +
					   " foodLevel=" + this.getCurrentFoodLevel() + 
					   " foodConsumptionRate=" + this.getFoodConsumptionRate();
				
		return outputString;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		
		Point location = INSTANCE.getLocation();
		int diameter = INSTANCE.getSize();
		
		int x = (int) (pCmpRelPrnt.getX() + location.getX()) - diameter/2;
		int y = (int) (pCmpRelPrnt.getY() + location.getY()) - diameter/2;
		
		g.setColor(this.getColor());
		
		g.fillArc(x, y, diameter, diameter, 0, 360);
	}

	@Override
	public Boolean collidesWith(GameObject otherObject) {
		
		int thisBoundaryOffset = this.getSize()/2;
		int otherBoundaryOffset = otherObject.getSize()/2;
		
		float thisX = this.getLocation().getX();
		float thisY = this.getLocation().getY();
		
		float otherX = otherObject.getLocation().getX();
		float otherY = otherObject.getLocation().getY();
		
		if (((thisX + thisBoundaryOffset < otherX - otherBoundaryOffset) ||
				(thisX - thisBoundaryOffset > otherX + otherBoundaryOffset)) ||
			((thisY + thisBoundaryOffset < otherY - otherBoundaryOffset) ||
				(thisY - thisBoundaryOffset > otherY + otherBoundaryOffset))) {
			
			return false;
		}
		
		return true;
	}

	@Override
	public void handleCollision(GameObject otherObject) {
		
		ArrayList<GameObject> objectsCurrentlyCollidingWith = this.getObjectsCurrentlyCollidingWith();
		
		if (!objectsCurrentlyCollidingWith.contains(otherObject)) {
			
			objectsCurrentlyCollidingWith.add(otherObject);
			
			this.setObjectsCurrentlyCollidingWith(objectsCurrentlyCollidingWith);
			
			if (otherObject instanceof FoodStation) {
				
				gw.foodStationCollision((FoodStation)otherObject);
			}
			
			else if (otherObject instanceof Spider) {
				
				gw.spiderCollision();
			}
			
			else if (otherObject instanceof Flag) {
				
				gw.flagCollision(((Flag) otherObject).getSequenceNumber());
			}
		}
	}
}
