package bns.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import bns.BNSParameters;
import bns.SceneVirtualizer;
import bns.simulation.Tcp;
import bns.SceneManager;


public class SimPanel extends JPanel implements BNSParameters {
	JPanel simpanel=new JPanel();
	public JButton startsim = new JButton ("Simulate");
	SceneVirtualizer f;
	SceneManager sm;
	Tcp tcpproto;
	
	
	public SimPanel(SceneVirtualizer f)
	{
		setLayout(new FlowLayout(0, 0, 0));
		this.f = f;
		this.sm=f.sm;
		//this.tcpproto=this.sm.tcpproto;
		
		
		((FlowLayout)getLayout()).setAlignment(0);
		setBackground(BNSParameters.PANEL_COLOR);
	
			this.startsim.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.out.printf("Hi");
				//	this.tcpproto.datatransfer();
				}
			});
			
	//	simpanel.add(this.startsim);
		
	}
	
	

}
