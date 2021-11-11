//© A+ Computer Science  -  www.apluscompsci.com
//Name -
//Date -
//Lab  -

import static java.lang.System.*;
import java.awt.Color;

public class PieceTester
{
	public static void main(String[] args)
	{
		Piece pieceOne = new Piece();
		out.println(pieceOne);
		
		Piece redChecker = new Piece("red checker");
		out.println(redChecker);	
		
		Piece theShoe = new Piece("gopher", Color.GREEN);
		out.println(theShoe);	
		
		Piece bishop = new Piece("bishop", Color.BLACK);
		out.println(bishop);					
	}
}