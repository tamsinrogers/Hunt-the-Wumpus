/**
 * File:Hunter.java
 * Author: Tamsin Rogers
 * Date: 4/28/20
 * CS 231 Project 10
 */
 
import java.util.*;
import java.util.Comparator;
import java.lang.Object;
import java.util.Arrays;
import java.util.Collections;
import java.awt.Color;
import java.awt.Graphics;

/* represents the player moving around the cave */
public class Hunter
{
	private Vertex location;
	private boolean armed;
	private boolean alive;
	
	/* constructor */
	public Hunter(Vertex vertex)
	{
		this.location = vertex;
		this.location.setVisible(true);
		this.armed = false;
		this.alive = true;
	}
	
	/* returns the hunter's location */
	public Vertex getLocation()
	{
		return this.location;
	}
	
	/* sets the hunter's location */
	public void setLocation(int x, int y)
	{
		this.location.setPosition(x,y);
	}
	
	/* sets the hunter's visibility */
	public void setVisible(boolean v)
	{
		this.location.setVisible(v);
	}
	
	/* returns the hunter's visiblity */
	public boolean getVisible()
	{
		return this.location.getVisible();
	}
	
	/* updates the vertex field when the hunter moves */
	public void moveToVertex(Vertex newVertex)
	{
		this.location = newVertex;
	}
	
	/* moves the hunter up */
	public void moveUp()
	{
		Vertex newV = this.getLocation().getNeighbor((this.getLocation().getX()), (this.getLocation().getY()-1));		
		this.moveToVertex(newV);
	}
	
	/* moves the hunter down */
	public void moveDown()
	{
		Vertex newV = this.getLocation().getNeighbor((this.getLocation().getX()), (this.getLocation().getY()+1));		
		this.moveToVertex(newV);
	}
	
	/* moves the hunter to the left */
	public void moveLeft()
	{
		Vertex newV = this.getLocation().getNeighbor((this.getLocation().getX()-1), (this.getLocation().getY()));		
		this.moveToVertex(newV);
	}
	
	/* moves the hunter to the right */
	public void moveRight()
	{
		Vertex newV = this.getLocation().getNeighbor((this.getLocation().getX()+1), (this.getLocation().getY()));		
		this.moveToVertex(newV);
	}
	
	/* sets whether the hunter is armed or not */
	public void setArmed(boolean a)
	{
		this.armed = a;
	}
	
	/* returns true/false whether the hunter is armed or not */
	public boolean getArmed()
	{
		return this.armed;
	}
	
	/* sets the hunter's life status */
	public void setAlive(boolean a)
	{
		this.alive = a;
	}
	
	/* returns the hunter's life status */
	public boolean getAlive()
	{
		return this.alive;
	}
	
	/* draws the hunter on the screen */
	public void draw(Graphics g, int scale)
	{
		int xpos = (int)this.location.getX()*scale;
        int ypos = (int)this.location.getY()*scale;
        int border = 2;
		int half = scale/2;
		int eighth = scale/8;
		int sixteenth = scale /16;
		
        if(this.getArmed() == true)					// if the hunter is armed, draw a rectangle
		{
			g.setColor(Color.blue);
        	g.drawRect(xpos, ypos, scale, scale);
        	g.fillRect(xpos, ypos, scale, scale);
		}
		
		if(this.getArmed() == false)				// if the hunter is not armed, draw a circle
		{
			g.setColor(Color.blue);						
			g.drawOval(xpos, ypos, scale, scale);
			g.fillOval(xpos, ypos, scale, scale);
        }
    }

}