/*  This example is to help you use keystrokes to control Actor(s) in the world.
 *  It utilizes the built-in features of the GridWorld without having to change any 
 *  of the black box classes.
 *
 *  This particlar world created a main Actor (mainGuy) that is controlled by the 
 *  WASD keys.  The other actors are controlled by their act() method.  The act
 *  method is called from the step method in this class.  You must hit the run
 *  button for the actors to begin moving, but you can control the mainGuy with
 *  the keyboard at any time.
 *
 *  mainGuy moves up/down/left/right when the WASD keys are pressed.  This is accomplished
 *  by overriding the keypressed method that originates in World.java.  The other actors
 *  are controlled by the Gem act method which just moves them down one space until they are off
 *  the grid.  mainGuy is declared as an attribute in the world, so that the world can control him.
 */


import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.awt.*;

public class GemWorld extends ActorWorld
{
	private Actor mainGuy;
	
	public GemWorld()
	{
		mainGuy = new Diamond();
		add(new Location(5,5),mainGuy);
		add(new Location(1,1),new Emerald());
		add(new Location(3,6),new Ruby());
		setMessage("This is a random game that has no purpose.  Have fun!");
        show();  
	}
	
	public void step()
    {
        Grid<Actor> gr = getGrid();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        for(int row = gr.getNumRows()-1; row >=0; row--)
        	for(int col = gr.getNumCols()-1; col >= 0; col--)
        	{
        		Location loc = new Location(row,col);
        		if (gr.get(loc) != null && !gr.get(loc).equals(mainGuy))
                    actors.add(gr.get(loc));
               
        	}
        		

        for (Actor a : actors)
        {
            // only act if another actor hasn't removed Actor a
            if (a.getGrid() == gr)
                a.act();
        }
        
             
    }
    
    // Uses either WASD or arrow keys to move the mainGuy gem.
    public boolean keyPressed(String description, Location loc)
    {
    	System.out.println("Pressed:"+description);
    	if ((description.equals("W")|| description.equals("UP")) && getGrid().isValid(mainGuy.getLocation().getAdjacentLocation(Location.NORTH)))
    	{	
 			mainGuy.moveTo(mainGuy.getLocation().getAdjacentLocation(Location.NORTH));
        	return true;    		
    	}
    	if ((description.equals("A")|| description.equals("LEFT")) && getGrid().isValid(mainGuy.getLocation().getAdjacentLocation(Location.WEST)))
    	{	
    		mainGuy.moveTo(mainGuy.getLocation().getAdjacentLocation(Location.WEST));        	
    		return true;    		
		}
    	if ((description.equals("S")|| description.equals("DOWN")) && getGrid().isValid(mainGuy.getLocation().getAdjacentLocation(Location.SOUTH)))
    	{
    		mainGuy.moveTo(mainGuy.getLocation().getAdjacentLocation(Location.SOUTH));
    		return true;
    	}
    	if ((description.equals("D") || description.equals("RIGHT")) && getGrid().isValid(mainGuy.getLocation().getAdjacentLocation(Location.EAST)))
    	{
    		mainGuy.moveTo(mainGuy.getLocation().getAdjacentLocation(Location.EAST));
    		return true;
    	}
    	
    	return false;
    	
    }
     
   
}