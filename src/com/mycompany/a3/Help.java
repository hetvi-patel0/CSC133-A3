package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class Help extends Command {

	public Help(String command) {
		
		super(command);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		// Display a dialog box 
		Command cOk = new Command("OK");
		Command[] cmds = new Command[]{cOk};
		
		Dialog.show("Available keys/commands", "a: accelerate\n"
				+ "b: brake\n"
				+ "l: turn left\n"
				+ "r: turn right\n"
				+ "f: collide with food station\n"
				+ "g: collide with spider\n"
				+ "t: advance time by one"
				, cmds);
		
	}

}
