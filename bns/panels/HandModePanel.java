/*  1:   */ package bns.panels;
/*  2:   */ 
/*  3:   */ import java.awt.FlowLayout;

/*  4:   */ import javax.swing.JPanel;

import bns.BNSParameters;
/*  6:   */ 
/*  7:   */ public class HandModePanel
/*  8:   */   extends JPanel
/*  9:   */ {
/* 10:   */   
/* 11:   */   
/* 12:   */   public HandModePanel()
/* 13:   */   {
/* 14:14 */     setLayout(new FlowLayout(0, 0, 0));
/* 15:15 */     ((FlowLayout)getLayout()).setAlignment(0);
/* 16:16 */     setBackground(BNSParameters.PANEL_COLOR);
/* 17:   */   }
/* 18:   */ }



