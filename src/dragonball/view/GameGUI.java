package dragonball.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.sound.sampled.Clip;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import dragonball.controller.GameControl;
import dragonball.model.battle.Battle;
import dragonball.model.battle.BattleEvent;
import dragonball.model.character.fighter.NonPlayableFighter;
import dragonball.model.character.fighter.PlayableFighter;

public class GameGUI extends JFrame{
    private GameControl listener; //control listener
    private JLabel back; // label for the background
    private ButtonGroup gr; //button group for fighter's icons to ensure only one button selected
    private JLabel ac_race; // active fighter race
    private JTextField fighName; // fighter's name field 
    private JButton[][] buttons; //map
    private JLabel playName; 
    private JLabel fighter; //icon image of the fighter in the map
    private static ImageIcon en = new ImageIcon(new ImageIcon("OVLDU9x copy 2.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)); ;
    // this is how to scale the image as we desire the two numbers reperesent the width and the height respectively
    private static ImageIcon dis = new ImageIcon(new ImageIcon("OVLDU9x.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)); ;
    // I am not sure why I made those especially static :D
    private JLabel sen; // label for senzu beans count in world mode
    private JLabel drag; //label for dragonballs count in world mode
    private JLabel t; // label for complete message in world mode
    private JLabel level; // label for level in world mode
    private JFrame j;
    private JComboBox<String> jcheck;
    private JPanel me;
    private JPanel opp;
    private  JLabel [] battleAtt;
    private BattleEvent event;
    private JLabel ff;
    private JButton Dr;
    private JToggleButton fr;
    private JToggleButton fr1;
    private JToggleButton fr11;
    private JToggleButton fr111;
    private JToggleButton fr1111;
    private JLabel battleArea;
    private JLabel battleMe;
    private JLabel battleFoe;
    private Clip c;
    
    public JLabel getBattleArea()
    {
        return battleArea;
    }
    public void setBattleArea(JLabel battleArea)
    {
        this.battleArea = battleArea;
    }
    public JLabel getBattleMe()
    {
        return battleMe;
    }
    public void setBattleMe(JLabel battleMe)
    {
        this.battleMe = battleMe;
    }
    public JLabel getBattleFoe()
    {
        return battleFoe;
    }
    public void setBattleFoe(JLabel battleFoe)
    {
        this.battleFoe = battleFoe;
    }
    public JLabel getJl6()
    {
	return jl6;
    }
    public void setJl6(JLabel jl6)
    {
	this.jl6 = jl6;
    }
    private JLabel jl6;

    public JToggleButton getFr()
    {
	return fr;
    }
    public void setFr(JToggleButton fr)
    {
	this.fr = fr;
    }
    public JToggleButton getFr1()
    {
	return fr1;
    }
    public void setFr1(JToggleButton fr1)
    {
	this.fr1 = fr1;
    }
    public JToggleButton getFr11()
    {
	return fr11;
    }
    public void setFr11(JToggleButton fr11)
    {
	this.fr11 = fr11;
    }
    public JToggleButton getFr111()
    {
	return fr111;
    }
    public void setFr111(JToggleButton fr111)
    {
	this.fr111 = fr111;
    }
    public JToggleButton getFr1111()
    {
	return fr1111;
    }
    public void setFr1111(JToggleButton fr1111)
    {
	this.fr1111 = fr1111;
    }
    public JButton getDr() {
	return Dr;
    }
    public void setDr(JButton dr) {
	Dr = dr;
    }
    public GameGUI()
    {
	super("DragonBall");
	setExtendedState(JFrame.MAXIMIZED_BOTH);
	addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent win) {
		int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "DragonBall", JOptionPane.OK_CANCEL_OPTION);
		if(i == JOptionPane.OK_OPTION){
		    JOptionPane.showMessageDialog(null, "See you next time!");
		    System.exit(0); 
		}
		else{
		    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	    }
	});
	setLayout(null);
	setVisible(true);
	setResizable(false);
	validate();
    }
    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		//				try{
		//					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		//				}
		//				catch(Exception e){
		//					e.printStackTrace();
		//				}
		new GameControl();
		//				new GameControl().getView().chooseFighterPanel(); //to access only the choose fighter panel
		//				new GameControl().getView().worldModePanel(); //to access only the  world mode panel
		//								new GameControl().getView().battleModePanel(); //to access only the  world mode panel

	    }
	});

    }


    public void startPanel(){
	ImageIcon icon = new ImageIcon("DragonBallWikiBackground(.png"); 
	back = new JLabel(icon);	//create a label of this image
	setContentPane(back); //I set the background to be this label by this

	icon = new ImageIcon("load Game icon.png");   //create a new imageIcon using this pic and the pic is in the project
	JButton ld = new JButton(icon); //you assign the icon for the button this way
	ld.setActionCommand("Load"); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	ld.setBounds(800, 425, 400,100); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	ld.setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	ld.setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	ld.setBorderPainted(true);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	ld.addActionListener(listener); // add listener to the button here it is the GameControl class I set it there in the constructor check it
	add(ld); //this is how we add components to the content pane we don't need to getContentPane() it is fixed for this it will always add to the content pane

	icon = new ImageIcon("new game icon.png");
	JButton cr = new JButton(icon);
	cr.setActionCommand("New");
	cr.setBounds(100, 425, 400, 100); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	cr.setContentAreaFilled(false);
	cr.setFocusPainted(false);
	cr.setBorderPainted(true);
	cr.addActionListener(listener);
	add(cr);

	//??
	pack();
	setVisible(true);
	setResizable(false);
	validate();
    }

    public void chooseFighterPanel(boolean flag){ // boolean true if on start false if in middle of the game 

	setContentPane(new JLabel()); // to remove the old background 
	getContentPane().removeAll(); // to remove any components just in case
	ImageIcon icon = new ImageIcon(new ImageIcon("View Backgroundedit55.png").getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));; 
	back = new JLabel(icon);	//create a label of this image
	setContentPane(back); 

	icon = new ImageIcon("new game icon2.png");
	if(!flag){
	    icon = new ImageIcon(new ImageIcon("NewFighter.png").getImage().getScaledInstance(200,100,Image.SCALE_SMOOTH)); 
	}
	JButton start = new JButton(icon);
	start.setActionCommand(flag? "Start":"Return");
	start.setBounds(490, 515, flag?200:290, flag?100:72); //the first two parameters represent the x and y of the top left corner and the other two the width and the height

	start.setContentAreaFilled(false);
	start.setFocusPainted(false);
	start.setBorderPainted(false);
	start.addActionListener(listener);
	add(start);

	//		JLabel n  = new JLabel("<html><p>Choose your fighter's race</p></html>");
	//		n.setForeground(new Color(255,140,0));
	//		n.setFont(new Font("", Font.BOLD,20));
	//		n.setBounds(450, 0, 300, 100);
	//		add(n);

	ac_race = new JLabel("");
	ac_race.setBounds(850, 250, 400, 100);
	ac_race.setForeground(new Color(255,140,0));
	ac_race.setFont(new Font("", Font.BOLD,40));
	add(ac_race);

	//		icon = new ImageIcon("chooseF2.jpg");  // here in this window we have three parts side, top and bottom so I will set this as a panel and specify its size and the same for the other one the panel reperesents a container for other items
	JLabel lside = new JLabel("");
	lside.setBounds(500, 0, 800, 615); //label for panel's background
	lside.setBackground(new Color(255,255,255,255));
	add(lside);	

	icon = new ImageIcon("Frieza.gif"); 
	fr = new JToggleButton(icon);
	fr.setActionCommand("Frieza");
	fr.setBounds(100, 230, 227, 227); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	fr.setContentAreaFilled(false);
	fr.setFocusPainted(false);
	fr.setBorderPainted(false);
	fr.addActionListener(listener);
	fr.addMouseListener(listener);
	lside.add(fr);

	gr = new ButtonGroup();
	gr.clearSelection();
	gr.add(fr);

	icon = new ImageIcon("Majin.gif"); 
	fr1 = new JToggleButton(icon);
	fr1.setActionCommand("Majin");
	fr1.setBounds(550, 230, 227, 227); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	fr1.setContentAreaFilled(false);
	fr1.setFocusPainted(false);
	fr1.setBorderPainted(false);
	fr1.addActionListener(listener);
	fr1.addMouseListener(listener);
	gr.add(fr1);
	lside.add(fr1);

	icon = new ImageIcon("Saiyan.gif"); 
	fr11 = new JToggleButton(icon);
	fr11.setActionCommand("Saiyan");
	fr11.setOpaque(true);
	fr11.setBounds(322, 370, 227, 227); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	fr11.setContentAreaFilled(false);
	fr11.setFocusPainted(false);
	fr11.setBorderPainted(false);
	fr11.addActionListener(listener);
	fr11.addMouseListener(listener);

	gr.add(fr11);
	lside.add(fr11);

	icon = new ImageIcon("Earthling.gif"); 
	fr111 = new JToggleButton(icon);
	fr111.setActionCommand("Earthling");
	fr111.setOpaque(true);
	fr111.setBounds(430, 0, 197, 227); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	fr111.setContentAreaFilled(false);
	fr111.setFocusPainted(false);
	fr111.setBorderPainted(false);
	fr111.addActionListener(listener);
	fr111.addMouseListener(listener);
	gr.add(fr111);
	lside.add(fr111);

	icon = new ImageIcon("Namekian.gif"); 
	fr1111 = new JToggleButton(icon);
	fr1111.setActionCommand("Namekian");
	fr1111.setOpaque(true);
	fr1111.setBounds(193, 0, 227, 227); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	fr1111.setContentAreaFilled(false);
	fr1111.setFocusPainted(false);
	fr1111.setBorderPainted(false);
	fr1111.addActionListener(listener);
	fr1111.addMouseListener(listener);
	gr.add(fr1111);
	lside.add(fr1111);



	icon = new ImageIcon("fighback.jpg");
	//		n  = new JLabel("Fighter's Name");
	//		n.setForeground(new Color(255,140,0));
	//		n.setFont(new Font("", Font.BOLD,20));
	//		n.setBounds(430, 615, 1400, 100);
	//		add(n);



	fighName = new JTextField();
	fighName.setActionCommand("name");
	fighName.setBounds(700, 630, 200, 70); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	fighName.addActionListener(listener);
	add(fighName);


	ff=new JLabel ();
	ff.setBounds(100, 70, 300, 500);

	add(ff);

	revalidate();
	repaint();
    }


    public void dragonModePanel(){
	setContentPane(new JLabel()); // to remove the old background 
	getContentPane().removeAll(); 
	ImageIcon icon = new ImageIcon(new ImageIcon("Shenron1.jpg").getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)); 
	//include here in cons the file name of the dragon image
	back = new JLabel(icon);	//create a label of this image
	setContentPane(back);
	revalidate();
	repaint();
    }

    public void battleModePanel(){
	setContentPane(new JLabel()); // to remove the old background 
	getContentPane().removeAll(); 
	//before temple I want to make introductory view to the battle
	Battle b = (Battle)(event.getSource());
	PlayableFighter m = (PlayableFighter)b.getMe();
	NonPlayableFighter o = (NonPlayableFighter)b.getFoe();
	battleAtt = new JLabel[8]; // 0 me, 1 opp health pts -> 2 me, 3 opp Ki -> 4 me,5 opp stamina -> 6 Turn -> 7 Trans Flag

//	ImageIcon icon = new ImageIcon(m.getClass().getName().substring(1+m.getClass().getName().lastIndexOf('.')).charAt(0)+".png"); 
//	back = new JLabel();	//create a label of this image
//	back.setIcon(icon);
//	setContentPane(back);
//	Timer t = new Timer(1000,listener);
//	t.start();
//	t.
	ImageIcon icon = new ImageIcon("fighting-game-background-gifs-.gif"); 
	back = new JLabel();	//create a label of this image
	back.setIcon(icon);
	setContentPane(back);

	me = new JPanel(null);
	opp = new JPanel(null);

	JLabel n  = new JLabel("Level: "+m.getLevel());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(110, 60 , 200, 100);
	me.add(n);

	n  = new JLabel("Level: "+o.getLevel());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(110,60 , 200, 100);
	opp.add(n);

	n  = new JLabel("Health Points: "+m.getHealthPoints() + "/" + m.getMaxHealthPoints());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(10, 210, 400, 100);
	me.add(n);
	battleAtt[0] = n;

	n  = new JLabel("Health Points: "+o.getHealthPoints() + "/" + o.getMaxHealthPoints());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(10, 210, 400, 100);
	opp.add(n);
	battleAtt[1] = n;

	n  = new JLabel("Ki Bars: "+ m.getKi()+"/"+m.getMaxKi());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(110, 400, 200, 100);
	me.add(n);
	battleAtt[2] = n;

	n  = new JLabel("Ki Bars: "+ o.getKi()+"/"+o.getMaxKi());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(110,400, 200, 100);
	opp.add(n);
	battleAtt[3] = n;

	n  = new JLabel("Stamina: "+m.getStamina()+"/"+m.getMaxStamina());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(110, 500, 200, 100);
	me.add(n);
	battleAtt[4] = n;

	n  = new JLabel("Stamina: "+o.getStamina()+"/"+o.getMaxStamina());
	n.setForeground(new Color(255,255,255));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(110, 500, 200, 100);
	opp.add(n);
	battleAtt[5] = n;

	n  = new JLabel(""+ o.getName()+ "'s Turn");
	n.setForeground(new Color(85,107,47));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(600, 0 , 200, 100);
	add(n);
	battleAtt[6] = n;

	n  = new JLabel();
	n.setForeground(new Color(220,20,60));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(450, 30, 200, 100);
	add(n);
	battleAtt[7] = n;


	n  = new JLabel(m.getName());
	n.setForeground(new Color(220,20,60));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(400, 0, 200, 100);
	add(n);

	n  = new JLabel(o.getName());
	n.setForeground(new Color(220,20,60));
	n.setFont(new Font("", Font.BOLD,20));
	n.setBounds(800, 0, 200, 100);
	add(n);

	battleMe = new JLabel();
	battleMe.setBounds(320,400,500,200);
	add(battleMe);
	battleFoe = new JLabel();
	battleFoe.setBounds(700,400,500,200);
	add(battleFoe);
	
	icon = new ImageIcon(new ImageIcon("buttondragon.gif").getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH)); 
	JButton cr = new JButton(icon);
	cr.setActionCommand("Use");
	cr.setBounds(100, 620, 140, 90); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	cr.setContentAreaFilled(false);
	cr.setFocusPainted(false);
	cr.setBorderPainted(false);
//	//	cr.setOpaque(true);
//	cr.setBackground(new Color(255,255,255,0));
//	cr.setForeground(new Color(255,255,255));
	cr.addActionListener(listener);
	cr.addMouseListener(listener);
	add(cr);
	
	cr = new JButton(icon);
	cr.setActionCommand("Attack");
	cr.setBounds(550, 620, 140, 90); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	cr.setContentAreaFilled(false);
	cr.setFocusPainted(false);
	cr.setBorderPainted(false);
//	cr.setBackground(new Color(255,255,255,0));
//	cr.setForeground(new Color(255,255,255));
	cr.addActionListener(listener);
	cr.addMouseListener(listener);
	add(cr);
	
	cr = new JButton(icon);
	cr.setActionCommand("Block");
	cr.setBounds(1000, 620, 140, 90); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	cr.setContentAreaFilled(false);
	cr.setFocusPainted(false);
//	cr.setBackground(new Color(255,255,255,0));
//	cr.setForeground(new Color(255,255,255));
	cr.setBorderPainted(false);
	cr.addActionListener(listener);
	cr.addMouseListener(listener);
	add(cr);

	
	
	add(me);
	me.setBounds(0,0,320,getHeight()-100);
	me.setBackground(new Color(205,51,51,100));
	add(opp);

	opp.setBounds(getWidth()-320,0,320,getHeight()-100);
	opp.setBackground(new Color(205,51,51,100));
	revalidate();
	repaint();
    }
    public void worldModePanel(){
	getContentPane().removeAll(); 
	setContentPane(new JLabel()); // to remove the old background 
	ImageIcon icon = new ImageIcon(new ImageIcon("map3.png").getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)); 
	back = new JLabel(icon);	//create a label of this image
	setContentPane(back);

	icon = new ImageIcon(new ImageIcon("Display.png").getImage().getScaledInstance(290,72,Image.SCALE_SMOOTH));   //create a new imageIcon using this pic and the pic is in the project
	JButton dis1 = new JButton(icon); //you assign the icon for the button this way
	dis1.setActionCommand("Display"); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	dis1.setBounds(980, 305, 290, 72); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	dis1.setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	dis1.setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	dis1.setBorderPainted(false);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	dis1.addActionListener(listener); // add listener to the button here it is the GameControl class I set it there in the constructor check it
	add(dis1); //this is how we add c

	icon = new ImageIcon(new ImageIcon("Upgrade.png").getImage().getScaledInstance(290,72,Image.SCALE_SMOOTH));   //create a new imageIcon using this pic and the pic is in the project
	JButton ld = new JButton(icon); //you assign the icon for the button this way
	ld.setActionCommand("upgrade"); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	ld.setBounds(980, 385, 290, 72); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	ld.setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	ld.setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	ld.setBorderPainted(false);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	ld.addActionListener(listener); // add listener to the button here it is the GameControl class I set it there in the constructor check it
	add(ld); //this is how we add c

	icon = new ImageIcon(new ImageIcon("Attacks.png").getImage().getScaledInstance(290,72,Image.SCALE_SMOOTH)); 
	JButton a = new JButton(icon); //you assign the icon for the button this way
	a.setActionCommand("attacks"); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	a.setBounds(980, 465, 290, 72); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	a.setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	a.setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	a.setBorderPainted(false);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	a.addActionListener(listener); // add listener to the button here it is the GameControl class I set it there in the constructor check it
	add(a); //this is how we add c

	icon = new ImageIcon(new ImageIcon("NewFighter.png").getImage().getScaledInstance(290,72,Image.SCALE_SMOOTH));   //create a new imageIcon using this pic and the pic is in the project
	JButton n = new JButton(icon); //you assign the icon for the button this way
	n.setActionCommand("newfighter"); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	n.setBounds(980, 620, 290, 72); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	n.setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	n.setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	n.setBorderPainted(false);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	n.addActionListener(listener); // add listener to the button here it is the GameControl class I set it there in the constructor check it
	add(n); //this is how we add c		

	icon = new ImageIcon(new ImageIcon("save.png").getImage().getScaledInstance(62,62,Image.SCALE_SMOOTH));   //create a new imageIcon using this pic and the pic is in the project
	JButton save = new JButton(icon); //you assign the icon for the button this way
	save.setActionCommand("save"); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	save.setBounds(110, 620, 62, 62); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	save.setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	save.setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	save.setBorderPainted(false);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	save.addActionListener(listener); // add listener to the button here it is the GameControl class I set it there in the constructor check it
	add(save); //this is how we add c		

	//		icon = new ImageIcon();   //create a new imageIcon using this pic and the pic is in the project
	icon=new ImageIcon(new ImageIcon("Switch.png").getImage().getScaledInstance(290,72,Image.SCALE_SMOOTH));
	JButton s = new JButton(icon); //you assign the icon for the button this way
	s.setActionCommand("switch"); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
	s.setBounds(980, 540, 290, 72); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
	s.setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
	s.setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
	//		s.setBorderPainted(false);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
	s.addActionListener(listener); // add listener to the button here it is the GameControl class I set it there in the constructor check it
	add(s); //this is how we add c		

	icon = new ImageIcon("zgmre.gif"); 
	jl6 = new JLabel(icon);
	jl6.setBounds(0,0,getWidth(), getHeight());


	fighter = new JLabel((new ImageIcon(new ImageIcon(ac_race.getText()+"99.png").getImage().getScaledInstance(50, 60, Image.SCALE_SMOOTH))));
	fighter.setBounds(300+(9*58)+5, 65+(9*65)+5, 50, 60);
	add(fighter);

	//create a new imageIcon using this pic and the pic is in the project
	buttons = new JButton[10][10];
	for (int i = 0; i < buttons.length; i++) {
	    for (int j = 0; j < buttons.length; j++) {

		buttons[i][j] = new JButton(en);
		buttons[i][j].setActionCommand("Cell "+ i+ " "+j); // for the listeners: we call there in gamecontrol getActionCommand and that represents the identity of the button/label/panel..
		buttons[i][j].setBounds(300+(j*58), 65+(i*65), 52, 62); //the first two parameters represent the x and y of the top left corner and the other two the width and the height
		buttons[i][j].setContentAreaFilled(false); //these three lines for removing the border it may help at first to comment this to adjust the boundaries for 
		buttons[i][j].setFocusPainted(false);      //the button to allow for the consistency of the setbounds fn: you will become familiar 
		buttons[i][j].setBorderPainted(false);     // how it works then when you use it you may become confused because the behavior is changed when it is circular :D
		buttons[i][j].addActionListener(listener);
		buttons[i][j].addMouseListener(listener);
		//				buttons[i][j].addKeyListener(listener); //this is not working because it needs focus on the button trying to fix it
		buttons[i][j].setEnabled(false); //to disable clicking if not a possible move I manually enable the buttons the fighter goes to
		buttons[i][j].setDisabledIcon(dis); // the dark dragon image I edited :D
		//				buttons[i][j].setFocusable(true); //an attempt to fix keyboard listener
		add(buttons[i][j]);

	    }
	}
	//		requestFocusInWindow();
	listener.assignIcon();
	buttons[9][9].setIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
	buttons[9][9].setDisabledIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
	buttons[9][9].setPressedIcon(new ImageIcon(new ImageIcon("empty.jpg").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
	buttons[0][0].setIcon(new ImageIcon(new ImageIcon("Beerus.png").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
	buttons[0][0].setDisabledIcon(new ImageIcon(new ImageIcon("Beerus.png").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));
	buttons[0][0].setPressedIcon(new ImageIcon(new ImageIcon("Beerus.png").getImage().getScaledInstance(52, 62, Image.SCALE_SMOOTH)));

	//below are top labels we need to change those to fix the spacing issue we can make a combined label for all of them and write the statement again and display just that one
	// this is an attempt for my suggestion tell me if you like it or not
	t = new JLabel("Welcome "+playName.getText()+ " to your DragonBall World! Your "+ac_race.getText()+" Fighter "+ fighName.getText()+" is here to save the world!");
	t.setFont(new Font("Serif", Font.BOLD, 25));
	t.setBounds(10,20,1300,40);

	//t = new JLabel("<html><span style='font-size:20px'>"+Welcome "+playName.getText()+ " to your DragonBall World! Your "+ac_race.getText()+" Fighter "+ fighName.getText()+" is here to save the world!"+"</span></html>");


	//t.setFont(Font.decode("Marker Felt-BOLD-30"));
	t.setForeground(new Color(3,3,3));
	t.setBackground(new Color(255,153,18));
	t.setOpaque(true);
	add(t);

	//uncomment the following if you like the original one better
	//		
	//		playName.setBounds(130, 0, 450, 70);
	//		playName.setFont(Font.decode("Marker Felt-BOLD-30"));
	//		playName.setForeground(new Color(94,38,18));
	//		add(playName);
	//
	//		ac_race.setBounds(540, 0, 450, 70);
	//		ac_race.setFont(Font.decode("Marker Felt-BOLD-30"));
	//		ac_race.setForeground(new Color(94,38,18));
	//		add(ac_race);
	//
	//		JLabel ac_name = new JLabel(fighName.getText());
	//		ac_name.setBounds(730, 0, 450, 70);
	//		ac_name.setFont(Font.decode("Marker Felt-BOLD-35"));
	//		ac_name.setForeground(new Color(94,38,18));
	//		add(ac_name);


	sen = new JLabel("0");
	sen.setBounds(200,550,40,50);
	sen.setFont(Font.decode("Marker Felt-BOLD-30"));
	sen.setForeground(new Color(94,38,18));
	add(sen);

	level = new JLabel();
	level.setBounds(110,180,30,30);
	level.setFont(Font.decode("Marker Felt-BOLD-30"));
	level.setForeground(new Color(94,38,18));
	add(level);

	drag = new JLabel("0");
	drag.setBounds(200,325,40,50);
	drag.setFont(Font.decode("Marker Felt-BOLD-30"));
	drag.setForeground(new Color(94,38,18));
	add(drag);

	revalidate();
	repaint();
	//		pack();
	//		setVisible(true);
	//		setResizable(false);



    }
    public static ImageIcon getEn() {
	return en;
    }
    public static void setEn(ImageIcon en) {
	GameGUI.en = en;
    }
    public static ImageIcon getDis() {
	return dis;
    }
    public static void setDis(ImageIcon dis) {
	GameGUI.dis = dis;
    }
    public JLabel getSen() {
	return sen;
    }
    public void setSen(JLabel sen) {
	this.sen = sen;
    }
    public JLabel getDrag() {
	return drag;
    }
    public void setDrag(JLabel drag) {
	this.drag = drag;
    }
    public JButton[][] getButtons() {
	return buttons;
    }
    public void setButtons(JButton[][] buttons) {
	this.buttons = buttons;
    }
    public JTextField getFighName() {
	return fighName;
    }
    public void setFighName(JTextField fighName) {
	this.fighName = fighName;
    }

    public JLabel getMy_name() {
	return ac_race;
    }
    public void setMy_name(JLabel my_name) {
	this.ac_race = my_name;
    }
    public JLabel getBack() {
	return back;
    }
    public void setBack(JLabel back) {
	this.back = back;
    }
    public ButtonGroup getGr() {
	return gr;
    }
    public void setGr(ButtonGroup gr) {
	this.gr = gr;
    }
    public ActionListener getListener() {
	return listener;
    }
    public void setListener(GameControl listener) {
	this.listener = listener;
    }
    public JLabel getAc_race() {
	return ac_race;
    }
    public void setAc_race(JLabel ac_race) {
	this.ac_race = ac_race;
    }

    public JLabel getPlayName() {
	return playName;
    }
    public void setPlayName(JLabel playName) {
	this.playName = playName;
    }
    public JLabel getFighter() {
	return fighter;
    }
    public void setFighter(JLabel fighter) {
	this.fighter = fighter;
    }
    public JLabel getT() {
	return t;
    }
    public void setT(JLabel t) {
	this.t = t;
    }
    public JLabel getLevel() {
	return level;
    }
    public void setLevel(JLabel level) {
	this.level = level;
    }
    public JPanel getOpp() {
	return opp;
    }
    public void setOpp(JPanel opp) {
	this.opp = opp;
    }
    public JPanel getMe() {
	return me;
    }
    public void setMe(JPanel me) {
	this.me = me;
    }
    public JComboBox<String> getJcheck() {
	return jcheck;
    }
    public void setJcheck(JComboBox<String> jc) {
	this.jcheck = jc;
    }
    public JFrame getJ() {
	return j;
    }
    public void setJ(JFrame j) {
	this.j = j;
    }
    public JLabel[] getBattleAtt() {
	return battleAtt;
    }
    public void setBattleAtt(JLabel[] battleAtt) {
	this.battleAtt = battleAtt;
    }
    public BattleEvent getEvent() {
	return event;
    }
    public void setEvent(BattleEvent event) {
	this.event = event;
    }

    public JLabel getFf() {
	return ff;
    }
    public void setFf(JLabel ff) {
	this.ff = ff;
    }
    public Clip getC()
    {
	return c;
    }
    public void setC(Clip c)
    {
	this.c = c;
    }

}
