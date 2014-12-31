package bns.component;

import bns.BNSParameters;
import bns.DataMaintainer;

public class Queue implements BNSParameters{
	
	public int head;
	public int qbuffer[];
	public int bufsize;
	public int tail;
	
	public Queue(int bufsize)
	{
		this.head=0;
		this.bufsize=bufsize;
		this.qbuffer=new  int [100000]; 
		this.tail=0;
		
	}

}
