package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class Pause extends Command {

	private Game g;

	public Pause(String command, Game inGame) {
		
		super(command);
		
		g = inGame;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		System.out.println("Game paused");
		g.pause();
	}
}
