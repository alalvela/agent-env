package node2node;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import domain.AID;
import domain.AgentCenter;
import repository.AgentRepositoryBeanLocal;
import repository.NodeRepositoryBeanLocal;
import util.PropertyBean;
import util.UrlBuilder;
import util.rest.ExistingNodeAgentClasses;
import util.rest.NewNodeAgentClasses;


@Stateless
@LocalBean
public class MasterStarterServiceBean implements MasterStarterServiceBeanLocal {
	
	@EJB
	private PropertyBean props;
	
	@EJB
	private NodeRepositoryBeanLocal nodeRepo;

	@EJB
	private AgentRepositoryBeanLocal agentRepo;
	
	private Client client;
	
	@PostConstruct
	private void init() {
		client = ClientBuilder.newClient();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAgentClasses(AgentCenter agentCenter) {
		String targetUrl = UrlBuilder.getUrl(agentCenter.getAddress(), "slave", "classes");
		Response response = client.target(targetUrl).request().get();
		
		if (!responseOK(response.getStatus())) {
			Response response2 = client.target(targetUrl).request().get();
			if (!responseOK(response2.getStatus())) {
				return null;
			}
		}
		
		List<String> agentClasses = response.readEntity(List.class);
		agentRepo.addAgentTypes(agentCenter.getAlias(), agentClasses);
		return agentClasses;
	}

	@Override
	public boolean postNew(NewNodeAgentClasses neww) {
		AgentCenter newAgentCenter = neww.getAgentCenter();
		
		for (AgentCenter ac : nodeRepo.getAgentCenters()) {
			if(!ac.equals(newAgentCenter)) {
				String targetUrl = UrlBuilder.getUrl(ac.getAddress(), "slave", "agents/classes/new");
				Response response = client.target(targetUrl).request().post(Entity.json(neww));
				
				if (!responseOK(response.getStatus())) {
					Response response2 = client.target(targetUrl).request().post(Entity.json(neww));
					if (!responseOK(response2.getStatus())) {
						return false;
					}
				}
			}
		}	
		return true;
	}
	
	@Override
	public boolean postExisting(ExistingNodeAgentClasses existing) {
		AgentCenter reciever = existing.getReciever();
		
		String targetUrl = UrlBuilder.getUrl(reciever.getAddress(), "slave", "agents/classes/existing");	
		Response response = client.target(targetUrl).request().post(Entity.json(existing));
		if (!responseOK(response.getStatus())) {
			Response response2 = client.target(targetUrl).request().post(Entity.json(existing));
			if (!responseOK(response2.getStatus())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean postRunningAgents(AgentCenter ac, List<AID> running) {
		String targetUrl = UrlBuilder.getUrl(ac.getAddress(), "slave", "agents/running");
		Response response = client.target(targetUrl).request().post(Entity.json(running));
		if (!responseOK(response.getStatus())) {
			Response response2 = client.target(targetUrl).request().post(Entity.json(running));
			if (!responseOK(response2.getStatus())) {
				return false;
			}
		}
		return true;
	}


	@Override
	public void deleteNode(String alias) {
		nodeRepo.getAgentCenters()
		.forEach( ac -> {
			if(!ac.getAlias().equals(alias)) {
				String targetUrl = UrlBuilder.getUrl(ac.getAddress(), "slave", "node/" + alias);
				client.target(targetUrl).request().delete();
			}
		});
		
	}
	
	private boolean responseOK(int status) {
		return status > 199 && status < 300;
	}


	

}
