import info.gridworld.actor.*;
import java.awt.*;
import info.gridworld.grid.Location;
import info.gridworld.grid.*;

public class Gem extends Actor
{
	// Each gem acts by moving down in the grid.  It does this by getting the next location down,
	// removing itself, then if the next location down is in the grid, removes what's there
	// and finally puts itself there.
	public void act()
	{
		Grid <Actor> g = getGrid();
		Location curr = getLocation();
		Location next = new Location(curr.getRow()+1, curr.getCol());
		removeSelfFromGrid();
		if (g.isValid(next))
		{
			Actor old = (Actor)g.get(next);
			if (old != null)
				old.removeSelfFromGrid();
			putSelfInGrid(g, next);	
		}
		
	}
	
	// so the gif will not get distorted
	public Color getColor()
	{
		return null;
	}
}