/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Door class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Door class that provides the user a path to progress to the next level when each level is complete.
 */
public class Door extends Actor
{
	private boolean open;
	/**
	 * Constructs a Door object
	 */
	public Door()
	{
		setColor(null);
		open = false;
		setDirection(Location.NORTH);
	}
	/**
	 * Overrides the act method so it does nothing.
	 */
	public void act(){}
	/**
	 * Alters image based on health.
	 * @return a string suffix to specify the gif.
	 */
	public String getImageSuffix()
	{
		return open?"_open":"";
	}
	/**
	 * Opens the door by setting open to true.
	 */
	public void open()
	{
		open = true;
	}
	/**
	 * Closes the door, setting open to false.
	 */
	public void close()
	{
		open = false;
	}
	/**
	 * Returns whether this door is open.
	 * @return whether this door is open.
	 */
	public boolean isOpen()
	{
		return open;
	}
	/**
	 * Returns a char version of this class.
	 * @return a char version of this class.
	 */
	public String toString()
	{
		return "D";
	}
}