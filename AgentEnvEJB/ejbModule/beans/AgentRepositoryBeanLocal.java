package beans;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface AgentRepositoryBeanLocal {
	
	List<String> getLocalAgentTypes();
	
	void addAgentTypes(String alias, List<String> newAgentTypes);
	
	Map<String, List<String>> getAllAgentTypes();

}
