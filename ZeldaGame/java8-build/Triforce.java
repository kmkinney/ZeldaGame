/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Triforce class
 */

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * The last key that signals the comletion of the game.
 */
public class Triforce extends Actor
{
	/**
	 * Constructs a Triforce object
	 */
	public Triforce()
	{
		setColor(null);
		setDirection(Location.NORTH);
	}
	/**
	 * Overrides act so it does nothing.
	 */
	public void act(){}
}