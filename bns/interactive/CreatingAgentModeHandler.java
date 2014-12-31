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
import bns.component.BNSComponent;
import bns.component.Node;
import bns.menu.AgentPopMenu;
import bns.panels.AgentPanel;
import bns.tool.Tool;
/*  27:    */ 
/*  28:    */ public class CreatingAgentModeHandler
/*  29:    */   implements MouseListener, MouseMotionListener, BNSParameters
/*  30:    */ {
/*  31:    */   public BNSComponent target;
/*  32:    */   public Node src;
/*  33:    */   public Agent agentSrc;
/*  34:    */   int agentid;
/*  35:    */   int mouseX;
/*  36:    */   int mouseY;
/*  37:    */   SceneManager sm;
/*  38:    */   SceneVirtualizer sv;
/*  39:    */   DataMaintainer dm;
/*  40:    */   AgentPopMenu menu;
/*  41:    */   
/*  42:    */   public CreatingAgentModeHandler(SceneManager sm)
/*  43:    */   {
/*  44: 38 */     this.sm = sm;
/*  45: 39 */     this.sv = sm.sv;
/*  46:    */     
/*  47: 41 */     this.dm = sm.dm;
/*  48: 42 */     this.menu = new AgentPopMenu(sm);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void mouseMoved(MouseEvent e)
/*  52:    */   {
/*  53: 46 */     this.sm.status.setText("Location : ( " + Tool.translateX(this.sv.shiftX, e.getX(), this.sv.scale) + ", " + Tool.translateY(this.sv.shiftY, e.getY(), this.sv.scale) + " )");
/*  54: 47 */     if (this.sm.scroll(e.getX(), e.getY())) {
/*  55: 48 */       return;
/*  56:    */     }
/*  57: 50 */     int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/*  58: 51 */     int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/*  59: 52 */     this.mouseX = X;
/*  60: 53 */     this.mouseY = Y;
/*  61:    */     
/*  62: 55 */     this.target = this.dm.findNode(X, Y);
/*  63: 56 */     if (this.target != null)
/*  64:    */     {
/*  65: 57 */       this.sv.repaint();
/*  66: 58 */       return;
/*  67:    */     }
/*  68: 60 */     this.target = this.dm.findAgent(X, Y);
/*  69:    */     
/*  70: 62 */     this.sm.repaint();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void mousePressed(MouseEvent e)
/*  74:    */   {
/*  75: 66 */     if (SwingUtilities.isRightMouseButton(e))
/*  76:    */     {
/*  77: 67 */       if ((this.target != null) && (this.target.type == 3))
/*  78:    */       {
/*  79: 68 */         this.menu.setTarget((Agent)this.target);
/*  80: 69 */         this.menu.show(this.sv, e.getX(), e.getY());
/*  81:    */       }
/*  82: 71 */       return;
/*  83:    */     }
/*  84: 73 */     int X = (int)(e.getX() / this.sv.scale - this.sv.shiftX);
/*  85: 74 */     int Y = (int)(e.getY() / this.sv.scale - this.sv.shiftY);
/*  86: 75 */     if (SwingUtilities.isLeftMouseButton(e)) {
/*  87: 76 */       if (this.target == null)
/*  88:    */       {
/*  89: 77 */         if (this.src != null)
/*  90:    */         {
/*  91: 78 */           addAgent(this.src, X, Y);
/*  92: 79 */           this.src = null;
/*  93: 80 */           this.sv.repaint();
/*  94:    */         }
/*  95: 82 */         if (this.agentSrc != null)
/*  96:    */         {
/*  97: 83 */           this.agentSrc = null;
/*  98: 84 */           this.sv.repaint();
/*  99:    */         }
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103: 87 */         if (this.target.type == 1)
/* 104:    */         {
/* 105: 88 */           this.src = ((Node)this.target);
/* 106: 89 */           this.agentSrc = null;
/* 107:    */         }
/* 108: 91 */         else if (this.agentSrc == null)
/* 109:    */         {
/* 110: 92 */           this.src = null;
/* 111: 93 */           this.agentSrc = ((Agent)this.target);
/* 112:    */         }
/* 113: 95 */         else if ((this.agentSrc.agentType + ((Agent)this.target).agentType) % 4 == 1)
/* 114:    */         {
/* 115: 96 */           if (this.agentSrc.remoteAgent != null) {
/* 116: 97 */             this.agentSrc.remoteAgent.remoteAgent = null;
/* 117:    */           }
/* 118: 99 */           if (((Agent)this.target).remoteAgent != null) {
/* 119:100 */             ((Agent)this.target).remoteAgent.remoteAgent = null;
/* 120:    */           }
/* 121:102 */           ((Agent)this.target).remoteAgent = this.agentSrc;
/* 122:103 */           this.agentSrc.remoteAgent = ((Agent)this.target);
						this.agentSrc.remoteConnectedNode = (((Agent)this.target).connectednode);
/* 123:104 */           this.agentSrc = null;
/* 124:    */         }
/* 125:    */         else
/* 126:    */         {
/* 127:106 */           this.src = null;
/* 128:107 */           this.agentSrc = ((Agent)this.target);
/* 129:    */         }
/* 130:111 */         this.sv.repaint();
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void draw(Graphics2D g)
/* 136:    */   {
/* 137:117 */     g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 138:    */     
/* 139:119 */     int R = 8;
/* 140:120 */     int Rdiv2 = R / 2;
/* 141:123 */     if (this.target != null)
/* 142:    */     {
/* 143:124 */       g.setColor(TARGET_COLOR);
/* 144:125 */       if (this.target.type == 1)
/* 145:    */       {
/* 146:126 */         R = 30;
/* 147:127 */         Rdiv2 = R / 2;
/* 148:128 */         Ellipse2D shap = new Ellipse2D.Double(this.target.x - Rdiv2, this.target.y - Rdiv2, R, R);
/* 149:129 */         g.draw(shap);
/* 150:130 */         shap = new Ellipse2D.Double(this.target.x - Rdiv2 - 1, this.target.y - Rdiv2 - 1, R + 2, R + 2);
/* 151:131 */         g.draw(shap);
/* 152:    */       }
/* 153:    */       else
/* 154:    */       {
/* 155:133 */         R = 8;
/* 156:134 */         Rdiv2 = R / 2;
/* 157:135 */         Ellipse2D shap = new Ellipse2D.Double(this.target.x - Rdiv2, this.target.y - Rdiv2, R, R);
/* 158:136 */         g.fill(shap);
/* 159:137 */         shap = new Ellipse2D.Double(this.target.x - Rdiv2 - 3, this.target.y - Rdiv2 - 3, R + 5, R + 5);
/* 160:138 */         g.draw(shap);
/* 161:    */       }
/* 162:    */     }
/* 163:141 */     if (this.src != null)
/* 164:    */     {
/* 165:142 */       g.setColor(SRC_COLOR);
/* 166:143 */       R = 30;
/* 167:144 */       Rdiv2 = R / 2;
/* 168:145 */       Ellipse2D shap = new Ellipse2D.Double(this.src.x - Rdiv2, this.src.y - Rdiv2, R, R);
/* 169:146 */       g.draw(shap);
/* 170:147 */       shap = new Ellipse2D.Double(this.src.x - Rdiv2 - 1, this.src.y - Rdiv2 - 1, R + 2, R + 2);
/* 171:148 */       g.draw(shap);
/* 172:    */       
/* 173:150 */       int X1 = this.src.x;
/* 174:151 */       int Y1 = this.src.y;
/* 175:152 */       int X2 = this.mouseX;
/* 176:153 */       int Y2 = this.mouseY;
/* 177:154 */       Line2D.Double line = new Line2D.Double(X1, Y1, X2, Y2);
/* 178:155 */       g.draw(line);
/* 179:    */     }
/* 180:157 */     if (this.agentSrc != null)
/* 181:    */     {
/* 182:158 */       R = 8;
/* 183:159 */       Rdiv2 = R / 2;
/* 184:160 */       g.setColor(SRC_COLOR);
/* 185:161 */       Ellipse2D shap = new Ellipse2D.Double(this.agentSrc.x - Rdiv2, this.agentSrc.y - Rdiv2, R, R);
/* 186:162 */       g.fill(shap);
/* 187:163 */       shap = new Ellipse2D.Double(this.agentSrc.x - Rdiv2 - 3, this.agentSrc.y - Rdiv2 - 3, R + 5, R + 5);
/* 188:164 */       g.draw(shap);
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void addAgent(Node node, int x, int y)
/* 193:    */   {
/* 194:169 */     Agent agent = new Agent(this.agentid, x, y,this.sv.agentPanel.agentType.getSelectedIndex(),node);
/* 195:170 */     switch (this.sv.agentPanel.agentType.getSelectedIndex())
/* 196:    */     {
/* 197:    */     case 0: 
/* 198:172 */       agent.agentType = 0;
/* 199:173 */       if (this.sv.agentPanel.packetSize.getText().equals("")) {
/* 200:174 */         agent.packetSize = -1;
/* 201:    */       } else {
/* 202:176 */         agent.packetSize = Integer.parseInt(this.sv.agentPanel.packetSize.getText());
/* 203:    */       }
/* 204:178 */       break;
/* 205:    */    
/* 237:    */     case 1: 
/* 238:212 */       agent.agentType = 1;
/* 239:213 */       break;
/* 240:    */    
/* 251:    */     default: 
/* 252:226 */       System.err.println("addAgent error");
/* 253:    */     }
/* 254:229 */     agent.attachedNode = node;
/* 255:230 */     this.dm.agents.add(agent);
/* 256:231 */     this.agentid += 1;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void mouseDragged(MouseEvent e) {}
/* 260:    */   
/* 261:    */   public void mouseClicked(MouseEvent e) {}
/* 262:    */   
/* 263:    */   public void mouseEntered(MouseEvent e) {}
/* 264:    */   
/* 265:    */   public void mouseExited(MouseEvent e) {}
/* 266:    */   
/* 267:    */   public void mouseReleased(MouseEvent e) {}
/* 268:    */ }



