/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Life class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Life class used to visually represent player health and boss health.
 */
public class Life extends Actor
{
	/**
	 * Constructs a default red Life for Link health.
	 */
	public Life()
	{
		setColor(null);
		setDirection(Location.NORTH);
	}
	/**
	 * Overrides the act method so it does nothing.
	 */
	public void act(){}
	/**
	 * Represented as a blank space, not saved or loaded.
	 */
	public String toString()
	{
		return " ";
	}
}