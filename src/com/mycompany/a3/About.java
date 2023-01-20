package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class About extends Command {

	public About(String command) {
		
		super(command);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		// Display a dialog box 
		Command cOk = new Command("OK");
		Command[] cmds = new Command[]{cOk};
		
		Dialog.show("About this Program", "Name: Hetvi Patel\n"
				+ "Class: CSC 133-02\n"
				+ "Version: 2.0"
				, cmds);
		
	}
}
