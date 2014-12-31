package bns.simulation;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.awt.Dimension;

import javax.swing.*;

import bns.component.Agent;
import bns.component.App;
import bns.component.Link;
import bns.component.Node;
import bns.component.Packet;
import bns.component.Queue;
import bns.component.Ack;
import bns.DataMaintainer;
import bns.SceneManager;
import bns.SceneVirtualizer;

public class Tcp {
	//public ArrayList nodes;
	//public ArrayList links;
	//public ArrayList agents;
	//public ArrayList apps;
	//public ArrayList linkconnection;
	//public ArrayList tempconnect;
	//public static Iterator it;
	public ArrayList packets;
	public ArrayList acks;
	SceneManager sm;
	SceneVirtualizer sv;
	DataMaintainer dm;
	int k;
	int count=0;
	int dest[];
	int chosenPathindex[];
	Link compArray;
	Link link;
	Link currentPath;
	int src[];
	public ArrayList linkseq;
	Link tempLink;
	public ArrayList transferlinks;
	
	
	public Tcp(SceneManager sm){
		this.sm=sm;
		this.sv=sm.sv;
		this.dm = sm.dm;
		this.packets=new ArrayList();
		this.acks=new ArrayList();
		
		//this.nodes = new ArrayList();
		//this.links = new ArrayList();
		//this.agents = new ArrayList();
		//this.apps = new ArrayList();
		
	}
	
	public void connectionEstablish(){
		
			
		Object[] nodes=this.dm.getNodes();
		src=new int[20];
		dest=new int[20];
		for(int i=0;i<this.dm.nodes.size();i++)
		{
		Node p=(Node)nodes[i];		
		
		}
		Object [] agents=this.dm.getAgents();
		int j=0;
		for(int i=0;i<this.dm.agents.size();i++)
		{
			Agent p=(Agent)agents[i];
			
			if(p.type==0 &&p.remoteAgent!=null)
			{
				src[j]=((Node)p.connectednode).id;
				
				dest[j]=((Node)p.remoteConnectedNode).id;
				System.out.println(src[j]+" transfer to "+dest[j]);
				j++;
				
			}
						
		}
		int agentconnections=j-1;
		linkseq=new ArrayList();
		int l=0;
		int k=0;
		Object[] links=this.dm.getLinks();
		/*for(int i=0;i<this.dm.links.size();i++)
			{Link p=(Link)links[i];
		System.out.print(p.id);}*/
		//for(j=0;j<agentconnections;j++)
	//	{
		j=0;
		for(int i=0;i<this.dm.links.size();i++)
		{
			Link srclink=(Link)links[i];
			if(((Node)srclink.src).id==src[j])
			{
				this.linkseq.add(i);
				if(((Node)srclink.dst).id!=dest[j])
				{
					
					while(k<this.dm.links.size())
					{
						tempLink=(Link)links[k];
						//System.out.print(((Node)tempLink.src).id);
						if(((Node)srclink.dst).id==((Node)tempLink.src).id)
						{
							this.linkseq.add(k);
							srclink=(Link)links[k];
							k=0;
		
						}
						else
							k++;
						
						 if(((Node)srclink.dst).id==dest[j])
						{
							 Object[] linkseqprint = this.linkseq.toArray();
							System.out.printf("The Path is ");
							for(int m=0;m<linkseq.size();m++)
								{
									
									System.out.print(linkseqprint[m]);
									tempLink=(Link)links[(int)linkseqprint[m]];
									if(((Node)tempLink.dst).id!=dest[j])
										System.out.printf("->");
								}
							break;
						}
						
				
					}
				}
				else
					System.out.printf("The path is "+srclink.id+"\n");
				if(k>=this.dm.links.size())
				{
					k=0;
					this.linkseq.removeAll(linkseq);
				}
				else
					break;
		}
		}
		}
	
	public void datatransfer(){
		Object[] apptns=this.dm.getApps();    //get all apps which are set
		App app=(App)apptns[0];					//currently using only 1 ftp protocol
		Object[] links=this.dm.getLinks();		//get all links which are used in network
		Object linksequsage[]=this.linkseq.toArray();		//get all links used to send packets
		double timers=0;						//the time required to compare with end time
		int noofpacketsinnetwork=0;				//for computing each packet
		int id=0;				//id of packet
		int exit=0;
		double temptime=0;					//to compare which is the least time
		double totalsimtime=0;				//this tells till when computation is required;
		int noacks=0;						//to know how many new packets to be sent
		int i;							//required for iteration
		Link linkcused;	//to find which link the packet will traverse or currently traversing
		String original="";
		String line="";
		Random randomnum=new Random();
		int ida=0;	//id of acknowledgment
		line+="The path is ";
		
		for(int o=0;o<linkseq.size();o++)
		{
			
			tempLink=(Link)links[(int)linksequsage[o]];
			line+=((Node)tempLink.src).id;
			if(((Node)tempLink.dst).id!=dest[0])
				line+="->";
			else{
				line+="->"+dest[0]+"\n";
				break;
			}
		}
			
		Queue queue[]=new Queue [linkseq.size()];		//queues to be used as buffer over links
		Queue queueack[]=new Queue[linkseq.size()];		//queues for acknowledgment
		for(i=0;i<linkseq.size();i++)
		{
			queue[i]=new Queue(((Link)links[(int)this.linkseq.get(i)]).queueSize); //to intialize with the link's buffersize
			queueack[i]=new Queue(((Link)links[(int)this.linkseq.get(i)]).queueSize);		//same for acknowledgment
		}
		this.packets.removeAll(packets);
		temptime=app.stopTime-app.startTime;			//time of simulation
		totalsimtime=temptime;	//total simulation time
		JFrame log=new JFrame();
		JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 5, 5));
		JTextArea textArea = new JTextArea(50, 50);
		textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);  
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(
	            BorderFactory.createTitledBorder("Log"));
	        scrollPane.setViewportView(textArea);
	        centerPanel.add(scrollPane);
		//scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		textArea.setEditable(false);
		end:while(totalsimtime>timers&&exit!=1)					//loop it till simulation time ends
		{
			temptime=app.stopTime-app.startTime;
			if(this.packets.size()==0&&ida<=0)				//create first packet
			{
				id=0;
				Packet packet = new Packet(id,0,(int)linksequsage[0],timers,0);//if packet then type 0
				this.packets.add(packet);
			}
			
	
			noofpacketsinnetwork=this.packets.size();
	next:		for(i=0;i<noofpacketsinnetwork;i++)
			{
				linkcused=(Link) links[((Packet)this.packets.get(i)).linkno];
			
				
				if(((Packet)this.packets.get(i)).ctime==timers)			//to compute only dose packets whose time is da least to make it synchronised
				{
						//to know which link the packet is currrently on
					//at the source or immediate node and added to queue
					if(((Packet)this.packets.get(i)).inqueue==0 && ((Packet)this.packets.get(i)).type==0&&((Packet)this.packets.get(i)).sent==0)
					{	
						if(((Packet)this.packets.get(i)).queueno!=0)
						((Packet)this.packets.get(i)).queueno+=1;
						queue[((Packet)this.packets.get(i)).queueno].qbuffer[queue[((Packet)this.packets.get(i)).queueno].head]=((Packet)this.packets.get(i)).seqno;
						queue[((Packet)this.packets.get(i)).queueno].head++;
						System.out.printf("packet "+((Packet)this.packets.get(i)).seqno+" added to queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime) +"\n");
						line+="packet "+((Packet)this.packets.get(i)).seqno+" added to queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime) +"\n";
						((Packet)this.packets.get(i)).inqueue=1;
					
						if(queue[((Packet)this.packets.get(i)).queueno].head-queue[((Packet)this.packets.get(i)).queueno].tail>=queue[((Packet)this.packets.get(i)).queueno].bufsize)
						{
							System.out.printf("packet "+((Packet)this.packets.get(i)).seqno+" dropped at queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime) +"\n");
							line+="packet "+((Packet)this.packets.get(i)).seqno+" dropped at queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime) +"\n";
							--queue[((Packet)this.packets.get(i)).queueno].head;
							this.packets.remove(((Packet)this.packets.get(i)));
							noofpacketsinnetwork=this.packets.size();
							if(this.packets.size()!=0)
							continue next;
							else
								break end;
								
						}
					//if((((Packet)this.packets.get(i)).seqno==8||((Packet)this.packets.get(i)).seqno==15||((Packet)this.packets.get(i)).seqno==16||((Packet)this.packets.get(i)).seqno==18||((Packet)this.packets.get(i)).seqno==20||((Packet)this.packets.get(i)).seqno==31||((Packet)this.packets.get(i)).seqno==32||((Packet)this.packets.get(i)).seqno==34||((Packet)this.packets.get(i)).seqno==36||((Packet)this.packets.get(i)).seqno==38||((Packet)this.packets.get(i)).seqno==40||((Packet)this.packets.get(i)).seqno==42||((Packet)this.packets.get(i)).seqno==44||((Packet)this.packets.get(i)).seqno==63||((Packet)this.packets.get(i)).seqno==64||((Packet)this.packets.get(i)).seqno==66||((Packet)this.packets.get(i)).seqno==68))
					//{
				//		continue next;
				//	}
				
					
						
					}
				}
					
					//how to send packets once in queue
				if(((Packet)this.packets.get(i)).ctime==timers)			//to compute only dose packets whose time is da least to make it synchronised
				{
					if(((Packet)this.packets.get(i)).inqueue==1 && queue[((Packet)this.packets.get(i)).queueno].qbuffer[queue[((Packet)this.packets.get(i)).queueno].tail]==((Packet)this.packets.get(i)).seqno &&((Packet)this.packets.get(i)).type==0)
					{							
						queue[((Packet)this.packets.get(i)).queueno].tail++;
						for(int j=0;j<noofpacketsinnetwork;j++)
						{
							if(((Packet)this.packets.get(i)).seqno!=((Packet)this.packets.get(j)).seqno&&((Packet)this.packets.get(j)).type==0&&((Packet)this.packets.get(j)).queueno==((Packet)this.packets.get(i)).queueno&&((Packet)this.packets.get(j)).sent!=1)
							{
								((Packet)this.packets.get(j)).ltime=((Packet)this.packets.get(j)).ctime;
								((Packet)this.packets.get(j)).ctime+=0.000130;
								//System.out.println(((Packet)this.packets.get(j)).seqno);
								//System.out.print(((Packet)this.packets.get(j)).ctime);
								//this.packets.set(j,temppacket);
								//System.out.println(((Packet)this.packets.get(j)).ctime);
							}
						}
							System.out.printf("Packet "+((Packet)this.packets.get(i)).seqno+" dequeued from queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n");
							line+="Packet "+((Packet)this.packets.get(i)).seqno+" dequeued from queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n";
							((Packet)this.packets.get(i)).ltime=((Packet)this.packets.get(i)).ctime;
							((Packet)this.packets.get(i)).ctime+=((double)linkcused.propagationDelay)/1000;
							//System.out.println(((Packet)this.packets.get(i)).ctime);
							((Packet)this.packets.get(i)).sent=1;
							((Packet)this.packets.get(i)).inqueue=0;
					}
				}
					//how to receive packet at destination
				if(((Packet)this.packets.get(i)).ctime==timers)			//to compute only dose packets whose time is da least to make it synchronised
				{
					if(((Packet)this.packets.get(i)).sent==1 && ((Packet)this.packets.get(i)).type==0)
					{
						if(((Node)linkcused.dst).id!=dest[0])
						{
						System.out.printf("The packet "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.dst.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n");
						line+="The packet "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.dst.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n";
						for(int n=0;n<linkseq.size();n++)
						{
							if((int)linksequsage[n]==((Packet)this.packets.get(i)).linkno)
							{
								((Packet)this.packets.get(i)).linkno=(int)linksequsage[n+1];
								linkcused=(Link) links[((Packet)this.packets.get(i)).linkno];
								((Packet)this.packets.get(i)).sent=0;
								break;
							}
							
						}
						
						}
						else	//how to make current packet as an acknowledgment
						{
							System.out.printf("The packet "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.dst.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n");
							line+="The packet "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.dst.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n";
							((Packet)this.packets.get(i)).type=1;
							((Packet)this.packets.get(i)).linkno=(int)linksequsage[linkseq.size()-1];
							((Packet)this.packets.get(i)).seqno=++id;	
							linkcused=(Link)links[((Packet)this.packets.get(i)).linkno];
							((Packet)this.packets.get(i)).inqueue=0;
							((Packet)this.packets.get(i)).sent=0;
							((Packet)this.packets.get(i)).queueno=-1;
						}
					}
				}
					//acknowledgment part starts from here
					
					//acknowledgment added to queue
				if(((Packet)this.packets.get(i)).ctime==timers)			//to compute only dose packets whose time is da least to make it synchronised
				{
					if(((Packet)this.packets.get(i)).inqueue==0 && ((Packet)this.packets.get(i)).type==1 && ((Packet)this.packets.get(i)).sent==0)
					{
						((Packet)this.packets.get(i)).queueno+=1;
						queueack[((Packet)this.packets.get(i)).queueno].qbuffer[queueack[((Packet)this.packets.get(i)).queueno].head]=((Packet)this.packets.get(i)).seqno;
						queueack[((Packet)this.packets.get(i)).queueno].head++;
						System.out.printf("Acknowledgement "+((Packet)this.packets.get(i)).seqno+" added to queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n");
						line+="Acknowledgement "+((Packet)this.packets.get(i)).seqno+" added to queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n";
						((Packet)this.packets.get(i)).inqueue=1;
						
					}
				}
					
					//acknowledgment dequeued
				if(((Packet)this.packets.get(i)).ctime==timers)			//to compute only dose packets whose time is da least to make it synchronised
				{
					if(((Packet)this.packets.get(i)).inqueue==1 && queueack[((Packet)this.packets.get(i)).queueno].qbuffer[queueack[((Packet)this.packets.get(i)).queueno].tail]==((Packet)this.packets.get(i)).seqno &&((Packet)this.packets.get(i)).type==1)
					{
						queueack[((Packet)this.packets.get(i)).queueno].tail++;
						
						for(int j=0;j<noofpacketsinnetwork;j++)
						{
							//Packet temppacket=(Packet) packets[j];
							if(((Packet)this.packets.get(i)).seqno!=((Packet)this.packets.get(j)).seqno &&((Packet)this.packets.get(j)).type==1&&((Packet)this.packets.get(j)).queueno==((Packet)this.packets.get(i)).queueno&&((Packet)this.packets.get(j)).sent!=1)
							{
								((Packet)this.packets.get(j)).ltime=((Packet)this.packets.get(j)).ctime;
								((Packet)this.packets.get(j)).ctime+=0.000130;
								System.out.println(((Packet)this.packets.get(j)).seqno);
								//this.packets.set(j,temppacket);
							//	System.out.println(((Packet)this.packets.get(j)).ctime);
							}
						}
						System.out.printf("Acknowledgment "+((Packet)this.packets.get(i)).seqno+" dequeued from queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n");
						line+="Acknowledgment "+((Packet)this.packets.get(i)).seqno+" dequeued from queue of link "+((Packet)this.packets.get(i)).linkno+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n";
							((Packet)this.packets.get(i)).ltime=((Packet)this.packets.get(i)).ctime;
							//Link templink=(Link)links[lineno];
							((Packet)this.packets.get(i)).ctime+=(((double)linkcused.propagationDelay)/1000);
							((Packet)this.packets.get(i)).sent=1;
							((Packet)this.packets.get(i)).inqueue=0;
					}
				}
					//acknowledgment received
				if(((Packet)this.packets.get(i)).ctime==timers)			//to compute only dose packets whose time is da least to make it synchronised
				{
					if(((Packet)this.packets.get(i)).sent==1 && ((Packet)this.packets.get(i)).type==1)
					{
						if(((Node)linkcused.src).id!=src[0])
						{
						System.out.printf("The acknowledgement "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.src.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n");
						line+="The acknowledgement "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.src.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n";
						for(int n=0;n<linkseq.size();n++)
						{
							if((int)linksequsage[n]==((Packet)this.packets.get(i)).linkno)
							{
								((Packet)this.packets.get(i)).linkno=(int)linksequsage[n-1];
								linkcused=(Link) links[((Packet)this.packets.get(i)).linkno];
								((Packet)this.packets.get(i)).sent=0;
								break;
							}
							
						}
						
						}
						else
						{
							System.out.printf("The acknowledgement "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.src.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n");
							line+="The acknowledgement "+((Packet)this.packets.get(i)).seqno+" received at "+(int)linkcused.src.id+" at time "+(((Packet)this.packets.get(i)).ctime+app.startTime)+"\n";
							//if(id<15){
							Packet packet = new Packet(++id,0,(int)linksequsage[0],((Packet)this.packets.get(i)).ctime,0);//if packet then type 0
							this.packets.add(packet);
						/*	if(id==8||id==15||id==16||id==32||id==65)
							{
								Packet packet2 = new Packet(++id,0,(int)linksequsage[0],((Packet)this.packets.get(i)).ctime,0);//if packet then type 0
								this.packets.add(packet2);
							}*/
							Packet packet1 = new Packet(++id,0,(int)linksequsage[0],((Packet)this.packets.get(i)).ctime,0);//if packet then type 0
							this.packets.add(packet1);//}
						/*	if(id==8||id==15||id==32||id==65)
							{
								Packet packet2 = new Packet(++id,0,(int)linksequsage[0],((Packet)this.packets.get(i)).ctime,0);//if packet then type 0
								this.packets.add(packet2);
							}*/
							
							/*else{
								Packet packet = new Packet(++id,0,(int)linksequsage[0],((Packet)this.packets.get(i)).ctime);//if packet then type 0
							this.packets.add(packet);}*/
							this.packets.remove(((Packet)this.packets.get(i)));
							noofpacketsinnetwork=this.packets.size();
							break next;
							
							
							
						}
						
					}
					
				}
					
				
				
				
			}
			
	
			for(i=0;i<noofpacketsinnetwork;i++)
			{
				if(((Packet)this.packets.get(i)).ctime<temptime)
				{
					timers=((Packet)this.packets.get(i)).ctime;
					
					temptime=((Packet)this.packets.get(i)).ctime;
									
				}
				else if(((Packet)this.packets.get(i)).ctime>totalsimtime)
					exit=1;
				
			}
			

			
		}
		line+="The Connection has been torn down.";
		textArea.append(original + line);
		textArea.setCaretPosition(textArea.getDocument().getLength());
		//log.add(textArea);
		log.add(centerPanel, BorderLayout.CENTER);
		log.setVisible(true);
	}	
		
		
}
