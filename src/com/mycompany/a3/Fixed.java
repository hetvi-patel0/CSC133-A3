package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Fixed extends GameObject implements ISelectable {

	public Fixed() {
		
		super.setLocation(this.generateLocation());
	}

	public Fixed(Point inLocation) {
		
		super.setLocation(inLocation);;
	}
}
