/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Game class
 */
 
import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
/**
 * A Game Class that extends ActorWorld. It acts as the main game 
 * controller, and manages the GUIController and Grid.
 */
public class Game extends ActorWorld
{
	//GRID DIMENSIONS
	public static final int WIDTH = 40;
	public static final int HEIGHT = 26;
	public static final int NUM_LEVELS = 6;//change this
	public static final String LEVEL_PATH = "levels/Level_";
	public static final String SAVE_PATH = "save/";
	public static final String SCORE_PATH = "scores/highscores.txt";
	
	private Link link;
	private Door door;
	private Ganon boss;
	private Location keyLoc, bowLoc;
	private int currentLevel;
	private int time;
	private boolean complete;
	private int puzzlesComplete;
	private boolean allDead;
	private boolean paused;
	private String name;

	
	/**
	 * Constructs a new Game, which is an ActorWorld,
	 * and loads the first level.
	 */
	public Game()
	{
		super(new BoundedGrid<Actor>(HEIGHT, WIDTH));  
		//Default values
		keyLoc = new Location(0,0);
		bowLoc = new Location(0,0);
		link = new Link();
		boss = new Ganon();
		name = JOptionPane.showInputDialog(null,"ENTER A NAME","NAME",JOptionPane.PLAIN_MESSAGE);
		if(name==null)
			System.exit(0);
		door=new Door();
		currentLevel = 0;//change this
		time=0;
		complete=false;
		puzzlesComplete=0;
		allDead = false;
		paused=false;
		
		//Load Level
		loadLevel(LEVEL_PATH+currentLevel+".txt");
		//Hide unecessary info
		System.setProperty("info.gridworld.gui.tooltips", "hide");
		System.setProperty("info.gridworld.gui.selection", "hide"); 
		show();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Constructs a new Game with a given name.
	 * Used when loading save files.
	 * @param n the name of the user.
	 */
	public Game(String n)
	{
		super(new BoundedGrid<Actor>(HEIGHT, WIDTH)); 
		//Default values
		keyLoc = new Location(0,0);
		bowLoc = new Location(0,0);
		link = new Link();
		boss = new Ganon();
		name = n;
		door=new Door();
		currentLevel = 0;
		time=0;
		complete=false;
		puzzlesComplete=0;
		allDead = false;
		paused=false;
		
		loadLevel(SAVE_PATH+name);
		//Hide unecessary info
		System.setProperty("info.gridworld.gui.tooltips", "hide");
		System.setProperty("info.gridworld.gui.selection", "hide"); 
		show();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Called at an interval to advance the game one frame. Based on
	 * GUIController Timer. Calls the act method for every Actor still in the grid,
	 * checks to see if a level is complete, and updates the in game time
	 */
	public void step()
    {
    	if(!paused)
    	{
    		Grid<Actor> gr = getGrid();
	        ArrayList<Actor> actors = new ArrayList<Actor>();
	        for (Location loc : gr.getOccupiedLocations())
	            actors.add(gr.get(loc));
			allDead = true;
	        for (Actor a : actors)
	        {
	            allDead = allDead && !(a instanceof Enemy);
	            if (a.getGrid() == gr)
	                a.act();
	        }
	        if(!complete) //HOW EACH LEVEL IS COMPLETED
	        {
	     		checkComplete();
	     		if(complete)
	     			add(keyLoc, currentLevel==NUM_LEVELS?new Triforce():new Key());
	        }
	        //Bow Spawn
	        if(!link.hasBow()&&currentLevel==3 && allDead)
	     		add(bowLoc, new Bow());
	        time++;
	        setMessage("Time: "+time);
			updateHealth();
			
    	}
    	
	}
	/**
	 * Method to check whether a level is complete.
	 * If it is complete, sets complete to true.
	 */
	public void checkComplete()
	{
		switch(currentLevel){
	     			case 0:
	     				complete=puzzlesComplete>=1;
	     				break;
	     			case 1:
	     				complete=allDead;
	     				break;
	     			case 2:
	     				complete=allDead&&puzzlesComplete>=1;
	     				break;
	     			case 3:
	     				complete=puzzlesComplete>=1;
	     				break;
	     			case 4:
	     				complete=puzzlesComplete>=3;
	     				break;
	     			case 5:
	     				complete=puzzlesComplete>=1&&allDead;
	     				break;
	     			case NUM_LEVELS:
	     				complete = boss.getHealth()<=0;;
	     				break;
	     		}
	}
	/**
     * Creates a new text file in the save folder with the name of the player as the title.
     * This saves the current level, health, time, key location, door state, and the position of all Actors.
     * Uses format that will allow it to be loaded by loadLevel()
     */
    public void save()
    {
    	try{
    		PrintWriter writer = new PrintWriter("save/"+name);
	    	writer.println(""+currentLevel);
	    	writer.println(link.getHealth());
	    	writer.println(""+time);
	    	writer.println(keyLoc.getRow()+" "+keyLoc.getCol());
	    	writer.println(bowLoc.getRow()+" "+bowLoc.getCol());
	    	writer.println(door.isOpen()?"1":"0");
	    	writer.println(link.hasBow()?"1":"0");
	    	Grid<Actor> gr = getGrid();
	    	ArrayList<Location> locs = gr.getOccupiedLocations();
		    for(int r=1;r<HEIGHT;r++)
		    {
				for(int c=0;c<WIDTH;c++)
					if(locs.contains(new Location(r,c)))
						writer.print(gr.get(new Location(r,c)));
					else
						writer.print(" ");
				writer.println();
		    }		
			writer.close();
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null, "NO SUCH FILE FOUND");
    	}
	}
	/**
	 * Loads the given level from a file
	 * using the given file name.
	 * @param fileName the path of the file to be loaded.
	 */
	public void loadLevel(String fileName) 
	{
		for(int r=0;r<HEIGHT;r++)
			for(int c=0;c<WIDTH;c++)
				remove(new Location(r,c));
		boolean open = false;
		try{
			//paused = true;//change this
			Scanner in = new Scanner(new File(fileName));
			
			if(in.hasNextInt())
			{
				currentLevel = in.nextInt();
				link.setHealth(in.nextInt());
				time = in.nextInt();
				keyLoc = new Location(in.nextInt(), in.nextInt());
				bowLoc = new Location(in.nextInt(), in.nextInt());
				open = in.nextInt()==1;
				if(in.nextInt()==1)
					link.getBow();
				in.nextLine();
			}
			
			char[][] map = new char[HEIGHT][WIDTH];
			int line = 0;
			while(in.hasNextLine())
			{
				map[line] = in.nextLine().toCharArray();
				line++;
			}
			for(int r=0;r<HEIGHT;r++)
				for(int c=0;c<WIDTH;c++)
				{
					switch(map[r][c])
					{
						case 'W':
							add(new Location(r+1,c), new Wall());
							break;
						case 'L':
							add(new Location(r+1,c), link);
							break;
						case 'S':
							add(new Location(r+1,c), new Skeleton());
							break;
						case 'P':
							add(new Location(r+1,c), new Pig());
							break;
						case 'K':
							keyLoc = new Location(r+1,c);
							break;
						case 'D':
							add(new Location(r+1,c), door);
							break;
						case 'G':
							add(new Location(r+1,c), boss);
							break;
						case 'T':
							keyLoc = new Location(r+1,c);
							break;
							
						case '8': //4 different puzzle walls based on how you need to push them
							add(new Location(r+1,c), new Wall(Location.NORTH));
							break;
						case '6':
							add(new Location(r+1,c), new Wall(Location.EAST));
							break;
						case '4':
							add(new Location(r+1,c), new Wall(Location.WEST));
							break;
						case '2':
							add(new Location(r+1,c), new Wall(Location.SOUTH));
							break;
							
						case 'F':
							add(new Location(r+1,c), new Fireball());
							break;
						case 'O':
							add(new Location(r+1,c), new Octorok());
							break;
						case 'B':
							bowLoc = new Location(r+1,c);
							break;
					}
				}
				
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error Loading file","ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		puzzlesComplete = 0;
		complete = false;
		if(open){door.open();}
		else{door.close();}
		updateHealth();
	}
	
	/**
	 * Updates the health bar at the top of the screen
	 * and checks to see if health drops to 0.
	 */
	public void updateHealth()
	{
		for(int i=0;i<5;i++)
		{
			remove(new Location(0,i));
			if(i<link.getHealth())
				add(new Location(0,i), new Heart());
		}
		if(link.getHealth()<=0)
			gameOver();
		if(currentLevel == NUM_LEVELS) //BOSS HEALTH
		{	
			for(int i=1;i<=5;i++)
			{
				remove(new Location(0,WIDTH-i));
				if(i<=boss.getHealth())
					add(new Location(0, WIDTH-i), new Heart(Color.BLUE));
			}
			if(boss.getHealth()<=0 && !complete)
				remove(boss.getLocation());
		}
			
	}
	/**
	 * Method to handle keyboard input controlling the main character
	 * @param description pf key pressed based on KeyStroke class
	 * @param loc the active Location when the key was pressed(not used)
	 * @return whether a key was used
	 */
	public boolean keyPressed(String description, Location loc)
    {
    	if(!paused)
    	{
    		if (description.equals("UP") && !link.isAttacking())
	    	{	
	    		if(canMove(link.getLocation(), Location.NORTH))
	 				link.moveTo(link.getLocation().getAdjacentLocation(Location.NORTH));
	 			link.setOrientation(Location.NORTH);
	        	return true;    		
	    	}
	    	if (description.equals("LEFT")&& !link.isAttacking())
	    	{	
	    		if(canMove(link.getLocation(), Location.WEST))
	    			link.moveTo(link.getLocation().getAdjacentLocation(Location.WEST));
	 			link.setOrientation(Location.WEST);        	
	    		return true;    		
			}
	    	if (description.equals("DOWN")&& !link.isAttacking())
	    	{
	    		if(canMove(link.getLocation(), Location.SOUTH))
	    			link.moveTo(link.getLocation().getAdjacentLocation(Location.SOUTH));
	 			link.setOrientation(Location.SOUTH);
	    		return true;
	    	}
	    	if (description.equals("RIGHT")&& !link.isAttacking())
	    	{
	    		if(canMove(link.getLocation(), Location.EAST))
	    			link.moveTo(link.getLocation().getAdjacentLocation(Location.EAST));
	 			link.setOrientation(Location.EAST);
	    		return true;
	    	}
	    	
	    	if(description.equals("SPACE") && canAttack(link.getLocation(), link.getOrientation()))
	    	{
	    		link.swordAttack();
	    		return true;
	    	}
	    	if(description.equals("ENTER") && canAttack(link.getLocation(), link.getOrientation()))
	    	{
	    		link.bowAttack();
	    		return true;
	    	}
	    	if(description.equals("P"))
	    	{
	    		paused = true;
	    		pause();
	    		return true;
	    	}
	    	if(description.equals("H"))
	    	{
	    		link.setHealth(5);
	    	}
	    	if(boss.getGrid() == getGrid() && description.equals("Q") && currentLevel == NUM_LEVELS)
	    	{
	    		boss.hit();
	    		return true;
	    	}
    	}
	    return false;
    	
    }
    
    
    /**
     * Executes and views a Pause Menu
     */
    public void pause()
    {
    	final JFrame frame = new JFrame();
    	frame.setUndecorated(true);
    	frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    	frame.setSize(300,300);
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
    	frame.setAlwaysOnTop(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	JPanel panel = new JPanel();
    	panel.setBackground(Color.GRAY);
    	panel.setLayout(new GridLayout(5,1));
    	
    	final JLabel save = new JLabel("       SAVE");
    	final JLabel help = new JLabel("       HELP");
    	final JLabel cont = new JLabel("       CONTINUE");
    	final JLabel quit = new JLabel("       QUIT");
    	final JLabel title = new JLabel("      PAUSED");
    	
    	MouseListener mouseInput = new MouseAdapter(){
		    /**
			 * required by the MouseListener interface.  Invoked when no mouse buttons are clicked and 
			 * the mouse pointer enters a particular gui element.  In this implementation, if the mouse
			 * pointer enters one of the menu labels, the label is set to red.
			 * @param e the MouseEvent that triggered the method
			 */
			public void mouseEntered(MouseEvent e)
			{
				JLabel label = (JLabel) e.getSource();
				String s = label.getText();
				label.setText(s.substring(0,s.lastIndexOf(" "))+">"+s.substring(s.lastIndexOf(" ")+1));
				label.setForeground(Color.red);
			}	
			/**
			 * required by the MouseListener interface.  Invoked when no mouse buttons are clicked and 
			 * the mouse pointer leaves a particular gui element.  In this implementation, if the mouse
			 * pointer leaves one of the menu labels, the label is set to back to black.
			 * @param e the MouseEvent that triggered the method
			 */
			public void mouseExited(MouseEvent e)
			{
				JLabel label = (JLabel) e.getSource();
				String s = label.getText();
				label.setText(s.substring(0,s.indexOf(">"))+" "+s.substring(s.indexOf(">")+1));
				label.setForeground(Color.white);
			}
			/**
			 * required by the MouseListener interface.  Invoked when the left mouse button is pressed.
			 * In this implementation, if the mouse is pressed on a particular labels, that menu item
			 * is invoked.
			 * @param e the MouseEvent that triggered the method
			 */
			public void mousePressed(MouseEvent e) 
			{
		    	
				JLabel label = (JLabel) e.getSource();
				if(label.equals(save))
				{
					save();
					frame.dispose();
					paused=false;
				}
				if(label.equals(help))
				{
					showHelp();
					frame.dispose();
					paused=false;
				}
				if(label.equals(cont))
				{
					frame.dispose();
					paused=false;
				}
				if(label.equals(quit))
				{
					System.exit(0);
				}		    
			}
    	};
    	
    	title.setForeground(Color.WHITE);
    	title.setFont(new Font("Courier", Font.PLAIN, 25));
    	
    	
    	save.setForeground(Color.WHITE);
    	save.addMouseListener(mouseInput);
    	save.setFont(new Font("Courier", Font.PLAIN, 15));
    	
    	
    	help.setForeground(Color.WHITE);
    	help.addMouseListener(mouseInput);
    	help.setFont(new Font("Courier", Font.PLAIN, 15));
    	
    	cont.setForeground(Color.WHITE);
    	cont.addMouseListener(mouseInput);
    	cont.setFont(new Font("Courier", Font.PLAIN, 15));
    	
    	quit.setForeground(Color.WHITE);
    	quit.addMouseListener(mouseInput);
    	quit.setFont(new Font("Courier", Font.PLAIN, 15));
    	
    	panel.add(title);
    	panel.add(save);
    	panel.add(help);
    	panel.add(cont);
    	panel.add(quit);
    	
    	frame.add(panel);
    }
    /**
     * Displays the html help document.
     */
    public void showHelp()
    {
    	JDialog dialog = new JDialog();
        final JEditorPane helpText = new JEditorPane();
        try
        {
            //URL url = new URL("file:///"+System.getProperty("user.dir")+"/ZeldaHelp.html");
            URL url = getClass().getResource("ZeldaHelp.html");
            helpText.setPage(url);
        }
        catch (Exception e)
        {
            helpText.setText("error");
        }
        helpText.setEditable(false);
        JScrollPane sp = new JScrollPane(helpText);
        sp.setPreferredSize(new Dimension(650, 500));
        dialog.getContentPane().add(sp);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    /**
     * Method to determine whether link can move one space from the given location in the given direction
     * @param loc the current Location
     * @param direction the direction(see Location class constants)
     * @return whether the move is valid
     */
    public boolean canMove(Location loc, int direction)
    {
    	Location adj = loc.getAdjacentLocation(direction);
    	Actor a = getGrid().get(adj);
    	//Puzzle Wall
    	if(a instanceof Wall && ((Wall)a).isSecret()&& ((Wall)a).willMove(direction)&& getGrid().get(adj.getAdjacentLocation(direction))==null)
    	{
    		add(adj.getAdjacentLocation(direction), new Wall());
    		puzzlesComplete++;
    		return true;
    	}
    	//Enemy contact
    	if(a instanceof Enemy || a instanceof Hazard)
    	{
    		link.hit();
    		return false;
    	}
    	//Key
    	if(a instanceof Key)
    	{
    		add(new Location(0,20), remove(adj));
    		door.open();
    		return true;
    	}
    	//Triforce
    	else if(a instanceof Triforce)
    	{
    		link.moveTo(adj);
    		win();
    	}
    	//Bow
    	else if(a instanceof Bow)
    	{
    		link.getBow();
    		return true;
    	}
    	else if(a instanceof Life)
    	{
    		link.setHealth(link.getHealth()+1);
    		return true;
    	}
    	//Door
    	else if(currentLevel<NUM_LEVELS && a instanceof Door && ((Door)a).isOpen())
    	{
    		link.moveTo(adj);
    		if(currentLevel==NUM_LEVELS-1)
    		{
    			loadLevel(LEVEL_PATH+"boss.txt");
    			currentLevel++;
    		}
    		else
    			loadLevel(LEVEL_PATH + (++currentLevel)+".txt");
    	}
    	return getGrid().isValid(adj) && a==null;
    }
    /**
     * Method to determine whether link can attack one space from the given location in the given direction
     * @param loc the current Location
     * @param direction the direction(see Location class constants)
     * @return whether the attack is valid
     */
    public boolean canAttack(Location loc, int direction)
    {
    	Location adj = loc.getAdjacentLocation(direction);
    	Grid<Actor> gr = getGrid();
    	if(gr.isValid(adj))
    	{
    		if(gr.get(adj) == null)
    			return true;
    		if(gr.get(adj) instanceof Enemy)
    		{
    			System.out.println(gr.get(adj));
    			if(gr.get(adj) instanceof Ganon)
    			{
    				link.setAttack(true);
    				boss.hit();
    				return false;
    			}
    			link.spawn();
    			return true;
    		}
    		return false;
    	}
    	return false;
    }
    /**
     * Game over.
     */
    public void gameOver()
    {
    	paused=true;
    	frame.dispose();
    	
    	JOptionPane.showMessageDialog(null, "GAME OVER!", "YOU DIED", JOptionPane.PLAIN_MESSAGE);
    	playAgain();
   		
    }
    /**
     * Prompts the user to play again or exit.
     */
    public void playAgain()
    {
    	final JFrame f = new JFrame();
    	f.setSize(400,400);
    	
    	JPanel panel = new JPanel();
    	panel.setBackground(Color.BLACK);
    	
    	
    	final JLabel again = new JLabel("     PLAY AGAIN?");
    	final JLabel yes =   new JLabel("     Yes");
    	final JLabel no =    new JLabel("     No");
    	MouseListener mouseInput = new MouseAdapter(){
		    /**
			 * required by the MouseListener interface.  Invoked when no mouse buttons are clicked and 
			 * the mouse pointer enters a particular gui element.  In this implementation, if the mouse
			 * pointer enters one of the menu labels, the label is set to red.
			 * @param e the MouseEvent that triggered the method
			 */
			public void mouseEntered(MouseEvent e)
			{
				JLabel label = (JLabel) e.getSource();
				String s = label.getText();
				label.setText(s.substring(0,s.lastIndexOf(" "))+">"+s.substring(s.lastIndexOf(" ")+1));
				label.setForeground(Color.red);
			}	
			/**
			 * required by the MouseListener interface.  Invoked when no mouse buttons are clicked and 
			 * the mouse pointer leaves a particular gui element.  In this implementation, if the mouse
			 * pointer leaves one of the menu labels, the label is set to back to black.
			 * @param e the MouseEvent that triggered the method
			 */
			public void mouseExited(MouseEvent e)
			{
				JLabel label = (JLabel) e.getSource();
				String s = label.getText();
				label.setText(s.substring(0,s.indexOf(">"))+" "+s.substring(s.indexOf(">")+1));
				label.setForeground(Color.white);
			}
			/**
			 * required by the MouseListener interface.  Invoked when the left mouse button is pressed.
			 * In this implementation, if the mouse is pressed on a particular labels, that menu item
			 * is invoked.
			 * @param e the MouseEvent that triggered the method
			 */
			public void mousePressed(MouseEvent e) 
			{
		    	
				JLabel label = (JLabel) e.getSource();
				if(label.equals(yes))
				{
					f.dispose();
					Menu m = new Menu();
				}
				if(label.equals(no))
				{
					System.exit(0);
				}
			}
    	};
    	
    	again.setForeground(Color.WHITE);
    	again.setFont(new Font("Courier", Font.PLAIN, 30));
    	
    	yes.setForeground(Color.WHITE);
    	yes.setFont(new Font("Courier", Font.PLAIN, 25));
    	yes.addMouseListener(mouseInput);
    	
    	no.setForeground(Color.WHITE);
    	no.setFont(new Font("Courier", Font.PLAIN, 25));
    	no.addMouseListener(mouseInput);
    	
    	panel.setLayout(new GridLayout(5,1));
    	panel.add(again);
    	panel.add(yes);
    	panel.add(no);
    	
    	f.add(panel);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	f.setVisible(true);
    	f.setLocationRelativeTo(null);
    }
    /**
     * Complete the game and display high scores.
     */
    public void win()
    {
    	paused=true;
    	JOptionPane.showMessageDialog(null, "YOU WIN");
    	try{
    		Scanner in = new Scanner(new File(SCORE_PATH));
    		Queue<String> scores = new PriorityQueue<String>(10, new Comparator<String>()
    		{
    			public int compare(String a, String b)
    			{
    				return Integer.parseInt(a.substring(0,a.indexOf(" "))) -
    					Integer.parseInt(b.substring(0,b.indexOf(" ")));
    			}
    		});
    		scores.add(time+" "+ name);
    		while(in.hasNextLine())
    		{
    			String data = in.nextLine();
    			if(!data.equals(""))
    				scores.add(data);
    		}
    		in.close();
    		PrintWriter write = new PrintWriter(SCORE_PATH);
    		String highScores = "";
    		int numScores = scores.size();
    		for(int i=0;i<10;i++)
    		{
    			if(i<numScores)
    			{
    				String score = scores.poll()+"\n";
	    			highScores+= score;
	    			write.print(score);
    			}
    			else
    				highScores+="................................................................................\n";
	    	}
	    	write.close();
    		JOptionPane.showMessageDialog(null, highScores, "HIGHSCORES",JOptionPane.PLAIN_MESSAGE);
    		frame.dispose();
    		playAgain();
    	}catch(Exception e){
    		JOptionPane.showMessageDialog(null, "Error Loading HighScores","ERROR", JOptionPane.ERROR_MESSAGE);
    		e.printStackTrace();
			System.exit(0);
    	}
    }
}