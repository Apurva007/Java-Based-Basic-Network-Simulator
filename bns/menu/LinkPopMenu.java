/*  1:   */ package bns.menu;
/*  2:   */ 
/*  3:   */ import java.awt.event.ActionEvent;
/*  4:   */ import java.awt.event.ActionListener;

/*  5:   */ import javax.swing.JMenuItem;
/*  6:   */ import javax.swing.JPopupMenu;

import bns.DataMaintainer;
import bns.SceneManager;
import bns.component.Link;
/* 10:   */ 
/* 11:   */ public class LinkPopMenu
/* 12:   */   extends JPopupMenu
/* 13:   */ {
/* 14:   */   
/* 15:   */   Link target;
/* 16:   */   SceneManager sm;
/* 17:   */   
/* 18:   */   public void setTarget(Link target)
/* 19:   */   {
/* 20:18 */     this.target = target;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public LinkPopMenu(SceneManager sm)
/* 24:   */   {
/* 25:22 */     this.sm = sm;
/* 26:   */     
/* 27:24 */     JMenuItem item = new JMenuItem("Delete");
/* 28:25 */     item.addActionListener(new ActionListener()
/* 29:   */     {
/* 30:   */       public void actionPerformed(ActionEvent e)
/* 31:   */       {
/* 32:27 */         LinkPopMenu.this.sm.dm.removeLink(LinkPopMenu.this.target);
/* 33:   */       }
/* 34:29 */     });
/* 35:30 */     add(item);
/* 36:31 */     item = new JMenuItem("Setup");
/* 37:32 */     item.addActionListener(new ActionListener()
/* 38:   */     {
/* 39:   */       public void actionPerformed(ActionEvent e)
/* 40:   */       {
/* 41:34 */         LinkPopMenu.this.sm.linkmenu.setTarget(LinkPopMenu.this.target);
/* 42:35 */         LinkPopMenu.this.sm.linkmenu.setVisible(true);
/* 43:   */       }
/* 44:37 */     });
/* 45:38 */     add(item);
/* 46:   */   }
/* 47:   */ }



