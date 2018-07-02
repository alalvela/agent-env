package util.rest;

import java.util.List;
import java.util.Map;

import domain.AgentCenter;

public class ExistingNodeAgentClasses {

	private AgentCenter reciever;
	private List<AgentCenter> agentCenters;
	private Map<String, List<String>> agentClasses;
	
	public ExistingNodeAgentClasses() {	}

	public ExistingNodeAgentClasses(AgentCenter reciever, List<AgentCenter> agentCenters, Map<String, List<String>> agentClasses) {
		this.reciever = reciever;
		this.agentCenters = agentCenters;
		this.agentClasses = agentClasses;
	}

	public AgentCenter getReciever() {
		return reciever;
	}

	public void setReciever(AgentCenter reciever) {
		this.reciever = reciever;
	}

	public List<AgentCenter> getAgentCenters() {
		return agentCenters;
	}

	public void setAgentCenters(List<AgentCenter> agentCenters) {
		this.agentCenters = agentCenters;
	}

	public Map<String, List<String>> getAgentClasses() {
		return agentClasses;
	}

	public void setAgentClasses(Map<String, List<String>> agentClasses) {
		this.agentClasses = agentClasses;
	}
	
}
