/*   1:    */ package bns.interactive;
/*   2:    */ 
/*   3:    */ import java.awt.BasicStroke;
/*   4:    */ import java.awt.Graphics2D;
/*   5:    */ import java.awt.event.MouseEvent;
/*   6:    */ import java.awt.event.MouseListener;
/*   7:    */ import java.awt.event.MouseMotionListener;
/*   8:    */ import java.awt.geom.Ellipse2D;
/*   9:    */ import java.awt.geom.Ellipse2D.Double;
/*  10:    */ import java.util.ArrayList;

/*  11:    */ import javax.swing.JComboBox;
/*  12:    */ import javax.swing.JLabel;
/*  13:    */ import javax.swing.JTextField;
/*  14:    */ import javax.swing.SwingUtilities;

import bns.DataMaintainer;
import bns.BNSParameters;
import bns.SceneManager;
import bns.SceneVirtualizer;
import bns.component.Node;
import bns.component.WiredNode;
import bns.menu.NodePopMenu;
import bns.panels.NodePanel;
import bns.tool.Tool;
/*  24:    */ 
/*  25:    */ public class CreatingNodeModeHandler
/*  26:    */   implements MouseListener, MouseMotionListener, BNSParameters
/*  27:    */ {
/*  28:    */   NodePopMenu menu;
/*  29: 24 */   int nodeid = 0;
/*  30:    */   SceneManager sm;
/*  31:    */   SceneVirtualizer sv;
/*  32:    */   DataMaintainer dm;
/*  33:    */   Node target;
/*  34:    */   
/*  35:    */   public CreatingNodeModeHandler(SceneManager sm)
/*  36:    */   {
/*  37: 31 */     this.sm = sm;
/*  38: 32 */     this.dm = sm.dm;
/*  39: 33 */     this.sv = sm.sv;
/*  40: 34 */     this.nodeid = 0;
/*  41: 35 */     this.menu = new NodePopMenu(sm);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void mouseDragged(MouseEvent e)
/*  45:    */   {
/*  46: 39 */     if (SwingUtilities.isRightMouseButton(e)) {
/*  47: 39 */       return;
/*  48:    */     }
/*  49: 40 */     if (this.target == null) {
/*  50: 40 */       return;
/*  51:    */     }
/*  52: 41 */     this.sm.status.setText("Location : ( " + Tool.translateX(this.sv.shiftX, e.getX(), this.sv.scale) + ", " + Tool.translateY(this.sv.shiftY, e.getY(), this.sv.scale) + " )");
/*  53: 42 */     int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/*  54: 43 */     int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/*  55: 44 */     this.target.x = X;
/*  56: 45 */     this.target.y = Y;
/*  57: 46 */     this.sm.repaint();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void mouseMoved(MouseEvent e)
/*  61:    */   {
/*  62: 51 */     this.sm.status.setText("Location : ( " + Tool.translateX(this.sv.shiftX, e.getX(), this.sv.scale) + ", " + Tool.translateY(this.sv.shiftY, e.getY(), this.sv.scale) + " )");
/*  63: 52 */     if (this.sm.scroll(e.getX(), e.getY())) {
/*  64: 53 */       return;
/*  65:    */     }
/*  66: 55 */     int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/*  67: 56 */     int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/*  68: 57 */     this.target = this.dm.findNode(X, Y);
/*  69: 58 */     this.sm.repaint();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void mousePressed(MouseEvent e)
/*  73:    */   {
/*  74: 62 */     if (SwingUtilities.isRightMouseButton(e))
/*  75:    */     {
/*  76: 63 */       if (this.target != null)
/*  77:    */       {
/*  78: 64 */         this.menu.setTarget(this.target);
/*  79: 65 */         this.menu.show(this.sv, e.getX(), e.getY());
/*  80:    */       }
/*  81: 67 */       return;
/*  82:    */     }
/*  83: 69 */     if (SwingUtilities.isLeftMouseButton(e))
/*  84:    */     {
/*  85: 71 */       if (this.target != null) {
/*  86: 71 */         return;
/*  87:    */       }
/*  88: 74 */       int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/*  89: 75 */       int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/*  90: 76 */       switch (this.sv.nodePanel.type.getSelectedIndex())
/*  91:    */       {
/*  92:    */       case 0: 
/*  93: 78 */         addNode(X, Y);
/*  94: 79 */         break;
/*  95:    */       
/* 113: 95 */       
/* 129:    */       }
/* 130:111 */       this.sv.repaint();
/* 131:112 */       return;
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void draw(Graphics2D g)
/* 136:    */   {
/* 137:116 */     if (this.target != null)
/* 138:    */     {
/* 139:117 */       g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 140:    */       
/* 141:119 */       int R = 30;
/* 142:120 */       int Rdiv2 = R / 2;
/* 143:121 */       g.setColor(TARGET_COLOR);
/* 144:122 */       Ellipse2D shap = new Ellipse2D.Double(this.target.x - Rdiv2, this.target.y - Rdiv2, R, R);
/* 145:123 */       g.draw(shap);
/* 146:124 */       shap = new Ellipse2D.Double(this.target.x - Rdiv2 - 1, this.target.y - Rdiv2 - 1, R + 2, R + 2);
/* 147:125 */       g.draw(shap);
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void addNode(int x, int y)
/* 152:    */   {
/* 153:130 */     this.dm.nodes.add(new WiredNode(this.nodeid, x, y));
/* 154:131 */     this.nodeid += 1;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void mouseClicked(MouseEvent e) {}
/* 158:    */   
/* 159:    */   public void mouseEntered(MouseEvent e) {}
/* 160:    */   
/* 161:    */   public void mouseExited(MouseEvent e) {}
/* 162:    */   
/* 163:    */   public void mouseReleased(MouseEvent e) {}
/* 164:    */ }



