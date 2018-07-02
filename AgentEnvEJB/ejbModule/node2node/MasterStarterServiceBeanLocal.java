package node2node;

import java.util.List;

import javax.ejb.Local;

import domain.AgentCenter;
import util.rest.ExistingNodeAgentClasses;
import util.rest.NewNodeAgentClasses;

@Local
public interface MasterStarterServiceBeanLocal {
	
	List<String> getAgentClasses(AgentCenter agentCenter);
	
	boolean postNew(NewNodeAgentClasses neww);
	
	boolean postExisting(ExistingNodeAgentClasses existing);
	
	boolean postRunningAgents();
	
	void deleteNode(String alias);
	
}
