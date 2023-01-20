package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	
	private ArrayList<GameObject> gameObjects;
	
	public GameObjectCollection() {
		
		this.gameObjects = new ArrayList<GameObject>();
	}
	
	public ArrayList<GameObject> getObjects() {
		
		return gameObjects;
	}
	
	public void removeAllObjects() {
		
		gameObjects.clear();
	}
	
	public int getCollectionSize() {
		
		return gameObjects.size();
	}

	@Override
	public void add(Object newObject) {
		
		gameObjects.add((GameObject) newObject);
	}
	
	@Override
	public IIterator getIterator() {
		
		return new GameObjectsIterator();
	}

	private class GameObjectsIterator implements IIterator {

		private int currElementIndex;

		public GameObjectsIterator() {
			
			currElementIndex = -1;
		}
	
		public int getCurrElementIndex() {
			
			return currElementIndex;
		}
	
		@Override
		public GameObject getElement(int index) {
			
			return gameObjects.get(index);
		}
		
		@Override
		public void setElement(int index, Object object) {
			
			gameObjects.set(index, (GameObject) object);
		}
	
		@Override
		public boolean hasNext() {
		
			if (gameObjects.size() <= 0) {
				
				return false;
			}
			
			if (currElementIndex == (gameObjects.size() - 1)) {
				
				return false;
			}
			
			return true;
		}

		@Override
		public Object getNext() {
			
			currElementIndex++ ;
			
			return(gameObjects.get(currElementIndex));
		}
	}
}
