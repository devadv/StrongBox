package strongbox.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * This class provides methods to create "color-shifts running along a timer"
 * so the text colors in the program can be animated.
 * @version 07-02-2017
 */
public class ColorAnim {

	Timer timerSlow;
	Timer timerFast;
	JLabel label;
	int r;
	
	static long t1;
	static long t2;
	
	/**
	 * Constructor
	 */
	public ColorAnim() {
		
        timerSlow = new Timer(69, null);
     	timerSlow.setInitialDelay(2800);
     	
        timerFast = new Timer(7, null);
     	timerFast.setInitialDelay(27);
     	
        addListenersToTimers();
	}
	
	/**
	 * Set the JLabel associated with these timed color-shifts
	 */
	public void setLabel(JLabel label) {
        this.label = label;
        r = label.getForeground().getRed();
	}
	
	/**
	 * Add some action to the timers.
	 */
    public void addListenersToTimers() {
    	timerSlow.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			fadeToWhite();
    		}
    	});
    	
    	timerFast.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			fadeToWhite();
    		}
    	});
    }
    
    /**
     * The method to be executed repeatedly by the timers so dark gray text
     * can be faded to white.
     */
    public void fadeToWhite() {
    	if (r < 255) {
    		r++;
    		label.setForeground(new Color(r, r, r));
    	}
    	else {
    		label.setText("");
    		label.setForeground(new Color(51, 51, 51));
    		r = 51;
    		timerSlow.stop();
    		timerFast.stop();
    	}
    }
    
    /**
     * Start slow fading. If fast fading is running stop it.
     */
    public void slowFade() {
    	
    	if (timerFast.isRunning()) {
    		timerFast.stop();
    	}

    	label.setForeground(new Color(51, 51, 51));
    	r = label.getForeground().getRed();
    	t1 = System.currentTimeMillis(); // timestamp used by method below
    	timerSlow.restart();
    }
    
    /**
     * Start fast fading. But ONLY if slow fading IS running BUT hasn't run any
     * longer then it's initial delay (2800 ms) from the start.
     */
    public void fastFade() {
    	
    	t2 = System.currentTimeMillis();
    	
    	if (timerSlow.isRunning() && t2 - t1 < 2798) {
    		timerSlow.stop();
    		timerFast.stop();
    		label.setForeground(new Color(51, 51, 51));
    		r = label.getForeground().getRed();
    		timerFast.restart();
    	}
    }
    
}
