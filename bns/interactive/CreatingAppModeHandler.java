/*   1:    */ package bns.interactive;
/*   2:    */ 
/*   3:    */ import java.awt.BasicStroke;
/*   4:    */ import java.awt.Graphics2D;
/*   5:    */ import java.awt.event.MouseEvent;
/*   6:    */ import java.awt.event.MouseListener;
/*   7:    */ import java.awt.event.MouseMotionListener;
/*   8:    */ import java.awt.geom.*;
/*   9:    */ //import java.awt.geom.Ellipse2D.Double;
/*  10:    */ //import java.awt.geom.Line2D.Double;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.util.ArrayList;

/*  13:    */ import javax.swing.JComboBox;
/*  14:    */ import javax.swing.JLabel;
/*  15:    */ import javax.swing.JTextField;
/*  16:    */ import javax.swing.SwingUtilities;

import bns.DataMaintainer;
import bns.BNSParameters;
import bns.SceneManager;
import bns.SceneVirtualizer;
import bns.component.Agent;
import bns.component.App;
import bns.menu.AppPopMenu;
import bns.panels.AppPanel;
import bns.tool.Tool;
/*  26:    */ 
/*  27:    */ public class CreatingAppModeHandler
/*  28:    */   implements MouseListener, MouseMotionListener, BNSParameters
/*  29:    */ {
/*  30:    */   public Agent target;
/*  31:    */   public App appTarget;
/*  32:    */   public Agent src;
/*  33:    */   int mouseX;
/*  34:    */   int mouseY;
/*  35: 28 */   int appid = 0;
/*  36:    */   AppPopMenu menu;
/*  37:    */   SceneManager sm;
/*  38:    */   SceneVirtualizer sv;
/*  39:    */   DataMaintainer dm;
/*  40:    */   
/*  41:    */   public CreatingAppModeHandler(SceneManager sm)
/*  42:    */   {
/*  43: 37 */     this.sm = sm;
/*  44: 38 */     this.sv = sm.sv;
/*  45: 39 */     this.dm = sm.dm;
/*  46: 40 */     this.appid = 0;
/*  47: 41 */     this.menu = new AppPopMenu(sm);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void mouseDragged(MouseEvent arg0) {}
/*  51:    */   
/*  52:    */   public void mouseClicked(MouseEvent arg0) {}
/*  53:    */   
/*  54:    */   public void mouseEntered(MouseEvent arg0) {}
/*  55:    */   
/*  56:    */   public void mouseExited(MouseEvent arg0) {}
/*  57:    */   
/*  58:    */   public void mouseReleased(MouseEvent arg0) {}
/*  59:    */   
/*  60:    */   public void mouseMoved(MouseEvent e)
/*  61:    */   {
/*  62: 54 */     this.sm.status.setText("Location : ( " + Tool.translateX(this.sv.shiftX, e.getX(), this.sv.scale) + ", " + Tool.translateY(this.sv.shiftY, e.getY(), this.sv.scale) + " )");
/*  63: 55 */     if (this.sm.scroll(e.getX(), e.getY())) {
/*  64: 56 */       return;
/*  65:    */     }
/*  66: 59 */     int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/*  67: 60 */     int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/*  68: 61 */     this.mouseX = X;
/*  69: 62 */     this.mouseY = Y;
/*  70:    */     
/*  71: 64 */     this.target = this.dm.findAgent(X, Y);
/*  72: 65 */     if ((this.target != null) && (this.target.agentType % 2 == 1)) {
/*  73: 66 */       this.target = null;
/*  74:    */     }
/*  75: 68 */     if (this.target != null)
/*  76:    */     {
/*  77: 69 */       Object[] apps = this.dm.getApps();
/*  78: 71 */       for (int j = 0; j < apps.length; j++)
/*  79:    */       {
/*  80: 72 */         App app = (App)apps[j];
/*  81: 73 */         if (app.agent == this.target)
/*  82:    */         {
/*  83: 74 */           this.target = null;
/*  84: 75 */           break;
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88: 79 */     if (this.target == null) {
/*  89: 80 */       this.appTarget = this.dm.findApp(X, Y);
/*  90:    */     }
/*  91: 82 */     this.sm.repaint();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void mousePressed(MouseEvent e)
/*  95:    */   {
/*  96: 85 */     if (SwingUtilities.isRightMouseButton(e))
/*  97:    */     {
/*  98: 86 */       if (this.appTarget != null)
/*  99:    */       {
/* 100: 87 */         this.menu.setTarget(this.appTarget);
/* 101: 88 */         this.menu.show(this.sv, e.getX(), e.getY());
/* 102:    */       }
/* 103: 90 */       return;
/* 104:    */     }
/* 105: 92 */     int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/* 106: 93 */     int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/* 107: 94 */     if (SwingUtilities.isLeftMouseButton(e))
/* 108:    */     {
/* 109: 95 */       if (this.target != null)
/* 110:    */       {
/* 111: 96 */         this.src = this.target;
/* 112:    */       }
/* 113: 97 */       else if (this.src != null)
/* 114:    */       {
/* 115: 98 */         addApp(X, Y, this.src);
/* 116: 99 */         this.src = null;
/* 117:    */       }
/* 118:101 */       this.sv.repaint();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void addApp(int x, int y, Agent agent)
/* 123:    */   {
/* 124:106 */     App app = new App(this.appid, x, y, agent);
/* 125:107 */     switch (this.sv.appPanel.appType.getSelectedIndex())
/* 126:    */     {
/* 127:    */     case 0: 
/* 128:109 */       app.appType = 0;
/* 129:110 */       app.startTime = Float.parseFloat(this.sv.appPanel.startTime.getText());
/* 130:111 */       app.stopTime = Float.parseFloat(this.sv.appPanel.stopTime.getText());
/* 131:112 */       app.packetSize = Integer.parseInt(this.sv.appPanel.packetSize.getText());
/* 132:    */       
/* 133:114 */       break;
/* 134:    */     
/* 152:    */     default: 
/* 153:134 */       System.out.println("addApp error");
/* 154:135 */       return;
/* 155:    */     }
/* 156:137 */     this.dm.apps.add(app);
/* 157:138 */     this.appid += 1;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void draw(Graphics2D g)
/* 161:    */   {
/* 162:143 */     g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 163:144 */     int R = 8;
/* 164:145 */     int Rdiv2 = R / 2;
/* 165:    */     
/* 166:147 */     g.setColor(TARGET_COLOR);
/* 167:148 */     if (this.target != null)
/* 168:    */     {
/* 169:149 */       Ellipse2D shap = new Ellipse2D.Double(this.target.x - Rdiv2, this.target.y - Rdiv2, R, R);
/* 170:150 */       g.fill(shap);
/* 171:151 */       shap = new Ellipse2D.Double(this.target.x - Rdiv2 - 3, this.target.y - Rdiv2 - 3, R + 5, R + 5);
/* 172:152 */       g.draw(shap);
/* 173:    */     }
/* 174:154 */     if (this.appTarget != null)
/* 175:    */     {
/* 176:156 */       Ellipse2D shap = new Ellipse2D.Double(this.appTarget.x - Rdiv2, this.appTarget.y - Rdiv2, R, R);
/* 177:157 */       g.fill(shap);
/* 178:    */     }
/* 179:159 */     g.setColor(SRC_COLOR);
/* 180:160 */     if (this.src != null)
/* 181:    */     {
/* 182:161 */       Ellipse2D shap = new Ellipse2D.Double(this.src.x - Rdiv2, this.src.y - Rdiv2, R, R);
/* 183:162 */       g.fill(shap);
/* 184:163 */       shap = new Ellipse2D.Double(this.src.x - Rdiv2 - 3, this.src.y - Rdiv2 - 3, R + 5, R + 5);
/* 185:164 */       g.draw(shap);
/* 186:    */       
/* 187:166 */       int X1 = this.src.x;
/* 188:167 */       int Y1 = this.src.y;
/* 189:168 */       int X2 = this.mouseX;
/* 190:169 */       int Y2 = this.mouseY;
/* 191:170 */       Line2D.Double line = new Line2D.Double(X1, Y1, X2, Y2);
/* 192:171 */       g.draw(line);
/* 193:    */     }
/* 194:    */   }
/* 195:    */ }




 