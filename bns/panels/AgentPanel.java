/*  1:   */ package bns.panels;
/*  2:   */ 
/*  3:   */ import java.awt.FlowLayout;

/*  4:   */ import javax.swing.JComboBox;
/*  5:   */ import javax.swing.JLabel;
/*  6:   */ import javax.swing.JPanel;
/*  7:   */ import javax.swing.JTextField;

import bns.BNSParameters;
import bns.SceneVirtualizer;
/* 10:   */ 
/* 11:   */ public class AgentPanel
/* 12:   */   extends JPanel
/* 13:   */   implements BNSParameters
/* 14:   */ {
/* 15:   */   
/* 16:16 */   public JComboBox agentType = new JComboBox(new String[] { "TCP","TCPSink"});
/* 17:17 */   public JTextField packetSize = new JTextField("1500", 4);
/* 18:18 */   JLabel l1 = new JLabel("Agent type");
/* 19:19 */   JLabel l2 = new JLabel("Packet size");
/* 20:20 */   JLabel l3 = new JLabel("bytes  ");
/* 21:   */   SceneVirtualizer f;
/* 22:   */   
/* 23:   */   public AgentPanel(SceneVirtualizer f)
/* 24:   */   {
/* 25:24 */     setLayout(new FlowLayout(0, 0, 0));
/* 26:25 */     this.f = f;
/* 27:26 */     ((FlowLayout)getLayout()).setAlignment(0);
/* 28:27 */     setBackground(BNSParameters.PANEL_COLOR);
/* 29:28 */     add(this.l1);
/* 30:29 */     add(this.agentType);
/* 31:30 */     add(this.l2);
/* 32:31 */     add(this.packetSize);
/* 33:32 */     add(this.l3);
/* 34:   */   }
/* 35:   */ }



