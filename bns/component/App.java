/*  1:   */ package bns.component;
/*  2:   */ 
/*  3:   */ public class App
/*  4:   */   extends BNSComponent
/*  5:   */ {
/*  6:   */   public int id;
/*  7:   */   public Agent agent;
/*  8:   */   public int appType;
/*  9:   */   public int packetSize;
/* 10:   */   public float rate;
/* 11:   */   public String random;
/* 12:   */   public double startTime;
/* 13:   */   public double stopTime;
/* 14:   */   
/* 15:   */   public App(int id, int x, int y, Agent agent)
/* 16:   */   {
/* 17:17 */     super(4);
/* 18:18 */     this.id = id;
/* 19:19 */     this.x = x;
/* 20:20 */     this.y = y;
/* 21:21 */     this.agent = agent;
/* 22:   */   }
/* 23:   */ }



