package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {

	GameWorld gw;
	
	private int iPx;
	private int iPy;
	
	private Boolean searchingForPosition;

	public MapView(Object data) {
		
		GameWorld gw = (GameWorld) data;
		
		searchingForPosition = false;
		
		gw.printGameMap();
		
		setLayout(new BorderLayout());
		
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
	}

	@Override
	public void update(Observable observable, Object data) {
	
		GameWorld gw = (GameWorld) data;
		//System.out.println();
		//gw.printGameMap();
		this.gw = gw;
		//System.out.println();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		GameObjectCollection goc = gw.getGameObjectCollection();
		
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		IIterator theElements = goc.getIterator();

		while (theElements.hasNext()) {
			
			GameObject object = (GameObject) theElements.getNext();
			object.draw(g, pCmpRelPrnt);
		}
	}

	public void position() {
		
		if (gw.getIsPaused()) {
			
			IIterator theElements = gw.getGameObjectCollection().getIterator();
			
			while (theElements.hasNext()) { 
				
				GameObject object = (GameObject) theElements.getNext();
				
				if (object instanceof ISelectable && ((ISelectable) object).isSelected()) {
					
					searchingForPosition = true;
				}
			}
		}
	}

	@Override
	public void pointerPressed(int x, int y) {
		//make pointer location relative to parent’s origin

		if (gw.getIsPaused()) {
			
			iPx = x - getParent().getAbsoluteX();
			iPy = y - getParent().getAbsoluteY();
			
			Point pPtrRelPrnt = new Point(iPx, iPy); 
			Point pCmpRelPrnt = new Point(getX(), getY());
			
			GameObjectCollection goc = gw.getGameObjectCollection();
			
			IIterator theElements = goc.getIterator();
			
			while (theElements.hasNext()) {
				
				GameObject object = (GameObject) theElements.getNext();
				
				if (object instanceof ISelectable) {
					
					ISelectable selectableObject = (ISelectable)object;
					
					if (searchingForPosition && selectableObject.isSelected()) {
						
						GameObject selectableGameObject = (GameObject)selectableObject;
						
						if (selectableGameObject.getSize() > 120) {
						
							selectableGameObject.setLocation((float)(iPx - selectableGameObject.getSize()*1.5), (float)(iPy - selectableGameObject.getSize()*0.5));
						}
						
						else if (selectableGameObject.getSize() >= 92 && selectableGameObject.getSize() <= 120) {
							
							selectableGameObject.setLocation((float)(iPx - selectableGameObject.getSize()*1.8), (float)(iPy - selectableGameObject.getSize()*0.75));
						}
						
						else {
							
							selectableGameObject.setLocation((float)(iPx - selectableGameObject.getSize()*2.2), (float)(iPy - selectableGameObject.getSize()*0.75));
						}
						
						searchingForPosition = false;
						System.out.println(selectableGameObject.getClass().getSimpleName() + " was repositioned by player");
					}
					
					if (selectableObject.contains(pPtrRelPrnt, pCmpRelPrnt)) {
						
						selectableObject.setSelected(true);
					} 
					
					else {
						
						selectableObject.setSelected(false);
					}
				}
			}
			
			repaint();
		}
	}
}
