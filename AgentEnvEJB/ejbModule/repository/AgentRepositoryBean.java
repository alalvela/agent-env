package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
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
	@Lock(LockType.READ)
	public List<String> getLocalAgentTypes() {
		return agentTypeMap.get(propertyBean.getLocal().getAlias());
	}
	
	@Override	
	@Lock(LockType.WRITE)
	public void addAgentTypes(String nodeAlias, List<String> agentTypes) {
		agentTypeMap.put(nodeAlias, agentTypes);
	}
	
	@Override
	@Lock(LockType.WRITE)
	public void joinAgentTypes(Map<String, List<String>> ats) {
		ats.forEach((k, v) -> agentTypeMap.put(k, v));
	}

	@Override
	@Lock(LockType.READ)
	public Map<String, List<String>> getAllAgentTypes() {
		return agentTypeMap;
	}

	@Override
	@Lock(LockType.WRITE)
	public void removeAgentTypes(String alias) {
		if (agentTypeMap.containsKey(alias)) {
			agentTypeMap.remove(alias);			
		} 
	}

	@Override
	@Lock(LockType.READ)
	public Agent getAgent(AID aid) {
		return runningAgentsLocal.get(aid);
	}

	@Override
	@Lock(LockType.WRITE)
	public void addAgentToRunning(Agent agent) {
		runningAgentsLocal.put(agent.getAID(), agent);
		runningAgentAIDList.add(agent.getAID());
	}


	@Override
	@Lock(LockType.READ)
	public Map<AID, Agent> getLocalRunningAgents() {
		return runningAgentsLocal;
	}


	@Override
	@Lock(LockType.WRITE)
	public void removeAgentFromRunning(AID aid) {
		runningAgentsLocal.remove(aid);
		runningAgentAIDList.remove(aid);
	}


	@Override
	@Lock(LockType.WRITE)
	public void addRunningAID(AID aid) {
		runningAgentAIDList.add(aid);
	}


	@Override
	@Lock(LockType.WRITE)
	public void removeRunningAID(AID aid) {
		runningAgentAIDList.remove(aid);
	}
	
	@Override
	@Lock(LockType.READ)
	public List<AID> getAllRunningAgents() {
		return runningAgentAIDList;
	}
	
}
