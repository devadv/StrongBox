package strongbox.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This class provides methods to create "color-shifts running along a timer"
 * so the text colors in the program can be animated.
 * @version 31-01-2017
 */
public class ColorAnim {

	Timer timer;
	JLabel label;
	int r;
	
	/**
	 * Constructor
	 */
	public ColorAnim() {
        timer = new Timer(69, null);
        addListenerToTimer();
     	timer.setInitialDelay(2800);
	}
	
	/**
	 * Set the JLabel associated with this timer
	 */
	public void setLabel(JLabel label) {
        this.label = label;
        r = label.getForeground().getRed();
	}
	
    public void addListenerToTimer() {
        timer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fadeToWhite();
                }
            }
        );
    }
    
    public void fadeToWhite() {
    	if (r < 255) {
    		r++;
    		label.setForeground(new Color(r, r, r));
    	}
    	else {
    		label.setText("");
    		label.setForeground(new Color(51, 51, 51));
    		r = 51;
    		timer.stop();
    	}
    }
    
    public void startTimer() {
        timer.restart();
    	label.setForeground(new Color(51, 51, 51));
    	r = label.getForeground().getRed();
    }
    
}
