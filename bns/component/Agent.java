/*  1:   */ package bns.component;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;

import bns.BNSParameters;
import bns.DataMaintainer;
/*  5:   */ 
/*  6:   */ public class Agent
/*  7:   */   extends BNSComponent
/*  8:   */   implements BNSParameters
/*  9:   */ {
/* 10:   */   public int id;
/* 11:   */   public int agentType;
			  public Node connectednode;
/* 12:   */   public Node attachedNode;
/* 13:   */   public Agent remoteAgent;
/* 14:19 */   public int packetSize = -1;
/* 15:20 */   public int window = -1;
/* 16:21 */   public int maxcwnd = -1;
/* 17:22 */   public int windowInit = -1;
/* 18:23 */   public int tcpTick = -1;
/* 19:24 */   public int maxburst = -1;
			  public int type=-1;
			  public Node remoteConnectedNode;
/* 20:   */   
/* 21:   */   public Agent(int id, int x, int y,int agentType,Node connectednode)
/* 22:   */   {
/* 23:28 */     super(3);
/* 24:29 */     this.id = id;
/* 25:30 */     this.x = x;
/* 26:31 */     this.y = y;
				this.type=agentType;
				this.connectednode=connectednode;
				
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static String convertType(int type)
/* 30:   */   {
/* 31:35 */     switch (type)
/* 32:   */     {
/* 33:   */     case 0: 
/* 34:37 */       return "TCP";
/* 35:   */     case 1: 
/* 36:39 */       return "TCPSink";
/* 37:   */    
/* 49:   */     }
/* 50:53 */     System.out.println("Agent convertType error");
/* 51:54 */     return "unknow";
/* 52:   */   }
/* 53:   */ }



