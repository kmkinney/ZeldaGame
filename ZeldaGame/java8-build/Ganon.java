/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Ganon class
 */

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;   
/**
 * Final Boss class.
 */
public class Ganon extends Actor implements Enemy
{
	private int health;
	private boolean hurt;
	private int fireTime;
	/**
	 * Constructs a new Ganon boss with 5 health.
	 */
	public Ganon()
	{
		health = 5;
		fireTime=0;
		hurt=false;
		setColor(null);
		setDirection(Location.SOUTH);
	}
	/**
	 * Fight
	 */
	public void act()
	{
		int dir = getDirection();
		if(fireTime==60)
		{
			int r = getLocation().getAdjacentLocation(dir).getRow();
			for(int c=1;c<getGrid().getNumCols()-1;c++)
			{
				Fireball f = new Fireball(dir);
				Location spot = new Location(r,c);
				if(getGrid().get(spot) instanceof Link)
					((Link)getGrid().get(spot)).hit();
				else 
					f.putSelfInGrid(getGrid(), new Location(r,c));	
			}
			fireTime=0;
		}
		if(health==0)
			removeSelfFromGrid();
		
		if(hurt){
			setColor(Color.RED);
			hurt=false;
		}
		else{
			setColor(null);
		}
		fireTime++;
	}
	/**
	 * Decreases health by one.
	 */
	public void hit()
	{
		health--;
		Grid<Actor> gr = getGrid();
		Location loc = new Location(gr.getNumRows()-getLocation().getRow(), getLocation().getCol());
		if(gr.get(loc)==null){
			moveTo(loc);
			setDirection((getDirection()+Location.HALF_CIRCLE)%Location.FULL_CIRCLE);
		}
		hurt=true;
	}
	/**
	 * Sets health to given value.
	 * @param h the new health value.
	 */
	public void setHealth(int h)
	{
		health = h;
	}
	/**
	 * Returns the current health.
	 * @return the current health.
	 */
	public int getHealth()
	{
		return health;
	}
	/**
	 * Alters image based on health.
	 * @return a string suffix to specify the gif.
	 */
	public String getImageSuffix()
	{
		return health>=2?"":"_hurt";
	}
	/**
	 * Returns char used in saving and loading files.
	 * @return the char version of this class.
	 */
	public String toString()
	{
		return "G";
	}
}