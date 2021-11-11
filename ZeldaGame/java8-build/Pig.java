/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Pig class
 */


import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
/**
 * Enemy class, looks like a Pig.
 */
public class Pig extends Actor implements Enemy
{
	private int moveDelay, smartDelay;
	private boolean smart;
	/**
	 * Constructs a new Pig
	 */
	public Pig()
	{
		setColor(null);
		moveDelay=0;
		smart=true;
		smartDelay=0;
		setDirection(Location.SOUTH);
	}
	/**
	 * Go until it hits something, then turn 180 degrees
	 */
	public void act()
	{
		if(moveDelay==5)
		{
			Grid<Actor> gr = getGrid();
			Location loc = getLocation();
			if(smart)
			{
				Location linkLoc = new Location(0,0);
				for(Location l:gr.getOccupiedLocations())
					if(gr.get(l) instanceof Link)
						linkLoc=l;
				Location move;
				int vdis = Math.abs(linkLoc.getRow()-loc.getRow());
				int hdis = Math.abs(linkLoc.getCol()-loc.getCol());
				if(vdis>=hdis)
					if(linkLoc.getRow()>=loc.getRow())
						move = new Location(loc.getRow()+1, loc.getCol());
					else
						move = new Location(loc.getRow()-1, loc.getCol());
				else
					if(linkLoc.getCol()>=loc.getCol())
						move = new Location(loc.getRow(), loc.getCol()+1);
					else
						move = new Location(loc.getRow(), loc.getCol()-1);	
				if(gr.get(move) instanceof Link)
				{
					((Link)gr.get(move)).hit();
					smart=false;
					setDirection((getDirection()+180)%Location.FULL_CIRCLE);
				}
				else if (gr.get(move) == null)
				{
					moveTo(move);
				}
				else
					setDirection((getDirection()+180)%Location.FULL_CIRCLE);
				moveDelay=0;
			}
			else
			{
				Location adj = loc.getAdjacentLocation(getDirection());
				if(gr.get(adj)==null)
					moveTo(adj);
				else
					setDirection((getDirection()+90)%Location.FULL_CIRCLE);
				if(smartDelay==5)
				{
					smart=true;
					smartDelay=0;
				}
				else
					smartDelay++;
				moveDelay=0;
			}
		}
		else
			moveDelay++;
	}
	/**
	 * Returns char version of Pig to use when saving and loading files.
	 * @return the char version of Pig.
	 */
	public String toString()
	{
		return "P";
	}
}