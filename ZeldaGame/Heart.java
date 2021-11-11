/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Heart class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Heart class used to visually represent player health and boss health.
 */
public class Heart extends Actor
{
	/**
	 * Constructs a default red Heart for Link health.
	 */
	public Heart()
	{
		setColor(null);
		setDirection(Location.NORTH);
	}
	/**
	 * Constructs a Heart with a specified Color for enemy health.
	 * @param c the Coloe of the Heart.
	 */
	public Heart(Color c)
	{
		setColor(c);
		setDirection(Location.NORTH);
	}
	/**
	 * Overrides the act method so it does nothing.
	 */
	public void act(){}
}