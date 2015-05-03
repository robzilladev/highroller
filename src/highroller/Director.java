package highroller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

/**
 * A program for rolling dice
 *
 */

public class Director extends JPanel implements ActionListener {
    
    private final String aboutText = "<html>"
	+ "<h2>High Roller</span></h2>"
        + "<hr width=100 align=left>"
	+ "<p>An Interactive Computer Systems Prac Exercise</p>"
        + "<p align=right><span color=gray>From the students of ICS</span></p>"
        + "</html>";

    //private Spots spots;
    
    int numDice, currentDice, direction;
    boolean canChangeDirection = true;
    private JPanel rp;
    private JSpinner spinner = new JSpinner();
    private ArrayList<Spots> spots = new ArrayList<>();
    JPanel p = new JPanel();
    JFrame frame = new JFrame();
    JButton stepButton = new JButton("Step up");
    Timer timer;

    /**
     * Creates a Director to run the program
     *
     * @param window the window in which the program is visible
     * @args the command line arguments to the program
     */
    public Director(JFrame window, String args[]) {
        JMenuBar menus = new JMenuBar();
        frame = window;
        currentDice = 1;
        direction = 1;
        
        for (int i = 0; i<9; i++)
        {
            spots.add(new Spots(200,200));
            
            spots.get(i).setBorder(BorderFactory.createEtchedBorder());
        }
        
        spinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 9, 1));
        spinner.setMaximumSize(new java.awt.Dimension(50, 25));
        spinner.setMinimumSize(new java.awt.Dimension(50, 25));
        spinner.setPreferredSize(new java.awt.Dimension(50, 25));
        spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerStateChanged(evt);
            }
        });
        
        window.setJMenuBar(menus);
        menus.add(makeFileMenu());
        menus.add(makeDirectionMenu());
        menus.add(makeThemeMenu());

        window.getContentPane().add(makeSpots(), BorderLayout.CENTER);
        window.getContentPane().add(makeSidePanel(), BorderLayout.WEST);
        
        timer = new Timer(50, timerListener);
        timer.setInitialDelay(0);
        
        rollAgain();
    }

    /**
     * Rolls the dice
     */
    public void rollAgain() {
        for (Spots s: spots)
        {
            int v = 1 + (int)(Math.random() * 6);
            s.setValue(v);
        }
        upMenu.setEnabled(true);
        downMenu.setEnabled(true);
        currentDice = 1;
        
    }

    /**
     * Displays the "About ..." window
     */
    public void showAbout() {
	JOptionPane.showMessageDialog(this, aboutText);
    }

    /**
     * Exits the program
     */
    public void quit() {
	System.exit(0);
    }

    public void actionPerformed(ActionEvent e) {
	String command = e.getActionCommand();
	if (command.equals("Quit")) {
	    quit();
	} else if (command.equals("About...")) {
	    showAbout();
	}
    }

    private JComponent makeSpots() {
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        numDice = (int) spinner.getValue();
        
        for (int i = 0; i<numDice; i++)
        {
            p.add(spots.get(i));
        }
        if (numDice <=3)
            p.setLayout(new GridLayout(0, numDice, 0, 0));
        else
            p.setLayout(new GridLayout(0, 3, 0, 0));
        
        return p;
    }
    
    private void step()
    {
        Color s = new Color(191,188,245);
        canChangeDirection = false;
        upMenu.setEnabled(false);
        downMenu.setEnabled(false);
        
//        for (Spots sp: spots)
//        {
//            sp.setBackground(Color.white);
//        }
        
        //spots.get(currentDice-1).setBackground(s);
        
        if (direction > 0)
        {
            if (spots.get(currentDice-1).getValue() < 6)
            {
                spots.get(currentDice-1).setValue(spots.get(currentDice-1).getValue()+1);
            }
            else
            {
                spots.get(currentDice-1).setValue(1 + (int) (Math.random() * 6));
                if (currentDice < numDice)
                    currentDice++;
                else
                    currentDice=1;
            }
        }
        else
        {
            if (spots.get(currentDice-1).getValue() > 1)
                spots.get(currentDice-1).setValue(spots.get(currentDice-1).getValue()-1);
            else
            {
                spots.get(currentDice-1).setValue(1 + (int) (Math.random() * 6));
                if (currentDice < numDice)
                    currentDice++;
                else
                    currentDice=1;
            }
        }
    }

    private JComponent makeFileMenu() {
	JMenu m = new JMenu("File");
	m.setMnemonic(KeyEvent.VK_F);

	JMenuItem about = new JMenuItem("About...");
	about.setMnemonic(KeyEvent.VK_B);
	about.addActionListener(this);

	JMenuItem quit = new JMenuItem("Quit");
	quit.setMnemonic(KeyEvent.VK_Q);
	quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
	quit.addActionListener(this);

	m.add(about);
	m.addSeparator();
	m.add(quit);
	return m;
    }
    
    ActionListener timerListener = new ActionListener() {
        int count = 10;
        public void actionPerformed(ActionEvent event) 
        {
            if (count > 0)
            {
                rollAgain();
                count--;
            }
            else
            {
                timer.stop();
                count=5;
            }
        }
    };
    
    JCheckBoxMenuItem upMenu;
    JCheckBoxMenuItem downMenu;
    
    private JComponent makeDirectionMenu() {
	JMenu m = new JMenu("Direction");
	m.setMnemonic(KeyEvent.VK_D);

	upMenu = new JCheckBoxMenuItem("Up");
	upMenu.setMnemonic(KeyEvent.VK_U);
        upMenu.setSelected(true);
	upMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                downMenu.setSelected(false);
                stepButton.setText("Step up");
                direction = 1;
            }
        });

	downMenu = new JCheckBoxMenuItem("Down");
	downMenu.setMnemonic(KeyEvent.VK_U);
	downMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                upMenu.setSelected(false);
                stepButton.setText("Step down");
                direction = -1;
            }
        });

	m.add(upMenu);
	m.add(downMenu);
	return m;
    }
    
    ActionListener themeListener = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (command.equalsIgnoreCase("Classic")) {
                for (int i = 0; i < 9; i++) {
                    spots.get(i).setBackground(Color.white);
                    spots.get(i).setForeground(Color.black);
                    repaint();
                }
            } else if (command.equalsIgnoreCase("Metal")) {
                for (int i = 0; i < 9; i++) {
                    spots.get(i).setBackground(Color.LIGHT_GRAY);
                    spots.get(i).setForeground(Color.DARK_GRAY);
                }
            } else if (command.equalsIgnoreCase("Tiger")) {
                for (int i = 0; i < 9; i++) {
                    spots.get(i).setBackground(Color.yellow);
                    spots.get(i).setForeground(new Color(148,116,21));
                }
            } else if (command.equalsIgnoreCase("Blue")) {
                for (int i = 0; i < 9; i++) {
                    spots.get(i).setBackground(Color.blue);
                    spots.get(i).setForeground(Color.red);
                }
            } else if (command.equalsIgnoreCase("Red")) {
                for (int i = 0; i < 9; i++) {
                    spots.get(i).setForeground(Color.black);
                    spots.get(i).setBackground(Color.red);
                }
            } else if (command.equalsIgnoreCase("Random")) {
                for (int i = 0; i < 9; i++) {
                    Color colour1 = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
                    Color colour2 = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
                    spots.get(i).setForeground(colour1);
                    spots.get(i).setBackground(colour2);
                }
                p.revalidate();
            }
        }
    };
    
    private JComponent makeThemeMenu() {
        JMenu theme = new JMenu("Themes");
        theme.setMnemonic(KeyEvent.VK_T);

        JMenuItem classic = new JMenuItem("Classic");
        classic.setMnemonic(KeyEvent.VK_C);
        classic.addActionListener(themeListener);

        JMenuItem metal = new JMenuItem("Metal");
        metal.setMnemonic(KeyEvent.VK_M);
        metal.addActionListener(themeListener);
        
        JMenuItem tiger = new JMenuItem("Tiger");
        tiger.setMnemonic(KeyEvent.VK_G);
        tiger.addActionListener(themeListener);

        JMenuItem blue = new JMenuItem("Blue");
        blue.setMnemonic(KeyEvent.VK_B);
        blue.addActionListener(themeListener);

        JMenuItem red = new JMenuItem("Red");
        red.setMnemonic(KeyEvent.VK_R);
        red.addActionListener(themeListener);

        JMenuItem random = new JMenuItem("Random");
        random.setMnemonic(KeyEvent.VK_D);
        random.addActionListener(themeListener);

        theme.add(classic);
        theme.add(metal);
        theme.add(tiger);
        theme.add(blue);
        theme.add(red);
        theme.add(random);
        return theme;
    }
    
    private JComponent makeSidePanel() {
        JPanel sidePanel = new JPanel();
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel outerTextPanel = new JPanel();
        JPanel outerButtonPanel = new JPanel();
        JPanel spinnerPanel = new JPanel();

        sidePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.setLayout(new GridLayout(3, 10, 1, 10));
        textPanel.setLayout(new GridLayout(1, 0, 1, 0));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        outerTextPanel.setLayout(new BorderLayout());
        outerButtonPanel.setLayout(new BorderLayout());

        JLabel text = new JLabel("<html><font size = 5>High Roller<br><br><font size = 4>Today might be<br>your lucky day!<html>");
        
        JButton rollAgainButton = new JButton("RollAgain");
        
        stepButton.setBackground(new Color(57,58,90));
        rollAgainButton.setBackground(new Color(21,21,38));
        stepButton.setForeground(Color.white);
        rollAgainButton.setForeground(Color.white);
        stepButton.setBorderPainted(false);
        rollAgainButton.setBorderPainted(false);
        
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                step();
            }
        });

        rollAgainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });

        JLabel diceLabel = new JLabel("Dice: ");
        spinnerPanel.setLayout(new GridLayout(1, 10, 2, 10));
        spinnerPanel.add(diceLabel);
        spinnerPanel.add(spinner);

        textPanel.add(text);
        buttonPanel.add(spinnerPanel);
        buttonPanel.add(stepButton);
        buttonPanel.add(rollAgainButton);

        outerButtonPanel.add(buttonPanel, BorderLayout.SOUTH);
        outerTextPanel.add(textPanel, BorderLayout.NORTH);

        sidePanel.add(outerTextPanel);
        JComponent glue = (JComponent) Box.createVerticalGlue();
        sidePanel.add(glue);
        sidePanel.add(outerButtonPanel);
        sidePanel.setPreferredSize(new Dimension(150,100));

        return sidePanel;
    }
    
    
    private void spinnerStateChanged(javax.swing.event.ChangeEvent evt) {
        numDice = (int) spinner.getValue();
        p.removeAll();
        JPanel panel = new JPanel();
        
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < numDice; i++) {
            p.add(spots.get(i));
        }
        
        if (numDice <=3)
            p.setLayout(new GridLayout(0, numDice, 0, 0));
        else
            p.setLayout(new GridLayout(0, 3, 10, 10));
        
        //frame.add(panel);
        p.revalidate();
        p.repaint();
    }
}

