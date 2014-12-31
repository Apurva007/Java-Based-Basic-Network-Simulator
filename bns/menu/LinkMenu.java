/*  1:   */ package bns.menu;
/*  2:   */ 
/*  3:   */ import java.awt.Container;
/*  4:   */ import java.awt.Dimension;
/*  5:   */ import java.awt.FlowLayout;
/*  6:   */ import java.awt.GridLayout;
/*  7:   */ import java.awt.Toolkit;
/*  8:   */ import java.awt.event.ActionEvent;
/*  9:   */ import java.awt.event.ActionListener;

/* 10:   */ import javax.swing.JButton;
/* 11:   */ import javax.swing.JComboBox;
/* 12:   */ import javax.swing.JDialog;
/* 13:   */ import javax.swing.JFrame;
/* 14:   */ import javax.swing.JLabel;
/* 15:   */ import javax.swing.JPanel;
/* 16:   */ import javax.swing.JTextField;

import bns.BNSParameters;
import bns.component.Link;
/* 19:   */ 
/* 20:   */ public class LinkMenu
/* 21:   */   extends JDialog
/* 22:   */   implements BNSParameters
/* 23:   */ {
/* 24:   */   
/* 25:25 */   JButton done = new JButton("Done");
/* 26:   */   Link target;
/* 27:28 */   public JComboBox queueType = new JComboBox(new String[] { "DropTail"});
/* 28:29 */   public JComboBox linkType = new JComboBox(new String[] { "duplex-link", "simplex-link" });
/* 29:30 */   public JTextField capacity = new JTextField("100");
/* 30:31 */   public JTextField propagationDelay = new JTextField("10");
/* 31:32 */   public JTextField queueSize = new JTextField("50");
			  public JTextField distance = new JTextField("20");
/* 32:   */   
/* 33:   */   public LinkMenu(JFrame p)
/* 34:   */   {
/* 35:35 */     super(p, true);
/* 36:36 */     setTitle("Link parameters setup");
/* 37:   */     
/* 38:38 */     int w = Toolkit.getDefaultToolkit().getScreenSize().width;
/* 39:39 */     int h = Toolkit.getDefaultToolkit().getScreenSize().height;
/* 40:40 */     setBounds(w / 2 - 250, h / 2 - 300, 300, 250);
/* 41:   */     
/* 42:42 */     JPanel panel = new JPanel();
/* 43:43 */     panel.setLayout(new FlowLayout());
/* 44:44 */     JPanel innerPanel = new JPanel();
/* 45:45 */     innerPanel.setLayout(new GridLayout(6, 2));
/* 46:46 */     innerPanel.add(new JLabel("Queue type"));innerPanel.add(this.queueType);
/* 47:47 */     innerPanel.add(new JLabel("Link type"));innerPanel.add(this.linkType);
/* 48:48 */     innerPanel.add(new JLabel("Capacity"));innerPanel.add(this.capacity);
/* 49:49 */     innerPanel.add(new JLabel("Propagation delay"));innerPanel.add(this.propagationDelay);
/* 50:50 */     innerPanel.add(new JLabel("Queue size"));innerPanel.add(this.queueSize);
				innerPanel.add(new JLabel("Distance"));innerPanel.add(this.distance);
/* 51:51 */     panel.add(innerPanel);
/* 52:52 */     getContentPane().add(panel, "Center");
/* 53:53 */     panel = new JPanel();
/* 54:54 */     panel.add(this.done);
/* 55:55 */     getContentPane().add(panel, "South");
/* 56:   */     
/* 57:57 */     this.done.addActionListener(new ActionListener()
/* 58:   */     {
/* 59:   */       public void actionPerformed(ActionEvent e)
/* 60:   */       {
/* 61:64 */         LinkMenu.this.target.capacity = Float.parseFloat(LinkMenu.this.capacity.getText());
/* 62:65 */         LinkMenu.this.target.linkType = LinkMenu.this.linkType.getSelectedIndex();
/* 63:66 */         LinkMenu.this.target.propagationDelay = Integer.parseInt(LinkMenu.this.propagationDelay.getText());
/* 64:67 */         LinkMenu.this.target.queueSize = Integer.parseInt(LinkMenu.this.queueSize.getText());
LinkMenu.this.target.distance = Integer.parseInt(LinkMenu.this.distance.getText());
/* 65:68 */         LinkMenu.this.target.queueType = LinkMenu.this.queueType.getSelectedIndex();
/* 66:69 */         LinkMenu.this.target = null;
/* 67:70 */         LinkMenu.this.setVisible(false);
/* 68:   */       }
/* 69:   */     });
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setTarget(Link target)
/* 73:   */   {
/* 74:75 */     this.target = target;
/* 75:76 */     this.queueType.setSelectedIndex(target.queueType);
/* 76:77 */     this.linkType.setSelectedIndex(target.linkType);
/* 77:78 */     this.capacity.setText(String.valueOf(target.capacity));
this.distance.setText(String.valueOf(target.distance));
/* 78:79 */     this.propagationDelay.setText(String.valueOf(target.propagationDelay));
/* 79:80 */     this.queueSize.setText(String.valueOf(target.queueSize));
/* 80:   */   }
/* 81:   */ }



