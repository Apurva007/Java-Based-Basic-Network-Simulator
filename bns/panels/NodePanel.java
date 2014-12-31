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
import bns.component.Node;
/* 14:   */ 
/* 15:   */ public class NodePanel
/* 16:   */   extends JPanel
/* 17:   */ {
/* 18:   */   
/* 19:19 */   public JComboBox type = new JComboBox(new String[] { "Single"});
/* 20:20 */   
/* 32:   */   Node node;
/* 33:   */   SceneVirtualizer sv;
/* 34:   */   
/* 35:   */   public void clearTarget()
/* 36:   */   {
/* 37:35 */     this.node = null;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setTarget(Node node)
/* 41:   */   {
/* 42:39 */     this.node = node;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public NodePanel(SceneVirtualizer sv)
/* 46:   */   {
/* 47:45 */     setLayout(new FlowLayout(0, 0, 0));
/* 48:46 */     this.sv = sv;
/* 49:47 */     ((FlowLayout)getLayout()).setAlignment(0);
/* 50:48 */     setBackground(BNSParameters.PANEL_COLOR);
/* 51:49 */     add(this.type);
/* 52:50 */    
/* 76:74 */     this.type.addItemListener(new ItemListener()
/* 77:   */     {
/* 78:   */       public void itemStateChanged(ItemEvent e)
/* 79:   */       {
/* 80:76 */       
/* 92:88 */         switch (NodePanel.this.type.getSelectedIndex())
/* 93:   */         {
/* 94:   */         case 0: 
/* 95:   */           break;
/* 96:   */         
/* =0:   */  
/* =2:   */ 
/* =3:   */ 
/* =4:   */ 
/* =5:=8 */        
/* >0:   */         default: 
/* >1:>4 */           System.out.println("NodePanel switch error");
/* >2:>5 */           return;
/* >3:   */         }
/* >4:   */       }
/* >5:   */     });
/* >6:   */   }
/* >7:   */ }



