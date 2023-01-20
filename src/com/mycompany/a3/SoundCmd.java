package com.mycompany.a3;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCmd extends Command {
	
	private Game myForm;
	private GameWorld gw;

	public SoundCmd(String command, Game fForm, GameWorld inGw) {
		
		super(command); //do not forget to set the “command name”
		
		myForm = fForm;
		gw = inGw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if (((CheckBox)evt.getComponent()).isSelected()) {
			
			gw.setSound(true);
			
			if (!gw.getIsPaused()) {
				
				gw.playBackgroundSound();
			}
		}
		
		else {
			
			gw.setSound(false);
			gw.pauseBackgroundSound();
		}
		
		gw.handleObservers();
			
		myForm.getToolbar().closeSideMenu();} //do not forget to close the side menu
	}
