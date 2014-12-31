/*  1:   */ package bns.tool;
/*  2:   */ 
/*  3:   */ import bns.component.Node;
/*  4:   */ 
/*  5:   */ public class Tool
/*  6:   */ {
/*  7:   */   public static double distance(Node s, Node d)
/*  8:   */   {
/*  9: 7 */     int x = s.x - d.x;
/* 10: 8 */     int y = s.y - d.y;
/* 11: 9 */     double dis = Math.sqrt(x * x + y * y);
/* 12:10 */     return dis;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static int translateX(int shiftX, int x, float scale)
/* 16:   */   {
/* 17:14 */     return (int)(x / scale - shiftX);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static int translateY(int shiftY, int y, float scale)
/* 21:   */   {
/* 22:19 */     return (int)(10000.0F - (y / scale - shiftY));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static int translateX(int x)
/* 26:   */   {
/* 27:24 */     return x;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static int translateY(int y)
/* 31:   */   {
/* 32:28 */     return 10000 - y;
/* 33:   */   }
/* 34:   */ }



