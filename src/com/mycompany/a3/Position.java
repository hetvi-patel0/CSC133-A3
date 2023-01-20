package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class Position extends Command {

	private MapView game;

	public Position(String command, MapView inGame) {
		
		super(command);
		
		this.game = inGame;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
	
		game.position();
	}
}
