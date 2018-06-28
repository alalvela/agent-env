package beans;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import domain.AgentCenter;

@Local
public interface NodeRepositoryBeanLocal {
	
	void addNode(AgentCenter ac);

	void removeNode(String acAlias);
	
	AgentCenter getNode(String alias);
	
	boolean containsNode(String alias);
	
	Map<String, AgentCenter> getAll();
	
	List<AgentCenter> getAgentCenters();
}
