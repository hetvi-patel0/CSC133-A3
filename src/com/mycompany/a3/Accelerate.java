package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class Accelerate extends Command {
	
	private GameWorld gw;
	
	public Accelerate(String command, GameWorld inGw) {
		
		super(command);
		
		gw = inGw;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		System.out.println("Ant accelerated");
		gw.accelerate();
	}

}
