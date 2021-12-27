/**
 * File: Wumpus.java
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

/* represents the Wumpus */
public class Wumpus
{
	private Vertex home;
	private boolean alive;
	private boolean drawVisible;						// whether the wumpus should be drawn or not

	/* constructor */
	public Wumpus(Vertex vertex)
	{
		this.home = vertex;
		this.setVisible(false);				
		this.alive = true;
	}
	
	/* returns the wumpus's home location */
	public void setHome(Vertex v)
	{
		this.home = v;
	}
	
	/* returns the wumpus's home location */
	public Vertex getHome()
	{
		return this.home;
	}
	
	/* sets the wumpus's visibility */
	public void setVisible(boolean v)
	{
		this.drawVisible = v;
	}
	
	/* returns the wumpus's visiblity */
	public boolean getVisible()
	{
		return this.drawVisible;
	}
	
	/* sets the wumpus's life status */
	public void setAlive(boolean a)
	{
		this.alive = a;
	}
	
	/* returns the wumpus's life status */
	public boolean getAlive()
	{
		return this.alive;
	}
	
	/* updates the vertex field when the wumpus is moved */
	public void moveToVertex(Vertex newVertex)
	{
		this.home = newVertex;
	}
	
	/* draws the vertex on the screen */
	public void draw(Graphics g, int scale)
	{
		int xpos = (int)this.home.getX()*scale;
		int ypos = (int)this.home.getY()*scale;
		int border = 2;
		int half = scale / 2;
		int eighth = scale / 8;
		int sixteenth = scale / 16;
	
		if(this.getVisible() == false)					// if the wumpus is set to be not visible
		{
			return;
		}
		
		else											// if the wumpus is visible
		{
			if(this.getAlive() == true)					// if the wumpus is alive
			{	
				g.setColor(Color.green);				// color it green
				g.drawOval(xpos, ypos, scale, scale);
				g.fillOval(xpos, ypos, scale, scale);
			}
			
			if(this.getAlive() == false)				// if the wumpus is dead
			{
				g.setColor(Color.red);					// color it red
				g.drawOval(xpos, ypos, scale, scale);
				g.fillOval(xpos, ypos, scale, scale);
			}
			
		}
	}
}