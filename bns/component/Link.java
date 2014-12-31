/*  1:   */ package bns.component;
/*  2:   */ 
/*  3:   */ import bns.BNSParameters;
/*  4:   */ 
/*  5:   */ public class Link
/*  6:   */   extends BNSComponent
/*  7:   */   implements BNSParameters
/*  8:   */ {
/*  9:   */   public int id;
/* 10:   */   public Node src;
/* 11:   */   public Node dst;
/* 12:   */   public int linkType;
/* 13:   */   public int queueType;
/* 14:   */   public float capacity;
/* 15:   */   public int propagationDelay;
/* 16:   */   public int queueSize;
			public int distance;
public int queue[];
/* 17:   */   
/* 18:   */   public Link(int id, Node src, Node dst)
/* 19:   */   {
/* 20:20 */     super(2);
/* 21:21 */     this.id = id;
/* 22:22 */     this.src = src;
/* 23:23 */     this.dst = dst;
/* 24:24 */     this.linkType = 0;
/* 25:25 */     this.queueType = 0;
/* 26:26 */     this.propagationDelay = 20;
/* 27:27 */     this.queueSize = 50;
				this.distance= 20;
/* 28:28 */     this.x = ((int)((src.x + dst.x) / 2.0F));
/* 29:29 */     this.y = ((int)((src.y + dst.y) / 2.0F));
/* 30:   */   }
/* 31:   */ }



