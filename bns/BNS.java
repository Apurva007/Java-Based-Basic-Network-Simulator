			package bns;
			import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

			import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

 
			public class BNS
			extends JFrame
			implements BNSParameters
			{
	   
				public static final int WIRED_FRAME = 1;
				
				private static JDesktopPane jdp = new JDesktopPane();
				
				public static JDesktopPane getMainDesktopPane()
				{
					return jdp;
				}
				
				JMenuBar menubar = new JMenuBar();
				private static BNS mainFrame;
				
				public BNS()
				{
					splash s1= new splash();
					s1.showSplash();
					
					mainFrame = this;
					jdp.setDragMode(1);
					jdp.setBackground(BNSParameters.DSEKTOP_BACKGROUND_COLOR);
					getContentPane().add(jdp, "Center");
					createMenuBar();
				}
				
				private void createMenuBar()
				{
					setJMenuBar(this.menubar);
	    
	 
	 
					JMenu menu = new JMenu("File");
					menu.setMnemonic('S');
					JMenuItem item = new JMenuItem("New");
					item.setMnemonic('E');
					item.setAccelerator(KeyStroke.getKeyStroke(87, 2));
					item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							BNS.this.createInternalFrame(1);
						}
					});
					menu.add(item);
					JMenuItem item1 = new JMenuItem("Save");
					item1.setMnemonic('V');
					item1.addActionListener(new ActionListener()
					     {
					       public void actionPerformed(ActionEvent e)
					       {
					          JFileChooser saveFile = new JFileChooser();
		           			  saveFile.showSaveDialog(null);
					        }
					     });
					menu.add(item1);
					item = new JMenuItem("Exit");
					item.setMnemonic('X');
					item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							System.exit(0);
						}
					});
					menu.add(item);
					this.menubar.add(menu);
					menu = new JMenu("Window");
					menu.setMnemonic('W');
					item = new JMenuItem("Iconify all");
					item.setAccelerator(KeyStroke.getKeyStroke(73, 2));
					item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							JInternalFrame[] all = BNS.jdp.getAllFrames();
							for (int i = 0; i < all.length; i++) {
								try
								{
									all[i].setIcon(true);
								}
								catch (PropertyVetoException localPropertyVetoException) {}
							}
						}
					});
					menu.add(item);
	     
					item = new JMenuItem("Deiconify all");
					item.setAccelerator(KeyStroke.getKeyStroke(68, 2));
					item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							JInternalFrame[] all = BNS.jdp.getAllFrames();
							for (int i = 0; i < all.length; i++) {
								try
								{
									all[i].setIcon(false);
								}
								catch (PropertyVetoException localPropertyVetoException) {}
							}
						}
					});
					menu.add(item);
	    
					item = new JMenuItem("Close all");
					item.setAccelerator(KeyStroke.getKeyStroke(67, 2));
					item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							JInternalFrame[] all = BNS.jdp.getAllFrames();
							for (int i = 0; i < all.length; i++) {
								try
								{
									all[i].setClosed(true);
									all[i].dispose();
								}
								catch (PropertyVetoException localPropertyVetoException) {}
							}
						}
					});
					menu.add(item);
	     
					this.menubar.add(menu);
	     
					menu = new JMenu("About");
					menu.setMnemonic('A');
					item = new JMenuItem("Version");
					item.setAccelerator(KeyStroke.getKeyStroke(86, 2));
					item.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
							{
							
							}
					});
					menu.add(item);
					this.menubar.add(menu);
				}
	   
				public void createInternalFrame(int type)
				{
					JInternalFrame iframe = new JInternalFrame();
					switch (type)
					{
					case 1: 
						iframe = new SceneManager(this, 1);
						iframe.setBounds(0, 0, jdp.getWidth(), jdp.getHeight());
						break;
						
					default: 
						iframe = new JInternalFrame();
						iframe.setBounds(640, 0, 720, 512);
					}
					iframe.setDefaultCloseOperation(2);
					iframe.setVisible(true);
     
					jdp.add(iframe);
					try
					{
						iframe.setSelected(true);
					}
					catch (PropertyVetoException e)
					{
						e.printStackTrace();
					}
				}
   
				public static void main(String[] args)
				{
					BNS f = new BNS();
					
					f.setDefaultCloseOperation(3);
					f.setExtendedState(6);
					f.setVisible(true);
				}
				 }



