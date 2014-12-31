	package bns;
	 
		import java.util.ArrayList;
		import java.util.Iterator;

		import bns.component.Agent;
		import bns.component.App;
		import bns.component.Link;
		import bns.component.Node;
	 
		public class DataMaintainer
		{
			public ArrayList nodes;
			public ArrayList links;
			public ArrayList agents;
			public ArrayList apps;
			public static Iterator it;
	   
			public DataMaintainer()
			{
				this.nodes = new ArrayList();
				this.links = new ArrayList();
				this.agents = new ArrayList();
				this.apps = new ArrayList();
			}
	  
			public void removeApp(App app)
			{
				this.apps.remove(app);
			}
	   
			public void removeAgent(Agent agent)
			{
				this.agents.remove(agent);
	     
				checkAgents();
				checkApps();
			}
			
			public void removeLink(Link link)
			{
				this.links.remove(link);
			}
			
			public void checkApps()
			{
				Object[] apps = getApps();
				for (int i = 0; i < apps.length; i++)
				{
					App app = (App)apps[i];
					if (!this.agents.contains(app.agent)) {
						this.apps.remove(app);
					}
				}
			}
			
			public void checkAgents()
			{
				Object[] agents = getAgents();
				for (int i = 0; i < agents.length; i++)
				{
					Agent agent = (Agent)agents[i];
					if (!this.nodes.contains(agent.attachedNode)) {
						this.agents.remove(agent);
					}
				}
				for (int i = 0; i < agents.length; i++)
				{
					Agent agent = (Agent)agents[i];
					if (!this.agents.contains(agent.remoteAgent)) {
						agent.remoteAgent = null;
					}
				}
			}
	  
			public void checkLinks()
			{
				Object[] links = getLinks();
				for (int i = 0; i < links.length; i++)
				{
					Link link = (Link)links[i];
					if ((!this.nodes.contains(link.src)) || (!this.nodes.contains(link.dst))) {
						this.links.remove(link);
					}
				}
			}
			
			public void removeNode(Node node)
			{
				this.nodes.remove(node);
				checkLinks();
				checkAgents();
				checkApps();
			}
			
			public Node findNode(int x, int y)
			{
				it = this.nodes.iterator();
				while (it.hasNext())
				{
					Node p = (Node)it.next();
					if ((Math.abs(p.x - x) < 10) && (Math.abs(p.y - y) < 10)) {
						return p;
					}
				}
				return null;
			}
	   
			public Link findLink(int x, int y)
			{
				it = this.links.iterator();
 
				Link tempP = null;
				double tempD = 0.0D;
				while (it.hasNext())
				{
					Link linkP = (Link)it.next();
					int temp = ((linkP.src.x > x ? 1 : 0) ^ (linkP.dst.x > x ? 1 : 0));//I changed type from boolean to int
					if (temp==1)
					{
						temp = (linkP.src.y > y ? 1 : 0) ^ (linkP.dst.y > y ? 1 : 0);
						if (temp==1)
						{
							int x1 = linkP.src.x;
							int x2 = linkP.dst.x;
							int y1 = linkP.src.y;
							int y2 = linkP.dst.y;
							double d = Math.abs((y2 - y1) * x + (x1 - x2) * y + (y1 * (x2 - x1) + x1 * (y1 - y2))) / Math.sqrt((y2 - y1) * (y2 - y1) + (x1 - x2) * (x1 - x2));
							if (d < 40.0D) {
								if (tempP == null)
								{
									tempP = linkP;
									tempD = d;
								}
								else if (tempD > d)
								{
									tempP = linkP;
									tempD = d;
								}
							}
						}
					}
				}
				return tempP;
			}
			public Agent findAgent(int x, int y)
			{
				it = this.agents.iterator();
				while (it.hasNext())
				{
					Agent p = (Agent)it.next();
					if ((Math.abs(p.x - x) < 10) && (Math.abs(p.y - y) < 10)) {
						return p;
					}
				}
				return null;
			}
			
			public App findApp(int x, int y)
			{
				it = this.apps.iterator();
				while (it.hasNext())
				{
					App p = (App)it.next();
					if ((Math.abs(p.x - x) < 10) && (Math.abs(p.y - y) < 10)) {
						return p;
					}
				}
				return null;
			}

			public Object[] getNodes()
			{
				return this.nodes.toArray();
			}
			
			public Object[] getLinks()
			{
				return this.links.toArray();
			}
			
			public Object[] getAgents()
			{
				return this.agents.toArray();
			}
			
			public Object[] getApps()
			{
				return this.apps.toArray();
			}
		}



