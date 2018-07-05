package client2node;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import domain.AID;
import domain.Agent;
import repository.AgentRepositoryBeanLocal;
import repository.NodeRepositoryBeanLocal;
import ws.SessionManager;
import ws.WSMessage;


@Stateless
@LocalBean
@Path("/agents/master")
public class MasterNodeEndpoint {

	@EJB
	private NodeRepositoryBeanLocal nodeRepo;
	
	@EJB
	private SessionManager sessionManager;
	
	@EJB
	private AgentRepositoryBeanLocal agentRepo;
	
	@EJB
	private RestClientMaster restCli;
	
	@POST
	@Path("/running")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAgentToRunning(AID aid) {
		agentRepo.addRunningAID(aid);
		sessionManager.sendMessage(new WSMessage("NEW_RUNNING", aid.toJson()));
		
		restCli.notifyOfNewRunning(aid);
	}
	
	@DELETE
	@Path("/running/{aid}")
	public void killAgent(@PathParam("aid") AID aid) {
		agentRepo.removeRunningAID(aid);
	
		restCli.notifyOfKillRunning(aid);
	}
}
