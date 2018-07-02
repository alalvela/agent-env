package domain;

import java.io.Serializable;

public class AID implements Serializable{

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
	
	public AID(String aidStr) {	// name!address,alias!name,module
		String[] parts = aidStr.trim().split("!");
		String[] acParts = parts[1].split(",");
		String[] atParts = parts[2].split(",");

		this.name = parts[0];
		this.agentCenter = new AgentCenter(acParts[0], acParts[1]);
		this.agentType = new AgentType(atParts[0], atParts[1]);
	}
	
	public String formatAID() {
		return String.format("%s!%s,%s!%s,%s", name, agentCenter.getAddress(), 
				agentCenter.getAlias(), agentType.getName(), agentType.getModule());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentCenter == null) ? 0 : agentCenter.hashCode());
		result = prime * result + ((agentType == null) ? 0 : agentType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AID other = (AID) obj;
		if (agentCenter == null) {
			if (other.agentCenter != null)
				return false;
		} else if (!agentCenter.equals(other.agentCenter))
			return false;
		if (agentType == null) {
			if (other.agentType != null)
				return false;
		} else if (!agentType.equals(other.agentType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AID [name=" + name + ", agentCenter=" + agentCenter + ", agentType=" + agentType + "]";
	}
	
	
}
