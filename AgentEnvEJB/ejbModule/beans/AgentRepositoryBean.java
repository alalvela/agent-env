package beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import util.PropertyBean;

/**
 * Session Bean implementation class AgentRepositoryBean
 */
@Singleton
@LocalBean
@DependsOn("PropertyBean")
@Startup
public class AgentRepositoryBean implements AgentRepositoryBeanLocal {

	@EJB
	private PropertyBean propertyBean;
	
//	private Set<String> agentTypes;

	private Map<String, List<String>> agentTypeMap;
	
	@PostConstruct
	private void init() {
		agentTypeMap = new HashMap<>();

		List<String> agentTypesList = propertyBean.getAgentTypes().stream().map(t -> t.concat(".class")).collect(Collectors.toList());
		agentTypeMap.put(propertyBean.getLocal().getAlias(), agentTypesList);
		
	}

	public List<String> getLocalAgentTypes() {
		return agentTypeMap.get(propertyBean.getLocal().getAlias());
	}
	
	public void addAgentTypes(String nodeAlias, List<String> agentTypes) {
		agentTypeMap.put(nodeAlias, agentTypes);
	}

	@Override
	public Map<String, List<String>> getAllAgentTypes() {
		return agentTypeMap;
	}
	
}
