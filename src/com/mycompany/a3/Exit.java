package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class Exit extends Command {

	private GameWorld gw;

	public Exit(String command, GameWorld inGw) {
		
		super(command);
		
		gw = inGw;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		// Display a dialog box 
		Command cYes = new Command("Yes");
		Command cNo = new Command("No");
		Command[] cmds = new Command[]{cYes, cNo};
		
		Command c = Dialog.show("Exit the game?", "Select 'Yes' to exit or 'No' to return to the game", cmds);
		
		if (c == cYes) {
			
			gw.exit();
		}
	}
}
