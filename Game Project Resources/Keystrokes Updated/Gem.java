import info.gridworld.actor.*;
import java.awt.*;
import info.gridworld.grid.Location;
import info.gridworld.grid.*;

public class Gem extends Actor
{
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
	
	public Color getColor()
	{
		return null;
	}
}