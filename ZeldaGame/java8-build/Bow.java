/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Bow class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Enemy class, looks like a Bow.
 */
public class Bow extends Actor 
{
	/**
	 * Constructs a new Bow
	 */
	public Bow()
	{
		setColor(null);
		setDirection(Location.NORTH);
	}
	/**
	 * Overrides the act method so it does nothing.
	 */
	public void act()
	{
	}
	/**
	 * Returns char version of Bow to use when saving and loading files.
	 * @return the char version of Bow.
	 */
	public String toString()
	{
		return "B";
	}
}