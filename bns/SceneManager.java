	package bns;
	
		import java.awt.BorderLayout;
		import java.awt.Container;
		import java.awt.Dimension;
		import java.awt.event.ActionEvent;
		import java.awt.event.ActionListener;
		import java.awt.event.ItemEvent;
		import java.awt.event.ItemListener;
		import java.io.PrintStream;

		import javax.swing.Box;
		import javax.swing.ImageIcon;
		import javax.swing.JButton;
		import javax.swing.JCheckBoxMenuItem;
		import javax.swing.JInternalFrame;
		import javax.swing.JLabel;
		import javax.swing.JMenu;
		import javax.swing.JMenuBar;
		import javax.swing.JMenuItem;
		import javax.swing.JOptionPane;
		import javax.swing.JPanel;
		import javax.swing.JSlider;
		import javax.swing.JTextField;
		import javax.swing.JToolBar;
		import javax.swing.KeyStroke;
		import javax.swing.SwingUtilities;
		import javax.swing.event.ChangeEvent;
		import javax.swing.event.ChangeListener;

		import java.awt.*;

		import javax.swing.*;

		import bns.menu.AgentMenu;
		import bns.menu.AppMenu;
		import bns.menu.LinkMenu;
		import bns.simulation.Tcp;
		import bns.simulation.Djikstra;
 
		public class SceneManager
		extends JInternalFrame
		implements BNSParameters
		{
			
			public boolean drawGrid = false;
			public boolean drawNode = true;
			public boolean drawLink = true;
			public boolean drawLinkDetail = false;
			public boolean drawAgent = true;
			public boolean drawAgentDetail = false;
			public boolean drawApp = true;
			
			public JButton b1 = new JButton(new ImageIcon(getClass().getResource("/bns/Hand.jpg")));
			public JButton b2 = new JButton(new ImageIcon(getClass().getResource("/bns/node.gif")));
			public JButton b3 = new JButton(new ImageIcon(getClass().getResource("/bns/connector.gif")));
			public JButton b4 = new JButton(new ImageIcon(getClass().getResource("/bns/agent.jpg")));
			public JButton b5 = new JButton(new ImageIcon(getClass().getResource("/bns/ftp.jpg")));
			public JButton b6 = new JButton(new ImageIcon(getClass().getResource("/bns/sim.jpg")));
			
			JMenuBar menubar = new JMenuBar();
			public float centerX = -1.0F;
			public float centerY = -1.0F;
			public int sceneMode;
			JToolBar toolbar = new JToolBar();
			BNS bns;
			JPanel center = new JPanel(new BorderLayout());
			public JLabel status = new JLabel();
			JSlider slider = new JSlider(8, 500, 100);
			
			public DataMaintainer dm;
			public SceneVirtualizer sv;
			public Tcp tcpproto = new Tcp(this);
			public Djikstra djikproto = new Djikstra(this);
			public LinkMenu linkmenu = new LinkMenu(this.bns);
			public AgentMenu agentmenu = new AgentMenu(this.bns);
			public AppMenu appmenu = new AppMenu(this.bns);
			JFrame frame=new JFrame();
			
			public int goEast = 0;
			public int goWest = 0;
			public int goNorth = 0;
			public int goSouth = 0;
			
			public Thread thread;
			
			public class MoveThread
			extends Thread
			{
				public MoveThread() {}
	     
				public void run()
				{
					try
					{
						Thread.sleep(800L);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					while ((SceneManager.this.goEast != 0) || (SceneManager.this.goWest != 0) || (SceneManager.this.goNorth != 0) || (SceneManager.this.goSouth != 0))
					{
						if (SceneManager.this.goEast != 0) {
							SceneManager.this.sv.shiftX -= (int)(SceneManager.this.goEast / SceneManager.this.sv.scale);
						}
						if (SceneManager.this.goWest != 0) {
							SceneManager.this.sv.shiftX += (int)(SceneManager.this.goWest / SceneManager.this.sv.scale);
						}
						if (SceneManager.this.goSouth != 0) {
							SceneManager.this.sv.shiftY -= (int)(SceneManager.this.goSouth / SceneManager.this.sv.scale);
						}
						if (SceneManager.this.goNorth != 0) {
							SceneManager.this.sv.shiftY += (int)(SceneManager.this.goNorth / SceneManager.this.sv.scale);
						}
						SceneManager.this.centerX = (SceneManager.this.sv.getWidth() / 2 / SceneManager.this.sv.scale - SceneManager.this.sv.shiftX);
						SceneManager.this.centerY = (SceneManager.this.sv.getHeight() / 2 / SceneManager.this.sv.scale - SceneManager.this.sv.shiftY);
						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								SceneManager.this.repaint();
							}
						});
						try
						{
							Thread.sleep(100L);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					System.out.println("Thread stop");
					SceneManager.this.thread = null;
				}
			   }
			
			public boolean scroll(int x, int y)
			{
				return false;
			}
			public SceneManager(BNS f, int mode)
			{
				super("", true, true, true, true);
				this.bns = f;
				this.sceneMode = mode;
				this.dm = new DataMaintainer();
				this.sv = new SceneVirtualizer(this);
				this.tcpproto=new Tcp(this);
				this.djikproto=new Djikstra(this);
				  b1.setToolTipText("Hand");
				  b2.setToolTipText("Node");
				  b3.setToolTipText("Link");
				  b4.setToolTipText("Agent");
				  b5.setToolTipText("Application");
				  b6.setToolTipText("Simulation");
				  
				  if (mode == 1)
				  {
					  setTitle("Wired");
				  }
				  //createMenuBar();
				  createToolBar();
				  Box p = Box.createHorizontalBox();
				  this.status.setPreferredSize(new Dimension(200, 20));
				  this.status.setMaximumSize(new Dimension(200, 20));
				  this.status.setMinimumSize(new Dimension(200, 20));
				  
				  this.slider.setPreferredSize(new Dimension(200, 20));
				  this.slider.setMaximumSize(new Dimension(200, 20));
				  this.slider.setMinimumSize(new Dimension(200, 20));
				  this.slider.addChangeListener(new ChangeListener()
				  {
					  public void stateChanged(ChangeEvent e)
					  {
						  if (SceneManager.this.centerX == -1.0F)
						  {
							  SceneManager.this.centerX = (SceneManager.this.sv.getWidth() / 2 / SceneManager.this.sv.scale - SceneManager.this.sv.shiftX);
							  SceneManager.this.centerY = (SceneManager.this.sv.getHeight() / 2 / SceneManager.this.sv.scale - SceneManager.this.sv.shiftY);
						  }
						  SceneManager.this.sv.scale = (SceneManager.this.slider.getValue() / 100.0F);
						  SceneManager.this.sv.shiftX = ((int)(SceneManager.this.sv.getWidth() / 2 / SceneManager.this.sv.scale - SceneManager.this.centerX));
						  SceneManager.this.sv.shiftY = ((int)(SceneManager.this.sv.getHeight() / 2 / SceneManager.this.sv.scale - SceneManager.this.centerY));
						  SceneManager.this.sv.repaint();
					  }
				  });
				  
				  p.setBackground(STATUS_LABEL_COLOR);
				  p.setOpaque(true);
				  this.slider.setOpaque(false);
				  p.add(Box.createHorizontalGlue());
				  p.add(new JLabel("Zoom"));
				  p.add(this.slider);
				  this.center.add(p, "South");
				  this.center.add(this.sv, "Center");
				  getContentPane().add(this.center, "Center");
			}
			
			public void createToolBar()
			{
				this.toolbar.setBackground(TOOLBAR_COLOR);
				this.toolbar.setOpaque(true);
				this.toolbar.setFloatable(true);
					this.toolbar.setBorderPainted(true);
					this.toolbar.setBorder(getBorder());
			this.toolbar.setOrientation(1);
					getContentPane().add(this.toolbar, "West");
				
					
					this.b1.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							SceneManager.this.sv.switchMode(1);
						}
					});
					this.toolbar.add(this.b1);
					
					this.b2.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							SceneManager.this.sv.switchMode(2);
						}
					});
					this.toolbar.add(this.b2);
					if (this.sceneMode == 1)
					{
						this.b3.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								SceneManager.this.sv.switchMode(5);
							}
						});
						this.toolbar.add(this.b3);
					}
					this.b4.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							SceneManager.this.sv.switchMode(3);
						}
					});
					this.toolbar.add(this.b4);
					this.b5.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							SceneManager.this.sv.switchMode(4);
						}
					});
					this.toolbar.add(this.b5);
					
					this.b6.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{	
							
							
							try{
								Object[] possible={"Normal","Djikstra"};
							String s=(String)JOptionPane.showInputDialog(frame,"Choose Algorithm","Select",JOptionPane.PLAIN_MESSAGE,null,possible,"Normal");
								System.out.print(s);
								if((s=="Djikstra"))
								{
								int check=djikproto.connectionEstablish();
								if(check==1)
							djikproto.datatransfer();
								}
								else
								{
								tcpproto.connectionEstablish();
								tcpproto.datatransfer();
							}
								SceneManager.this.sv.switchMode(6);
							}
							catch(ArrayIndexOutOfBoundsException ae){
								
								JOptionPane.showMessageDialog(null, "Connection Missing");
								SceneManager.this.sv.switchMode(3);
							}
							
						}
					});
					this.toolbar.add(this.b6);
			}
			public void createMenuBar()
			{
				setJMenuBar(this.menubar);
				JMenu menu = new JMenu("File");
				menu.setMnemonic('F');
				JMenuItem item = new JMenuItem("Close");
				item.setMnemonic('C');
				item.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						SceneManager.this.dispose();
					}
				});
				menu.add(item);
				this.menubar.add(menu);
				menu = new JMenu("Draw");
				menu.setMnemonic('D');
				JCheckBoxMenuItem checkItem = new JCheckBoxMenuItem("Draw grid");
				checkItem.setAccelerator(KeyStroke.getKeyStroke(112, 0));
				checkItem.setSelected(false);
				checkItem.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						if (evt.getStateChange() == 1) {
							SceneManager.this.drawGrid = true;
						} else {
							SceneManager.this.drawGrid = false;
						}
						SceneManager.this.repaint();
					}
				});
				menu.add(checkItem);
				
				checkItem = new JCheckBoxMenuItem("Draw node");
				checkItem.setAccelerator(KeyStroke.getKeyStroke(113, 0));
				checkItem.setSelected(true);
				checkItem.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						if (evt.getStateChange() == 1) {
							SceneManager.this.drawNode = true;
						} else {
							SceneManager.this.drawNode = false;
						}
						SceneManager.this.repaint();
					}
				});
				menu.add(checkItem);
				if (this.sceneMode == 1)
				{
					checkItem = new JCheckBoxMenuItem("Draw link");
					checkItem.setAccelerator(KeyStroke.getKeyStroke(114, 0));
					checkItem.setSelected(true);
					checkItem.addItemListener(new ItemListener()
					{
						public void itemStateChanged(ItemEvent evt)
						{
							if (evt.getStateChange() == 1) {
								SceneManager.this.drawLink = true;
							} else {
								SceneManager.this.drawLink = false;
							}
							SceneManager.this.repaint();
						}
					});
					menu.add(checkItem);
					
					checkItem = new JCheckBoxMenuItem("Draw link detail");
					checkItem.setAccelerator(KeyStroke.getKeyStroke(115, 0));
					checkItem.setSelected(false);
					checkItem.addItemListener(new ItemListener()
					{
						public void itemStateChanged(ItemEvent evt)
						{
							if (evt.getStateChange() == 1) {
								SceneManager.this.drawLinkDetail = true;
							} else {
								SceneManager.this.drawLinkDetail = false;
							}
							SceneManager.this.repaint();
						}
					});
					menu.add(checkItem);
				}
				checkItem = new JCheckBoxMenuItem("Draw agent");
				checkItem.setAccelerator(KeyStroke.getKeyStroke(116, 0));
				checkItem.setSelected(true);
				checkItem.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						if (evt.getStateChange() == 1) {
							SceneManager.this.drawAgent = true;
						} else {
							SceneManager.this.drawAgent = false;
						}
						SceneManager.this.repaint();
					}
				});
				menu.add(checkItem);
				
				checkItem = new JCheckBoxMenuItem("Draw agent detail");
				checkItem.setAccelerator(KeyStroke.getKeyStroke(117, 0));
				checkItem.setSelected(false);
				checkItem.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						if (evt.getStateChange() == 1) {
							SceneManager.this.drawAgentDetail = true;
						} else {
							SceneManager.this.drawAgentDetail = false;
						}
						SceneManager.this.repaint();
					}
				});
				menu.add(checkItem);
				
				checkItem = new JCheckBoxMenuItem("Draw application");
				checkItem.setAccelerator(KeyStroke.getKeyStroke(118, 0));
				checkItem.setSelected(true);
				checkItem.addItemListener(new ItemListener()
				{
					public void itemStateChanged(ItemEvent evt)
					{
						if (evt.getStateChange() == 1) {
							SceneManager.this.drawApp = true;
						} else {
							SceneManager.this.drawApp = false;
						}
						SceneManager.this.repaint();
					}
				});
				menu.add(checkItem);
				
				this.menubar.add(menu);
				menu = new JMenu("Mode");
				menu.setMnemonic('M');
				item = new JMenuItem("Hand mode");
				item.setAccelerator(KeyStroke.getKeyStroke(49, 2));
				item.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						SceneManager.this.sv.switchMode(1);
					}
				});
				menu.add(item);
				
				item = new JMenuItem("Node mode");
				item.setAccelerator(KeyStroke.getKeyStroke(50, 2));
				item.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						SceneManager.this.sv.switchMode(2);
					}
				});
				menu.add(item);
				if (this.sceneMode == 1)
				{
					item = new JMenuItem("Link mode");
					item.setAccelerator(KeyStroke.getKeyStroke(51, 2));
					item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent evt)
						{
							SceneManager.this.sv.switchMode(5);
						}
/* 584:560 */       });
/* 585:561 */       menu.add(item);
/* 586:    */     }
/* 587:564 */     item = new JMenuItem("Agent mode");
/* 588:565 */     item.setAccelerator(KeyStroke.getKeyStroke(52, 2));
/* 589:566 */     item.addActionListener(new ActionListener()
/* 590:    */     {
/* 591:    */       public void actionPerformed(ActionEvent evt)
/* 592:    */       {
/* 593:568 */         SceneManager.this.sv.switchMode(3);
/* 594:    */       }
/* 595:570 */     });
/* 596:571 */     menu.add(item);
/* 597:    */     
/* 598:573 */     item = new JMenuItem("Application mode");
/* 599:574 */     item.setAccelerator(KeyStroke.getKeyStroke(53, 2));
/* 600:575 */     item.addActionListener(new ActionListener()
/* 601:    */     {
/* 602:    */       public void actionPerformed(ActionEvent evt)
/* 603:    */       {
/* 604:577 */         SceneManager.this.sv.switchMode(4);
/* 605:    */       }
/* 606:579 */     });
/* 607:580 */     menu.add(item);
/* 608:    */     
/* 609:582 */    
/* 630:    */     
/* 631:600 */     this.menubar.add(menu);
/* 632:    */   }
/* 633:    */ }



 