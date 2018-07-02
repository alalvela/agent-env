package client2node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.Agent;
import domain.AgentCenter;
import node2agent.JMSClient;
import repository.AgentRepositoryBeanLocal;
import repository.NodeRepositoryBeanLocal;

@Path("/test")
@Stateless
@LocalBean
public class Test {

	@EJB
	NodeRepositoryBeanLocal nodeRepo;
	
	@EJB
	AgentRepositoryBeanLocal agentRepo;
	
	@EJB
	AgentBuilderBean agentBuilder;
	
	@EJB
	JMSClient jmsCli;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "testttttt";
	}
	
	@GET
	@Path("/nodes")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, AgentCenter> getNodes() {
		return nodeRepo.getAll();
	}
	
	@GET
	@Path("/agents")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<String>> getAgents() {
		return agentRepo.getAllAgentTypes();
	}
	
	
	@GET
	@Path("/jms/{message}")
	public void testMessage(@PathParam("message") String message) throws NamingException, JMSException {	// address!messagecontent
		String address = message.split("!")[0];
		String msg = message.split("!")[1];
		jmsCli.sendStringMessage(address, msg);
	}
}
