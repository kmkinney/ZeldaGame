

import java.awt.Color;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.actor.*;

public class TicTacToeBoard extends ActorWorld
{
   private String whoseTurn;
   private boolean gameOver;
   	
   public TicTacToeBoard()
   {
   	  super(new BoundedGrid<Actor>(3,3));
   	  whoseTurn="X";
   	  gameOver=false;
      setMessage("Welcome to TIC TAC TOE!  - -  Click a spot - "+whoseTurn+" turn.");   	  	
      show();
   }

   public boolean locationClicked(Location loc)
   {
      Grid<Actor> grid = getGrid();
     
      if(gameOver==true)
      {
      	setMessage(whoseTurn+ " click a spot to play a new game");    
	      resetBoard(grid);
	      gameOver=false;
	      return false;      	
      }
      else
      {
	      String winner = getWinner(grid);
	      if(!winner.equals("no winner"))
	      {
		      setMessage("And the winner is . . . . "+winner+".   Click again to play another game!");                  
			   gameOver=true;
				return true;
			}
      }     	

      Actor piece = grid.get(loc);      
      if(whoseTurn.equals("X")&&piece==null)
      {
         add(loc,new Piece("X",Color.RED));
         whoseTurn="O";
      }
      else if(whoseTurn.equals("O")&&piece==null)
      {            
         add(loc,new Piece("O",Color.GREEN));
         whoseTurn="X";
      }      
      setMessage("Click a spot - "+whoseTurn+" turn.   Click again to check for a winner!");   	      
      return true;      
   }
   
   public boolean isBoardFull(Grid<Actor> grid)
   {
   	for(int r=0; r<grid.getNumRows();r++)
   	{
   		for(int c=0; c<grid.getNumCols();c++)
   		{
   			if(grid.get(new Location(r,c))==null)
					return false;
   		}
   	}
   	return true;
   }

   public void resetBoard(Grid<Actor> grid)
   {
   	for(int r=0; r<grid.getNumRows();r++)
   	{
   		for(int c=0; c<grid.getNumCols();c++)
   		{
   	      grid.remove(new Location(r,c));
	  		}
   	}
   }

   public String getWinner(Grid<Actor> grid)
   {
		String winner="";
		for (int r = 0; r<grid.getNumRows(); r++)
		{
			Piece row0 = (Piece)grid.get(new Location(r,0));
			Piece row1 = (Piece)grid.get(new Location(r,1));
			Piece row2 = (Piece)grid.get(new Location(r,2));
			
			if(row0==null||row1==null||row2==null) continue;
			
			if(row0.getName().equals(row1.getName())&&row0.getName().equals(row2.getName()))
			{
				winner=row0.getName()+" wins horizontally!";
				break;
			}
		}
		for (int c= 0; c<grid.getNumCols(); c++)
		{
			Piece col0 =(Piece)grid.get(new Location(0,c));
			Piece col1 =(Piece)grid.get(new Location(1,c));
			Piece col2 =(Piece)grid.get(new Location(2,c));
				
			if(col0==null||col1==null||col2==null) continue;
			
			if(col0.getName().equals(col1.getName())&&col0.getName().equals(col2.getName()))
			{
				winner=col0.getName()+" wins vertically!";
				break;
			}
		}
		
		if(winner.equals(""))
		{
			Piece diag0 =(Piece)grid.get(new Location(0,0));
			Piece diag1 =(Piece)grid.get(new Location(1,1));
			Piece diag2 =(Piece)grid.get(new Location(2,2));
				
			if(!(diag0==null||diag1==null||diag2==null)) 
				if(diag0.getName().equals(diag1.getName())&&diag0.getName().equals(diag2.getName()))
				{
					winner=diag0.getName()+" wins diagonally!";
				}
		}
		
		if(winner.equals(""))
		{
			Piece diag0 =(Piece)grid.get(new Location(2,0));
			Piece diag1 =(Piece)grid.get(new Location(1,1));
			Piece diag2 =(Piece)grid.get(new Location(0,2));
				
			if(!(diag0==null||diag1==null||diag2==null)) 
				if(diag0.getName().equals(diag1.getName())&&diag0.getName().equals(diag2.getName()))
				{
					winner=diag0.getName()+" wins diagonally!";
				}
		}

	   if(isBoardFull(grid)&&winner.length()==0){
		   winner =  "cat's game - no winner!\n\n";
		}
		else if(!isBoardFull(grid)&&winner.length()==0){
			winner="no winner";
		}
		return winner;
   }
}