/**
 * File: Vertex.java
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

/* represents the nodes in a graph, each vertex represents a room */
public class Vertex implements Comparable<Vertex>
{
	private ArrayList<Vertex> neighbors;
	private int x;
	private int y;
	private boolean visible;
	private double cost;
	private boolean visited;
	private Vertex parent;	
	
	/* constructor */
	public Vertex(int x, int y, boolean visited) 
	{
        this.neighbors = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = visited;
    }
    
    /* constructor */
    public Vertex(int x, int y) 
	{
        this.neighbors = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.visited = false;
    }
    
	public void setNeighbors(ArrayList<Vertex> neighbors)
	{
		this.neighbors = neighbors;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	/* returns the vertex's x position */
	public int getX()
	{
		return this.x;
	}
	
	/* sets the vertex's Y position */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/* returns the vertex's Y position */
	public int getY()
	{
		return this.y;
	}
	
	/* sets the vertex's xy position */
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/* sets the vertex's visibility */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	/* returns the vertex's visibility */
	public boolean getVisible()
	{
		return this.visible;
	}
	
	/* sets the vertex's cost */
	public void setCost(double cost)
	{
		this.cost = cost;
	}
	
	/* returns the vertex's cost */
	public double getCost()
	{
		return this.cost;
	}
	
	/* sets the vertex's visited status */
	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}
	
	/* returns the vertex's visited status */
	public boolean getVisited()
	{
		return this.visited;
	}
	
	/* sets the vertex's parent vertex */
	public void setParent(Vertex parent)
	{
		this.parent = parent;
	}
	
	/* returns the vertex's parent vertex */
	public Vertex getParent()
	{
		return this.parent;
	}
	
	/* returns the distance between this vertex and the other vertex */
	public double distance(Vertex other)
	{
		double x1 = this.getX();
		double y1 = this.getY();
		double x2 = other.getX();
		double y2 = other.getY();
		
		double d = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
		return d;
	}
	
	/* update this vertex's adjacency list so that it connects with the other vertex 
		(unidirectional link) */
	public void connect(Vertex other)
	{
		if (! this.neighbors.contains(other))
		{
            this.neighbors.add(other);
        }
	}

	/* returns the vertex's neighbor at this location if it has one */
	public Vertex getNeighbor(int x, int y)
	{
		for(Vertex neighbor : this.neighbors)
		{
			if((neighbor.getX() == x) && (neighbor.getY() == y))
			{
				return neighbor;
			}
		}
		return null;
	}
	
	/* returns an ArrayList containing all of the Vertex's neighbors */
	public ArrayList<Vertex> getNeighbors()
	{
		return this.neighbors;
	}
	
	/* returns the number of connected vertices */
	public int numNeighbors()
	{
		return this.neighbors.size();
	}
	
	/* returns a string containing the Vertex's neighbors, cost, and marked flag */
	public String toString()
	{
		String s = "(position: " + "(" + this.getX() + ", " + this.getY() + ") neighbors: " + this.numNeighbors() + ", cost: " + this.getCost() + ", visited? " + this.getVisited() + ")";
		return s;
	}
	
	public static boolean matchPosition(Vertex a, Vertex b)
	{
		if((a.getX() == b.getX()) && (a.getY() == b.getY()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/* compares two vertexes based on their distance */
	public int compareTo(Vertex other) 
	{
		double x = this.getCost();
		double y = other.getCost();
		
		if(x>y)
		{
			return 1;
		}
		if(x<y)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	/* draws the vertex on the screen */
	 public void draw(Graphics g, int scale) 
	 {
        if (!this.visible)
        {
            return;
        }
        
        int xpos = (int)this.getX()*scale;
        int ypos = (int)this.getY()*scale;
        int border = 2;
        int half = scale / 2;
        int eighth = scale / 8;
        int sixteenth = scale / 16;
    
        if (this.getCost() <= 2)
        {
            g.setColor(Color.red);		// vertices are red if wumpus is close
        }
        else
        {
            g.setColor(Color.black);	// vertices are black if wumpus is not close
        }
        
        g.drawRect(xpos + border, ypos + border, scale - 2*border, scale - 2 * border);

        
        if (this.getNeighbor( this.getX(), this.getY()-1 ) != null )
        {
            g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
        }
        if (this.getNeighbor( this.getX(), this.getY()+1 ) != null )
    	{   
            g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), 
                       eighth, eighth + sixteenth);
        }
        if (this.getNeighbor( this.getX()-1, this.getY() ) != null)
        {   
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        }
        if (this.getNeighbor( this.getX()+1, this.getY() ) != null)
    	{
            g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, 
                       eighth + sixteenth, eighth);
        }
    }

	public static void main(String[] args) 
	{
		Vertex A = new Vertex(1,1, true);
		Vertex B = new Vertex(2,2, false);
		
		A.setX(3);
		A.setY(5);
		B.setX(4);
		B.setY(6);
		System.out.println("Ax = " + A.getX() + " (should be 3)");
		System.out.println("Ay = " + A.getY() + " (should be 5)");
		System.out.println("Bx = " + B.getX() + " (should be 4)");
		System.out.println("By = " + B.getY() + " (should be 6)");
		A.setPosition(7,8);
		System.out.println("Ax = " + A.getX() + " (should be 7)");
		System.out.println("Ay = " + A.getY() + " (should be 8)");
		
		A.setVisible(true);
		B.setVisible(false);
		System.out.println("A visibility: " + A.getVisible() + " (should be true)");
		System.out.println("B visibility: " + B.getVisible() + " (should be false)");
		
		A.setCost(10);
		System.out.println("A cost: " + A.getCost() + " (should be 10)");
		B.setCost(5);
		System.out.println("B cost: " + B.getCost() + " (should be 5)");
		System.out.println("distance between A & B: " + A.distance(B) );
		
		A.setVisited(false);
		B.setVisited(true);
		System.out.println("A visited: " + A.getVisited() + " (should be false)");
		System.out.println("B visited: " + B.getVisited() + " (should be true)");
		
		Vertex C = new Vertex(5,5, true);
		A.setParent(C);
		System.out.println("A parent: " + A.getParent() + " (should be C)");
		
		A.connect(B);
		A.connect(C);
		System.out.println("get A's neighbor at (4,6): " + A.getNeighbor(4,6) + " (should be B)");
		System.out.println("get A's neighbors: " + A.getNeighbors() );
		System.out.println("A's number of neighbors: " + A.numNeighbors() + " (should be 2)");
		System.out.println("get B's neighbors: " + B.getNeighbors() + " (should be null)");
		System.out.println("B's number of neighbors: " + B.numNeighbors() + " (should be 0)");

		System.out.println("compare A to B: " + A.compareTo(B) + " (should be 1)");
		System.out.println("compare B to A: " + B.compareTo(A) + " (should be -1)");
	}

}