/*   1:    */ package bns.menu;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.FlowLayout;
/*   6:    */ import java.awt.GridLayout;
/*   7:    */ import java.awt.Toolkit;
/*   8:    */ import java.awt.event.ActionEvent;
/*   9:    */ import java.awt.event.ActionListener;
/*  10:    */ import java.awt.event.ItemEvent;
/*  11:    */ import java.awt.event.ItemListener;
/*  12:    */ import java.io.PrintStream;

/*  13:    */ import javax.swing.JButton;
/*  14:    */ import javax.swing.JComboBox;
/*  15:    */ import javax.swing.JDialog;
/*  16:    */ import javax.swing.JFrame;
/*  17:    */ import javax.swing.JLabel;
/*  18:    */ import javax.swing.JPanel;
/*  19:    */ import javax.swing.JTextField;

import bns.BNSParameters;
import bns.component.Agent;
/*  22:    */ 
/*  23:    */ public class AgentMenu
/*  24:    */   extends JDialog
/*  25:    */   implements BNSParameters
/*  26:    */ {
/*  27:    */   
/*  28: 26 */   JButton done = new JButton("Done");
/*  29:    */   Agent target;
/*  30: 29 */   JLabel l1 = new JLabel("Agent type");
/*  31: 30 */   public JComboBox agentType = new JComboBox(new String[] { "TCP", "TCPSink"});
/*  32: 31 */   JLabel l2 = new JLabel("Packet size");
/*  33: 32 */   public JTextField packetSize = new JTextField("1500");
/*  34: 33 */   JLabel l3 = new JLabel("Advertised window");
/*  35: 34 */   public JTextField window = new JTextField("");
/*  36: 35 */   JLabel l4 = new JLabel("Congestion window maximum value");
/*  37: 36 */   public JTextField maxcwnd = new JTextField("");
/*  38: 37 */   JLabel l5 = new JLabel("Congestion window init. value");
/*  39: 38 */   public JTextField windowInit = new JTextField("");
/*  40: 39 */   JLabel l6 = new JLabel("Timeout");
/*  41: 40 */   public JTextField tcpTick = new JTextField("");
/*  42: 41 */   JLabel l7 = new JLabel("Maximum burst");
/*  43: 42 */   public JTextField maxburst = new JTextField("");
/*  44:    */   
/*  45:    */   public AgentMenu(JFrame p)
/*  46:    */   {
/*  47: 45 */     super(p, true);
/*  48: 46 */     setTitle("Agent parameters setup");
/*  49:    */     
/*  50: 48 */     int w = Toolkit.getDefaultToolkit().getScreenSize().width;
/*  51: 49 */     int h = Toolkit.getDefaultToolkit().getScreenSize().height;
/*  52: 50 */     setBounds(w / 2 - 250, h / 2 - 300, 500, 400);
/*  53:    */     
/*  54: 52 */     JPanel panel = new JPanel();
/*  55: 53 */     panel.setLayout(new FlowLayout());
/*  56: 54 */     JPanel innerPanel = new JPanel();
/*  57: 55 */     innerPanel.setLayout(new GridLayout(7, 2));
/*  58: 56 */     innerPanel.add(this.l1);innerPanel.add(this.agentType);
/*  59: 57 */     innerPanel.add(this.l2);innerPanel.add(this.packetSize);
/*  60: 58 */     innerPanel.add(this.l3);innerPanel.add(this.window);
/*  61: 59 */     innerPanel.add(this.l4);innerPanel.add(this.maxcwnd);
/*  62: 60 */     innerPanel.add(this.l5);innerPanel.add(this.windowInit);
/*  63: 61 */     innerPanel.add(this.l6);innerPanel.add(this.tcpTick);
/*  64: 62 */     innerPanel.add(this.l7);innerPanel.add(this.maxburst);
/*  65: 63 */     panel.add(innerPanel);
/*  66: 64 */     getContentPane().add(panel, "Center");
/*  67: 65 */     panel = new JPanel();
/*  68: 66 */     panel.add(this.done);
/*  69: 67 */     getContentPane().add(panel, "South");
/*  70:    */     
/*  71: 69 */     this.done.addActionListener(new ActionListener()
/*  72:    */     {
/*  73:    */       public void actionPerformed(ActionEvent e)
/*  74:    */       {
/*  75: 71 */         switch (AgentMenu.this.agentType.getSelectedIndex())
/*  76:    */         {
/*  77:    */         case 0: 
/*  78: 73 */           AgentMenu.this.target.agentType = 0;
/*  79: 74 */           AgentMenu.this.setupTCP();
/*  80: 75 */           break;
/*  81:    */        
/*  97:    */         case 1: 
/*  98: 93 */           AgentMenu.this.target.agentType = 1;
/*  99: 94 */           AgentMenu.this.setupSink();
/* 100: 95 */           break;
/* 101:    */     
/* 109:    */         default: 
/* 110:105 */           System.err.println("Agent error");
/* 111:    */         }
/* 112:107 */         AgentMenu.this.target = null;
/* 113:108 */         AgentMenu.this.setVisible(false);
/* 114:    */       }
/* 115:111 */     });
/* 116:112 */     this.agentType.addItemListener(new ItemListener()
/* 117:    */     {
/* 118:    */       public void itemStateChanged(ItemEvent e)
/* 119:    */       {
/* 120:114 */         switch (AgentMenu.this.agentType.getSelectedIndex())
/* 121:    */         {
/* 122:    */        
/* 126:    */         case 0: 
/* 127:120 */           AgentMenu.this.switchToTCP();
/* 128:121 */           break;
/* 129:    */         case 1: 
/* 130:123 */           AgentMenu.this.switchToSink();
/* 131:124 */           break;
/* 132:    */         
/* 138:    */         default: 
/* 139:132 */           System.out.println("app type error");
/* 140:133 */           return;
/* 141:    */         }
/* 142:    */       }
/* 143:    */     });
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setTarget(Agent target)
/* 147:    */   {
/* 148:140 */     this.target = target;
/* 149:141 */     this.packetSize.setEditable(true);
/* 150:142 */     switch (target.agentType)
/* 151:    */     {
/* 152:    */     case 0: 
/* 153:144 */       this.agentType.setSelectedIndex(0);
/* 154:145 */       break;
/* 155:    */     
/* 167:    */     case 1: 
/* 168:159 */       this.agentType.setSelectedIndex(1);
/* 169:160 */       break;
/* 170:    */   
/* 176:    */     
/* 185:    */     default: 
/* 186:168 */       System.err.println("addAgent error");
/* 187:    */     }
/* 188:170 */     this.packetSize.setText(String.valueOf(target.packetSize));
/* 189:171 */     this.window.setText(String.valueOf(target.window));
/* 190:172 */     this.maxcwnd.setText(String.valueOf(target.maxcwnd));
/* 191:173 */     this.windowInit.setText(String.valueOf(target.windowInit));
/* 192:174 */     this.tcpTick.setText(String.valueOf(target.tcpTick));
/* 193:175 */     this.maxburst.setText(String.valueOf(target.maxburst));
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setupTCP()
/* 197:    */   {
/* 198:180 */     this.target.packetSize = Integer.parseInt(this.packetSize.getText());
/* 199:181 */     this.target.window = Integer.parseInt(this.window.getText());
/* 200:182 */     this.target.maxcwnd = Integer.parseInt(this.maxcwnd.getText());
/* 201:183 */     this.target.windowInit = Integer.parseInt(this.windowInit.getText());
/* 202:184 */     this.target.tcpTick = Integer.parseInt(this.tcpTick.getText());
/* 203:185 */     this.target.maxburst = Integer.parseInt(this.maxburst.getText());
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setupSink()
/* 207:    */   {
/* 208:188 */     this.target.packetSize = -1;
/* 209:189 */     this.target.window = -1;
/* 210:190 */     this.target.maxcwnd = -1;
/* 211:191 */     this.target.windowInit = -1;
/* 212:192 */     this.target.tcpTick = -1;
/* 213:193 */     this.target.maxburst = -1;
/* 214:    */   }
/* 215:    */   
/* 216:    */   
/* 235:    */   
/* 236:    */   public void switchToTCP()
/* 237:    */   {
/* 238:213 */     this.l1.setVisible(true);
/* 239:214 */     this.agentType.setVisible(true);
/* 240:215 */     this.l2.setVisible(true);
/* 241:216 */     this.packetSize.setVisible(true);
/* 242:217 */     this.l3.setVisible(true);
/* 243:218 */     this.window.setVisible(true);
/* 244:219 */     this.l4.setVisible(true);
/* 245:220 */     this.maxcwnd.setVisible(true);
/* 246:221 */     this.l5.setVisible(true);
/* 247:222 */     this.windowInit.setVisible(true);
/* 248:223 */     this.l6.setVisible(true);
/* 249:224 */     this.tcpTick.setVisible(true);
/* 250:225 */     this.l7.setVisible(true);
/* 251:226 */     this.maxburst.setVisible(true);
/* 252:    */   }
/* 253:    */   
/* 254:    */  
/* 271:    */   
/* 272:    */ 
/* 289:    */   
/* 290:    */   public void switchToSink()
/* 291:    */   {
/* 292:264 */     this.l1.setVisible(true);
/* 293:265 */     this.agentType.setVisible(true);
/* 294:266 */     this.l2.setVisible(false);
/* 295:267 */     this.packetSize.setVisible(false);
/* 296:268 */     this.l3.setVisible(false);
/* 297:269 */     this.window.setVisible(false);
/* 298:270 */     this.l4.setVisible(false);
/* 299:271 */     this.maxcwnd.setVisible(false);
/* 300:272 */     this.l5.setVisible(false);
/* 301:273 */     this.windowInit.setVisible(false);
/* 302:274 */     this.l6.setVisible(false);
/* 303:275 */     this.tcpTick.setVisible(false);
/* 304:276 */     this.l7.setVisible(false);
/* 305:277 */     this.maxburst.setVisible(false);
/* 306:    */   }
/* 307:    */ }



