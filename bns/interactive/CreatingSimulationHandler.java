package bns.interactive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bns.DataMaintainer;
import bns.BNSParameters;
import bns.SceneManager;
import bns.SceneVirtualizer;
import bns.component.Agent;
import bns.component.BNSComponent;
import bns.component.Node;
import bns.menu.AgentPopMenu;
import bns.panels.AgentPanel;
import bns.tool.Tool;
import bns.simulation.Tcp;
import bns.panels.SimPanel;


public class CreatingSimulationHandler  {
	
	SceneManager sm;
	SceneVirtualizer sv;
	DataMaintainer dm;
	Tcp tcpproto;

	
	
	public CreatingSimulationHandler(SceneManager sm)
	{
		this.sm=sm;
		this.sv=sm.sv;
		this.dm=sm.dm;
		this.tcpproto=sm.tcpproto;
	}	
	
	
	
}

