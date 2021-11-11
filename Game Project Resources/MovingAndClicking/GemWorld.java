import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;

   	
   

	
	
public class GemWorld extends ActorWorld
{
	
	private String whosTurn;
    private boolean gameOver;
   
   
   /**
    * Sets up the GameBoard by creating a GemWorld object, filling the world with random 
    * gems, and putting a message at the top.
    */
   public GemWorld()
   {
   	
   	for(int row = 0; row < 10; row++)
   		for (int col = 0; col < 10; col++)
   		{
   			int pick = (int)(Math.random()*3);
   			switch(pick)
   			{
   				case 0: add(new Location(row,col),new Diamond()); break;
   				case 1: add(new Location(row,col),new Emerald());break;
   				case 2: add(new Location(row,col),new Ruby());
   			}
   		}
  
      setMessage("This is a random game that has no purpose.  Have fun!");
      show();  
     	  	
   }
	public void step()
    {
        Grid<Actor> gr = getGrid();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        
        // gets all of the Actors in the grid
        for(int row = gr.getNumRows()-1; row >=0; row--)
        	for(int col = gr.getNumCols()-1; col >= 0; col--)
        	{
        		Location loc = new Location(row,col);
        		if (gr.get(loc) != null)
                    actors.add(gr.get(loc));
        	}
        		

        for (Actor a : actors)
        {
            // only act if another actor hasn't removed Actor a
            if (a.getGrid() == gr)
                a.act(); // see Gem class for the act implementation
        }
        
        
        // adds new random gems to the top row
        for(int col = gr.getNumCols()-1; col >= 0; col--)
        {
        	Location loc = new Location(0,col);
        	if (gr.get(loc) == null)
        	{
        		int pick = (int)(Math.random()*3);
	   			switch(pick)
	   			{
	   				case 0: add(loc,new Diamond()); break;
	   				case 1: add(loc,new Emerald());break;
	   				case 2: add(loc,new Ruby());
	   			}	
        	}
        }    
        
    }
     
    // This method is called automatically anytime you click a location.
    // loc is the Location that was clicked.
    public boolean locationClicked(Location loc)
    {
        System.out.println(loc + " clicked.");
        getGrid().get(loc).removeSelfFromGrid();
        return true;
    }
    
    // overrides the play method
	public void play()
	{
		boolean playing = true;
		while(playing)
		{
			//for (double i = 0; i <= 100000; i += .05); // a simple pause
			step();
			
		
		}
	}
}