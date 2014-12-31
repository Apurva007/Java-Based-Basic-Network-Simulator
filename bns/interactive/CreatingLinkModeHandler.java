/*   1:    */ package bns.interactive;
/*   2:    */ 
/*   3:    */ import java.awt.BasicStroke;
/*   4:    */ import java.awt.Graphics2D;
/*   5:    */ import java.awt.event.MouseEvent;
/*   6:    */ import java.awt.event.MouseListener;
/*   7:    */ import java.awt.event.MouseMotionListener;
/*   8:    */ import java.awt.geom.*;
/*   9:    */// import java.awt.geom.Ellipse2D.Double;
/*  10:    */ //import java.awt.geom.Line2D.Double;
/*  11:    */ import java.util.ArrayList;

/*  12:    */ import javax.swing.JComboBox;
/*  13:    */ import javax.swing.JLabel;
/*  14:    */ import javax.swing.JOptionPane;
/*  15:    */ import javax.swing.JTextField;
/*  16:    */ import javax.swing.SwingUtilities;

import bns.DataMaintainer;
import bns.BNSParameters;
import bns.SceneManager;
import bns.SceneVirtualizer;
import bns.component.Link;
import bns.component.Node;
import bns.menu.LinkPopMenu;
import bns.panels.LinkPanel;
import bns.tool.Tool;
/*  26:    */ 
/*  27:    */ public class CreatingLinkModeHandler
/*  28:    */   implements MouseListener, MouseMotionListener, BNSParameters
/*  29:    */ {
/*  30:    */   public Node target;
/*  31:    */   Link targetLink;
/*  32:    */   public Node src;
/*  33:    */   public Node dst;
/*  34: 29 */   int linkid = 0;
/*  35:    */   LinkPopMenu menu;
/*  36:    */   SceneManager sm;
/*  37:    */   SceneVirtualizer sv;
/*  38:    */   DataMaintainer dm;
/*  39:    */   
/*  40:    */   public CreatingLinkModeHandler(SceneManager sm)
/*  41:    */   {
/*  42: 38 */     this.sm = sm;
/*  43: 39 */     this.dm = sm.dm;
/*  44: 40 */     this.sv = sm.sv;
/*  45: 41 */     this.linkid = 0;
/*  46: 42 */     this.menu = new LinkPopMenu(sm);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void mousePressed(MouseEvent e)
/*  50:    */   {
/*  51: 46 */     if (SwingUtilities.isRightMouseButton(e))
/*  52:    */     {
/*  53: 47 */       if (this.targetLink != null)
/*  54:    */       {
/*  55: 48 */         this.menu.setTarget(this.targetLink);
/*  56: 49 */         this.menu.show(this.sv, e.getX(), e.getY());
/*  57:    */       }
/*  58: 51 */       return;
/*  59:    */     }
/*  60: 53 */     if (SwingUtilities.isLeftMouseButton(e))
/*  61:    */     {
/*  62: 62 */       if (this.target == null)
/*  63:    */       {
/*  64: 63 */         if (this.src != null) {
/*  65: 64 */           this.src = null;
/*  66:    */         }
/*  67:    */       }
/*  68: 67 */       else if (this.src == null)
/*  69:    */       {
/*  70: 68 */         this.src = this.target;
/*  71:    */       }
/*  72: 70 */       else if (this.src == this.target)
/*  73:    */       {
/*  74: 71 */         this.src = null;
/*  75:    */       }
/*  76:    */       else
/*  77:    */       {
/*  78: 73 */         addLink(this.src, this.target);
/*  79: 74 */         this.src = null;
/*  80:    */       }
/*  81: 78 */       this.sv.repaint();
/*  82: 79 */       return;
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void mouseMoved(MouseEvent e)
/*  87:    */   {
/*  88: 84 */     this.sm.status.setText("Location : ( " + Tool.translateX(this.sv.shiftX, e.getX(), this.sv.scale) + ", " + Tool.translateY(this.sv.shiftY, e.getY(), this.sv.scale) + " )");
/*  89: 85 */     if (this.sm.scroll(e.getX(), e.getY())) {
/*  90: 86 */       return;
/*  91:    */     }
/*  92: 88 */     int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/*  93: 89 */     int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/*  94:    */     
/*  95: 91 */     this.target = this.dm.findNode(X, Y);
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103: 99 */     this.targetLink = this.dm.findLink(X, Y);
/* 104:100 */     this.sm.repaint();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void addLink(Node src, Node dst)
/* 108:    */   {
/* 109:104 */     Link link = new Link(this.linkid, src, dst);
/* 110:105 */     if ((this.sv.linkPanel.capacity.getText().equals("")) || 
/* 111:106 */       (this.sv.linkPanel.propagationDelay.getText().equals(""))||(this.sv.linkPanel.distance.getText().equals("")))
/* 112:    */     {
/* 113:107 */       JOptionPane.showMessageDialog(this.sv, "Capacity and propagation delay and Distance are necessary");
/* 114:108 */       return;
/* 115:    */     }
/* 116:110 */     link.capacity = Float.parseFloat(this.sv.linkPanel.capacity.getText());
/* 117:111 */     link.linkType = this.sv.linkPanel.linkType.getSelectedIndex();
/* 118:112 */     link.propagationDelay = Integer.parseInt(this.sv.linkPanel.propagationDelay.getText());
				  link.distance= Integer.parseInt(this.sv.linkPanel.distance.getText());
/* 119:113 */     if (this.sv.linkPanel.queueSize.getText().equals("")) {
/* 120:114 */       link.queueSize = -1;
/* 121:    */     } else {
/* 122:116 */       link.queueSize = Integer.parseInt(this.sv.linkPanel.queueSize.getText());
/* 123:    */     }
/* 124:118 */     link.queueType = this.sv.linkPanel.queueType.getSelectedIndex();
/* 125:119 */     this.dm.links.add(link);
/* 126:120 */     this.linkid += 1;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void draw(Graphics2D g)
/* 130:    */   {
/* 131:126 */     g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 132:    */     
/* 133:128 */     int R = 30;
/* 134:129 */     int Rdiv2 = R / 2;
/* 135:131 */     if (this.target != null)
/* 136:    */     {
/* 137:132 */       g.setColor(TARGET_COLOR);
/* 138:133 */       Ellipse2D shap = new Ellipse2D.Double(this.target.x - Rdiv2, this.target.y - Rdiv2, R, R);
/* 139:134 */       g.draw(shap);
/* 140:135 */       shap = new Ellipse2D.Double(this.target.x - Rdiv2 - 1, this.target.y - Rdiv2 - 1, R + 2, R + 2);
/* 141:136 */       g.draw(shap);
/* 142:    */     }
/* 143:138 */     if (this.src != null)
/* 144:    */     {
/* 145:139 */       g.setColor(SRC_COLOR);
/* 146:140 */       Ellipse2D shap = new Ellipse2D.Double(this.src.x - Rdiv2, this.src.y - Rdiv2, R, R);
/* 147:141 */       g.draw(shap);
/* 148:142 */       shap = new Ellipse2D.Double(this.src.x - Rdiv2 - 1, this.src.y - Rdiv2 - 1, R + 2, R + 2);
/* 149:143 */       g.draw(shap);
/* 150:    */     }
/* 151:159 */     if (this.targetLink != null)
/* 152:    */     {
/* 153:160 */       g.setColor(SRC_COLOR);
/* 154:    */       
/* 155:162 */       int X1 = this.targetLink.src.x;
/* 156:163 */       int Y1 = this.targetLink.src.y;
/* 157:164 */       int X2 = this.targetLink.dst.x;
/* 158:165 */       int Y2 = this.targetLink.dst.y;
/* 159:166 */       Line2D.Double line = new Line2D.Double(X1, Y1, X2, Y2);
/* 160:167 */       g.draw(line);
/* 161:168 */       line = new Line2D.Double(X1, Y1 - 1, X2, Y2 - 1);
/* 162:169 */       g.draw(line);
/* 163:170 */       line = new Line2D.Double(X1, Y1 + 1, X2, Y2 + 1);
/* 164:171 */       g.draw(line);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void mouseClicked(MouseEvent e) {}
/* 169:    */   
/* 170:    */   public void mouseEntered(MouseEvent e) {}
/* 171:    */   
/* 172:    */   public void mouseExited(MouseEvent e) {}
/* 173:    */   
/* 174:    */   public void mouseReleased(MouseEvent e) {}
/* 175:    */   
/* 176:    */   public void mouseDragged(MouseEvent e) {}
/* 177:    */ }





 