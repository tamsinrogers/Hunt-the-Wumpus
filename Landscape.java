/**
 * File: Landscape.java
 * Author: Tamsin Rogers
 * Date: 4/28/20
 * CS 231 Project 10
 */
 
import java.util.Iterator;
import java.util.*;
import java.util.Collections;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Landscape
{
	private int width;
	private int height;
	private Hunter hunter;
	private Wumpus wumpus;
	private ArrayList<Vertex> list;
	private Graph graph;
	
	private LinkedList<Vertex> agentList;

	/* constructor */
	public Landscape(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.agentList = new LinkedList<Vertex>();
	}
	
	/* inserts an agent at the beginning of its list of agents */
	public void addBackgroundAgent(Vertex v)
	{
		agentList.addFirst(v);								// add agent a to the beginning of the list
	}
	
	/* returns the height of the landscape */
	public int getHeight()
	{
		return this.height;
	}
	
	/* returns the height of the landscape */
	public int getWidth()
	{
		return this.width;
	}
	
	public ArrayList<Vertex> getList()
	{
		return this.list;
	}
	
	public void setHunter(Hunter hunter)
	{
		this.hunter = hunter;
	}

	public Hunter getHunter()
	{
		return this.hunter;
	}
	
	public void setWumpus(Wumpus wumpus)
	{
		this.wumpus = wumpus;
	}
	
	public Wumpus getWumpus()
	{
		return this.wumpus;
	}

	public void draw(Graphics g, int scale)
	{
		for (Vertex v : this.agentList)
		{
			v.draw(g,scale);			// draw the vertices
		}
		this.hunter.draw(g,scale);	// draw the hunter
		this.wumpus.draw(g,scale);	// draw the wumpus
	}

	
	
}