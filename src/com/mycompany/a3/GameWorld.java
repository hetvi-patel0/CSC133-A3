package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import com.codename1.charts.models.Point;

public class GameWorld extends Observable
{
	
	private GameObjectCollection gameObjectCollection;
	
	private static int height;
	private static int width;
	private float clock;
	private int lives;
	private int numFlags;
	private Boolean sound;
	private Boolean isPaused;
	
	private int tickRate;
	
	private String gameMap;
	private String gameScore;
	private ArrayList<String> gameState;
	
	private Sound foodStationCollisionSound, flagCollisionSound, spiderCollisionSound;
	private BGSound backgroundSound;
	
	public GameWorld() {
		
		gameObjectCollection = new GameObjectCollection();
		
		this.clock = 0;
		this.lives = 3;
		this.setSound(false);
		this.setIsPaused(false);
		
		this.gameState = new ArrayList<String>();
		gameState.add(gameMap);
		gameState.add(gameScore);
	}
	
	public void init() {
		
		ArrayList<GameObject> gameObjects = gameObjectCollection.getObjects();
		
		// reset gameObjects
		gameObjectCollection.removeAllObjects();
		
		// height/width of a GameWorld "block", calculated by treating 
		// it as a 10x10 board; used for manually placing flags 
		float blockHeight = height/10;
		float blockWidth = width/10;
		
		// add four flag objects at predetermined locations with increasing sequenceNumbers
        Flag flag1 = new Flag(new Point(6 * blockWidth, 1 * blockHeight), 1);
        gameObjectCollection.add(flag1);
        Flag flag2 = new Flag(new Point(8 * blockWidth, 6 * blockHeight), 2);
        gameObjectCollection.add(flag2);
        Flag flag3 = new Flag(new Point(3 * blockWidth, 4 * blockHeight), 3);
        gameObjectCollection.add(flag3);
        Flag flag4 = new Flag(new Point(2 * blockWidth, 8 * blockHeight), 4);
        gameObjectCollection.add(flag4);
        numFlags = gameObjectCollection.getCollectionSize();
        
        // add Ant object, starting at the location of the first flag
        Ant ant = Ant.getInstance(tickRate, this);
        ant.setFoodConsumptionRate(tickRate / 100);
        //ant.resetAnt(flag1.getLocation());
        ant.resetAnt(new Point(0f, 0f));
        gameObjectCollection.add(ant);
        
        // add two spider objects of random size, speed, heading, and location
        Spider spider1 = new Spider();
        gameObjectCollection.add(spider1);
        Spider spider2 = new Spider();
        gameObjectCollection.add(spider2);
        
        // add two FoodStation objects of random size and location
        FoodStation foodStation1 = new FoodStation();
        gameObjectCollection.add(foodStation1);
        FoodStation foodStation2 = new FoodStation();
        gameObjectCollection.add(foodStation2);
        
        handleObservers();
	}

	public void handleObservers() {
		
		setChanged();
		notifyObservers(this);
	}
	
	protected static void setHeight(int inHeight) {
		
		height = inHeight;
	}
	
	protected static void setWidth(int inWidth) {
		
		width = inWidth;
	}
	
	protected static int getHeight() {
		
		return height;
	}
	
	protected static int getWidth() {
		
		return width;
	}
	
	public float getClock() {
		
		return clock;
	}

	public void setClock(float clock) {
		
		this.clock = clock;
	}

	public int getLives() {
		
		return lives;
	}

	public void setLives(int lives) {
		
		this.lives = lives;
	}
	
	public int getNumFlags() {
		
		return numFlags;
	}

	public void setNumFlags(int numFlags) {
		
		this.numFlags = numFlags;
	}

	public Boolean getSound() {
		
		return sound;
	}

	public void setSound(Boolean sound) {
		
		this.sound = sound;
	}

	public int getTickRate() {
		
		return tickRate;
	}

	public void setTickRate(int tickRate) {
		
		this.tickRate = tickRate;
	}

	public Boolean getIsPaused() {
		
		return isPaused;
	}

	public void setIsPaused(Boolean isPaused) {
		
		this.isPaused = isPaused;
	}

	public GameObjectCollection getGameObjectCollection() {
		
		return this.gameObjectCollection;
	}
	
	public void createSounds() {
		
		foodStationCollisionSound = new Sound("chomp.wav");
		flagCollisionSound = new Sound("flag.wav");
		spiderCollisionSound = new Sound("ow.wav"); 
		backgroundSound = new BGSound("background.wav");
	}

	public void playBackgroundSound() {
		
		backgroundSound.play();
	}

	public void pauseBackgroundSound() {
		
		backgroundSound.pause();
	}

	public void printGameMap() {
		
		gameMap = "";
		
		IIterator theElements = gameObjectCollection.getIterator();
		
		while (theElements.hasNext()) {
			
			GameObject object = (GameObject) theElements.getNext();
			
			gameMap += object.toString() + '\n';
		}
		
		System.out.print(gameMap);
	}

	private ArrayList<Integer> getObjectIndicesByType(String typeName) {
		
		IIterator theElements = gameObjectCollection.getIterator();
		
		ArrayList<Integer> objectIndices = new ArrayList<Integer>();
		
		int index = 0;
		while (theElements.hasNext()) {
			
			if (theElements.getNext().getClass().getSimpleName().equals(typeName)) {
				
				objectIndices.add(index);
			}
			
			index++;
		}
		
		return objectIndices;
	}

	public void accelerate() {
		
		// number to increase Ant speed by whenever accelerate() is called
		int accelerationValue = 5;
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		int currentSpeed = ant.getSpeed();
		
		// Ant can never accelerate over its current max speed
		if (currentSpeed + 5 > ant.getCurrentMaximumSpeed()) {
			
			ant.setSpeed(ant.getCurrentMaximumSpeed());
		}
		
		else {
			
			ant.setSpeed(ant.getSpeed() + accelerationValue);
		}
		
		handleObservers();
	}

	public void brake() {
		
		// number to decrease Ant speed by whenever brake() is called
		int brakeValue = 5;
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		int currentSpeed = ant.getSpeed();
		
		// Ant speed can never be negative
		if (currentSpeed - 5 < 0) {
			
			ant.setSpeed(0);
		}
		
		else {
			
			ant.setSpeed(ant.getSpeed() - brakeValue);
		}
		
		handleObservers();
	}
	
	public void turnLeft() {
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		int currentHeading = ant.getHeading();
		
		if (currentHeading == 0) {
			
			currentHeading = 360;
		}
		
		int newHeading = ant.getHeading() - 20;
		
		// Ant heading can never be negative
		if (newHeading < 0) {
			
			ant.setHeading(newHeading + 360);
		}
		
		else {
			
			ant.setHeading(newHeading);
		}
		
		handleObservers();
	}

	public void turnRight() {
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		int newHeading = ant.getHeading() + 20;
		
		if (newHeading > 359) {
			
			ant.setHeading(newHeading - 360);
		}
		
		else {
			
			ant.setHeading(newHeading);
		}
		
		handleObservers();
	}

	public void flagCollision(int flagSequenceNumber) {
		
		if (sound) {
		
			flagCollisionSound.play();
		}
		
		Ant ant = Ant.getInstance(tickRate, this);

		if (flagSequenceNumber == (ant.getLastFlagReached() + 1)) {
			ant.setLastFlagReached(flagSequenceNumber);
			
			if (flagSequenceNumber == numFlags) {
				
				gameWon();
			}
		}
		
		handleObservers();
	}

	public void foodStationCollision(FoodStation foodStation) {
		
		if (sound) {
			
			foodStationCollisionSound.play();
		}
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		if (foodStation.getCapacity() != 0) {
			
			ant.setCurrentFoodLevel(ant.getCurrentFoodLevel() + foodStation.getCapacity());
			foodStation.setCapacity(0);
			foodStation.lightenColor();
			
	        FoodStation newFoodStation = new FoodStation();
	        gameObjectCollection.add(newFoodStation);
		}

        
		handleObservers();
	}

	public void spiderCollision() {
		
		if (sound) {
			
			spiderCollisionSound.play();
		}
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		ant.setHealthLevel(ant.getHealthLevel() - 1);
		
		if (ant.getHealthLevel() == 0) {
			
			loseLife();
		}
		
		else {
			
			ant.lightenColorSlightly(ant.getHealthLevel());
		}
		
		handleObservers();
	}

	public void advanceTime() {
		
		ArrayList<Integer> spiderIndices = getObjectIndicesByType("Spider");
		
		IIterator theElements = gameObjectCollection.getIterator();
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		// change the heading of each Spider slightly
		for (int i = 0; i < spiderIndices.size(); i++) {
			
			Spider spider = (Spider)theElements.getElement(spiderIndices.get(i));
			spider.changeHeadingSlightly();
			theElements.setElement(spiderIndices.get(i), spider);
		}
		
		
		// if current object is Movable, move it
		while (theElements.hasNext()) { 
			
			GameObject object = (GameObject) theElements.getNext();
			
			if (object instanceof Movable) {
				
				Movable mObj = (Movable) object;
				mObj.move(tickRate);
			}
		}
		
		
		// check if Ant has collided with another object, and handle it accordingly
		IIterator theOtherElements = gameObjectCollection.getIterator();
		while (theOtherElements.hasNext()) {
			
			GameObject otherObject = (GameObject) theOtherElements.getNext();
			
			if (ant != otherObject) {
				
				if (ant.collidesWith(otherObject)) {
					
					ant.handleCollision(otherObject);
				}
				
				else {
					
					ArrayList<GameObject> objectsCurrentlyCollidingWith = ant.getObjectsCurrentlyCollidingWith();
					
					if (objectsCurrentlyCollidingWith.contains(otherObject)) {
						
						objectsCurrentlyCollidingWith.remove(otherObject);
						
						ant.setObjectsCurrentlyCollidingWith(objectsCurrentlyCollidingWith);
					}
				}
			}
		}
		
		
		// decrease Ant's foodLevel by its foodConsumptionRate
		// if Ant runs out of food, a life is lost
		ant.setCurrentFoodLevel(ant.getCurrentFoodLevel() - ant.getFoodConsumptionRate());
		
		if (ant.getCurrentFoodLevel() == 0) {
			
			loseLife();
		}
		
		
		// advance clock
		clock += tickRate * 0.001;
		
		handleObservers();
	}

	private void loseLife() {

		lives -= 1;
		
		System.out.println();
		
		// if player is out of lives, the game ends
		if (lives <= 0) {
			
			gameLost();
		}
		
		// if player has lives remaining, the game "resets" (but clock and # of lives remain)
		else {
			System.out.println("Life lost! Resetting game");
			init();
		}
		
		handleObservers();
	}

	private void gameLost() {
		
		System.out.println("Game over, you failed!");
		exit();
	}

	private void gameWon() {
		
		System.out.println();
		System.out.println("Game over, you win! Total time: " + (int) Math.floor(clock));
		handleObservers();
		exit();
	}

	public void exit() {
		
		System.exit(0);
	}

	@Override
	public String toString() {
		
		Ant ant = Ant.getInstance(tickRate, this);
		
		return "Lives remaining: " + lives
						+ "\nElapsed time: " + (int) Math.floor(clock)
						+ "\nHighest flag reached: " + ant.getLastFlagReached()
						+ "\nAnt's food level: " + ant.getCurrentFoodLevel()
						+ "\nAnt's health level: " + ant.getHealthLevel();
	}
}
