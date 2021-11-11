import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.awt.*;
import javax.swing.JOptionPane;

/**
 * Starts and manages a game of Collapse
 */
public class Collapse extends ActorWorld
{
	private boolean gameOver;
	private int delay;
	private int numSteps;
	private int score;
	public static final Color orange = new Color(245,109,10);
	public static final Color purple = new Color(171,41,214);
	public static final Color aqua = new Color(24,231,225);
	
	/**
	 * Creates a new Collapse game.  
	 * Fills the 20 x 20 grid halfway up with random colored rocks.  Shows user
	 * instructions/prompt to start the game.  Continually calls step until the
	 * game is over.  Manages the speed of the game.
	 */
	public Collapse()
	{
		super(new BoundedGrid<Actor>(20,20));
		gameOver = false;
		delay = 8000;
		numSteps = 0;
		score = 0;
		Grid<Actor> grid = getGrid();
		setMessage("Click on a colored rock to remove all connected rocks of the same color.\nRocks will fall increasingly faster. Don't let the rocks reach the top of the grid!");
		for (int r = grid.getNumRows()/2; r < grid.getNumRows(); r++)
			for (int c = 0; c < grid.getNumCols(); c++)
			{
				Rock rock = new Rock();
				setRandomColor(rock);
				add(new Location(r,c), rock);
			}
		System.setProperty("info.gridworld.gui.selection", "hide"); // hides the selected cell
		show();
		JOptionPane.showMessageDialog(null,"Click OK when ready to play\nCould show instructions here.\nMake sure you maximize the window to play!");
		while (!gameOver)
		{
			try
			{
				Thread.sleep(delay);
			}
			catch(InterruptedException e){}
			step();
			show();
			numSteps++;
			switch(numSteps)
			{
				case 20: delay = 7000; break;
				case 50: delay = 6000; break;
				case 100: delay = 4000; break;
				case 150: delay = 2000; break;
				case 200: delay = 1000; break;
			}
		
		}
		
	}
	
	/** 
	 * Called when a particular cell is clicked.  Calls the removeColor
	 * method to remove all matching rocks horizontally and vertically 
	 * adjacent to the one that was clicked if it has any matching neighbors.
	 */
	public boolean locationClicked(Location loc)
	{
		Grid<Actor> grid = getGrid();
		if (!gameOver && grid.get(loc) != null)
		{
			if (hasMatch(loc))
			{
				removeColor(loc,grid.get(loc).getColor());
				removeBlanks();
				show();
			}	
		}	
		return true;
	}
	
	/**
	 * Recursive method that removes adjacent matching rocks throughout grid
	 * @param loc the location to check 
	 * @param c the color of rocks to remove - is the color of the originally clicked rock
	 */
	public void removeColor(Location loc, Color c)
	{
		Grid<Actor> grid = getGrid();
		if(grid.isValid(loc) && grid.get(loc)!= null && grid.get(loc).getColor() == c)
		{
				remove(loc);
				score++;
				setMessage("Score: " + score);
				removeColor(loc.getAdjacentLocation(Location.NORTH),c);
				removeColor(loc.getAdjacentLocation(Location.SOUTH),c);
				removeColor(loc.getAdjacentLocation(Location.EAST),c);
				removeColor(loc.getAdjacentLocation(Location.WEST),c);
				// diagonals can be added if desired			
		}	
	}
	
	/**
	 * After all rocks have been removed, this method traverses the grid "dropping" all
	 * remaining rocks down into the empty spaces left.
	 */
	public void removeBlanks()
	{
		Grid<Actor> grid = getGrid();
		for(int col = 0;col < grid.getNumCols(); col++)
			for (int row = grid.getNumRows()-1; row >= 0; row--)
			{
				if (grid.get(new Location(row,col)) == null)
				{
					int checkRow = row-1;
					while(checkRow >= 0 && grid.get(new Location(checkRow,col)) == null)
						checkRow--;
					if (checkRow >= 0)
						grid.get(new Location(checkRow,col)).moveTo(new Location(row,col));
				}
			}
	}
	
	/**
	 * Checks to see if a rock at a specified location has a matching rock to its
	 * north, south, east or west.  If it is "alone", it will not be removed from the grid.
	 * @param loc the location to check
	 * @return whether the rock at loc has a match
	 */
	public boolean hasMatch(Location loc)
	{
		Grid<Actor> grid = getGrid();
		Color c = grid.get(loc).getColor();
		Location north = loc.getAdjacentLocation(Location.NORTH);
		if (grid.isValid(north) && grid.get(north)!= null && grid.get(north).getColor().equals(c))
			return true;
		Location south = loc.getAdjacentLocation(Location.SOUTH);
		if (grid.isValid(south) && grid.get(south)!= null && grid.get(south).getColor().equals(c))
			return true;
		Location east = loc.getAdjacentLocation(Location.EAST);
		if (grid.isValid(east) && grid.get(east)!= null && grid.get(east).getColor().equals(c))
			return true;
		Location west = loc.getAdjacentLocation(Location.WEST);
		if (grid.isValid(west) && grid.get(west)!= null && grid.get(west).getColor().equals(c))
			return true;
		return false;		
	}	
	
	/**
	 * Adds a new Rock at the top of each column.
	 * If any Rock is above the top of the grid, sets 
	 * game over.
	 */	
	public void step()
	{
		Grid<Actor> grid = getGrid();
		for (int col = 0; col < grid.getNumCols(); col++)
		{
			if (grid.get(new Location(0,col))!= null)
			{
				gameOver = true;
				setMessage("Game Over\nScore: " + score);
				//System.out.println(gr.get(new Location(0,col)));
				show();
			}	
		}
		if (!gameOver)
		{
			for (int col = 0; col < grid.getNumCols(); col++)
			{	
				int row = 19;
				// row 20 crashed
				while (grid.get(new Location(row,col))!=null)
					row--;
				Rock rock = new Rock();
				setRandomColor(rock);
				add(new Location(row,col),rock);
			}
		}
	
	}
	
	/**
	 * Chooses one of six random colors and sets the specified actor to that color.
	 * @param a the actor to color
	 */
	private void setRandomColor(Actor a)
	{
		int color = (int) (Math.random() * 6);
				
		switch(color)
		{
			case 0:  a.setColor(Color.BLUE);break;
			case 1:  a.setColor(Color.GREEN); break;
			case 2:  a.setColor(Color.RED); break;
			case 3:  a.setColor(aqua); break; // aqua
			case 4: a.setColor(orange); break; // orange
			case 5: a.setColor(purple); break; // purple
		}
	}
}