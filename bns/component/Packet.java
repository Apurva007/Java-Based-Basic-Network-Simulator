package bns.component;



public class Packet {
	
	public int seqno;
	public int src;
	public int dest;
	public double ctime;
	public int linkno;
	public int sent=0;
	public int recv=0;
	public int inqueue=0;
	public double ltime=0.0;
	public int type;
	public int queueno;
	public int ackreceived=0;
	
	public Packet(int id,int type,int linkno,double ctime,int queueno){
		this.seqno=id;
		this.type=type;
		//this.src=src;
		//this.dest=dest;
		this.linkno=linkno;
		this.ctime=ctime;
		this.queueno=queueno;

		
		
	}
	

}
