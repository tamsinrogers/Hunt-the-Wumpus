/**
 * File:Graph.java
 * Author: Tamsin Rogers
 * Date: 4/28/20
 * CS 231 Project 10
 */
 
import java.util.*;
import java.util.Comparator;
import java.lang.Object;
import java.util.Arrays;
import java.util.Collections;

/* represents the nodes in a graph, each vertex represents a room */
public class Graph
{
	private ArrayList<Vertex> vertices;
	
	public Graph() 
	{
        this.vertices = new ArrayList<Vertex>();
    }
    
    /* returns the graph's list of vertices */
    public ArrayList<Vertex> getVertices()
    {
    	return this.vertices;
    }
	
	/* returns the number of vertices in the graph */
	public int vertexCount()
	{
		return this.vertices.size();
	}
	
	/* returns true if the given vertex is in the vertices list */
	public boolean inGraph(Vertex query)
	{
		if(vertices.contains(query))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/* connects v1 to v2 */
	public void addUniEdge(Vertex v1, Vertex v2)
	{
		if(! this.vertices.contains(v1))
		{
            this.vertices.add(v1);
        }
        if(! this.vertices.contains(v2))
		{
            this.vertices.add(v2);
        }
        v1.connect(v2);
	}
	
	/* connects v1 to v2 and v2 to v1 */
	public void addBiEdge(Vertex v1, Vertex v2)
	{
		if(!this.vertices.contains(v1))
		{
            this.vertices.add(v1);
        }
        if(!this.vertices.contains(v2))
		{
            this.vertices.add(v2);
        }
        v1.connect(v2);
        v2.connect(v1);
	}
	
	/* calculates the shortest path through the graph starting at v0 */
	public void shortestPath(Vertex v0)
	{
		for(Vertex v : this.vertices)				// initialize all vertices in G
		{
			v.setVisited(false);					// unmarked
			v.setCost(1000000);						// large cost
			v.setParent(null);						// null parent
		}
		
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();	// priority queue to hold vertex objects
		
		v0.setCost(0);                    									                             
		pq.add(v0);		
													
		
		while (pq.size() != 0)						// while pq is not empty
		{
			Vertex v = pq.poll();					// remove vertex with lowest cost
			
			v.setVisited(true);
			
			for (Vertex w: v.getNeighbors())		// for each of v's neighbors					
			{
				double distance = v.distance(w);	// distance between v and w
				
				if(w.getVisited() == false && v.getCost()+distance < w.getCost())	
				{
					w.setCost(v.getCost()+distance);	
					w.setParent(v);					//	v is w's parent			
					pq.add(w);							
				}	
			}
		}
	}
	
	public static void main(String[] args) 
	{
		// test graph class
		/*Graph graph = new Graph();
	
		Vertex A = new Vertex(1,1,false);
		Vertex B = new Vertex(1,4,false);
		Vertex C = new Vertex(3,6,false);
		Vertex D = new Vertex(6,5,false);
		Vertex E = new Vertex(6,1,false);
		Vertex F = new Vertex(3,2,false);
		
		graph.addUniEdge(A,B);
		graph.addUniEdge(B,C);
		graph.addUniEdge(C,D);
		graph.addUniEdge(D,E);
		graph.addUniEdge(E,F);
		graph.addUniEdge(F,A);
		
		ArrayList<Vertex> set = graph.getVertices();

		System.out.println("Before shortestPath");
		for( Vertex v: set ) 
		{
			System.out.println( v );
		}
		
		graph.shortestPath(C);
		
		System.out.println("\nAfter shortestPath");
		for( Vertex v: set ) 
		{
			System.out.println( v );
		}*/
		
		// task 1
		Graph graph = new Graph();
	
		Vertex A = new Vertex(1,1,false);
		Vertex B = new Vertex(1,4,false);
		Vertex C = new Vertex(3,6,false);
		Vertex D = new Vertex(6,5,false);
		Vertex E = new Vertex(6,1,false);
		Vertex F = new Vertex(3,2,false);
		Vertex G = new Vertex(5,5,false);
		Vertex H = new Vertex(7,2,false);
		Vertex I = new Vertex(3,3,false);
		Vertex J = new Vertex(2,2,false);
		
		graph.addUniEdge(A,B);
		graph.addUniEdge(B,C);
		graph.addUniEdge(C,D);
		graph.addUniEdge(D,H);
		graph.addUniEdge(E,F);
		graph.addUniEdge(F,G);
		graph.addUniEdge(F,J);
		graph.addUniEdge(J,I);
		graph.addUniEdge(G,H);
		graph.addUniEdge(I,B);
		graph.addUniEdge(A,J);
		
		ArrayList<Vertex> set = graph.getVertices();

		System.out.println("Before shortestPath");
		for( Vertex v: set ) 
		{
			System.out.println( v );
		}
		
		graph.shortestPath(I);
		
		System.out.println("\nAfter shortestPath");
		for( Vertex v: set ) 
		{
			System.out.println( v );
		}
		
	}
}