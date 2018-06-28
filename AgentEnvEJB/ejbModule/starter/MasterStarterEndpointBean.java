package starter;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.NodeRepositoryBeanLocal;
import domain.AgentCenter;

/**
 * Session Bean implementation class MasterStarterEndpointBean
 */
@Stateless
@LocalBean
@Path("/master")
public class MasterStarterEndpointBean {

	@EJB
	private NodeRepositoryBeanLocal nodeRepository;
	
	@EJB
	private MasterStarterServiceBeanLocal masterService;

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addNewNode(AgentCenter agentCenter) {
		
		nodeRepository.addNode(agentCenter);
		
		List<String> newAgentTypes = masterService.getAgentClasses(agentCenter);
		masterService.postNewNode(agentCenter);
//		masterService.postNewAgentClasses(newAgentTypes); TODO
		
	}
}
