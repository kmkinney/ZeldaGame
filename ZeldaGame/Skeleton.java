/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Skeleton class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * The Skeleton class is an enemy class that looks like a Skeleton.
 */
public class Skeleton extends Actor implements Enemy
{
	private int moveDelay;
	/**
	 * Constructs a new Skelelton.
	 */
	public Skeleton()
	{
		setColor(null);
		moveDelay=0;
		setDirection(Location.NORTH);
	}
	/**
	 * Goes until it hits something, then turns 90 degrees to the right.
	 */
	public void act()
	{
		if(moveDelay==3)
		{
			Grid<Actor> gr = getGrid();
			Location adj = getLocation().getAdjacentLocation(getDirection());
			if(gr.isValid(adj) && gr.get(adj) == null)
				moveTo(adj);
			else if(gr.get(adj) instanceof Link)
			{
				((Link)gr.get(adj)).hit();
				setDirection((getDirection()+180)%Location.FULL_CIRCLE);
			}
			else if(gr.get(adj) instanceof Sword)
			{
				removeSelfFromGrid();
			}
			else
				setDirection((getDirection()+90)%Location.FULL_CIRCLE);
			moveDelay=0;
		}
		else
			moveDelay++;
	}
	/**
	 * Returns a character version of this class to use when saving and loading files.
	 * @return the character used when saving and loading.
	 */
	public String toString()
	{
		return "S";
	}
}