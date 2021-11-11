/*Kevin Kinney
 *Mrs. Gallatin
 *3rd Period
 *Menu class
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;
/**
 * The Menu class displays the opening game menu. Extends JFrame and implements MouseListener.
 */
public class Menu implements MouseListener
{
	private JLabel title, newGame, cont, help, back;
	private ArrayList<JLabel> fileNames;
	private JFrame frame, fileFrame;
	/**
	 * Constrcuts and displays the Game Menu
	 */
	public Menu()
	{
		fileNames = new ArrayList<>();
		fileFrame = new JFrame();
		back=new JLabel("    <-BACK");
		back.setFont(new Font("Courier", Font.PLAIN, 18));
		back.setForeground(Color.WHITE);
		back.addMouseListener(this);
		
		frame = new JFrame();
		frame.setSize(400,300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		
		title = new JLabel("   Legend of Zelda");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Courier", Font.HANGING_BASELINE, 30));
		
		newGame = new JLabel("              New");
		newGame.setForeground(Color.WHITE);
		newGame.addMouseListener(this);
		newGame.setFont(new Font("Courier", Font.PLAIN, 20));
		
		cont = new JLabel("            Continue");
		cont.setForeground(Color.WHITE);
		cont.addMouseListener(this);
		cont.setFont(new Font("Courier", Font.PLAIN, 20));
		
		help = new JLabel("              Help");
		help.setForeground(Color.WHITE);
		help.addMouseListener(this);
		help.setFont(new Font("Courier", Font.PLAIN, 20));
		
		panel.setLayout(new GridLayout(4,1));
		panel.add(title);
		panel.add(newGame);
		panel.add(cont);
		panel.add(help);
		frame.add(panel);
	}
	/**
	 * required by the MouseListener interface.  Invoked when the mouse is clicked.
	 * @param e the MouseEvent that triggered the method
	 */
	public void mouseClicked(MouseEvent e)
	{
	}
	/**
	 * required by the MouseListener interface.  Invoked when the mouse is released.
	 * @param e the MouseEvent that triggered the method
	 */
	public void mouseReleased(MouseEvent e)
	{
		
	}
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
		if(!label.equals(back))
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
		if(!label.equals(back))
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
		if(label.equals(newGame))
		{
			frame.dispose();
			Game g = new Game();
		}
		if(label.equals(cont))
		{
			frame.dispose();
			fileFrame.setSize(300,300);
			fileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fileFrame.setLocationRelativeTo(null);
			fileFrame.setVisible(true);
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.BLACK);
			panel.setLayout(new GridLayout(5,1));
			
			File saves = new File("save");
			File[] saveFiles = saves.listFiles();
			for(int i=0;i<saveFiles.length;i++)
			{
				JLabel l = new JLabel(String.format("  %s", saveFiles[i].getName()));
				l.setForeground(Color.WHITE);
				l.setFont(new Font("Courier", Font.PLAIN, 20));
				l.addMouseListener(this);
				fileNames.add(l);
				panel.add(l);
			}
			panel.add(back);
			fileFrame.add(panel);
		}
		if(label.equals(help))
		{
			showHelp();
		}
		if(label.equals(back))
		{
			fileFrame.dispose();
			Menu m = new Menu();
		}
		else if(fileNames.contains(label))
		{
			fileFrame.dispose();
			String s = label.getText();
			Game g = new Game((s.substring(s.indexOf(">")+1)));
		}		    
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
	
}