package repository;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import domain.AID;
import domain.Agent;

@Local
public interface AgentRepositoryBeanLocal {
		
	void addAgentTypes(String alias, List<String> newAgentTypes);
	
	void joinAgentTypes(Map<String, List<String>> ats);

	void removeAgentTypes(String alias);
	
	Map<String, List<String>> getAllAgentTypes();

	List<String> getLocalAgentTypes();
	
//	List<String> getAgentTypesAsList();
	
	Agent getAgent(AID aid);
	
	void addAgentToRunning(Agent agent);
	
	void removeAgentFromRunning(AID aid);
	
	Map<AID, Agent> getLocalRunningAgents();
	
	void addRunningAID(AID aid);
	
	void removeRunningAID(AID aid);
	
	List<AID> getAllRunningAgents();
}
