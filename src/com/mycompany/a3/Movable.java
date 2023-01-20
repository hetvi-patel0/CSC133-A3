package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Movable extends GameObject {

	// Compass angle (in degrees) the object is moving; 0 means
	// heading north (upwards on the screen), 90 means heading east, etc.
	private int heading;
	private int speed;

	public Movable() {
	}

	public int getHeading() {
		
		return this.heading;
	}

	public void setHeading(int inHeading) {

		this.heading = inHeading;
	}

	public int getSpeed() {
		
		return this.speed;
	}

	public void setSpeed(int inSpeed) {
		
		if (inSpeed < 0) {
			
			this.speed = 0;
		}
		
		else {
			
			this.speed = inSpeed;
		}
	}
	
	private int convertHeadingToStandardPosition(int inHeading) {
		
		int newHeading = 0;
		
		if (inHeading <= 90 && inHeading >= 0) {
			
			newHeading = 90 - inHeading;
		}
		
		else {
			
			newHeading = (360 - inHeading) + 90;
		}
		
		return newHeading;
	}
	
	private void correctHeading() {
		 
		 float x = this.getLocation().getX();
		 float y = this.getLocation().getY();
		 
		 int boundaryOffset = this.getSize()/2;
		 
		 // If Spider hits the Eastern (right) boundary or Western (left) boundary
		 if (x == (float)GameWorld.getWidth() - 1f - boundaryOffset || x == 1f + boundaryOffset) {
			 
			 reflectHeadingAcrossYAxis(getHeading());
		 }
		 
		 
		// If Spider hits the Northern (top) boundary or the Southern (bottom) boundary
		 else if (y == (float)GameWorld.getHeight() - 1f - boundaryOffset || y == 1f + boundaryOffset) {
			 
			 reflectHeadingAcrossXAxis(getHeading());
		 }

	 }
	 	 
	 private void reflectHeadingAcrossXAxis(int inHeading) {

		int newHeading = 0;
		
		if (inHeading == 0)
		{
			inHeading = 360;
		}
		
		if (inHeading <= 360 && inHeading > 180)
		{
			newHeading = (360 - inHeading) + 180;
		}
		
		else if (inHeading <= 180 && inHeading >= 0)
		{
			newHeading = 180 - inHeading;
		}
		
		
		if (newHeading == 360)
		{
			newHeading = 0;
		}
		
		this.setHeading(newHeading);
	}

	private void reflectHeadingAcrossYAxis(int inHeading) {
		
		setHeading(360 - inHeading);
	}
	
	public void move(int tickRate) {

		Point oldLocation = this.getLocation();
		
		// heading must be converted to standard position and then to radians before it can be used in trigonometric calculations
		float standardPositionHeading = convertHeadingToStandardPosition(heading);
		float headingInRadians = (float) Math.toRadians(standardPositionHeading);
		
		float distance = (float) (speed);
		
		float deltaX = (float) (Math.cos(headingInRadians) * distance);
		float deltaY = (float) (Math.sin(headingInRadians) * distance);
		
		float newX = oldLocation.getX() + deltaX;
		float newY = oldLocation.getY() + deltaY;
		
		float boundaryOffset = this.getSize()/2; 
		
		// if the new point is outside of bounds and the object is not an Ant (which can move outside of bounds),
		// calculate a new location that remains on the original path while staying in bounds
		if (!(this instanceof Ant) && outsideBounds(new Point(newX, newY), this.getSize())) {
			
			// if new x-coordinate is outside of the Eastern (right) boundary,
			// set x-coordinate to the highest valid value and re-calculate y-coordinate
			if (newX + boundaryOffset >= GameWorld.getWidth()) {
				
				newX = (GameWorld.getWidth() - 1f) - boundaryOffset;
				
				deltaX = newX - oldLocation.getX();
				
				deltaY = (float)(deltaX * (Math.tan(headingInRadians)));
				
				newY = oldLocation.getY() + deltaY;
			}
			
			// if new x-coordinate is outside of the Western (left) boundary,
			// set x-coordinate to the lowest valid value and re-calculate y-coordinate
			else if (newX - boundaryOffset <= 0) {
				
				newX =  1f + boundaryOffset;
				
				deltaX = newX - oldLocation.getX();
				
				deltaY = (float)(deltaX * (Math.tan(headingInRadians)));
				
				newY = oldLocation.getY() + deltaY;
			}
			
			// if new y-coordinate is outside of the Northern (top) boundary,
			// set y-coordinate to the highest valid value and re-calculate x-coordinate
			if (newY + boundaryOffset >= GameWorld.getHeight()) {
				
				newY = (GameWorld.getHeight() - 1f) - boundaryOffset;
				
				deltaY = newY - oldLocation.getY();
				
				deltaX = (float)(deltaY / (Math.tan(headingInRadians)));
				
				newX = oldLocation.getX() + deltaX;
			}
			
			// if new y-coordinate is outside of the Southern (bottom) boundary,
			// set y-coordinate to the lowest valid value and re-calculate x-coordinate
			else if (newY - boundaryOffset <= 0) {
				
				newY =  1f + boundaryOffset;
				
				deltaY = newY - oldLocation.getY();
				
				deltaX = (float)(deltaY / (Math.tan(headingInRadians)));
				
				newX = oldLocation.getX() + deltaX;
			}
			
			// change heading so object "bounces" off boundary edge
			this.correctHeading();
		}
		
		Point newLocation = new Point(newX, newY);
		
		setLocation(newLocation);
	}
	
}
