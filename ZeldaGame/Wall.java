/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Wall class
 */
import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;

/**
 * A wall actor that serves as a barrier
 */
public class Wall extends Actor
{
	private boolean secret;
	private int pushDir;
	/**
	 * Constructs a default wall object.
	 */
	public Wall()
	{
		setColor(null);
		secret = false;
		pushDir = -1;
	}
	/**
	 * Constructs a default wall object.
	 * This wall is secret and will be used for a puzzle.
	 * @param pushd the direction it needs to be pushed.
	 */
	public Wall(int pushd)
	{
		secret = true;
		pushDir=pushd;
		setColor(null);
	}
	/**
	 * Overrides the act method so it does nothing.
	 */
	public void act(){}
	/**
	 * Returns whether or not this wall is secret.
	 * @return whether this wall is secret.
	 */
	public boolean isSecret()
	{
		return secret;
	}
	/**
	 * Returns whether this wall will move given a push.
	 * @param push the direction link is pushing.
	 * @return whether the wall will move given the push.
	 */
	public boolean willMove(int push)
	{
		return pushDir==push;
	}
	/**
	 * Returns a character version of this object for use in saving and loading files.
	 * @return th char version of the Wall class.
	 */
	public String toString()
	{
		String ret = "W";
		if(secret)
			switch(pushDir){
				case Location.NORTH:
					ret="8";
					break;
				case Location.EAST:
					ret="6";
					break;
				case Location.WEST:
					ret="4";
					break;
				case Location.SOUTH:
					ret="2";
					break;
			}
		return ret;
	}
}