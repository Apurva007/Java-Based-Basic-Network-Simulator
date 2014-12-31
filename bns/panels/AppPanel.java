/*  1:   */ package bns.panels;
/*  2:   */ 
/*  3:   */ import java.awt.FlowLayout;
/*  4:   */ import java.awt.event.ItemEvent;
/*  5:   */ import java.awt.event.ItemListener;
/*  6:   */ import java.io.PrintStream;

/*  7:   */ import javax.swing.JComboBox;
/*  8:   */ import javax.swing.JLabel;
/*  9:   */ import javax.swing.JPanel;
/* 10:   */ import javax.swing.JTextField;

import bns.BNSParameters;
import bns.SceneVirtualizer;
/* 13:   */ 
/* 14:   */ public class AppPanel
/* 15:   */   extends JPanel
/* 16:   */   implements BNSParameters
/* 17:   */ {
/* 18:   */   
/* 19:19 */   JLabel l1 = new JLabel("Aplication type");
/* 20:20 */   public JComboBox appType = new JComboBox(new String[] { "FTP"});
/* 21:21 */   JLabel l2 = new JLabel("   Start time");
/* 22:22 */   public JTextField startTime = new JTextField("1", 4);
/* 23:23 */   JLabel l3 = new JLabel("   Stop time");
/* 24:24 */   public JTextField stopTime = new JTextField("2", 4);
/* 25:27 */   JLabel l4 = new JLabel("   Packet size");
/* 26:28 */  public JTextField packetSize = new JTextField("1000", 4);
/* 27:29 */   
/* 37:   */   SceneVirtualizer sv;
/* 38:   */   
/* 39:   */   public AppPanel(SceneVirtualizer f)
/* 40:   */   {
/* 41:47 */     setLayout(new FlowLayout(0, 0, 0));
/* 42:48 */     this.sv = f;
/* 43:49 */     ((FlowLayout)getLayout()).setAlignment(0);
/* 44:50 */     setBackground(BNSParameters.PANEL_COLOR);
/* 45:51 */     add(this.l1);
/* 46:52 */     add(this.appType);
/* 47:53 */     add(this.l2);
/* 48:54 */     add(this.startTime);
/* 49:55 */     add(this.l3);
/* 50:56 */     add(this.stopTime);
/* 51:57 */     add(this.l4);
/* 52:58 */     add(this.packetSize);
/* 53:59 */    
/* 65:71 */     this.l4.setVisible(false);
/* 66:72 */     this.packetSize.setVisible(false);
/* 67:73 */    
/* 79:85 */     this.appType.addItemListener(new ItemListener()
/* 80:   */     {
/* 81:   */       public void itemStateChanged(ItemEvent e)
/* 82:   */       {
/* 83:87 */         AppPanel.this.l3.setVisible(false);
/* 84:88 */         AppPanel.this.stopTime.setVisible(false);
/* 85:89 */         AppPanel.this.l4.setVisible(false);
/* 86:90 */         AppPanel.this.packetSize.setVisible(false);
/* 87:91 */        
/* 99::3 */         switch (AppPanel.this.appType.getSelectedIndex())
/* :0:   */         {
/* :1:   */         case 0: 
/* :2::5 */           AppPanel.this.l3.setVisible(true);
/* :3::6 */           AppPanel.this.stopTime.setVisible(true);
/* :4:   */           
/* :5::8 */           break;

/* =4:   */         default: 
/* =5:=9 */           System.out.println("AppPanel switch error");
/* =6:>0 */           return;
/* =7:   */         }
/* =8:   */       }
/* =9:   */     });
/* >0:   */   }
/* >1:   */ }



