package agents;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import domain.ACLMessage;
import domain.AID;
import domain.Agent;
import domain.AgentI;


@Stateful
@Local(AgentI.class)
@LocalBean
public class TestAgent extends Agent {
	
	public TestAgent() {
		
	}

	public TestAgent(AID aid) {
		super(aid);
	}

	@Override
	public void start() {
		System.out.println("TestAgent started..");
		System.out.println("Test agent AID: " + id.toString());
	}

	@Override
	public void stop() {
		System.out.println("TestAgent stopped..");
		System.out.println("Test agent AID: " + id.toString());
	}

	@Override
	protected void handleMessage(ACLMessage message) {
		
	}


    
}
