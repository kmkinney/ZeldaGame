/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Fireball class
 */

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Fireball used by final boss.
 */
public class Fireball extends Actor implements Hazard
{
	private int life,aDelay,travel;
	private boolean diff, permanent;
	/**
	 *Constructs a temporary Fireball object with gievn travel direction.
	 *@param dir the direction  it will travel.
	 */
	public Fireball(int dir)
	{
		setColor(null);
		setDirection(Location.NORTH);
		life=0;
		permanent=false;
		aDelay=0;
		diff=false;
		travel = dir;
	}
	/**
	 *Constructs a permanent Fireball object.
	 */
	public Fireball()
	{
		setColor(null);
		setDirection(Location.NORTH);
		life=0;
		permanent=true;
		aDelay=0;
		diff=false;
		travel = Location.SOUTH;
	}
	/**
	 * Determines how it will move. Temporary Fireballs move in
	 * their direction until they hit something.
	 */
	public void act()
	{
		if(aDelay==2)
		{
			diff=!diff;
			aDelay=0;
			if(!permanent)
			{
				Location adj = getLocation().getAdjacentLocation(travel);
				if(getGrid().isValid(adj))
					if(getGrid().get(adj)==null)
						moveTo(adj);
					else if (getGrid().get(adj) instanceof Link)
					{
						((Link)getGrid().get(adj)).hit();
						removeSelfFromGrid();
					}
					else
						removeSelfFromGrid();
			}
		}
		if(!permanent && life==50)
			removeSelfFromGrid();
		life++;
		aDelay++;
		
	}
	/**
	 * Returns which image to use for animation.
	 * @return the suffix of the .gif to use.
	 */
	public String getImageSuffix()
	{
		return diff?"_diff":"";
	}
	/**
	 * Returns a character version of the Fireball to use
	 * when saving and loading. Only permanent Fireballs are saved and loaded.
	 * @return F if permanent, otherwise a space.
	 */
	public String toString()
	{
		return permanent?"F":" ";
	}
}