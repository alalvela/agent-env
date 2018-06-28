package service;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.AgentRepositoryBeanLocal;
import beans.NodeRepositoryBeanLocal;
import domain.AgentCenter;

@Path("/test")
@Stateless
@LocalBean
public class Test {

	@EJB
	NodeRepositoryBeanLocal nodeRepo;
	
	@EJB
	AgentRepositoryBeanLocal agentRepo;
	
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
}
