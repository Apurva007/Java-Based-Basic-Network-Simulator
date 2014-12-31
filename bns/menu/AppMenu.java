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
import bns.component.App;
/*  22:    */ 
/*  23:    */ public class AppMenu
/*  24:    */   extends JDialog
/*  25:    */   implements BNSParameters
/*  26:    */ {
/*  27:    */   
/*  28: 26 */   JButton done = new JButton("Done");
/*  29:    */   App target;
/*  30: 29 */   JLabel l1 = new JLabel("Application type");
/*  31: 30 */   public JComboBox appType = new JComboBox(new String[] { "FTP"});
/*  32: 31 */   JLabel l2 = new JLabel("Start time");
/*  33: 32 */   public JTextField startTime = new JTextField("1", 4);
/*  34: 33 */   JLabel l3 = new JLabel("Stop time");
/*  35: 34 */   public JTextField stopTime = new JTextField("2", 4);
/*  36: 35 */   JLabel l4 = new JLabel("Packet size");
/*  37: 36 */   public JTextField packetSize = new JTextField("1000", 4);
/*  38: 37 */   JLabel l5 = new JLabel("Rate");
/*  39: 38 */   public JTextField rate = new JTextField("1", 4);
/*  40: 39 */   JLabel l6 = new JLabel("Interval");
/*  41: 40 */   public JTextField interval = new JTextField("0.005", 4);
/*  42: 41 */   JLabel l7 = new JLabel("Random");
/*  43: 42 */   public JTextField random = new JTextField("false", 4);
/*  44:    */   
/*  45:    */   public AppMenu(JFrame p)
/*  46:    */   {
/*  47: 45 */     super(p, true);
/*  48: 46 */     setTitle("Application parameters setup");
/*  49:    */     
/*  50: 48 */     int w = Toolkit.getDefaultToolkit().getScreenSize().width;
/*  51: 49 */     int h = Toolkit.getDefaultToolkit().getScreenSize().height;
/*  52: 50 */     setBounds(w / 2 - 250, h / 2 - 300, 300, 350);
/*  53:    */     
/*  54: 52 */     JPanel panel = new JPanel();
/*  55: 53 */     panel.setLayout(new FlowLayout());
/*  56: 54 */     JPanel innerPanel = new JPanel();
/*  57: 55 */     innerPanel.setLayout(new GridLayout(7, 2));
/*  58: 56 */     innerPanel.add(this.l1);innerPanel.add(this.appType);
/*  59: 57 */     innerPanel.add(this.l2);innerPanel.add(this.startTime);
/*  60: 58 */     innerPanel.add(this.l3);innerPanel.add(this.stopTime);
/*  61: 59 */     innerPanel.add(this.l4);innerPanel.add(this.packetSize);
/*  62: 60 */     innerPanel.add(this.l5);innerPanel.add(this.rate);
/*  63:    */     
/*  64: 62 */     innerPanel.add(this.l7);innerPanel.add(this.random);
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
/*  75: 71 */         switch (AppMenu.this.appType.getSelectedIndex())
/*  76:    */         {
/*  77:    */         case 0: 
/*  78: 73 */           AppMenu.this.target.appType = 0;
/*  79: 74 */           AppMenu.this.setupFTP();
/*  80: 75 */           break;
/*  81:    */       
/*  89:    */         default: 
/*  90: 92 */           System.out.println("App error");
/*  91: 93 */           return;
/*  92:    */         }
/*  93: 96 */         AppMenu.this.target = null;
/*  94: 97 */         AppMenu.this.setVisible(false);
/*  95:    */       }
/*  96:100 */     });
/*  97:101 */     this.appType.addItemListener(new ItemListener()
/*  98:    */     {
/*  99:    */       public void itemStateChanged(ItemEvent e)
/* 100:    */       {
/* 101:103 */         switch (AppMenu.this.appType.getSelectedIndex())
/* 102:    */         {
/* 103:    */         case 0: 
/* 104:105 */           AppMenu.this.switchToFTP();
/* 105:106 */           break;
/* 106:    */        
/* 112:    */         default: 
/* 113:118 */           System.out.println("addApp error");
/* 114:119 */           return;
/* 115:    */         }
/* 116:    */       }
/* 117:    */     });
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setTarget(App target)
/* 121:    */   {
/* 122:126 */     this.target = target;
/* 123:127 */     this.appType.setSelectedIndex(target.appType);
/* 124:129 */     switch (this.appType.getSelectedIndex())
/* 125:    */     {
/* 126:    */     case 0: 
/* 127:131 */       switchToFTP();
/* 128:132 */       break;
/* 129:    */     
/* 138:    */     default: 
/* 139:143 */       System.out.println("addApp error");
/* 140:144 */       return;
/* 141:    */     }
/* 142:146 */     this.startTime.setText(String.valueOf(target.startTime));
/* 143:147 */     this.stopTime.setText(String.valueOf(target.stopTime));
/* 144:148 */     this.packetSize.setText(String.valueOf(target.packetSize));
/* 145:149 */     this.rate.setText(String.valueOf(target.rate));
/* 146:150 */     this.random.setText(target.random);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setupFTP()
/* 150:    */   {
/* 151:154 */     this.target.startTime = Float.parseFloat(this.startTime.getText());
/* 152:155 */     this.target.stopTime = Float.parseFloat(this.stopTime.getText());
/* 153:156 */     this.target.packetSize = -1;
/* 154:157 */     this.target.rate = -1.0F;
/* 155:158 */     this.target.random = "false";
/* 156:    */   }
/* 157:    */   
/* 158:    */   
/* 186:186 */  
/* 193:    */   
/* 194:    */   public void switchToFTP()
/* 195:    */   {
/* 196:195 */     this.l1.setVisible(true);
/* 197:196 */     this.appType.setVisible(true);
/* 198:197 */     this.l2.setVisible(true);
/* 199:198 */     this.startTime.setVisible(true);
/* 200:199 */     this.l3.setVisible(true);
/* 201:200 */     this.stopTime.setVisible(true);
/* 202:201 */     this.l4.setVisible(false);
/* 203:202 */     this.packetSize.setVisible(false);
/* 204:203 */     this.l5.setVisible(false);
/* 205:204 */     this.rate.setVisible(false);
/* 206:    */     
/* 207:    */ 
/* 208:207 */     this.l7.setVisible(false);
/* 209:208 */     this.random.setVisible(false);
/* 210:    */   }
/* 211:    */   
/* 212:    */  
/* 229:    */ }



