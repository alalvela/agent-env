package node2node;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.AgentCenter;
import repository.AgentRepositoryBeanLocal;
import repository.NodeRepositoryBeanLocal;
import util.rest.ExistingNodeAgentClasses;
import util.rest.NewNodeAgentClasses;

@Stateless
@LocalBean
@Path("/master")
public class MasterStarterEndpointBean {

	@EJB
	private NodeRepositoryBeanLocal nodeRepository;

	@EJB
	private AgentRepositoryBeanLocal agentRepository;
	
	@EJB
	private MasterStarterServiceBeanLocal masterService;

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addNewNode(AgentCenter agentCenter) {
		
		nodeRepository.addNode(agentCenter);
		System.out.println("added new node to master : " + agentCenter.toString());
		
		List<String> newAgentTypes = masterService.getAgentClasses(agentCenter);
		if (newAgentTypes == null) {
			return;
		}
		
		//send to existing nodes
		NewNodeAgentClasses neww = new NewNodeAgentClasses(agentCenter, newAgentTypes);
		if (!masterService.postNew(neww)) {
			return;
		}
		
		//send to new node
		ExistingNodeAgentClasses existing = filterExisting(agentCenter);
		if (!masterService.postExisting(existing)) {
			return;
		}
	}

	
	@DELETE
	@Path("/unregister/{alias}")
	public void removeNode(@PathParam("alias") String alias) {
		nodeRepository.removeNode(alias);
		agentRepository.removeAgentTypes(alias);
		
		//send request to other nodes to remove 
		masterService.deleteNode(alias);
	}
	
	private ExistingNodeAgentClasses filterExisting(AgentCenter agentCenter) {
		List<AgentCenter> nodes = nodeRepository.getAgentCenters()
				.stream()
				.filter(el -> !el.equals(agentCenter))
				.collect(Collectors.toList());
		
		Map<String, List<String>> classes = agentRepository.getAllAgentTypes().entrySet()
				.stream()
				.filter(pair -> !agentCenter.getAlias().equals(pair.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		ExistingNodeAgentClasses existing = new ExistingNodeAgentClasses(agentCenter, nodes, classes);
		return existing;
	}
}
