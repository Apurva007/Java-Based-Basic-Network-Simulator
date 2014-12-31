/*  1:   */ package bns.menu;
/*  2:   */ 
/*  3:   */ import java.awt.event.ActionEvent;
/*  4:   */ import java.awt.event.ActionListener;

/*  5:   */ import javax.swing.JMenuItem;
/*  6:   */ import javax.swing.JPopupMenu;

import bns.DataMaintainer;
import bns.BNSParameters;
import bns.SceneManager;
import bns.component.Node;
/* 11:   */ 
/* 12:   */ public class NodePopMenu
/* 13:   */   extends JPopupMenu
/* 14:   */   implements BNSParameters
/* 15:   */ {
/* 16:   */   
/* 17:   */   Node target;
/* 18:   */   SceneManager sm;
/* 19:   */   
/* 20:   */   public void setTarget(Node target)
/* 21:   */   {
/* 22:20 */     this.target = target;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public NodePopMenu(SceneManager sm)
/* 26:   */   {
/* 27:24 */     this.sm = sm;
/* 28:   */     
/* 29:26 */     JMenuItem item = new JMenuItem("Delete");
/* 30:27 */     item.addActionListener(new ActionListener()
/* 31:   */     {
/* 32:   */       public void actionPerformed(ActionEvent e)
/* 33:   */       {
/* 34:29 */         NodePopMenu.this.sm.dm.removeNode(NodePopMenu.this.target);
/* 35:   */       }
/* 36:31 */     });
/* 37:32 */     add(item);
/* 38:33 */    
/* 51:   */   }
/* 52:   */ }



