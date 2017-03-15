package dragonball.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import dragonball.model.attack.Attack;
import dragonball.model.attack.SuperAttack;
import dragonball.model.attack.SuperSaiyan;
import dragonball.model.attack.UltimateAttack;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.battle.BattleEventType;
import dragonball.model.cell.Collectible;
import dragonball.model.cell.CollectibleCell;
import dragonball.model.cell.FoeCell;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;
import dragonball.model.character.fighter.Saiyan;
import dragonball.model.dragon.Dragon;
import dragonball.model.dragon.DragonWish;
import dragonball.model.dragon.DragonWishType;
import dragonball.model.exceptions.DuplicateAttackException;
import dragonball.model.exceptions.MapIndexOutOfBoundsException;
import dragonball.model.exceptions.MaximumAttacksLearnedException;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotASaiyanException;
import dragonball.model.exceptions.NotEnoughAbilityPointsException;
import dragonball.model.exceptions.NotEnoughKiException;
import dragonball.model.exceptions.NotEnoughSenzuBeansException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;
import dragonball.model.game.GameListener;
import dragonball.view.GameGUI;

public class GameControl implements GameListener, ActionListener, MouseListener //, KeyListener
{
    private Game game;
    private GameGUI view;
    private transient Container c;
    private UltimateAttack u;
    private SuperAttack s;
    private Dragon d;

    public GameControl()
    {
	try {
	    game = new Game();
	} catch (MissingFieldException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (UnknownAttackTypeException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	game.setListener(this);
	setView(new GameGUI());
	getView().setListener(this);
	getView().startPanel();
	String name = "Welcome_at_the_beginning.wav";    
	    AudioInputStream aud;
	    try
	    {
		aud = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
		view.setC(AudioSystem.getClip());
		view.getC().open(aud);
		view.getC().start();
	    } catch (UnsupportedAudioFileException | IOException e1)
	    {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    } catch (LineUnavailableException e1)
	    {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
    }

    public void save(String path) throws IOException {
	// Create an output stream to be able to write the file.
	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
		new File(path)));
	// Serializing the object and writing it on the hard disk.
	oos.writeObject(this);
	game.save(path);
	// close the output stream.
	oos.close();
    }

    public void load(String path) throws ClassNotFoundException, IOException {
	// Create an input stream to be able to read the file.
	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
		new File(path)));
	// Deserializing the object and loading into a game type object.
	GameControl temp = (GameControl) ois.readObject();
	// Adjusting the instance variables.
	view = temp.view;
	game = temp.game;
	c = temp.c;
	view.revalidate();
	view.repaint();
	// closing the stream.
	ois.close();
	// clear the temp variable
	temp = null;
    } 
    //	@Override
    //	public void keyTyped(KeyEvent e) {
    //		// TODO Auto-generated method stub
    //
    //	}
    //
    //
    //	@Override
    //	public void keyPressed(KeyEvent e) {
    //		// TODO Auto-generated method stub
    ////		if(e.getKeyCode() == KeyEvent.VK_UP){
    ////			try{
    ////				view.getFighter().setBounds(view.getFighter().getX(), view.getFighter().getY()- 65, view.getFighter().getWidth(), view.getFighter().getHeight());				
    ////				changeEn(game.getWorld().getPlayerRow()-1, game.getWorld().getPlayerColumn());
    ////				view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				game.getWorld().moveUp();
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()].requestFocusInWindow();
    //////                view.        requestFocusInWindow();
    //////
    ////			}
    ////			catch(MapIndexOutOfBoundsException e2){
    ////
    ////			}
    ////		}
    ////		else if(e.getKeyCode() == KeyEvent.VK_DOWN){
    ////			try{
    ////				view.getFighter().setBounds(view.getFighter().getX(), view.getFighter().getY() + 65, view.getFighter().getWidth(), view.getFighter().getHeight());
    ////				changeEn(game.getWorld().getPlayerRow()+1, game.getWorld().getPlayerColumn());
    ////				view.getButtons()[game.getWorld().getPlayerRow()+1][game.getWorld().getPlayerColumn()].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()+1][game.getWorld().getPlayerColumn()].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()+1][game.getWorld().getPlayerColumn()].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////
    ////				game.getWorld().moveDown();
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()].requestFocusInWindow();
    ////			}catch(MapIndexOutOfBoundsException e2){
    ////
    ////			}
    ////		}
    ////		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
    ////			try{
    ////				view.getFighter().setBounds(view.getFighter().getX()+58, view.getFighter().getY(), view.getFighter().getWidth(), view.getFighter().getHeight());
    ////				changeEn(game.getWorld().getPlayerRow(), game.getWorld().getPlayerColumn()+1);
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()+1].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()+1].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()+1].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////
    ////				game.getWorld().moveRight();
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()].requestFocusInWindow();
    ////			}catch(MapIndexOutOfBoundsException e2){
    ////
    ////			}
    ////		}
    ////		else if(e.getKeyCode() == KeyEvent.VK_LEFT){
    ////			try{
    ////				view.getFighter().setBounds(view.getFighter().getX()-58, view.getFighter().getY(), view.getFighter().getWidth(), view.getFighter().getHeight());
    ////				changeEn(game.getWorld().getPlayerRow(), game.getWorld().getPlayerColumn()-1);
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
    ////				game.getWorld().moveLeft();
    ////				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()].requestFocusInWindow();
    ////			}
    ////			catch(MapIndexOutOfBoundsException e2){
    ////
    ////			}
    ////		}
    //
    //	}
    //
    //
    //
    //	@Override
    //	public void keyReleased(KeyEvent e) {
    //		// TODO Auto-generated method stub
    //
    //	}

    private boolean isValid(int i, int j){
	return i>=0 && j>=0 && i<10 && j<10;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource() instanceof JToggleButton){
	    JToggleButton b = ((JToggleButton)e.getSource());
	    if(b.getActionCommand().equals("Earthling")||b.getActionCommand().equals("Namekian")||b.getActionCommand().equals("Saiyan")||b.getActionCommand().equals("Majin")||b.getActionCommand().equals("Frieza"))
	    {
		if(b.getModel().isSelected()) {
		    Color bl =  new Color(135,206,250);
		    Color w =  new Color(0,0,0); 
		    view.getFr().setBackground(view.getFr().isSelected()?bl:w);
		    view.getFr1().setBackground(view.getFr1().isSelected()?bl:w);
		    view.getFr11().setBackground(view.getFr11().isSelected()?bl:w);
		    view.getFr111().setBackground(view.getFr111().isSelected()?bl:w);
		    view.getFr1111().setBackground(view.getFr1111().isSelected()?bl:w);

		    view.getFr().setOpaque(true);
		    view.getFr1().setOpaque(true);
		    view.getFr11().setOpaque(true);
		    view.getFr111().setOpaque(true);
		    view.getFr1111().setOpaque(true);
		    if(view.getC() != null)
	        	view.getC().stop();
		    String name = b.getActionCommand()+".wav";    
		    AudioInputStream aud;
		    try
		    {
			aud = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
			view.setC(AudioSystem.getClip());
			view.getC().open(aud);
			view.getC().start();
		    } catch (UnsupportedAudioFileException | IOException e1)
		    {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    } catch (LineUnavailableException e1)
		    {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		}	
	    }
	}
	else{
	    if(e.getSource() instanceof JButton){
		JButton b = ((JButton)e.getSource());
		if(b.getActionCommand().startsWith("Cell")){
		    if(b.isEnabled()){
			int i = b.getActionCommand().charAt(5)-'0';
			int j = b.getActionCommand().charAt(7)-'0';
			int row = game.getWorld().getPlayerRow();
			int col = game.getWorld().getPlayerColumn();
			view.getButtons()[i][j].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
			view.getButtons()[i][j].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
			view.getButtons()[i][j].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

			if(row - i == 1){
			    view.getFighter().setBounds(view.getFighter().getX(), view.getFighter().getY()- 65, view.getFighter().getWidth(), view.getFighter().getHeight());
			    changeEn(i, j);
			    game.getWorld().moveUp();

			}
			else if(row - i == -1){
			    view.getFighter().setBounds(view.getFighter().getX(), view.getFighter().getY() + 65, view.getFighter().getWidth(), view.getFighter().getHeight());
			    changeEn(i,j);
			    game.getWorld().moveDown(); 

			}
			else if(col - j == -1){
			    view.getFighter().setBounds(view.getFighter().getX()+58, view.getFighter().getY(), view.getFighter().getWidth(), view.getFighter().getHeight());
			    changeEn(i,j);
			    game.getWorld().moveRight(); 


			}
			else if(col - j == 1){
			    view.getFighter().setBounds(view.getFighter().getX()-58, view.getFighter().getY(), view.getFighter().getWidth(), view.getFighter().getHeight());
			    changeEn(i,j);
			    game.getWorld().moveLeft(); 

			}
		    }
		    else{

		    }
		}
	    }
	}
    }
    public void assignIcon(){
	for (int i = 0; i < 10; i++) {
	    for (int j = 0; j < 10; j++) {

		InputMap im = view.getButtons()[i][j].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = view.getButtons()[i][j].getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),"doU");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),"doD");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),"doR");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),"doL");
		am.put("doU", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			try{
			    if(isValid(game.getWorld().getPlayerRow()-1,game.getWorld().getPlayerColumn())){
				view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].doClick();;
				view.getFighter().setBounds(view.getFighter().getX(), view.getFighter().getY()- 65, view.getFighter().getWidth(), view.getFighter().getHeight());				
				changeEn(game.getWorld().getPlayerRow()-1, game.getWorld().getPlayerColumn());
				view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				game.getWorld().moveUp();
			    } 
			}
			catch(MapIndexOutOfBoundsException e2){

			}
		    }
		});
		am.put("doD", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			try{
			    if(isValid(game.getWorld().getPlayerRow()+1,game.getWorld().getPlayerColumn())){

				view.getButtons()[game.getWorld().getPlayerRow()+1][game.getWorld().getPlayerColumn()].doClick();;
				view.getFighter().setBounds(view.getFighter().getX(), view.getFighter().getY() + 65, view.getFighter().getWidth(), view.getFighter().getHeight());
				changeEn(game.getWorld().getPlayerRow()+1, game.getWorld().getPlayerColumn());
				view.getButtons()[game.getWorld().getPlayerRow()+1][game.getWorld().getPlayerColumn()].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()+1][game.getWorld().getPlayerColumn()].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()+1][game.getWorld().getPlayerColumn()].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

				game.getWorld().moveDown();
			    }
			}catch(MapIndexOutOfBoundsException e2){

			}
		    }
		});
		am.put("doR", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			try{
			    if(isValid(game.getWorld().getPlayerRow(),game.getWorld().getPlayerColumn()+1)){

				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()+1].doClick();;
				view.getFighter().setBounds(view.getFighter().getX()+58, view.getFighter().getY(), view.getFighter().getWidth(), view.getFighter().getHeight());
				changeEn(game.getWorld().getPlayerRow(), game.getWorld().getPlayerColumn()+1);
				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()+1].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()+1].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()+1].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

				game.getWorld().moveRight();
			    }
			}catch(MapIndexOutOfBoundsException e2){

			}
		    }
		});
		am.put("doL", new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
			try{
			    if(isValid(game.getWorld().getPlayerRow(),game.getWorld().getPlayerColumn()-1)){

				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].doClick();
				view.getFighter().setBounds(view.getFighter().getX()-58, view.getFighter().getY(), view.getFighter().getWidth(), view.getFighter().getHeight());
				changeEn(game.getWorld().getPlayerRow(), game.getWorld().getPlayerColumn()-1);
				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
				game.getWorld().moveLeft();
			    }
			}
			catch(MapIndexOutOfBoundsException e2){

			}
		    }
		});
		if(game.getWorld().getMap()[i][j] instanceof CollectibleCell)
		{
		    if(((CollectibleCell)game.getWorld().getMap()[i][j]).getCollectible() == Collectible.SENZU_BEAN)
			view.getButtons()[i][j].setPressedIcon(new ImageIcon(new ImageIcon("bean.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
		    else

			view.getButtons()[i][j].setPressedIcon(new ImageIcon(new ImageIcon("Dragonball.gif").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

		}
		else if(game.getWorld().getMap()[i][j] instanceof FoeCell){
		    if(!((FoeCell)game.getWorld().getMap()[i][j]).getFoe().isStrong())
			view.getButtons()[i][j].setPressedIcon(new ImageIcon(new ImageIcon("boss.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
		    //							else
		    //								view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()].setIcon(new ImageIcon(new ImageIcon(".gif").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
		    //		
		}
		else{
		    view.getButtons()[i][j].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

		}
	    }
	}
    }
    //	static int dx []={1,0,-1,0};
    //	static int dy []={0,1,0,-1};

    private void changeEn(int r,int c){
	for (int i = 0; i < 10; i++) {
	    for (int j = 0; j < 10; j++) {
		if(Math.abs(r-i)+Math.abs(c-j)==1){
		    view.getButtons()[i][j].setEnabled(true);
		}
		else{
		    view.getButtons()[i][j].setEnabled(false);

		}
	    }
	}

    }

    @Override
    public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource() instanceof JToggleButton){
	    JToggleButton b = ((JToggleButton)e.getSource());
	    if(b.getActionCommand().equals("Earthling")||b.getActionCommand().equals("Namekian")||b.getActionCommand().equals("Saiyan")||b.getActionCommand().equals("Majin")||b.getActionCommand().equals("Frieza"))
	    {
		view.getMy_name().setText(""+b.getActionCommand());
		b.setIcon(new ImageIcon(b.getActionCommand()+"_mod.gif"));
		view.getFf().setIcon(new ImageIcon(new ImageIcon(""+b.getActionCommand()+"99.png").getImage().getScaledInstance(300,500,Image.SCALE_SMOOTH))); 
	    }
	}
	else
	{
	    if(e.getSource() instanceof JButton){
		    JButton b = ((JButton)e.getSource());
		    if(b.getActionCommand().equals("Attack")||b.getActionCommand().equals("Block")||b.getActionCommand().equals("Use")){
			b.setIcon(new ImageIcon(new ImageIcon(b.getActionCommand().toLowerCase()+".gif").getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH))); 
		    }
	    }
	}
    }
    //    .requestFocusInWindow();

    @Override
    public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource() instanceof JToggleButton){
	    JToggleButton b = ((JToggleButton)e.getSource());
	    if(b.getActionCommand().equals("Earthling")||b.getActionCommand().equals("Namekian")||b.getActionCommand().equals("Saiyan")||b.getActionCommand().equals("Majin")||b.getActionCommand().equals("Frieza"))
	    {
		view.getMy_name().setText(""+b.getActionCommand());
		b.setIcon(new ImageIcon(b.getActionCommand()+".gif"));
		view.getFf().setIcon(new ImageIcon(new ImageIcon(""+(view.getGr().getSelection()==null?"empty.gif":view.getGr().getSelection().getActionCommand()+"99.png")).getImage().getScaledInstance(300,500,Image.SCALE_SMOOTH))); 

	    }
	}
	else{
	    if(e.getSource() instanceof JButton){
		    JButton b = ((JButton)e.getSource());
		    if(b.getActionCommand().equals("Attack")||b.getActionCommand().equals("Block")||b.getActionCommand().equals("Use")){
			b.setIcon(new ImageIcon(new ImageIcon("buttondragon.gif").getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH))); 
 
		    }
	    }
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getActionCommand().equals("Load")){ //to get the source button of the action by the action command added while initializing
	   
	    JFileChooser j = new JFileChooser(); 
	    j.setCurrentDirectory(new File(System.getProperty("user.home")));
	    int result = j.showOpenDialog(null);
	    if(result == JFileChooser.APPROVE_OPTION){
		try{
		    load(j.getSelectedFile().getAbsolutePath());
		    view.revalidate();
		    view.repaint();
		    view.setVisible(true);
		}
		catch (ClassNotFoundException e1) {
		    JOptionPane.showMessageDialog(null, "Error loading the saved game! Please try again!");
		} catch (IOException e1) {
		    JOptionPane.showMessageDialog(null, "Error loading the saved game! Please try again!");
		}
	    }
	    else{

	    }
	}


	else if(e.getActionCommand().equals("New")){
	    JTextField j = new JTextField();
	    Object [] o = {"Please enter your Player's name:",j};
	    int res =  JOptionPane.showConfirmDialog(null,o,"DragonBall",JOptionPane.OK_CANCEL_OPTION); //new window allowing user to choose desired action here it is either ok or cancel specified by the last parameter
	    while((j.getText().isEmpty() || j.getText().length() > 10) && res != JOptionPane.CANCEL_OPTION){
		JOptionPane.showMessageDialog(null, "The player's name shouldn't be empty or exceed 10 characters!");
		res =  JOptionPane.showConfirmDialog(null,o,"DragonBall",JOptionPane.OK_CANCEL_OPTION);
	    }
	    if(view.getC() != null)
        	view.getC().stop();
	    if(res == JOptionPane.OK_OPTION){

		game.getPlayer().setName(j.getText()); // setting the player's name taken as input from user
		view.setPlayName(new JLabel(j.getText())); //setting the label to be shown afterwards in world and battle modes
		view.chooseFighterPanel(true);
	    }
	    else{

	    }

	}

	else if(e.getActionCommand().equals("Start"))
	{ 
	    if(view.getGr().getSelection() == null || view.getFighName().getText().isEmpty()){
		JOptionPane.showMessageDialog(null, "You didn't specify all your fighter's attributes! Try again!");
	    }
	    else{
		game.getPlayer().createFighter(view.getGr().getSelection().getActionCommand().charAt(0), view.getFighName().getText());
		view.setAc_race(new JLabel(view.getGr().getSelection().getActionCommand()));
		PlayableFighter f = game.getPlayer().getActiveFighter();
		if(view.getC() != null)
	        	view.getC().stop();
		view.worldModePanel();
		view.getLevel().setText(""+f.getLevel());
		view.getButtons()[9][9].setEnabled(true);
		view.getButtons()[8][9].setEnabled(true);
		view.getButtons()[9][8].setEnabled(true);

	    }
	}
	else if(e.getActionCommand().equals("newfighter"))
	{
	    c = view.getContentPane();
	    view.chooseFighterPanel(false);

	}
	else if(e.getActionCommand().equals("Return"))
	{
	    if(view.getGr().getSelection() == null || view.getFighName().getText().isEmpty()){
		JOptionPane.showMessageDialog(null, "You didn't specify all your fighter's attributes! Try again!");
	    }
	    else{
		game.getPlayer().createFighter(view.getGr().getSelection().getActionCommand().charAt(0), view.getFighName().getText());
		view.setContentPane(c);
		if(view.getC() != null)
	        	view.getC().stop();
		view.revalidate();
		view.repaint();
	    }
	}

	else if(e.getActionCommand().equals("save"))
	{
	    JFileChooser j = new JFileChooser(); 
	    j.setCurrentDirectory(new File(System.getProperty("user.home")));
	    int result = j.showSaveDialog(null);
	    if(result == JFileChooser.APPROVE_OPTION){
		try {
		    save(j.getSelectedFile().getAbsolutePath());
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    JOptionPane.showMessageDialog(null, "Error saving the game!");
		}
	    }
	}
	else if(e.getActionCommand().equals("upgrade"))
	{
	    view.setJ(new JFrame());
	    view.getJ().setLayout(null);
	    view.getJ().setUndecorated(true);
	    //			view.getJ().setLocationRelativeTo(view);
	    view.getJ().setBounds(350, 100, 600, 600);
	    view.getJ().setOpacity(1);
	    view.getJ().setBackground(new Color(255,153,18,170));;
	    view.getJ().setShape(new Ellipse2D.Double(0, 0, view.getJ().getWidth(), view.getJ().getHeight()));
	    PlayableFighter f = game.getPlayer().getActiveFighter();
	    JLabel jl = new JLabel("Your active fighter has the following attributes:");
	    jl.setBounds(30,100,500, 35);
	    jl.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl);
	    JLabel jl2 = new JLabel("Maximum Health Points: "+ f.getMaxHealthPoints());
	    jl2.setBounds(30,140,500, 35);
	    jl2.setHorizontalAlignment(SwingConstants.CENTER);

	    view.getJ().add(jl2);
	    JLabel jl3 = new JLabel("Physical Damage: "+ f.getPhysicalDamage());
	    jl3.setBounds(30,180,500, 35);
	    jl3.setHorizontalAlignment(SwingConstants.CENTER);

	    view.getJ().add(jl3);
	    JLabel jl4 = new JLabel("Blast Damage: "+ f.getBlastDamage());
	    jl4.setBounds(30,220,500, 35);
	    jl4.setHorizontalAlignment(SwingConstants.CENTER);

	    view.getJ().add(jl4);
	    JLabel jl5 = new JLabel("Maximum Ki Bars: "+ f.getMaxKi());
	    jl5.setBounds(30,260,500, 35);
	    jl5.setHorizontalAlignment(SwingConstants.CENTER);

	    view.getJ().add(jl5);
	    JLabel jl6 = new JLabel("Maximum Stamina Bars: "+ f.getMaxStamina());
	    view.getJ().add(jl6);
	    jl6.setBounds(30,300,500, 35);
	    jl6.setHorizontalAlignment(SwingConstants.CENTER);

	    JLabel jl7 = new JLabel("Which attribute do you want to upgrade? Choose from the following list:");
	    jl7.setBounds(30,340,500, 35);
	    jl7.setHorizontalAlignment(SwingConstants.CENTER);

	    view.getJ().add(jl7);
	    view.setJcheck(new JComboBox<String>());
	    JComboBox<String> jc = view.getJcheck();
	    jc.addItem("Increase Max Health Points by 50 points");
	    jc.addItem("Increase Physical Damage by 50 points");
	    jc.addItem("Increase Blast Damage by 50 points");
	    jc.addItem("Increase Max Ki by one bar");
	    jc.addItem("Increase Max Stamina by one bar");
	    jc.setSelectedIndex(0);
	    jc.setBounds(70,380,400,35);
	    JButton b = new JButton("Upgrade");
	    b.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
		    // TODO Auto-generated method stub
		    view.getJ().setVisible(false);
		    int i = view.getJcheck().getSelectedIndex();
		    char c = i == 0?'H': i==1?'P':i==2?'B':i==3?'K':i==4?'S':'.';
		    try{
			game.getPlayer().upgradeFighter(game.getPlayer().getActiveFighter(), c);
		    }
		    catch(NotEnoughAbilityPointsException e2){
			JOptionPane.showMessageDialog(null, e2.getMessage() , "Upgrade Active Fighter", JOptionPane.WARNING_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

		    }
		}
	    });

	    b.setBounds(250,440,100,40);	
	    b.setBackground(new Color(139,69,19));
	    b.setForeground(new Color(127,255,0));
	    b.setOpaque(true);

	    view.getJ().add(b);
	    view.getJ().add(jc);
	    //			j.add(p);
	    //			Object [] o = {jl,jl2,jl3,jl4,jl5,jl6,jl7,jc};
	    view.getJ().setVisible(true);
	    view.getJ().validate();
	    //			int res = JOptionPane.showConfirmDialog(null,o, "Upgrade Active Fighter", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));


	}

	else if(e.getActionCommand().equals("attacks"))
	{
	    view.setJ(new JFrame());
	    view.getJ().setLayout(null);
	    view.getJ().setUndecorated(true);
	    //			view.getJ().setLocationRelativeTo(view);
	    view.getJ().setBounds(350, 100, 600, 600);
	    view.getJ().setOpacity(1);
	    //			view.getJ().setShape(new Ellipse2D.Double(0, 0, view.getJ().getWidth(), view.getJ().getHeight()));
	    JLabel jl = new JLabel("All of the available attacks are listed below check the ");
	    jl.setBounds(30,100,500, 35);
	    jl.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl);
	    JLabel jl3 = new JLabel("ones you want to assign to your active fighter");
	    jl3.setBounds(30,140,500,35);
	    jl3.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl3);
	    view.getJ().setBackground(new Color(255,153,18,170));;

	    int sizeA = game.getPlayer().getSuperAttacks().size();
	    int sizeU = game.getPlayer().getUltimateAttacks().size();

	    ArrayList<SuperAttack> ss = game.getPlayer().getActiveFighter().getSuperAttacks();
	    ArrayList<UltimateAttack> ua  = game.getPlayer().getActiveFighter().getUltimateAttacks();
	    //			JCheckBox [] box = new JCheckBox[sizeA+sizeU];
	    Box box = Box.createVerticalBox();
	    box.setBounds(0,160, 450, (sizeA+sizeU)*40);
	    for (int i = 0; i < sizeA; i++) {
		Attack a = game.getPlayer().getSuperAttacks().get(i);
		String s = a.getName()+" , Damage: "+ a.getDamage();
		JCheckBox j = new JCheckBox(s,ss.contains((SuperAttack)a));
		//				view.getJ().add(j);
		j.setBounds(100, (i*40), 500, 35);
		//				scrollableList.add(j);
		//				box[i] = j;
		box.add(j);
		j.addItemListener(new ItemListener(){
		    @Override
		    public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
			    JCheckBox tmp = (JCheckBox)(e.getSource());
			    String [] st = tmp.getText().split(",");
			    try {
				game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), new SuperAttack(st[0], Integer.parseInt(st[1].split(" ")[2])), null);
			    } catch (MaximumAttacksLearnedException e2) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e2.getMessage() , "Assign Attacks", JOptionPane.WARNING_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

			    } catch (DuplicateAttackException e2) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e2.getMessage() , "Assign Attacks", JOptionPane.WARNING_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

			    } catch (NotASaiyanException e2) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e2.getMessage() , "Assign Attacks", JOptionPane.WARNING_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

			    } catch (NumberFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			    }

			}
			else if(e.getStateChange() == ItemEvent.DESELECTED){
			    JCheckBox tmp = (JCheckBox)e.getSource();
			    String [] st = tmp.getText().split(",");
			    game.getPlayer().getActiveFighter().getSuperAttacks().remove(new SuperAttack(st[0], Integer.parseInt(st[1].split(" ")[2])));

			}

		    }
		});

	    }
	    for (int i = 0; i < sizeU; i++) {
		Attack a = game.getPlayer().getUltimateAttacks().get(i);
		String s = a.getName()+", Damage: "+ a.getDamage();
		JCheckBox j = new JCheckBox(s,ua.contains((UltimateAttack)a));
		j.setBounds(0, 180+(i*40), 500, 35);
		//				scrollableList.add(j);
		//				view.getJ().add(j);
		//                box[sizeA + i] = j;
		box.add(j);

		j.addItemListener(new ItemListener(){
		    @Override
		    public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
			    JCheckBox tmp = (JCheckBox)(e.getSource());
			    String [] st = tmp.getText().split(",");
			    try {
				game.getPlayer().assignAttack(game.getPlayer().getActiveFighter(), new UltimateAttack(st[0], Integer.parseInt(st[1].split(" ")[2])), null);
			    } catch (MaximumAttacksLearnedException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage() , "Assign Attacks", JOptionPane.WARNING_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

			    } catch (DuplicateAttackException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage() , "Assign Attacks", JOptionPane.WARNING_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

			    } catch (NotASaiyanException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage() , "Assign Attacks", JOptionPane.WARNING_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

			    } catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			    }

			}
			else if(e.getStateChange() == ItemEvent.DESELECTED){
			    JCheckBox tmp = (JCheckBox)e.getSource();
			    String [] st = tmp.getText().split(",");
			    game.getPlayer().getActiveFighter().getUltimateAttacks().remove(new UltimateAttack(st[0], Integer.parseInt(st[1].split(" ")[2])));
			}

		    }
		});
	    }
	    JScrollPane scrollableList = new JScrollPane(box);

	    scrollableList.setBounds(30, 180, 500, 240);
	    scrollableList.setOpaque(true);
	    scrollableList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollableList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	    //			//			jlist.setVisibleRowCount(5);
	    view.getJ().add(scrollableList);
	    //			view.getJ().add(jlist);
	    JButton b = new JButton("Done");
	    b.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
		    // TODO Auto-generated method stub
		    view.getJ().setVisible(false);
		}
	    });
	    b.setBounds(250,440,70,40);	
	    b.setBackground(new Color(139,69,19));
	    b.setForeground(new Color(127,255,0));
	    b.setOpaque(false);
	    view.getJ().add(b);

	    view.getJ().setVisible(true);
	    view.getJ().validate();
	}
	else if(e.getActionCommand().equals("switch"))
	{
	    JPanel jp = new JPanel(new BorderLayout());

	    JLabel jl = new JLabel("Choose your active fighter: ");
	    jp.add(jl, BorderLayout.NORTH);
	    JComboBox<String> jc = new JComboBox<String>();
	    int size = game.getPlayer().getFighters().size();
	    for (int i = 0; i < size; i++) {
		jc.addItem(game.getPlayer().getFighters().get(i).getName());
	    }
	    jc.setSelectedIndex(0);
	    jp.add(jc);

	    int res = JOptionPane.showConfirmDialog(null, jp, "Switch Active Fighter",  JOptionPane.OK_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE,new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
	    if(res == JOptionPane.OK_OPTION){
		game.getPlayer().setActiveFighter(game.getPlayer().getFighters().get(jc.getSelectedIndex()));
		PlayableFighter f = game.getPlayer().getActiveFighter();
		view.getT().setText("Welcome "+ view.getPlayName().getText()+ " to your DragonBall World! Your "+ f.getClass().getName().substring(1+f.getClass().getName().lastIndexOf('.')) +" Fighter "+ f.getName() +" is here to save the world!");
		view.getLevel().setText(""+f.getLevel());
		view.getFighter().setIcon(new ImageIcon(new ImageIcon(f.getClass().getName().substring(1+f.getClass().getName().lastIndexOf('.'))+"99.png").getImage().getScaledInstance(50, 60, Image.SCALE_SMOOTH)));

	    }
	}	
	else if(e.getActionCommand().equals("Attack"))
	{
	    Battle bat = ((Battle)(view.getEvent().getSource()));
	    bat.getAssignedAttacks();

	    JPanel jp = new JPanel(new BorderLayout());

	    JLabel jl = new JLabel("Choose the Attack");
	    jp.add(jl, BorderLayout.NORTH);
	    JComboBox<String> jc = new JComboBox<String>();
	    int size = bat.getAssignedAttacks().size();
	    for (int i = 0; i < size; i++) {
		jc.addItem(bat.getAssignedAttacks().get(i).getName());
	    }
	    jc.setSelectedIndex(0);
	    jp.add(jc);

	    int res = JOptionPane.showConfirmDialog(null, jp, "Choose Attack", JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
	    if(res == JOptionPane.OK_OPTION){
		try {
		    bat.attack(bat.getAssignedAttacks().get(jc.getSelectedIndex()));
		} catch (NotEnoughKiException e1) {
		    // TODO Auto-generated catch block
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Choose Attack", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));		
		}
	    }

	}
	else if(e.getActionCommand().equals("Block"))
	{
	    Battle bat = ((Battle)(view.getEvent().getSource()));
	    bat.block();
	}
	else if(e.getActionCommand().equals("Use"))
	{
	    Battle bat = ((Battle)(view.getEvent().getSource()));
	    try {
		bat.use(game.getPlayer(), Collectible.SENZU_BEAN);
	    } catch (NotEnoughSenzuBeansException e1) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(null, e1.getMessage(), "Error on Use", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));		
	    }
	}

	else if(e.getActionCommand().equals("SENZU_BEANS"))
	{

	    DragonWishType t=DragonWishType.SENZU_BEANS;
	    DragonWish dx=new DragonWish(d,t,d.getSenzuBeans());	
	    game.getPlayer().chooseWish(dx);
	    view.getSen().setText(""+game.getPlayer().getSenzuBeans());
	    view.getDrag().setText(""+game.getPlayer().getDragonBalls());

	    int x = JOptionPane.showConfirmDialog(null, "Senzubeans will be increased", "DragonBall", JOptionPane.PLAIN_MESSAGE);
	    if(x == JOptionPane.OK_OPTION){
		view.setContentPane(c);
	    }
	    else{

	    }
	}

	else if(e.getActionCommand().equals("ABILITY_POINTS"))
	{
	    DragonWishType t=DragonWishType.ABILITY_POINTS;

	    DragonWish dx=new DragonWish(d,t,d.getAbilityPoints());	
	    game.getPlayer().chooseWish(dx);

	    view.getDrag().setText(""+game.getPlayer().getDragonBalls());
	    int x = JOptionPane.showConfirmDialog(null, "Ability Points will be increased", "DragonBall", JOptionPane.PLAIN_MESSAGE);
	    if(x == JOptionPane.OK_OPTION){
		view.setContentPane(c);
	    }
	    else{

	    }
	}
	else if(e.getActionCommand().equals("SUPER_ATTACK"))
	{
	    DragonWishType t=DragonWishType.SUPER_ATTACK;

	    DragonWish dx=new DragonWish(d,t,s);	
	    game.getPlayer().chooseWish(dx);

	    view.getDrag().setText(""+game.getPlayer().getDragonBalls());

	    int x = JOptionPane.showConfirmDialog(null, "your wish SuperAttack will be added  ", "DragonBall", JOptionPane.PLAIN_MESSAGE);
	    if(x == JOptionPane.OK_OPTION){
		view.setContentPane(c);
	    }
	    else{

	    }
	}
	else if(e.getActionCommand().equals("ULTIMATE_ATTACK"))
	{
	    DragonWishType t=DragonWishType.ULTIMATE_ATTACK;

	    DragonWish dx=new DragonWish(d,t,u);	
	    game.getPlayer().chooseWish(dx);

	    view.getDrag().setText(""+game.getPlayer().getDragonBalls());

	    int x = JOptionPane.showConfirmDialog(null, "your wish Ultimate Attack will be added ", "DragonBall", JOptionPane.PLAIN_MESSAGE);
	    if(x == JOptionPane.OK_OPTION){
		view.setContentPane(c);
	    }
	    else{

	    }

	}
	else if(e.getActionCommand().equals("Continue"))
	{
	    view.getJ().setVisible(false);
	    view.getLevel().setText(""+game.getPlayer().getActiveFighter().getLevel());
	    view.remove(view.getJl6());
	    if(view.getC() != null)
        	view.getC().stop();
	    view.revalidate();
	    view.repaint();
	}
	else if (e.getActionCommand().equals("Display"))

	{

	    view.setJ(new JFrame());
	    view.getJ().setLayout(null);
	    view.getJ().setUndecorated(true);
	    view.getJ().setBounds(350, 100, 600, 600);
	    view.getJ().setOpacity(1);
	    view.getJ().setBackground(new Color(255,153,18,170));
	    view.getJ().setShape(new Ellipse2D.Double(0, 0, view.getJ().getWidth(), view.getJ().getHeight()));

	    PlayableFighter f = game.getPlayer().getActiveFighter();			
	    JLabel jl = new JLabel("Your active fighter has the following attributes:");	
	    jl.setBounds(30,100,500, 35);
	    jl.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl);
	    JLabel jl2 = new JLabel("Maximum Health Points: "+ f.getMaxHealthPoints());
	    jl2.setBounds(30,140,500, 35);
	    jl2.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl2);

	    JLabel jl3 = new JLabel("Physical Damage: "+ f.getPhysicalDamage());
	    jl3.setBounds(30,180,500, 35);
	    jl3.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl3);

	    JLabel jl4 = new JLabel("Blast Damage: "+ f.getBlastDamage());
	    jl4.setBounds(30,220,500, 35);
	    jl4.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl4);

	    JLabel jl5 = new JLabel("Maximum Ki Bars: "+ f.getMaxKi());
	    jl5.setBounds(30,260,500, 35);
	    jl5.setHorizontalAlignment(SwingConstants.CENTER);
	    view.getJ().add(jl5);

	    JLabel jl6 = new JLabel("Maximum Stamina Bars: "+ f.getMaxStamina());
	    view.getJ().add(jl6);
	    jl6.setBounds(30,300,500, 35);
	    jl6.setHorizontalAlignment(SwingConstants.CENTER);

	    JLabel jl7 = new JLabel("Ability Points: "+ f.getAbilityPoints());
	    view.getJ().add(jl7);
	    jl7.setBounds(30,340,500, 35);
	    jl7.setHorizontalAlignment(SwingConstants.CENTER);

	    JLabel jl8 = new JLabel("Target Xp "+ f.getTargetXp());
	    view.getJ().add(jl8);
	    jl8.setBounds(30,380,500, 35);
	    jl8.setHorizontalAlignment(SwingConstants.CENTER);


	    JLabel jl9 = new JLabel(" Xp "+ f.getXp());
	    view.getJ().add(jl9);
	    jl9.setBounds(30,420,500, 35);
	    jl9.setHorizontalAlignment(SwingConstants.CENTER);

	    JLabel j20 = new JLabel("ki "+ f.getKi());
	    view.getJ().add(j20);
	    j20.setBounds(30,460,500, 35);
	    j20.setHorizontalAlignment(SwingConstants.CENTER);


	    JLabel j21 = new JLabel("Stamina "+ f.getStamina());
	    view.getJ().add(j21);
	    j21.setBounds(30,500,500, 35);
	    j21.setHorizontalAlignment(SwingConstants.CENTER);

	    JButton b = new JButton("exit");
	    b.setBounds(250,540,70,40);	
	    b.setBackground(new Color(139,69,19));
	    b.setForeground(new Color(127,255,0));
	    b.setOpaque(true);
	    view.getJ().add(b);
	    view.getJ().setVisible(true);
	    view.getJ().validate();

	    b.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
		    // TODO Auto-generated method stub
		    view.getJ().setVisible(false);
		    view.revalidate();
		    view.repaint();
		}
	    });
	}
    }

    @Override
    public void onDragonCalled(Dragon dragon) {
	// TODO Auto-generated method stub
	d=dragon;
	c = view.getContentPane();
	view.dragonModePanel();
	for (int i=0;i<dragon.getWishes().length;i++)
	{    
	    String x=""+dragon.getWishes()[i].getType();
	    String y="";
	    if(x.equals("ABILITY_POINTS")|| x.equals("SENZU_BEANS")){
		view.setDr(new JButton(x));
		view.getDr().setBounds(50+(i*200),500,150,100); 

	    }
	    else if(x.equals("SUPER_ATTACK"))
	    {
		s=dragon.getWishes()[i].getSuperAttack();
		y=x+"  "+dragon.getWishes()[i].getSuperAttack().getName();
		view.setDr(new JButton(y));
		view.getDr().setBounds(50+(i*250),500,300,100); 
	    }		
	    else if(x.equals("ULTIMATE_ATTACK")){
		u=dragon.getWishes()[i].getUltimateAttack();
		y=x+"  "+dragon.getWishes()[i].getUltimateAttack().getName();
		view.setDr(new JButton(y));
		view.getDr().setBounds(50+(i*300),500,300,100); 
	    }

	    view.getDr().setActionCommand(x); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	    //view.getDr().setBounds(50+(i*300),500,200,100); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	    view.getDr().setBackground(new Color(113,198,113));

	    // add listener to the button here it is the GameControl class I set it there in the constructor check it
	    view.getDr().setBackground(new Color(139,69,19));
	    view.getDr().setForeground(new Color(127,255,0));
	    view.getDr().setOpaque(true);
	    view.getDr().setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	    view.getDr().setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	    view.getDr().setBorderPainted(true);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	    view.getDr().addActionListener(this);
	    view.add(view.getDr()); //
	}
    }

    @Override
    public void onCollectibleFound(Collectible collectible) {
	// TODO Auto-generated method stub
	//game.getWorld().getMap()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()];
	switch (collectible) {
	    case SENZU_BEAN:
		view.getSen().setText(""+game.getPlayer().getSenzuBeans());  //set the text with the updated number of the senzu beans
		JOptionPane.showConfirmDialog(null, "You have collected 1 Senzu Bean", "DragonBall", JOptionPane.PLAIN_MESSAGE);
		break;
	    case DRAGON_BALL:
		view.getDrag().setText(""+game.getPlayer().getDragonBalls()); //set the text with the updated number of the dragon balls
		JOptionPane.showConfirmDialog(null, "You have collected 1 DragonBall", "DragonBall", JOptionPane.PLAIN_MESSAGE);
		break;
	}
    }

    @Override
    public void onBattleEvent(BattleEvent e) {
	// TODO Auto-generated method stub
	//		long start = System.currentTimeMillis();
	//		while(System.currentTimeMillis() - start < 500){
	//
	//		}
	if(e.getType() == BattleEventType.STARTED)
	{
	    int res = 0;
	    do{
		res = JOptionPane.showConfirmDialog(null, "You encountered a Foe Cell, Click OK when you are ready for the battle!", "DragonBall", JOptionPane.OK_CANCEL_OPTION);
	    }while(res != JOptionPane.OK_OPTION);

	    c = view.getContentPane(); // save the content pane to return back in case you won the battle
	    view.setEvent(e);
	    view.battleModePanel();
	}
	else if(e.getType() == BattleEventType.ENDED)
	{
	    Battle b = (Battle)(e.getSource());
	    PlayableFighter m = (PlayableFighter)b.getMe();
	    NonPlayableFighter o = (NonPlayableFighter)b.getFoe();
	    if(e.getWinner() == m ){

		view.setContentPane(c);
		view.setJ(new JFrame());
		view.getJ().setLayout(null);
		view.getJ().setUndecorated(true);
		view.getJ().setBounds(300, 100, 600, 600);
		view.getJ().setOpacity(1);
		view.getJ().setBackground(new Color(255,153,18,170));;
		view.getJ().setShape(new Ellipse2D.Double(0, 0, 600, 600));
		PlayableFighter f = game.getPlayer().getActiveFighter();
		JLabel jl = new JLabel("Your active fighter has gained the following attributes:");
		jl.setBounds(30,100,500, 35);
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		view.getJ().add(jl);
		//				ImageIcon icon = new ImageIcon("zgmRe.gif");
		//				JLabel jll = new JLabel(icon);
		//				jll.setBounds(0,0,view.getWidth(), view.getHeight());
		//				view.getJ().add(jll);
		JLabel jl2 = new JLabel("Current XP: "+ f.getXp());
		jl2.setBounds(30,140,500, 35);
		jl2.setHorizontalAlignment(SwingConstants.CENTER);
		view.getJ().add(jl2);

		JLabel jl3 = new JLabel("Physical Damage: "+ f.getPhysicalDamage());
		jl3.setBounds(30,180,500, 35);
		jl3.setHorizontalAlignment(SwingConstants.CENTER);
		view.getJ().add(jl3);

		JLabel jl4 = new JLabel("Blast Damage: "+ f.getBlastDamage());
		jl4.setBounds(30,220,500, 35);
		jl4.setHorizontalAlignment(SwingConstants.CENTER);
		view.getJ().add(jl4);

		JLabel jl5 = new JLabel("Maximum Ki Bars: "+ f.getMaxKi());
		jl5.setBounds(30,260,500, 35);
		jl5.setHorizontalAlignment(SwingConstants.CENTER);
		view.getJ().add(jl5);

		view.add(view.getJl6());
		String name = "fire.wav";    
		    AudioInputStream aud;
		    try
		    {
			aud = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
			view.setC(AudioSystem.getClip());
			view.getC().open(aud);
			view.getC().start();
		    } catch (UnsupportedAudioFileException | IOException e1)
		    {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    } catch (LineUnavailableException e1)
		    {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		//gained attacks
		JList <JLabel>jlst = new JList<JLabel>();
		jlst.setBounds(0, 0, 500, 70);
		jlst.setLayoutOrientation(JList.VERTICAL);

		int sizeA = o.getSuperAttacks().size();
		int sizeU = o.getUltimateAttacks().size();

		ArrayList<SuperAttack> ss = o.getSuperAttacks();
		ArrayList<UltimateAttack> ua  = o.getUltimateAttacks();
		for (int i = 0; i < sizeA; i++) {
		    Attack a = ss.get(i);
		    String s = a.getName()+" , Damage: "+ a.getDamage();
		    JLabel j = new JLabel(s);
		    j.setBounds(0, 40*i, 200, 35);
		    jlst.add(j);

		}
		for (int i = 0; i < sizeU; i++) {
		    Attack a = ua.get(i);
		    String s = a.getName()+" , Damage: "+ a.getDamage();
		    JLabel j = new JLabel(s);
		    j.setBounds(0, 40*sizeA + 40*i, 200, 35);
		    jlst.add(j);

		}
		JScrollPane p = new JScrollPane(jlst,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		p.setBounds(30, 300, 500, 100);
		view.getJ().add(p);
		JButton but = new JButton("Continue");
		but.addActionListener(this);
		but.setBounds(250,440,100,40);	
		but.setBackground(new Color(139,69,19));
		but.setForeground(new Color(127,255,0));
		but.setOpaque(true);
		view.getJ().add(but);
		view.getJ().setVisible(true);
		view.getJ().validate();

		if(o.isStrong()){  //defeating a boss
		    JOptionPane.showMessageDialog(null, "Congratulations! You defeated the boss! You now have explored "+ game.getPlayer().getExploredMaps() +" maps");
		    view.worldModePanel();
		    PlayableFighter f1 = game.getPlayer().getActiveFighter();
		    view.getLevel().setText(""+f1.getLevel());
		    view.getFighter().setBounds(300+(game.getWorld().getPlayerColumn()*58)+5, 65+(game.getWorld().getPlayerRow()*65)+5, 50, 60);

		    view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()].setEnabled(true);
		    view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setEnabled(true);
		    view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setEnabled(true);			
		}
	    }
	    else
	    {
		JOptionPane.showMessageDialog(null, "You have been defeated by your opponent!");
		PlayableFighter f = game.getPlayer().getActiveFighter();
		view.worldModePanel();
		view.getLevel().setText(""+f.getLevel());
		view.getSen().setText(""+game.getPlayer().getSenzuBeans());
		view.getDrag().setText(""+game.getPlayer().getDragonBalls());
		view.getT().setText("Welcome "+ (game.getPlayer().getName())+ " to your DragonBall World! Your "+ f.getClass().getName().substring(1+f.getClass().getName().lastIndexOf('.')) +" Fighter "+ f.getName() +" is here to save the world!");
		view.getFighter().setBounds(300+(game.getWorld().getPlayerColumn()*58)+5, 65+(game.getWorld().getPlayerRow()*65)+5, 50, 60);

		view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()].setEnabled(true);
		view.getButtons()[game.getWorld().getPlayerRow()-1][game.getWorld().getPlayerColumn()].setEnabled(true);
		view.getButtons()[game.getWorld().getPlayerRow()][game.getWorld().getPlayerColumn()-1].setEnabled(true);			


	    }
	}
	else if(e.getType() == BattleEventType.ATTACK){
	    Battle b = (Battle)(e.getSource());
	    PlayableFighter m = (PlayableFighter)b.getMe();
	    NonPlayableFighter o = (NonPlayableFighter)b.getFoe();
	    JLabel[]tmp = view.getBattleAtt();
	    String name = "punches.wav";    
	    AudioInputStream aud;
	    try
	    {
		aud = AudioSystem.getAudioInputStream(new File(name).getAbsoluteFile());
		view.setC(AudioSystem.getClip());
		view.getC().open(aud);
		view.getC().start();
	    } catch (UnsupportedAudioFileException | IOException e1)
	    {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    } catch (LineUnavailableException e1)
	    {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    if(b.getAttacker() == m){
		view.getBattleMe().setIcon(new ImageIcon(e.getAttack() instanceof SuperSaiyan?"transformed.gif":"meAttack.gif"));
		view.getBattleFoe().setIcon(new ImageIcon(new ImageIcon("empty.gif").getImage().getScaledInstance(view.getBattleMe().getWidth(),view.getBattleMe().getHeight(),Image.SCALE_SMOOTH))); 
		JOptionPane.showMessageDialog(null, "You attacked your opponent with a(n) " + e.getAttack().getName(), "Attack", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));	
	    }else{
		view.getBattleMe().setIcon(new ImageIcon(new ImageIcon("empty.gif").getImage().getScaledInstance(view.getBattleMe().getWidth(),view.getBattleMe().getHeight(),Image.SCALE_SMOOTH))); 
		view.getBattleFoe().setIcon(new ImageIcon("foeAttack.gif"));
		JOptionPane.showMessageDialog(null, "Your opponent attacked you with a(n) " + e.getAttack().getName(), "Attack", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));	
	    }


	    tmp[0].setText("Health Points: "+m.getHealthPoints()+"/"+m.getMaxHealthPoints());;
	    tmp[1].setText("Health Points: "+o.getHealthPoints()+"/"+o.getMaxHealthPoints());;
	    tmp[2].setText("Ki Bars: "+m.getKi()+"/"+m.getMaxKi());
	    tmp[3].setText("Ki Bars: "+o.getKi()+"/"+o.getMaxKi());
	    tmp[4].setText("Stamina: "+m.getStamina()+"/"+m.getMaxStamina());
	    tmp[5].setText("Stamina: "+o.getStamina()+"/"+o.getMaxStamina());

	}
	else if(e.getType() == BattleEventType.BLOCK){
	    //blocking effect // this is temporary
	    Battle b = (Battle)(e.getSource());
	    PlayableFighter m = (PlayableFighter)b.getMe();
	    if(b.getAttacker() == m){
		JOptionPane.showMessageDialog(null, "Your fighter blocked your opponent's attack" , "Block", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));	
		view.getBattleMe().setIcon(new ImageIcon(b.isMeBlocking()?"blockMe.gif":"stillME.gif"));
	    }
	    else{
		    view.getBattleFoe().setIcon(new ImageIcon(b.isFoeBlocking()?"blockFoe.gif":"stillFoe.gif"));
		JOptionPane.showMessageDialog(null, "Your opponent blocked your attack" , "Block", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));	
	    }
	}
	else if(e.getType() == BattleEventType.USE){
	    JOptionPane.showMessageDialog(null, "You used a senzu bean to upgrade your fighter's attributes" , "Use", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));	
	    JLabel[]tmp = view.getBattleAtt();
	    Battle b = (Battle)(e.getSource());
	    PlayableFighter m = (PlayableFighter)b.getMe();
	    NonPlayableFighter o = (NonPlayableFighter)b.getFoe();
	    tmp[0].setText("Health Points: "+m.getHealthPoints()+"/"+m.getMaxHealthPoints());;
	    tmp[4].setText("Stamina: "+m.getStamina()+"/"+m.getMaxStamina());

	}
	else if(e.getType() == BattleEventType.NEW_TURN)
	{
	    JLabel[]tmp = view.getBattleAtt();
	    Battle b = (Battle)(e.getSource());
	    PlayableFighter m = (PlayableFighter)b.getMe();
	    NonPlayableFighter o = (NonPlayableFighter)b.getFoe();
	    tmp[0].setText("Health Points: "+m.getHealthPoints()+"/"+m.getMaxHealthPoints());;
	    tmp[1].setText("Health Points: "+o.getHealthPoints()+"/"+o.getMaxHealthPoints());;
	    tmp[2].setText("Ki Bars: "+m.getKi()+"/"+m.getMaxKi());
	    tmp[3].setText("Ki Bars: "+o.getKi()+"/"+o.getMaxKi());
	    tmp[4].setText("Stamina: "+m.getStamina()+"/"+m.getMaxStamina());
	    tmp[5].setText("Stamina: "+o.getStamina()+"/"+o.getMaxStamina());
	    tmp[6].setText(""+(e.getCurrentOpponent()==m?m.getName():o.getName())+"'s Turn");
	    if(m instanceof Saiyan){
		tmp[7].setText(((Saiyan)m).isTransformed()?"TF":"Not TF");
	    }
	    view.getBattleMe().setIcon(new ImageIcon(b.isMeBlocking()?"blockMe.gif":"stillME.gif"));
	    view.getBattleFoe().setIcon(new ImageIcon(b.isFoeBlocking()?"blockFoe.gif":"stillFoe.gif"));
            if(view.getC() != null)
        	view.getC().stop();
            
	    if(e.getCurrentOpponent() == o)
		try {
		    b.play();
		} catch (NotEnoughKiException e1) {
		    // TODO Auto-generated catch block
		    JOptionPane.showMessageDialog(null, e1.getMessage(), "Choose Attack", JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ImageIcon("chooseF.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));		
		}
	}
    }


    public GameGUI getView() {
	return view;
    }


    public void setView(GameGUI view) {
	this.view = view;
    }



}
