package util.rest;

import java.util.List;

import domain.AgentCenter;

public class NewNodeAgentClasses {
	
	private AgentCenter agentCenter;
	private List<String> agentClasses;
	
	public NewNodeAgentClasses() { }

	public NewNodeAgentClasses(AgentCenter agentCenter, List<String> agentClasses) {
		this.agentCenter = agentCenter;
		this.agentClasses = agentClasses;
	}

	public AgentCenter getAgentCenter() {
		return agentCenter;
	}

	public void setAgentCenter(AgentCenter agentCenter) {
		this.agentCenter = agentCenter;
	}

	public List<String> getAgentClasses() {
		return agentClasses;
	}

	public void setAgentClasses(List<String> agentClasses) {
		this.agentClasses = agentClasses;
	}

}
