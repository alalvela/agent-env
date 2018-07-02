package client2node;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.ACLMessage;
import domain.AID;
import domain.Performative;
import node2agent.JMSClient;


@Stateless
@LocalBean
@Path("/messages")
public class MessageManagerBean implements MessageManagerBeanLocal {

	@EJB
	private JMSClient jmsCli;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPerformatives() {
		return Arrays.asList(Performative.values())
				.stream()
				.map(p -> p.toString())
				.collect(Collectors.toList());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMessage(ACLMessage aclMessage) {
		
		IntStream.range(0, aclMessage.recievers.size())
			.forEach(idx -> {
				AID aid = aclMessage.recievers.get(idx);
				if (aid == null) throw new IllegalArgumentException("AID cannot be null");
				try {
					jmsCli.sendMessageToAgent(aid.getAgentCenter().getAddress(), aclMessage, idx);
				} catch (NamingException | JMSException e) {
					e.printStackTrace();
				}
			});
		
	}
	
//	private void postMessage(AID aid, int idx, ACLMessage aclMessage) throws NamingException, JMSException {
//		jmsCli.sendMessageToAgent(aid.getAgentCenter().getAddress(), aclMessage, idx);
//	}
}

//		aclMessage.recievers.forEach(rec -> {	
//			if (rec == null) throw new IllegalArgumentException("AID cannot be null");
//			
//			try {
//				jmsCli.sendMessageToAgent(rec.getAgentCenter().getAddress(), aclMessage);
//			} catch (NamingException | JMSException e) {
//				e.printStackTrace();
//			}			
//		});