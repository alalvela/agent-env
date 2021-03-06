package node2node;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.AID;
import domain.AgentCenter;
import repository.AgentRepositoryBeanLocal;
import repository.NodeRepositoryBeanLocal;
import util.rest.ExistingNodeAgentClasses;
import util.rest.NewNodeAgentClasses;
import ws.SessionManager;
import ws.WSMessage;


@Stateless
@LocalBean
@Path("/slave")
public class SlaveStarterEndpointBean {

	@EJB
	private AgentRepositoryBeanLocal agentRepo;
	
	@EJB
	private NodeRepositoryBeanLocal nodeRepo;
	
	@EJB
	private SessionManager sessionManager;
	
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getAgentClasses() {
		return agentRepo.getLocalAgentTypes();
	}

	@POST
	@Path("/agents/classes/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNewNodeAndClasses(NewNodeAgentClasses neww) {
		AgentCenter ac = neww.getAgentCenter();
		
		nodeRepo.addNode(ac);
		agentRepo.addAgentTypes(ac.getAlias(), neww.getAgentClasses());
		sessionManager.sendMessage(new WSMessage("NEW_TYPES", WSMessage.getNewTypeMessage(ac.getAlias(), neww.getAgentClasses())));
	}
	
	@POST
	@Path("/agents/classes/existing")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addExistingNodesAndClasses(ExistingNodeAgentClasses existing) {
		List<AgentCenter> nodes = existing.getAgentCenters();
		Map<String, List<String>> classes = existing.getAgentClasses();
		
		nodeRepo.addNodes(nodes);
		agentRepo.joinAgentTypes(classes);	
	}
	
	@POST
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addRunningAgents(List<AID> running) {
		running.forEach(ag -> agentRepo.addRunningAID(ag));
	}

	@DELETE
	@Path("/node/{alias}")
	public void deleteNode(@PathParam("alias") String alias) {
		nodeRepo.removeNode(alias);
		agentRepo.removeAgentTypes(alias);
	}
	
}

