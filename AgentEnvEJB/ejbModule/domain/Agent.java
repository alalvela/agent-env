package domain;


//public abstract class Agent {

public abstract class Agent implements AgentI {
	
	protected AID id;
	
	public Agent() {}

	public Agent(AID aid) {
		id = aid;
	}
		
	public AID getAID() {
		return id;
	}

	protected abstract void handleMessage(ACLMessage message);
}
