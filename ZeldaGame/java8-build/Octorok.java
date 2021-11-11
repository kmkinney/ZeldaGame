/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Octorok class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Enemy class, looks like an Octorok.
 */
public class Octorok extends Actor implements Enemy
{
	private int rockDelay, moveDelay, rockTime, moveTime;
	/**
	 * Constructs a new Octorok
	 */
	public Octorok()
	{
		setColor(null);
		setDirection(Location.SOUTH);
		rockTime=0;
		moveTime=0;
		moveDelay = (int)(Math.random()*10)+6;
		rockDelay = (int)(Math.random()*20)+20;
	}
	/**
	 * Randomly moves and shoots rocks in the direction it is facing.
	 */
	public void act()
	{
		int dir = getDirection();
		Grid<Actor> gr = getGrid();
		Location adj = getLocation().getAdjacentLocation(dir);
		if(moveTime==moveDelay)
		{
			int choice = (int)(Math.random()*2);
			if(choice==0)
				setDirection(((int)(Math.random()*5))*90);
			else 
				if(gr.get(adj) == null)
					moveTo(adj);
				else if(gr.get(adj) instanceof Sword)
					removeSelfFromGrid();
				else if(gr.get(adj) instanceof Link)
					((Link)gr.get(adj)).hit();
			moveTime=0;
			moveDelay = (int)(Math.random()*10)+6;
		}
		adj = getLocation().getAdjacentLocation(dir);
		if(rockTime==rockDelay)
		{
			Rock r = new Rock(dir);
			if(gr.isValid(adj) && gr.get(adj)==null)
				r.putSelfInGrid(gr, adj);
			rockTime=0;
			rockDelay = (int)(Math.random()*20)+20;
		}
		rockTime++;
		moveTime++;
	}
	/**
	 * Returns char version of Octorok to use when saving and loading files.
	 * @return the char version of Octorok.
	 */
	public String toString()
	{
		return "O";
	}
}