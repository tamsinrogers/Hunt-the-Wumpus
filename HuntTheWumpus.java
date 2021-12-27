/*
  Template created by Bruce A. Maxwell and Stephanie R Taylor
 * File: HuntTheWumpus.java
 * Author: Tamsin Rogers
 * Date: 5/6/20
 * CS 231 Project 10
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;
import javax.swing.ImageIcon;

import java.util.*;

/* creates the game window */
public class HuntTheWumpus
{
	
	/* controls the game state: playing or exiting */
    private enum PlayState 
    { 
    	PLAY, STOP 
    }

    private JFrame win;
    private LandscapePanel canvas;    
    private Landscape scape; 
    private int scale;
    private PlayState state;
    private Graph graph;
    private Hunter hunter;
    private Wumpus wumpus;
    private int height;
    private int width;
    private Vertex[][] vertices;
    private Random rand;
	private JLabel hunterLabel;
	private JLabel wumpusLabel;

    /* creates the main game window */
    public HuntTheWumpus() 
    {
        this.state = PlayState.PLAY; 									// game starts in play mode
        this.scale = 64; 										
        this.scape = new Landscape(scale*11,scale*11);
        this.graph = new Graph();										// create a graph
        this.height = this.scape.getHeight()/50;						// height of the vertex array
		this.width = this.scape.getWidth()/50;							// width of the vertex array
		this.rand = new Random();
		this.vertices = new Vertex[height][width];						// create an new 2d array to store vertices

		for(int i=0 ; i<height; i++)										
		{
			for(int j=0 ; j<width; j++)
			{
				vertices[i][j] = new Vertex(i,j);
			}
		}
		for(int i=0; i<height-1; i++)													
		{
			for(int j=0; j<width; j++)
			{
				this.graph.addBiEdge(vertices[i][j], vertices[i+1][j]);
			}
		}
		for(int j=0; j<height; j++)															
		{
			for(int i=0; i<width-1; i++)
			{
				this.graph.addBiEdge(vertices[j][i], vertices[j][i+1]);
			}
		}
        
        for(Vertex v : this.graph.getVertices())
        {
        	this.scape.addBackgroundAgent(v);							// add all the vertices in the grid to the Landscape list
        	v.setVisible( false );										// set all the vertices in the grid to be invisible
        }
        
        Vertex h = vertices[(int)(height)/2][(int)(width)/2];			// start hunter about in the middle
		this.hunter = new Hunter(h);									// set the hunter's location to the vertex
		this.scape.setHunter(this.hunter);	
		this.scape.addBackgroundAgent(h);
		this.hunter.setVisible(true);
		
		Vertex w = vertices[rand.nextInt(height)][rand.nextInt(width)];	// wumpus random start location		
		while(w == this.hunter.getLocation())							// if the wumpus has the same location as the hunter
		{
			w = vertices[rand.nextInt(height)][rand.nextInt(width)];	// get a new wumpus random start location		
		}
		this.wumpus = new Wumpus(w);									// set up the wumpus at this vertex											
		this.scape.setWumpus(this.wumpus);	
		this.scape.addBackgroundAgent(w);
		this.wumpus.setVisible(false);
		
		this.graph.shortestPath(this.wumpus.getHome());					// find the shortest distance to reach the wumpus

        this.win = new JFrame("Hunt The Wumpus");						// the main game window
        win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.canvas = new LandscapePanel( this.scape.getWidth(), this.scape.getHeight() );
        this.win.add( this.canvas, BorderLayout.CENTER );
        this.win.pack();
        
        this.hunterLabel = new JLabel("the hunter is not armed");		// hunter label
        this.hunterLabel.setForeground(Color.blue);	
        this.wumpusLabel = new JLabel("the wumpus is alive");			// wumpus label
        this.wumpusLabel.setForeground(Color.GREEN);
        JButton exit = new JButton("Exit Game");						// exit button
        JButton instructions = new JButton("Instructions");
        
        JPanel panel = new JPanel( new FlowLayout(FlowLayout.RIGHT));
        JPanel panel2 = new JPanel( new FlowLayout(FlowLayout.CENTER));

		panel.add(instructions);
		panel.add(exit);
        panel2.add( this.hunterLabel );
        panel2.add( this.wumpusLabel );
        
        this.win.add( panel, BorderLayout.SOUTH);
        this.win.add( panel2, BorderLayout.NORTH);
        this.win.pack();

        Control control = new Control();								// key & button controls
        this.win.addKeyListener(control);
        this.win.setFocusable(true);
        this.win.requestFocus();
        exit.addActionListener( control );
        instructions.addActionListener( control );
        
        MouseControl mc = new MouseControl();
        this.canvas.addMouseListener( mc );
        this.canvas.addMouseMotionListener( mc );

        this.win.setVisible( true );									// make the window visible
    }
    
    /* returns the height of the array */
		public int getHeight()
	{
		return this.height;
	}
	
	/* returns the width of the array */
	public int getWidth()
	{
		return this.width;
	}
	
	/* returns the graph */
	public Graph getGraph()
	{
		return this.graph;
	}
	
	/* returns the landscape */
	public Landscape getScape()
	{
		return this.scape;
	}
	
	/* returns the window */
	public JFrame getWin()
	{
		return this.win;
	}

	/* handles visuals */
    private class LandscapePanel extends JPanel 
    {
        /* the game's drawing canvas */
        public LandscapePanel(int height, int width) 
        {
            super();
            this.setPreferredSize( new Dimension( width, height ) );
            this.setBackground(Color.white);
        }

        /* draws components of the game onto the screen */
        public void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            scape.draw( g, 50 );
        }
    } 
    
    /* mouse click events */
	private class MouseControl extends MouseInputAdapter 
	{
		public void mouseClicked(MouseEvent e) 
		{
			state = PlayState.STOP;
		}
	} 
    
	/* defines functions controlled by user events */
    private class Control extends KeyAdapter implements ActionListener 
    {
    	private int hx;													// store the hunter's x position
        private int hy;													// store the hunter's y position
        
        // used for restricting movement in certain directions
        private boolean canMoveUp = true;
        private boolean canMoveDown = true;
        private boolean canMoveLeft = true;
        private boolean canMoveRight = true;
        private boolean canMove = true;

		/* handles key click events */
        public void keyTyped(KeyEvent e) 
        {
        	if(this.canMove == false)
			{
				canMoveUp = false;
				canMoveDown = false;
				canMoveLeft = false;
				canMoveRight = false;
			}
        
            if( ("" + e.getKeyChar()).equalsIgnoreCase(" ") )			// if the space key is pressed
            {
            	if(hunter.getArmed() == false)							// if the hunter is not armed
            	{
            		hunter.setArmed(true);								// arm the hunter
            		hunterLabel.setText("the hunter is armed");
            	}
            	else													// if the hunter is armed
            	{
            		hunter.setArmed(false);								// disarm the hunter
            		hunterLabel.setText("the hunter is not armed");
            	}
            }

            if( ("" + e.getKeyChar()).equalsIgnoreCase("q") ) 			// if the q key is pressed
            {
                state = PlayState.STOP;									// quit the game
            }
            
            // MOVE UP
            if( ("" + e.getKeyChar()).equalsIgnoreCase("w") )			// if the w key is pressed
            {
            	if(hunter.getArmed() == false && canMoveUp == true)		// if the hunter is not armed
            	{
            		hunter.moveUp();									// move the hunter up
            		hunter.getLocation().setVisible(true);
            		
            		if(hunter.getLocation() == wumpus.getHome())		// if the hunter hits the wumpus
            		{
            			lose();
            		}
            	}
            	else if(hunter.getArmed() == true)						// if the hunter is armed
            	{
            		hunterLabel.setText("the hunter shot an arrow to the north & missed");
            		// target vertex is above the hunter
            		Vertex target = hunter.getLocation().getNeighbor((hunter.getLocation().getX()), (hunter.getLocation().getY()-1));	
            		while(target != null)								// while this target exists
            		{
            			if(wumpus.getHome() == target)					// if the target vertex is the wumpus's home vertex
            			{
            				win();
            			}
            			target = target.getNeighbor(target.getX(), target.getY()-1);
            		}
            	}
            }
            
            // MOVE LEFT
            if( ("" + e.getKeyChar()).equalsIgnoreCase("a") )			// if the a key is pressed
            {
            	if(hunter.getArmed() == false && canMoveLeft == true)	// if the hunter is not armed
            	{
            		hunter.moveLeft();									// move the hunter to the left
            		hunter.getLocation().setVisible(true);
            		
            		if(hunter.getLocation() == wumpus.getHome())		// if the hunter hits the wumpus
            		{
            			lose();
            		}
            	}
            	else if(hunter.getArmed() == true)						// if the hunter is armed
            	{
            		hunterLabel.setText("the hunter shot an arrow to the west & missed");
            		// target vertex is to the left of the hunter
            		Vertex target = hunter.getLocation().getNeighbor(hunter.getLocation().getX()-1, hunter.getLocation().getY());	
            		while(target != null)								// while this target exists
            		{
            			if(wumpus.getHome() == target)					// if the target is the wumpus's location
            			{
            				win();
            			}
            			target = target.getNeighbor(target.getX()-1, target.getY());
            		}
            	}
            }
            
            // MOVE DOWN
            if( ("" + e.getKeyChar()).equalsIgnoreCase("s") )			// if the s key is pressed
            {
            	if(hunter.getArmed() == false && canMoveDown == true)	// if the hunter is not armed
            	{
            		hunter.moveDown();									// move the hunter down
            		hunter.getLocation().setVisible(true);
            		
            		if(hunter.getLocation() == wumpus.getHome())		// if the hunter hits the wumpus
            		{
            			lose();
            		}
            	}
            	else if(hunter.getArmed() == true)						// if the hunter is armed
            	{
            		hunterLabel.setText("the hunter shot an arrow to the south & missed");
            		// target vertex is below the hunter
            		Vertex target = hunter.getLocation().getNeighbor(hunter.getLocation().getX(), hunter.getLocation().getY()+1);	
            		while(target != null)								// while this target exists
            		{
            			if(wumpus.getHome() == target)					// if the target is the wumpus's location
            			{
            				win();
            			}
            			target = target.getNeighbor(target.getX(), target.getY()-1);
            		}
            	}
            }
            
            if( ("" + e.getKeyChar()).equalsIgnoreCase("d") )			// if the d key is pressed
            {
            	if(hunter.getArmed() == false && canMoveRight == true)	// if the hunter is not armed
            	{
            		hunter.moveRight();									// move the hunter to the right
            		hunter.getLocation().setVisible(true);
            		
            		if(hunter.getLocation() == wumpus.getHome())		// if the hunter hits the wumpus
            		{
            			lose();
            		}
            	}
            	else if(hunter.getArmed() == true)						// if the hunter is armed
            	{
            		hunterLabel.setText("the hunter shot an arrow to the east & missed");
            		// target vertex is to the right of the hunter
            		Vertex target = hunter.getLocation().getNeighbor(hunter.getLocation().getX()+1, hunter.getLocation().getY());	
            		while(target != null)								// while this target exists
            		{
            			if(wumpus.getHome() == target)					// if the target is the wumpus's location
            			{
            				win();
            			}
            			target = target.getNeighbor(target.getX()+1, target.getY());
            		}
            	}
            }
            
			 // keep the hunter in the bounds of the landscape
            if(hunter.getLocation() != null)
            {	
				hx = hunter.getLocation().getX();
				hy = hunter.getLocation().getY();
            }
			if(hx > (getWidth()-2))										// if the hunter touches the north border
			{
				canMoveRight = false;
				canMoveLeft = true;
				canMoveUp = true;
				canMoveDown = true;
			}
			if(hy > (getHeight()-2))									// if the hunter touches the south border
			{
				canMoveDown = false;
				canMoveUp = true;
				canMoveLeft = true;
				canMoveRight = true;
			}
			if(hx < 1)													// if the hunter touches the left border
			{
				canMoveLeft = false;
				canMoveRight = true;
				canMoveUp = true;
				canMoveDown = true;
			}
			if(hy < 1)													// if the hunter touches the north border
			{
				canMoveUp = false;
				canMoveDown = true;
				canMoveLeft = true;
				canMoveRight = true;
			}
        }
        
        /* the hunter successfully shoots the wumpus */
        public void win()
        {
        	wumpus.setVisible(true);									// show the wumpus
			wumpus.setAlive(false);										// kill the wumpus
			wumpusLabel.setText("the wumpus is dead");
			hunterLabel.setText("the hunter killed the wumpus!");
        }
        
        /* the hunter enters the wumpus's home without an arrow */
        public void lose()
        {
        	hunter.setAlive(false);										// kill the hunter
			hunterLabel.setText("the hunter is dead!");
			canMove = false;											
			wumpusLabel.setText("the wumpus ate the hunter!");
			wumpus.setVisible(true);									// show the wumpus
        }
        
		/* handles button click events */
        public void actionPerformed(ActionEvent event) 
        {
            if(event.getActionCommand().equalsIgnoreCase("Exit Game")) // if the exit button is pressed
            {
                state = PlayState.STOP;
            }
            
            if(event.getActionCommand().equalsIgnoreCase("Instructions"))// if the instructions button is pressed
            {
            	JFrame frame = new JFrame();
            	ImageIcon pic = new ImageIcon("instructions.png");
            	JLabel label = new JLabel(pic);							// display the image
            	frame.add(label);
            	frame.pack();
            	frame.setVisible(true);
            	win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
            	state = PlayState.STOP;									// close the game
            }
        }
    } 

	/* repaints the display */
    public void repaint() 
    {
    	this.win.repaint();
    }

	/* closes the display */
    public void dispose() 
    {
	    this.win.dispose();
    }

    public static void main(String[] argv) throws InterruptedException 
    {
        HuntTheWumpus w = new HuntTheWumpus();			// create a new game
        
        while (w.state == PlayState.PLAY) 				// while the game is in play mode
        {
            w.repaint();								// update the display
            Thread.sleep(33);
        }
        												// when the game is no longer in play mode
        w.dispose();									// close the display
    }
}
