/*   1:    */ package bns;
/*   2:    */ 
/*   3:    */ import java.awt.BasicStroke;
/*   4:    */ import java.awt.Graphics;
/*   5:    */ import java.awt.Graphics2D;
/*   6:    */ import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*   7:    */ import java.awt.event.MouseWheelEvent;
/*   8:    */ import java.awt.event.MouseWheelListener;
import java.awt.geom.*;
/*   9:    */ //import java.awt.geom.Ellipse2D;
/*  10:    */ //import java.awt.geom.Ellipse2D.Double;
/*  11:    */ //import java.awt.geom.Line2D.Double;
/*  12:    */ import java.io.PrintStream;

/*  13:    */ import javax.swing.JButton;
/*  14:    */ import javax.swing.JComponent;
/*  15:    */ import javax.swing.JDesktopPane;
/*  16:    */ import javax.swing.JPanel;
/*  17:    */ import javax.swing.JSlider;

import bns.component.Agent;
import bns.component.App;
import bns.component.Link;
import bns.component.Node;
import bns.interactive.CreatingAgentModeHandler;
import bns.interactive.CreatingAppModeHandler;
import bns.interactive.CreatingLinkModeHandler;
import bns.interactive.CreatingNodeModeHandler;
import bns.interactive.CreatingSimulationHandler;
import bns.interactive.HandModeHandler;
import bns.interactive.NormalModeHandler;
import bns.panels.AgentPanel;
import bns.panels.AppPanel;
import bns.panels.HandModePanel;
import bns.panels.LinkPanel;
import bns.panels.NodePanel;
import bns.panels.NormalModePanel;
import bns.panels.SimPanel;
import bns.tool.Tool;
import bns.simulation.Tcp;


/*  35:    */ 
/*  36:    */ public class SceneVirtualizer
/*  37:    */   extends JComponent
/*  38:    */   implements BNSParameters
/*  39:    */ {
/*  40:    */   
/*  41:    */   public int mainMode;
/*  42:    */   public int shiftX;
/*  43:    */   public int shiftY;
/*  44:    */   public HandModeHandler handModeHandler;
/*  45:    */   public CreatingNodeModeHandler creatingNodeModeHandler;
/*  46:    */   public NormalModeHandler normalModeHandler;
/*  47:    */   public CreatingLinkModeHandler creatingLinkModeHandler;
/*  48:    */   public CreatingAgentModeHandler creatingAgentModeHandler;
/*  49:    */   public CreatingAppModeHandler creatingAppModeHandler;
				public CreatingSimulationHandler creatingSimulationHandler;
/*  50:    */   public JPanel modePanel;
/*  51: 56 */   public LinkPanel linkPanel = new LinkPanel(this);
/*  52: 57 */   public NodePanel nodePanel = new NodePanel(this);
/*  53: 58 */   public AgentPanel agentPanel = new AgentPanel(this);
				public SimPanel simPanel = new SimPanel(this);
/*  54: 59 */   public AppPanel appPanel = new AppPanel(this);
/*  55: 60 */   public NormalModePanel normalModePanel = new NormalModePanel();
/*  56: 61 */   public HandModePanel handModePanel = new HandModePanel();
/*  57: 63 */  
/*  59: 66 */   public float scale = 1.0F;
/*  60:    */   public SceneManager sm;
/*  61:    */   DataMaintainer dm;
				public Tcp tcpproto;
/*  62:    */   
/*  63:    */   public SceneVirtualizer(SceneManager sm)
/*  64:    */   {
/*  65: 70 */     this.sm = sm;
/*  66: 71 */     sm.sv = this;
/*  67: 72 */     this.dm = sm.dm;
this.tcpproto=sm.tcpproto;
			
					
/*  68:    */     
/*  69: 74 */     this.handModeHandler = new HandModeHandler(sm);
/*  70: 75 */     this.creatingNodeModeHandler = new CreatingNodeModeHandler(sm);
/*  71:    */     
/*  72: 77 */     this.creatingLinkModeHandler = new CreatingLinkModeHandler(sm);
/*  73: 78 */     this.creatingAgentModeHandler = new CreatingAgentModeHandler(sm);
/*  74: 79 */     this.creatingAppModeHandler = new CreatingAppModeHandler(sm);
				  this.creatingSimulationHandler=new CreatingSimulationHandler(sm);
/*  75: 81 */     if (sm.sceneMode == 1)
/*  76:    */     {
/*  77: 82 */       this.shiftX = -5000;
/*  78: 83 */       this.shiftY = -5000;
/*  79:    */     }
/*  80:    */     
/*  84: 87 */    switchMode(2);
/*  85:    */     
/*  86: 89 */     
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void switchMode(int mode)
/*  96:    */   {
/*  97: 97 */     this.mainMode = mode;
/*  98: 98 */     if (getMouseListeners().length != 0)
/*  99:    */     {
/* 100: 99 */       removeMouseListener(getMouseListeners()[0]);
/* 101:100 */       removeMouseMotionListener(getMouseMotionListeners()[0]);
/* 102:    */     }
/* 103:102 */     if (this.modePanel != null)
/* 104:    */     {
/* 105:103 */       this.sm.center.remove(this.modePanel);
/* 106:104 */       this.sm.validate();
/* 107:    */     }
/* 108:106 */     this.sm.b1.setBackground(BUTTON_DISABLE_COLOR);
/* 109:107 */     this.sm.b2.setBackground(BUTTON_DISABLE_COLOR);
/* 110:108 */     this.sm.b3.setBackground(BUTTON_DISABLE_COLOR);
/* 111:109 */     this.sm.b4.setBackground(BUTTON_DISABLE_COLOR);
/* 112:110 */     this.sm.b5.setBackground(BUTTON_DISABLE_COLOR);
				  this.sm.b6.setBackground(BUTTON_DISABLE_COLOR);
/* 113:111 */     switch (mode)
/* 114:    */     {
/* 115:    */     case 0: 
/* 116:    */       break;
/* 117:    */     case 1: 
/* 118:118 */       this.sm.b1.setBackground(BUTTON_ENABLE_COLOR);
/* 119:119 */       this.modePanel = this.handModePanel;
/* 120:120 */       addMouseMotionListener(this.handModeHandler);
/* 121:121 */       addMouseListener(this.handModeHandler);
/* 122:122 */       break;
/* 123:    */     case 2: 
/* 124:124 */       this.sm.b2.setBackground(BUTTON_ENABLE_COLOR);
/* 125:125 */       this.modePanel = this.nodePanel;
/* 126:126 */       addMouseMotionListener(this.creatingNodeModeHandler);
/* 127:127 */       addMouseListener(this.creatingNodeModeHandler);
/* 128:128 */       break;
/* 129:    */     case 3: 
/* 130:130 */       this.sm.b4.setBackground(BUTTON_ENABLE_COLOR);
/* 131:    */       
/* 132:132 */       this.modePanel = this.agentPanel;
/* 133:    */       
/* 134:134 */       addMouseMotionListener(this.creatingAgentModeHandler);
/* 135:135 */       addMouseListener(this.creatingAgentModeHandler);
/* 136:136 */       break;
/* 137:    */     case 4: 
/* 138:138 */       this.sm.b5.setBackground(BUTTON_ENABLE_COLOR);
/* 139:    */       
/* 140:140 */       this.modePanel = this.appPanel;
/* 141:141 */       addMouseMotionListener(this.creatingAppModeHandler);
/* 142:142 */       addMouseListener(this.creatingAppModeHandler);
/* 143:143 */       break;
/* 144:    */     case 5: 
/* 145:145 */       this.sm.b3.setBackground(BUTTON_ENABLE_COLOR);
/* 146:146 */       System.out.println(this.sm.b5.getBackground());
/* 147:147 */       this.modePanel = this.linkPanel;
/* 148:148 */       addMouseMotionListener(this.creatingLinkModeHandler);
/* 149:149 */       addMouseListener(this.creatingLinkModeHandler);
/* 150:150 */       break;
				  case 6:
					this.sm.b6.setBackground(BUTTON_ENABLE_COLOR);
					this.modePanel=this.simPanel;
					//this.sm.tcpproto.datatransfer();
					//this.sm.
					
					break;
/* 151:    */     default: 
/* 152:152 */       this.modePanel = this.normalModePanel;
/* 153:    */       
/* 154:    */ 
/* 155:155 */       System.err.println("Mode switching error!!!");
/* 156:    */     }
/* 157:157 */     this.sm.center.add(this.modePanel, "North");
/* 158:158 */     this.sm.validate();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void paintComponent(Graphics g2)
/* 162:    */   {
/* 163:162 */     Graphics2D g = (Graphics2D)g2;
/* 164:163 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 165:164 */     g.setColor(BACKGROUND_COLOR);
/* 166:165 */     g.scale(this.scale, this.scale);
/* 167:166 */     g.fillRect(0, 0, 10000, 10000);
/* 168:    */     
/* 169:168 */     g.translate(this.shiftX, this.shiftY);
/* 170:171 */     if (this.sm.drawGrid) {
/* 171:171 */       drawGrid(g);
/* 172:    */     }
/* 173:172 */     if (this.sm.drawLink) {
/* 174:172 */       drawLinks(g);
/* 175:    */     }
/* 176:173 */     if (this.sm.drawApp) {
/* 177:173 */       drawApp(g);
/* 178:    */     }
/* 179:174 */     if (this.sm.drawAgent) {
/* 180:174 */       drawAgents(g);
/* 181:    */     }
/* 182:175 */   
/* 185:176 */     if (this.sm.drawNode) {
/* 186:176 */       drawNodes(g);
/* 187:    */     }
/* 188:177 */     switch (this.mainMode)
/* 189:    */     {
/* 190:    */     case 0: 
/* 191:    */       break;
/* 192:    */     case 1: 
/* 193:    */       break;
/* 194:    */     case 2: 
/* 195:185 */       this.creatingNodeModeHandler.draw(g);
/* 196:186 */       break;
/* 197:    */     case 3: 
/* 198:188 */       this.creatingAgentModeHandler.draw(g);
/* 199:189 */       break;
/* 200:    */     case 4: 
/* 201:191 */       this.creatingAppModeHandler.draw(g);
/* 202:192 */       break;
/* 203:    */     case 5: 
/* 204:194 */       this.creatingLinkModeHandler.draw(g);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */  
/* 214:    */   
/* 215:    */   private void drawGrid(Graphics2D g)
/* 216:    */   {
/* 217:206 */     g.setColor(GRID_COLOR);
/* 218:207 */     g.drawRect(0, 0, 10000, 10000);
/* 219:208 */     for (int i = 100; i < 10000; i += 100) {
/* 220:209 */       g.drawLine(i, 0, i, 10000);
/* 221:    */     }
/* 222:211 */     for (int i = 100; i < 10000; i += 100) {
/* 223:212 */       g.drawLine(0, i, 10000, i);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   private void drawNodes(Graphics2D g)
/* 228:    */   {
/* 229:218 */     int R = 30;
/* 230:    */     
/* 231:220 */     int Rdiv2 = R / 2;
/* 232:    */     
/* 233:222 */     Object[] nodes = this.dm.getNodes();
/* 234:223 */     g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 235:224 */     g.setColor(NODE_COLOR);
/* 236:225 */     for (int i = 0; i < nodes.length; i++)
/* 237:    */     {
/* 238:226 */       Node p = (Node)nodes[i];
/* 239:227 */       Ellipse2D shap = new Ellipse2D.Double(p.x - Rdiv2, p.y - Rdiv2, R, R);
/* 240:    */       
/* 241:229 */       g.setColor(NODE_COLOR);
/* 242:230 */       g.fill(shap);
/* 243:231 */       g.setColor(NODE_TEXT_COLOR);
/* 244:232 */       if (p.id < 10) {
/* 245:233 */         g.drawString("n" + String.valueOf(p.id), p.x - 6, p.y + 4);
/* 246:234 */       } else if (p.id < 100) {
/* 247:235 */         g.drawString("n" + String.valueOf(p.id), p.x - 10, p.y + 4);
/* 248:    */       } else {
/* 249:237 */         g.drawString("n" + String.valueOf(p.id), p.x - 14, p.y + 4);
/* 250:    */       }
/* 251:239 */      
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   
/* 291:    */   
/* 292:    */   private void drawLinks(Graphics2D g)
/* 293:    */   {
/* 294:278 */     g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 295:279 */     g.setColor(LINK_COLOR);
/* 296:    */     
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:284 */     String type = "";
/* 301:    */     
/* 302:286 */     Object[] links = this.dm.getLinks();
/* 303:287 */     for (int i = 0; i < links.length; i++)
/* 304:    */     {
/* 305:288 */       Link link = (Link)links[i];
/* 306:289 */       int X1 = link.src.x;
/* 307:290 */       int Y1 = link.src.y;
/* 308:291 */       int X2 = link.dst.x;
/* 309:292 */       int Y2 = link.dst.y;
/* 310:293 */       Line2D.Double line = new Line2D.Double(X1, Y1, X2, Y2);
/* 311:294 */       g.draw(line);
/* 312:295 */      if (link.linkType == 0) {
/* 313:296 */         type = "DuplexLink";
/* 314:    */       } else {
/* 315:298 */         type = "SimplexLink [" + link.src.id + "->" + link.dst.id + "]";
/* 316:    */       }
/* 317:300 */       g.drawString(type, (X1 + X2) / 2, (Y1 + Y2) / 2 + 13);
/* 318:301 */       if (this.sm.drawLinkDetail) {
/* 319:302 */        if (link.queueSize != -1) {
/* 320:303 */          g.drawString("capacity:" + link.capacity + " propagationDelay:" + link.propagationDelay + " queueSize:" + link.queueSize + " queueType:" + link.queueType, (X1 + X2) / 2, (Y1 + Y2) / 2 + -11);
/* 321:    */         } else {
/* 322:305 */           g.drawString("capacity:" + link.capacity + " propagationDelay:" + link.propagationDelay + " queueType:" + link.queueType, (X1 + X2) / 2, (Y1 + Y2) / 2 + -11);
/* 323:    */         }
/* 324:    */       }
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   private void drawAgents(Graphics2D g)
/* 329:    */   {
/* 330:313 */     int R = 8;
/* 331:    */     
/* 332:315 */     int Rdiv2 = R / 2;
/* 333:    */     
/* 334:    */ 
/* 335:    */ 
/* 336:319 */     g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 337:    */     
/* 338:321 */     Object[] agents = this.dm.getAgents();
/* 339:322 */     for (int i = 0; i < agents.length; i++)
/* 340:    */     {
/* 341:323 */       Agent a = (Agent)agents[i];
/* 342:324 */       g.setColor(AGENT_COLOR);
/* 343:325 */       Ellipse2D shap = new Ellipse2D.Double(a.x - Rdiv2, a.y - Rdiv2, R, R);
/* 344:326 */       g.fill(shap);
/* 345:327 */       shap = new Ellipse2D.Double(a.x - Rdiv2 - 3, a.y - Rdiv2 - 3, R + 5, R + 5);
/* 346:328 */       g.draw(shap);
/* 347:329 */       switch (a.agentType)
/* 348:    */       {
/* 349:    */       case 0: 
/* 350:    */       //case 4: 
/* 351:    */      // case 8: 
/* 352:    */       //case 12: 
/* 353:    */       //case 16: 
/* 354:335 */         if (a.remoteAgent != null)
/* 355:    */         {
/* 356:336 */           int X1 = a.x;
/* 357:337 */           int Y1 = a.y;
/* 358:338 */           int X2 = a.remoteAgent.x;
/* 359:339 */           int Y2 = a.remoteAgent.y;
/* 360:340 */           g.setColor(AGENT_LINK_COLOR);
/* 361:341 */           Line2D.Double line = new Line2D.Double(X1, Y1, X2, Y2);
/* 362:342 */           g.draw(line);
/* 363:    */         }
/* 364:344 */         g.setColor(AGENT_COLOR);
/* 365:345 */         g.drawString("tcp" + String.valueOf(a.id), a.x - R + 2, a.y - R);
/* 366:346 */         if (this.sm.drawAgentDetail) {
/* 367:347 */           if (a.packetSize != -1) {
/* 368:348 */             g.drawString(Agent.convertType(a.agentType) + " size:" + a.packetSize, a.x - R + 2, a.y - 3 * R);
/* 369:    */           } else {
/* 370:350 */             g.drawString(Agent.convertType(a.agentType) + " size:" + a.packetSize, a.x - R + 2, a.y - 3 * R);
/* 371:    */           }
/* 372:    */         }
/* 373:353 */         break;
/* 374:    */       case 1: 
/* 375:355 */         g.setColor(AGENT_COLOR);
/* 376:356 */         g.drawString("sink" + String.valueOf(a.id), a.x - R + 2, a.y - R);
/* 377:357 */         if (this.sm.drawAgentDetail) {
/* 378:357 */           g.drawString(Agent.convertType(a.agentType), a.x - R + 2, a.y - 3 * R);
/* 379:    */         }
/* 380:358 */         break;
/* 381:    */      
/* 405:    */       }
/* 406:379 */       int X1 = a.x;
/* 407:380 */       int Y1 = a.y;
/* 408:381 */       int X2 = a.attachedNode.x;
/* 409:382 */       int Y2 = a.attachedNode.y;
/* 410:383 */       g.setColor(AGENT_COLOR);
/* 411:384 */       Line2D.Double line = new Line2D.Double(X1, Y1, X2, Y2);
/* 412:385 */       g.draw(line);
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   private void drawApp(Graphics2D g)
/* 417:    */   {
/* 418:391 */     int R = 8;
/* 419:    */     
/* 420:393 */     int Rdiv2 = R / 2;
/* 421:    */     
/* 422:    */ 
/* 423:396 */     g.setStroke(new BasicStroke(1.0F, 1, 2));
/* 424:    */     
/* 425:    */ 
/* 426:399 */     String type = "";
/* 427:400 */     Object[] apps = this.dm.getApps();
/* 428:401 */     for (int i = 0; i < apps.length; i++)
/* 429:    */     {
/* 430:402 */       App a = (App)apps[i];
/* 431:403 */       g.setColor(APP_COLOR);
/* 432:404 */       Ellipse2D shap = new Ellipse2D.Double(a.x - Rdiv2, a.y - Rdiv2, R, R);
/* 433:405 */       g.fill(shap);
/* 434:406 */       switch (a.appType)
/* 435:    */       {
/* 436:    */       case 0: 
/* 437:408 */         type = "ftp";
/* 438:409 */         break;
/* 439:    */    
/* 444:    */       }
/* 445:417 */       g.drawString(type + String.valueOf(a.id), a.x - R + 2, a.y - R);
/* 446:418 */       int X1 = a.x;
/* 447:419 */       int Y1 = a.y;
/* 448:420 */       int X2 = a.agent.x;
/* 449:421 */       int Y2 = a.agent.y;
/* 450:422 */       Line2D.Double line = new Line2D.Double(X1, Y1, X2, Y2);
/* 451:423 */       g.draw(line);
/* 452:    */     }
/* 453:    */   }
/* 454:    */ }


