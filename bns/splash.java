package bns;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

public class splash extends JWindow {
	 public void showSplash() {
	    	JPanel content = (JPanel)getContentPane();
	    	 content.setBackground(Color.white);

	    	 // Set the window's bounds, centering the window
	    	 int width = 700;
	    	 int height =400;
	    	 Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    	 int x = (screen.width-width)/2;
	    	 int y = (screen.height-height)/2;
	    	 setBounds(x,y,width,height);
	    	 // Build the splash screen
	    	 JLabel label = new JLabel(new ImageIcon(getClass().getResource("/bns/front.gif")));
	    	 content.add(label, BorderLayout.CENTER);
	    	 Color oraRed = new Color(12, 20, 20,  255);
	    	 content.setBorder(BorderFactory.createLineBorder(oraRed, 5));

	    	 // Display it
	    	 setVisible(true);

	    	 // Wait a little while, maybe while loading resources
	    	 try { Thread.sleep(2000); } catch (Exception e) {}

	    	 setVisible(false);
	    	 
	    	}
}
