/*  1:   */ package bns.panels;
/*  2:   */ 
/*  3:   */ import java.awt.FlowLayout;

/*  4:   */ import javax.swing.JComboBox;
/*  5:   */ import javax.swing.JLabel;
/*  6:   */ import javax.swing.JPanel;
/*  7:   */ import javax.swing.JTextField;

import bns.BNSParameters;
import bns.SceneVirtualizer;
import bns.component.Link;
/* 11:   */ 
/* 12:   */ public class LinkPanel
/* 13:   */   extends JPanel
/* 14:   */ {
/* 15:   */   
/* 16:17 */   public JComboBox queueType = new JComboBox(new String[] { "DropTail"});
/* 17:18 */   public JComboBox linkType = new JComboBox(new String[] { "duplex-link", "simplex-link" });
/* 18:19 */   public JTextField capacity = new JTextField("100");
/* 19:20 */   public JTextField propagationDelay = new JTextField("10");
/* 20:21 */   public JTextField queueSize = new JTextField("50");
			  public JTextField distance = new JTextField("20");
/* 21:   */   Link link;
/* 22:   */   SceneVirtualizer sv;
/* 23:   */   
/* 24:   */   public LinkPanel(SceneVirtualizer sv)
/* 25:   */   {
/* 26:27 */     setLayout(new FlowLayout(0, 0, 0));
/* 27:28 */     this.sv = sv;
/* 28:29 */     this.capacity.setColumns(4);
/* 29:30 */     this.propagationDelay.setColumns(4);
/* 30:31 */     this.queueSize.setColumns(4);
				this.distance.setColumns(4);
/* 31:   */     
/* 32:33 */     ((FlowLayout)getLayout()).setAlignment(0);
/* 33:34 */     setBackground(BNSParameters.PANEL_COLOR);
/* 34:35 */     add(new JLabel("Link type"));
/* 35:36 */     add(this.linkType);
/* 36:37 */     add(new JLabel("   Queue type"));
/* 37:38 */     add(this.queueType);
/* 38:39 */     add(new JLabel("   Capacity"));
/* 39:40 */     add(this.capacity);
/* 40:41 */     add(new JLabel("Mbps"));
/* 41:42 */     add(new JLabel("   Propagation Delay"));
/* 42:43 */     add(this.propagationDelay);
/* 43:44 */     add(new JLabel("ms"));
/* 44:45 */     add(new JLabel("   Queue Size"));
/* 45:46 */     add(this.queueSize);
				add(new JLabel("   Distance"));
				add(this.distance);
/* 46:   */   }
/* 47:   */ }



