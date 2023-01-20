package com.mycompany.a3;

import java.io.IOException;
import java.io.InputStream;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.ComponentSelector;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class Game extends Form implements Runnable {
	
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private Toolbar tb;
	
	private UITimer timer;
	private int tickRate;
	
	// Commands
	private Accelerate a;
	private Brake b;
	private TurnLeft l;
	private TurnRight r;
	private Help h;
	private About about;
	private Exit e;
	
	private Play play;
	private Pause pause;
	private Position position;
	
	private Container bottomContainer;
	private Button modeButton;
	private Container leftContainer;
	private Container rightContainer;
	
	public Game() {
		
		tickRate = 20;
		
		timer = new UITimer(this);
		timer.schedule(tickRate, true, this);
		
		gw = new GameWorld(); // create “Observable” GameWorld
		mv = new MapView(gw); // create an “Observer” for the map
		sv = new ScoreView(gw); // create an “Observer” for the game/ant state data
		
		gw.addObserver(mv); // register the map observer
		gw.addObserver(sv); // register the score observer
		
		// Add Commands
		a = new Accelerate("Accelerate", gw);
		b = new Brake("Brake", gw);
		l = new TurnLeft("TurnLeft", gw);
		r = new TurnRight("TurnRight", gw);
		play = new Play("Play", this);
		pause = new Pause("Pause", this);
		position = new Position("Position", mv);

		
		gw.setTickRate(tickRate);
		
		setLayout(new BorderLayout());

		tb = new Toolbar();
		setToolbar(tb);
		
		this.setTitle("WalkIt Game");
		
		h = new Help("Help");
		tb.addCommandToRightBar(h);
		
		Command menu = new Command("");
		tb.addCommandToLeftBar(menu);
		
		tb.addCommandToSideMenu(a);
		
		about = new About("About");
		tb.addCommandToSideMenu(about);
		
		e = new Exit("Exit", gw);
		tb.addCommandToSideMenu(e);
		
		// side menu checkbox
		CheckBox checkSideMenuComponent = new CheckBox("Sound");
		checkSideMenuComponent.getAllStyles().setBgTransparency(255);
		checkSideMenuComponent.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		
		//create a command object and set it as the command of check box
		Command mySideMenuItemCheck = new SoundCmd("Sound", this, gw);
		checkSideMenuComponent.setCommand(mySideMenuItemCheck);
		
		//add the CheckBox component as a side menu item
		tb.addComponentToSideMenu(checkSideMenuComponent);
		
		
		

		
		// Add MapView and ScoreView to display
		add(BorderLayout.CENTER, mv);
		add(BorderLayout.NORTH, sv);
		
		
		
		// Add "Accelerate" and "Left" buttons to display
		
		leftContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		leftContainer.getAllStyles().setBgTransparency(255);
		
		Button accelerateButton = new Button("Accelerate");
		accelerateButton.setCommand(a);
		addKeyListener('a', a);
		
		accelerateButton.getUnselectedStyle().setBgTransparency(255);
		accelerateButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		accelerateButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		accelerateButton.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		accelerateButton.getUnselectedStyle().setMarginTop(75);
		accelerateButton.getUnselectedStyle().setPaddingTop(5);
		accelerateButton.getUnselectedStyle().setPaddingBottom(5);
		
		leftContainer.add(accelerateButton);
		
		
		
		Button leftButton = new Button("Left");
		leftButton.setCommand(l);
		addKeyListener('l', l);
		
		leftButton.getUnselectedStyle().setBgTransparency(255);
		leftButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		leftButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		leftButton.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		leftButton.getUnselectedStyle().setPaddingTop(5);
		leftButton.getUnselectedStyle().setPaddingBottom(5);
		
		leftContainer.add(leftButton);
		
		add(BorderLayout.WEST, leftContainer);
		
		
		
		// Add "Brake" and "Right" buttons to display
		
		rightContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		rightContainer.getAllStyles().setBgTransparency(255);
		
		Button brakeButton = new Button("Brake");
		brakeButton.setCommand(b);
		addKeyListener('b', b);
		
		brakeButton.getUnselectedStyle().setBgTransparency(255);
		brakeButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		brakeButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		brakeButton.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		brakeButton.getUnselectedStyle().setMarginTop(75);
		brakeButton.getUnselectedStyle().setPaddingTop(5);
		brakeButton.getUnselectedStyle().setPaddingBottom(5);
		
		rightContainer.add(brakeButton);
		
		
		
		Button rightButton = new Button("Right");
		rightButton.setCommand(r);
		addKeyListener('r', r);
		
		rightButton.getUnselectedStyle().setBgTransparency(255);
		rightButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		rightButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		rightButton.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		rightButton.getUnselectedStyle().setPaddingTop(5);
		rightButton.getUnselectedStyle().setPaddingBottom(5);
		
		rightContainer.add(rightButton);
		
		add(BorderLayout.EAST, rightContainer);
		
		
		
		// Add Position and Play/Pause buttons to display
		
		bottomContainer = new Container(new FlowLayout(Container.CENTER));
		
		bottomContainer.getAllStyles().setBgTransparency(255);
		
		
		modeButton = new Button("Pause");
		modeButton.setCommand(pause);
		
		modeButton.getUnselectedStyle().setBgTransparency(255);
		modeButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		modeButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		modeButton.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		modeButton.getUnselectedStyle().setPaddingTop(5);
		modeButton.getUnselectedStyle().setPaddingBottom(5);

		bottomContainer.add(modeButton);
		
		
		
		Button positionButton = new Button("Position");
		positionButton.setCommand(position);
		
		positionButton.getUnselectedStyle().setBgTransparency(255);
		positionButton.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		positionButton.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		positionButton.getUnselectedStyle().setBorder(Border.createLineBorder(3,ColorUtil.BLACK));
		positionButton.getUnselectedStyle().setPaddingTop(5);
		positionButton.getUnselectedStyle().setPaddingBottom(5);

		bottomContainer.add(positionButton);
		
		
		
		add(BorderLayout.SOUTH, bottomContainer);
		
		
		
		this.show();
		
		GameWorld.setHeight(mv.getHeight());
		GameWorld.setWidth(mv.getWidth());
		
		gw.init();
		
		gw.createSounds();
		
		revalidate();
	}

	public void play() {
		
		timer = new UITimer(this);
		timer.schedule(tickRate, true, this);
		
		ComponentSelector.$(modeButton, bottomContainer).setCommand(pause).setText("Pause");
		
		gw.setIsPaused(false);
		
		GameObjectCollection goc = gw.getGameObjectCollection();
		
		IIterator theElements = goc.getIterator();
		
		while (theElements.hasNext()) {
			
			GameObject object = (GameObject) theElements.getNext();
			
			if (object instanceof ISelectable) {
				
				ISelectable selectableObject = (ISelectable)object;
				
				selectableObject.setSelected(false);
			}
		}
		
		ComponentSelector.$(leftContainer).setEnabled(true);
		ComponentSelector.$(rightContainer).setEnabled(true);
		
		tb.removeOverflowCommand(a);
		a.setEnabled(true);
		tb.addCommandToSideMenu(a);
		
		addKeyListener('a', a);
		addKeyListener('b', b);
		addKeyListener('l', l);
		addKeyListener('r', r);
		
		if (gw.getSound()) {
			
			gw.playBackgroundSound();
		}
		
		this.revalidate();
	}
	
	public void pause() {

		timer.cancel();
		
		gw.pauseBackgroundSound();
		
		ComponentSelector.$(modeButton, bottomContainer).setCommand(play).setText("Play");
		ComponentSelector.$(leftContainer).setEnabled(false);
		ComponentSelector.$(rightContainer).setEnabled(false);
		
		tb.removeOverflowCommand(a);
		a.setEnabled(false);
		tb.addCommandToSideMenu(a);
		
		removeKeyListener('a', a);
		removeKeyListener('b', b);
		removeKeyListener('l', l);
		removeKeyListener('r', r);
		
		gw.setIsPaused(true);

		this.revalidate();
	}
	
	public void run() {
		
		gw.advanceTime();
		
		mv.repaint();
	}
}
