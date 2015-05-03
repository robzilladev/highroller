package highroller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * A class for displaying a value as an array of spots.
 *
 * Values in the range 1 to 6 use the familiar patterns of a
 * six-sided die; other values display patterns in the same spirit
 */

public class Spots extends JPanel {

    private final int spotData[][][] = {
        {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, // 0
        {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}, // 1
        {{1, 0, 0}, {0, 0, 0}, {0, 0, 1}}, // 2
        {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}, // 3
        {{1, 0, 1}, {0, 0, 0}, {1, 0, 1}}, // 4
        {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}}, // 5
        {{1, 1, 1}, {0, 0, 0}, {1, 1, 1}}, // 6
        {{1, 1, 1}, {0, 1, 0}, {1, 1, 1}}, // 7
        {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}, // 8
        {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}  // 9
    };

    int value;

    /**
     * Creates a new Spots object of default size.  The initial value is
     * 0 (blank).
     */
    public Spots() {
	setPreferredSize(new Dimension(200, 200));
    }

    /**
     * Creates a new Spots object of the specified width and height.
     * The initial value is 0 (blank).
     *
     * @param width the preferred width
     * @param height the preferred height
     */
    public Spots(int width, int height) {
	setPreferredSize(new Dimension(width, height));
    }

    /**
     * Gets the current displayed value.
     *
     * @return the current value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the displayed value.  Valid values are in the range 0 to 9.
     * The display for values outside that range is undefined.
     *
     * @param value the value to display
     */
    public void setValue(int value) {
        if (value != this.value) {
            this.value = value;
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Insets i = getInsets();
        int v = value%10;
	int width = getWidth() - i.left - i.right;
	int height = getHeight() - i.top - i.bottom;
	int size = Math.min(width, height);
	int x0 = i.left + width/2 - size/2;
	int y0 = i.top + height/2 - size/2;
	int dx = size/3;
	int dy = size/3;
	int s = size/4;
        for (int c = 0; c < 3; c++) {
            for (int r = 0; r < 3; r++) {
                if (spotData[v][c][r] == 1) {
                    g.fillOval(x0+c*dx+dx/2-s/2, y0+r*dy+dy/2-s/2, s, s);
                }
            }
        }
    }
}
