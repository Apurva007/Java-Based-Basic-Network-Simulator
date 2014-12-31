/*  1:   */ package bns.menu;
/*  2:   */ 
/*  3:   */ import java.awt.event.ActionEvent;
/*  4:   */ import java.awt.event.ActionListener;

/*  5:   */ import javax.swing.JMenuItem;
/*  6:   */ import javax.swing.JPopupMenu;

import bns.DataMaintainer;
import bns.SceneManager;
import bns.component.Agent;
/* 10:   */ 
/* 11:   */ public class AgentPopMenu
/* 12:   */   extends JPopupMenu
/* 13:   */ {
/* 14:   */   
/* 15:   */   Agent target;
/* 16:   */   SceneManager sm;
/* 17:   */   
/* 18:   */   public void setTarget(Agent target)
/* 19:   */   {
/* 20:18 */     this.target = target;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public AgentPopMenu(SceneManager sm)
/* 24:   */   {
/* 25:22 */     this.sm = sm;
/* 26:   */     
/* 27:24 */     JMenuItem item = new JMenuItem("Delete");
/* 28:25 */     item.addActionListener(new ActionListener()
/* 29:   */     {
/* 30:   */       public void actionPerformed(ActionEvent e)
/* 31:   */       {
/* 32:27 */         AgentPopMenu.this.sm.dm.removeAgent(AgentPopMenu.this.target);
/* 33:   */       }
/* 34:29 */     });
/* 35:30 */     add(item);
/* 36:   */     
/* 37:32 */     item = new JMenuItem("Setup");
/* 38:33 */     item.addActionListener(new ActionListener()
/* 39:   */     {
/* 40:   */       public void actionPerformed(ActionEvent e)
/* 41:   */       {
/* 42:35 */         AgentPopMenu.this.sm.agentmenu.setTarget(AgentPopMenu.this.target);
/* 43:36 */         AgentPopMenu.this.sm.agentmenu.setVisible(true);
/* 44:   */       }
/* 45:38 */     });
/* 46:39 */     add(item);
/* 47:   */   }
/* 48:   */ }


