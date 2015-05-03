package highroller;

import javax.swing.JFrame;

/**
 * A starter class for a Director-pattern Swing application program.
 *
 */

public class HighRoller {

    public static void main(String args[]) {
        JFrame window = new JFrame();
        Director director = new Director(window, args);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}
