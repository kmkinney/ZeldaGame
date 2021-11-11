/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Rock class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Enemy class, looks like a Rock.
 */
public class Rock extends Actor implements Hazard
{
	/**
	 * Constructs a new Rock.
	 * @param dir the direction it will travel.
	 */
	public Rock(int dir)
	{
		setColor(null);
		setDirection(dir);
	}
	/**
	 * Go until it hits something.
	 */
	public void act()
	{
		Grid<Actor> gr = getGrid();
		Location adj = getLocation().getAdjacentLocation(getDirection());
		if(gr.isValid(adj) && gr.get(adj) == null)
			moveTo(adj);
		else if(gr.get(adj) instanceof Link)
		{
			((Link)gr.get(adj)).hit();
			removeSelfFromGrid();
		}
		else
			removeSelfFromGrid();
	}
	/**
	 * Returns char version of Rock to use when saving and loading files.
	 * @return the char version of Rock.
	 */
	public String toString()
	{
		return " ";
	}
}