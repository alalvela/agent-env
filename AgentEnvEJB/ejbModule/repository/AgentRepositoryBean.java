package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import domain.AID;
import domain.Agent;
import util.PropertyBean;


@Singleton
@LocalBean
@DependsOn("PropertyBean")
@Startup
public class AgentRepositoryBean implements AgentRepositoryBeanLocal {

	@EJB
	private PropertyBean propertyBean;
	
	private Map<String, List<String>> agentTypeMap;

	private Map<AID, Agent> runningAgentsLocal;
	
	private List<AID> runningAgentAIDList;
	
	@PostConstruct
	private void init() {
		agentTypeMap = new HashMap<>();
		runningAgentsLocal = new HashMap<>();
		runningAgentAIDList = new ArrayList<>();
		
		List<String> agentTypesList = propertyBean.getAgentTypes();
		agentTypeMap.put(propertyBean.getLocal().getAlias(), agentTypesList);
	}


	@Override
	public List<String> getLocalAgentTypes() {
		return agentTypeMap.get(propertyBean.getLocal().getAlias());
	}
	
	@Override	
	public void addAgentTypes(String nodeAlias, List<String> agentTypes) {
		agentTypeMap.put(nodeAlias, agentTypes);
	}
	
	@Override
	public void joinAgentTypes(Map<String, List<String>> ats) {
		ats.forEach((k, v) -> agentTypeMap.put(k, v));
	}

	@Override
	public Map<String, List<String>> getAllAgentTypes() {
		return agentTypeMap;
	}

	@Override
	public void removeAgentTypes(String alias) {
		if (agentTypeMap.containsKey(alias)) {
			agentTypeMap.remove(alias);			
		} 
	}

	@Override
	public Agent getAgent(AID aid) {
		return runningAgentsLocal.get(aid);
	}

	@Override
	public void addAgentToRunning(Agent agent) {
		runningAgentsLocal.put(agent.getAID(), agent);
		runningAgentAIDList.add(agent.getAID());
	}


	@Override
	public Map<AID, Agent> getLocalRunningAgents() {
		return runningAgentsLocal;
	}


	@Override
	public void removeAgentFromRunning(AID aid) {
		runningAgentsLocal.remove(aid);
		runningAgentAIDList.remove(aid);
	}


	@Override
	public void addRunningAID(AID aid) {
		runningAgentAIDList.add(aid);
	}


	@Override
	public void removeRunningAID(AID aid) {
		runningAgentAIDList.remove(aid);
	}
	
	@Override
	public List<AID> getAllRunningAgents() {
		return runningAgentAIDList;
	}

//	@Override
//	public List<String> getAgentTypesAsList() {
//		return agentTypeMap.values()
//				.stream()
//				.flatMap(x -> x.stream())
//				.distinct()
//				.collect(Collectors.toList());
//	}
	
}
