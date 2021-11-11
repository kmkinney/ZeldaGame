/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Sword class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Sword class is Link's weapon of choice.
 */
public class Sword extends Actor
{
	/**
	 * Constructs a Sword with given direction.
	 * @param dir the direction.
	 */
	public Sword(int dir)
	{
		setColor(null);
		setDirection(dir);
	}
	/**
	 * Overrides the act method so it does nothing
	 */
	public void act(){}
		
}