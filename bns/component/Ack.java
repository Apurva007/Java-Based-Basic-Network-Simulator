package bns.component;

public class Ack {
	public int seqno;
	public int src;
	public int dest;
	public double ctime;
	public int linkno;
	public int recv=0;
	public int inqueue=0;
	public double ltime;
	
	public Ack(int id,int link,double ctime){
		
		this.seqno=id;
		this.linkno=linkno;
		//this.dest=dest;
		this.ctime=ctime;
		
	}

}
