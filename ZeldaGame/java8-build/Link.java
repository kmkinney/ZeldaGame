/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Link class
 */

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.*;
import javax.swing.*;
/**
 * The main actor class that serves as the user controlled character
 */
public class Link extends Actor
{
	
	private boolean attacking, hurt, hasBow, spawn;
	private int orientation, health, attackDelay;
	/**
	 * Constrcuts a Link object with default color and attacking set to false
	 */
	public Link()
	{
		attacking = false;
		hurt = false;
		hasBow=false;
		spawn=false;
		health = 5;
		attackDelay=0;
		setDirection(Location.NORTH);
		orientation = Location.SOUTH;
		setColor(null);
	}
	/**
	 * Overrides the act method so Link does nothing when step is pressed.
	 * Checks things associated with attacking and being attacked.
	 */
	public void act()
	{
		if(attacking)
		{
			if(attackDelay==2)
			{
				attacking = false;
				if(getGrid().get(getAttackLoc()) instanceof Sword)
					getGrid().get(getAttackLoc()).removeSelfFromGrid();
				if(spawn && Math.random()<=0.20)
				{
					(new Life()).putSelfInGrid(getGrid(), getAttackLoc());
					spawn=false;
				}
					
				attackDelay=0;
			}
			else
				attackDelay++;
		}
		if(hurt){
			setColor(Color.RED);
			hurt = false;
		}
		else{
			setColor(null);
		}
		if(health<=0)
			removeSelfFromGrid();
	}
	
	/**
	 * Method to determine which image to display. Returms "_sword" if attacking
	 * Default image is Link.gif
	 * @return the suffix to append to "Link" to retrieve the appropriate .gif file
	 */
	public String getImageSuffix()
	{
		String ret = "";
		switch(orientation){
			case Location.NORTH:
				ret+="_up";
				break;
			case Location.SOUTH:
				ret+="_down";
				break;
			case Location.EAST:
				ret+="_right";
				break;
			case Location.WEST:
				ret+="_left";
				break;
		}
		if(attacking) ret+="_sword";
		return ret;
	}
	/**
	 * Sword Attack.
	 */
	public void swordAttack()
	{
		if(!attacking)
		{
			attacking = true;
			Sword s = new Sword(orientation);
			s.putSelfInGrid(getGrid(), getAttackLoc());
		}
	}
	/**
	 * Gives Link the ablility to use a bow.
	 */
	public void getBow()
	{
		JOptionPane.showMessageDialog(null, "YOU HAVE A BOW!\n"+
			"USE WITH ENTER AND FIRE ARROWS AT YOUR ENEMIES!");
		hasBow=true;
	}
	/**
	 * Returns whether Link has the bow.
	 * @return whether Link has the bow.
	 */
	public boolean hasBow()
	{
		return hasBow;
	}
	/**
	 * Bow Attack.
	 */
	public void bowAttack()
	{
		if(!attacking && hasBow)
		{
			attacking = true;
			Arrow a = new Arrow(orientation);
			a.putSelfInGrid(getGrid(), getAttackLoc());
		}
	}
	/**
	 * Sets attack value to given value.
	 * @param b the attack value.
	 */
	public void setAttack(boolean b)
	{
		attacking = b;
	}
	/**
	 * Returns whether Link is attacking
	 * @return whether Link is attacking
	 */
	public boolean isAttacking()
	{
		return attacking;
	}
	/**
	 * Sets spawn to true, which yields a 25% chance of the killed enemy dropping a heart.
	 */
	public void spawn()
	{
		spawn = true;
	}
	/**
	 * Sets Link's orientation to the given value
	 * @param facing the direction link is facing (use Location constants)
	 */
	public void setOrientation(int facing)
	{
		orientation=facing;
	}
	/**
	 * Returns the direction Link is currently facing
	 * @return Link's direction (see Location constants)
	 */
	public int getOrientation()
	{
		return orientation;
	}
	/**
	 * Decreases health by 1.
	 */
	public void hit()
	{
		hurt = true;
		health--;
	}
	/**
	 * Sets the health to the given value.
	 * @param h the new health value.
	 */
	public void setHealth(int h)
	{
		health = h;
	}
	/**
	 * Returns link's current health.
	 * @return link's current health.
	 */
	public int getHealth()
	{
		return health;
	}
	/**
	 * Returns the location link's sword would be when attacking.
	 */
	public Location getAttackLoc()
	{
		return getLocation().getAdjacentLocation(orientation);
	}
	/**
	 * Returns char version of Link to use when saving and loading files.
	 * @return the char version of Link.
	 */
	public String toString()
	{
		return "L";
	}
}