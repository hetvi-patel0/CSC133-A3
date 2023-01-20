package com.mycompany.a3;

public interface IIterator {

	public boolean hasNext();

	public Object getNext();
	
	public Object getElement(int index);

	public void setElement(int index, Object object);
}
