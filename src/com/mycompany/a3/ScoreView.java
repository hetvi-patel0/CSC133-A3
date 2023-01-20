package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {
	
	private Label timeValueLabel;
	private Label livesValueLabel;
	private Label lastFlagValueLabel;
	private Label foodLevelValueLabel;
	private Label healthLevelValueLabel;
	private Label soundValueLabel;

	public ScoreView(Object data) {
		
		this.setLayout(new FlowLayout(Container.CENTER));
		
		GameWorld gw = (GameWorld) data;
		
		int lives = gw.getLives();
		int clock = (int) Math.floor(gw.getClock());
		int lastFlag = 1;
		int foodLevel = 30;
		int healthLevel = 10;
		Boolean sound = gw.getSound();
		
		this.getAllStyles().setBgTransparency(255);
		
		
		Label timeLabel = new Label("Time: ");
		timeLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(timeLabel);
		
		timeValueLabel = new Label(String.valueOf(clock));
		timeValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(timeValueLabel);
		
		
		Label livesLabel = new Label("Lives Left: ");
		livesLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(livesLabel);
		
		livesValueLabel = new Label(String.valueOf(lives));
		livesValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(livesValueLabel);
		
		
		Label lastFlagLabel = new Label("Last Flag Reached: ");
		lastFlagLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(lastFlagLabel);
		
		lastFlagValueLabel = new Label(String.valueOf(lastFlag));
		lastFlagValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(lastFlagValueLabel);
		
		
		Label foodLevelLabel = new Label("Food Level: ");
		foodLevelLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(foodLevelLabel);
		
		foodLevelValueLabel = new Label(String.valueOf(foodLevel));
		foodLevelValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(foodLevelValueLabel);
		
		
		Label healthLevelLabel = new Label("Health Level: ");
		healthLevelLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(healthLevelLabel);
		
		healthLevelValueLabel = new Label(String.valueOf(healthLevel));
		healthLevelValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(healthLevelValueLabel);
		
		
		Label soundLabel = new Label("Sound: ");
		soundLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.add(soundLabel);
		
		soundValueLabel = new Label();
		soundValueLabel.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		
		if (sound) {
			
			soundValueLabel.setText("ON");
		}
		
		else {
			
			soundValueLabel.setText("OFF");
		}
		
		this.add(soundValueLabel);
	}
	
	@Override
	public void update(Observable observable, Object data) {

		GameWorld gw = (GameWorld) data;
		
		Ant ant = Ant.getInstance(gw.getTickRate(), gw);
		
		int lives = gw.getLives();
		int clock = (int) Math.floor(gw.getClock());
		int lastFlag = ant.getLastFlagReached();
		int foodLevel = (int) ant.getCurrentFoodLevel();
		int healthLevel = ant.getHealthLevel();
		Boolean sound = gw.getSound();
		
		timeValueLabel.setText(String.valueOf(clock));
		livesValueLabel.setText(String.valueOf(lives));
		lastFlagValueLabel.setText(String.valueOf(lastFlag));
		foodLevelValueLabel.setText(String.valueOf(foodLevel));
		healthLevelValueLabel.setText(String.valueOf(healthLevel));
		
		if (sound) {
			
			soundValueLabel.setText("ON");
		}
		
		else {
			
			soundValueLabel.setText("OFF");
		}
	}

}
