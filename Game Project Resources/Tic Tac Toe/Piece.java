//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Lab  -

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import info.gridworld.actor.*;

public class Piece extends Actor implements Nameable
{
	private String name;
	private Color color;
	
	public Piece()
	{
		setName("empty");
		setColor(Color.BLUE);		
	}

	public Piece(String n)
	{
		setName(n);
		setColor(Color.BLUE);		
	}

   public Piece(String n, Color c)
	{
		setName(n);
		setColor(c);		
	}

	public void setName(String n)
	{
		name = n;
	}

	public void setColor(Color c)
	{
		color=c;
	}
	
	public String getName()
	{
		return name;
	}
	
	// needed for GridWorld to display the Piece properly
   public String getText() 
   { 
      return getName(); 
   } 
   	
	public Color getColor()
	{
		return color;
	}
	
	public String toString()
	{
		return getName() + " " + getColor();
	}
}