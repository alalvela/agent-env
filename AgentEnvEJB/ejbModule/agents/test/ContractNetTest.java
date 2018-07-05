package agents.test;

import java.lang.reflect.InvocationTargetException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import agents.ContractNetInitiator;
import agents.ContractNetParticipant;
import client2node.AgentBuilderBeanLocal;
import domain.AID;
import domain.Agent;
import repository.AgentRepositoryBeanLocal;

@Path("/test")
public class ContractNetTest {
	
	
	
	private final int PARTICIPANT_COUNT = 3;
	
	@EJB
	private AgentBuilderBeanLocal agentBuilder;
	
//	@EJB
//	private AgentRepositoryBeanLocal agentRepo;
	
	@GET
	@Path("/cn")
	public void test() {
		
	}
	
	
	private AID[] createParticipants() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		AID[] participants = new AID[PARTICIPANT_COUNT];
		for	(int i = 0; i < PARTICIPANT_COUNT; i++) {
			Agent participant = agentBuilder.buildAgentFromClassName("Participant"+i, "master", "test", ContractNetParticipant.class.getSimpleName());
			participant.start();
			participants[i] = participant.getAID();
		}
		return participants;
	}
	
	private AID createInitiator() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Agent initiator = agentBuilder.buildAgentFromClassName("Initiator", "master", "test", ContractNetInitiator.class.getSimpleName());
		initiator.start();
		return initiator.getAID();
	}

}
