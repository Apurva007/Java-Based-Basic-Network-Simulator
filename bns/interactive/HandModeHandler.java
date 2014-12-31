/*  1:   */ package bns.interactive;
/*  2:   */ 
/*  3:   */ import java.awt.event.MouseEvent;
/*  4:   */ import java.awt.event.MouseListener;
/*  5:   */ import java.awt.event.MouseMotionListener;

/*  6:   */ import javax.swing.JLabel;
/*  7:   */ import javax.swing.SwingUtilities;

import bns.SceneManager;
import bns.SceneVirtualizer;
import bns.tool.Tool;
/* 11:   */ 
/* 12:   */ public class HandModeHandler
/* 13:   */   implements MouseListener, MouseMotionListener
/* 14:   */ {
/* 15:   */   SceneVirtualizer sv;
/* 16:   */   SceneManager sm;
/* 17:   */   int referenceX;
/* 18:   */   int referenceY;
/* 19:   */   
/* 20:   */   public HandModeHandler(SceneManager sm)
/* 21:   */   {
/* 22:22 */     this.sm = sm;
/* 23:23 */     this.sv = sm.sv;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void mouseDragged(MouseEvent e)
/* 27:   */   {
/* 28:27 */     if (SwingUtilities.isLeftMouseButton(e))
/* 29:   */     {
/* 30:29 */       this.sv.shiftX += (int)((e.getX() - this.referenceX) / this.sv.scale);
/* 31:30 */       this.sv.shiftY += (int)((e.getY() - this.referenceY) / this.sv.scale);
/* 32:31 */       this.referenceX = e.getX();
/* 33:32 */       this.referenceY = e.getY();
/* 34:33 */       this.sm.centerX = (this.sv.getWidth() / 2 / this.sv.scale - this.sv.shiftX);
/* 35:34 */       this.sm.centerY = (this.sv.getHeight() / 2 / this.sv.scale - this.sv.shiftY);
/* 36:35 */       this.sm.repaint();
/* 37:36 */       return;
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void mouseMoved(MouseEvent e)
/* 42:   */   {
/* 43:41 */     this.sm.status.setText("Location : ( " + Tool.translateX(this.sv.shiftX, e.getX(), this.sv.scale) + ", " + Tool.translateY(this.sv.shiftY, e.getY(), this.sv.scale) + " )");
/* 44:42 */     if (this.sm.scroll(e.getX(), e.getY())) {
/* 45:43 */       return;
/* 46:   */     }
/* 47:45 */     this.sm.repaint();
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void mouseClicked(MouseEvent arg0) {}
/* 51:   */   
/* 52:   */   public void mouseEntered(MouseEvent arg0) {}
/* 53:   */   
/* 54:   */   public void mouseExited(MouseEvent arg0) {}
/* 55:   */   
/* 56:   */   public void mousePressed(MouseEvent e)
/* 57:   */   {
/* 58:54 */     if (SwingUtilities.isLeftMouseButton(e))
/* 59:   */     {
/* 60:56 */       this.referenceX = e.getX();
/* 61:57 */       this.referenceY = e.getY();
/* 62:58 */       return;
/* 63:   */     }
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void mouseReleased(MouseEvent arg0) {}
/* 67:   */ }





