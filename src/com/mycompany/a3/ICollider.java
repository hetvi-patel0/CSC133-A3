package com.mycompany.a3;

public interface ICollider {

	Boolean collidesWith(GameObject otherObject);
	
	void handleCollision(GameObject otherObject);
}
