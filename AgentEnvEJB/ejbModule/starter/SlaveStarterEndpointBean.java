package starter;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.AgentRepositoryBeanLocal;
import beans.NodeRepositoryBeanLocal;
import domain.AgentCenter;


@Stateless
@LocalBean
@Path("/slave")
public class SlaveStarterEndpointBean {

	@EJB
	private AgentRepositoryBeanLocal agentRepo;
	
	@EJB
	private NodeRepositoryBeanLocal nodeRepo;
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getAgentClasses() {
		return agentRepo.getLocalAgentTypes();
	}
	
	@POST
	@Path("/node")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addNewNode(AgentCenter agentCenter) {
		nodeRepo.addNode(agentCenter);
	}
	  	
}
