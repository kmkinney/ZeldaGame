/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Keyclass
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * A Key object class that extends Actor.
 * The Key is obtained to advance to the next level.
 */
public class Key extends Actor
{
	/**
	 * Constructs a Key object with null as color and direction NORTH
	 */
	public Key()
	{
		setColor(null);
		setDirection(Location.NORTH);
	}
	/**
	 * Overrides the act method so it does nothing
	 */
	public void act(){}
	/**
	 * Returns a character version of the Object to use when saving and loading.
	 * @return K the character used to represent a Key.
	 */
	public String toString()
	{
		return "K";
	}
}