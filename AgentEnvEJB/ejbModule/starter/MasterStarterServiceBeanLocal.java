package starter;

import java.util.List;

import javax.ejb.Local;

import domain.AgentCenter;

@Local
public interface MasterStarterServiceBeanLocal {
	
	List<String> getAgentClasses(AgentCenter agentCenter);
	
	void postNewNode(AgentCenter agentCenter);
	
	void postNewAgentClasses(List<String> agentTypeList);
	
	void postExistingNodes(List<AgentCenter> agentCenterList);

	void postExistingAgentClasses(List<String> agentTypeList);
	
	void postRunningAgents();
	
	void deleteNode(String alias);
	
}
