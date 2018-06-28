package domain;

public class AID {

	private String name;
	private AgentCenter agentCenter;
	private AgentType agentType;
	
	public AID() {
		super();
	}

	public AID(String name, AgentCenter agentCenter, AgentType agentType) {
		super();
		this.name = name;
		this.agentCenter = agentCenter;
		this.agentType = agentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AgentCenter getAgentCenter() {
		return agentCenter;
	}

	public void setAgentCenter(AgentCenter agentCenter) {
		this.agentCenter = agentCenter;
	}

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	
	
	
}
