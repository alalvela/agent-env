package client2node;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import domain.AID;
import domain.Agent;
import domain.AgentCenter;
import repository.AgentRepositoryBeanLocal;
import repository.NodeRepositoryBeanLocal;
import util.PropertyBean;
import util.UrlBuilder;
import ws.SessionManager;
import ws.WSMessage;


@Stateless
@LocalBean
@Path("/agents")
public class AgentManagerBean implements AgentManagerBeanLocal {

	@EJB
	private PropertyBean props;
	
	@EJB
	private RestClientMaster clientMaster;
	
	@EJB
	private RestClientSlave clientSlave;

	@EJB
    private AgentRepositoryBeanLocal agentRepo;
	
	@EJB
	private AgentBuilderBeanLocal agentBuilder;
	
	@EJB
	private NodeRepositoryBeanLocal nodeRepo;
	
	@EJB
	private SessionManager sessionManager;
	
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List<String>> getAgentTypes() {
		return agentRepo.getAllAgentTypes();
	}
	
	@GET
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AID> getRunningAgents() {
		return agentRepo.getAllRunningAgents();
	}
	
	@PUT
	@Path("/running/{type}/{name}")	//type format -> nodeAlias:module:type
	public void startAgent(@PathParam("type") String type, @PathParam("name") String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String[] parts = type.split(":");
		String nodeAlias = parts[0];
		String agModule = parts[1];
		String agType = parts[2];
		
		if(props.getLocal().getAlias().equals(nodeAlias)) {	//ako je agent na ovom cvoru
			Agent newAgent = agentBuilder.buildAgentFromClassName(name, nodeAlias, agModule, agType);
			newAgent.start();
			
			agentRepo.addAgentToRunning(newAgent);
			
			//posalji ws poruku svim sesijama sa ovog noda
			sessionManager.sendMessage(new WSMessage("NEW_RUNNING", newAgent.getAID().toJson()));
			
			notifyOfNewRunning(newAgent.getAID());	
		} else {
			AgentCenter targetAc = nodeRepo.getNode(nodeAlias);
			if(targetAc == null) {
				targetAc = props.getMaster();
			}
			delegateStartAgent(type, name, targetAc);
		} 
	}
	
	@DELETE
	@Path("/running/{aid}") 
	public void stopAgent(@PathParam("aid") AID aid) {
		
		if(props.getLocal().getAddress().equals(aid.getAgentCenter().getAddress())) {
			Agent agent = agentRepo.getAgent(aid);
			agent.stop();
			
			agentRepo.removeAgentFromRunning(aid);
			notifyOfKillRunning(aid);			
		} else {
			delegateStopAgent(aid);
		}
	
	}

	private void delegateStopAgent(AID aid) {
		Client client = ClientBuilder.newClient();
		String targetUrl = UrlBuilder.getUrl(aid.getAgentCenter().getAddress(), "agents", "running/" + aid.formatAID());
		client.target(targetUrl).request().delete();
	}
	
	
	private void delegateStartAgent(String type, String name, AgentCenter targetAc) {
		Client client = ClientBuilder.newClient();
		String targetUrl = UrlBuilder.getUrl(targetAc.getAddress(), "agents", "running/" + type + "/" + name);
		client.target(targetUrl).request().put(Entity.json(""));
	}
		
	private void notifyOfNewRunning(AID aid) {
		if (props.getLocal().equals(props.getMaster())) {
			clientMaster.notifyOfNewRunning(aid);
		} else {
			clientSlave.notifyMasterOfNewRunning(aid);
		}
	}
	
	private void notifyOfKillRunning(AID aid) {
		if (props.getLocal().equals(props.getMaster())) {
			clientMaster.notifyOfKillRunning(aid);
		} else {
			clientSlave.notifyMasterOfKillRunning(aid);
		}
	}
	

}
