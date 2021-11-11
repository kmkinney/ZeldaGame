/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Arrow class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Arrow class, a long ranged weapon for Link.
 */
public class Arrow extends Actor
{
	/**
	 * Constructs a new Arrow
	 */
	public Arrow(int dir)
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
		else if (gr.get(adj) instanceof Ganon)
		{
			removeSelfFromGrid();
		}
		else if(gr.get(adj) instanceof Enemy)
		{
			moveTo(adj);
			removeSelfFromGrid();
		}
		else
			removeSelfFromGrid();
	}
	/**
	 * Returns char version of Arrow to use when saving and loading files.
	 * @return the char version of Arrow.
	 */
	public String toString()
	{
		return " ";
	}
}