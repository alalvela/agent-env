package starter;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.AgentRepositoryBeanLocal;
import beans.NodeRepositoryBeanLocal;
import domain.AgentCenter;
import sun.management.resources.agent;
import util.PropertyBean;
import util.UrlBuilder;


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
	public List<String> getAgentClasses(AgentCenter agentCenter) {
//		Client client = ClientBuilder.newClient();
		String targetUrl = UrlBuilder.getUrl(agentCenter.getAddress(), "slave", "classes");
		Response response = client.target(targetUrl).request().get();	//TODO
		List<String> agentClasses = response.readEntity(List.class);
		
		agentRepo.addAgentTypes(agentCenter.getAlias(), agentClasses);
		return agentClasses;
	}

	@Override
	public void postNewNode(AgentCenter newAgentCenter) {
		nodeRepo.getAgentCenters()
			.forEach( ac -> {
				if(!ac.equals(newAgentCenter)) {
					String targetUrl = UrlBuilder.getUrl(ac.getAddress(), "slave", "node");
					client.target(targetUrl).request().post(Entity.json(newAgentCenter));
				}
			});
	}

	@Override
	public void postNewAgentClasses(List<String> agentTypeList) {
		
	}

	@Override
	public void postExistingNodes(List<AgentCenter> agentCenterList) {
		
	}

	@Override
	public void postExistingAgentClasses(List<String> agentTypeList) {
		
	}

	@Override
	public void postRunningAgents() {
		
	}

	@Override
	public void deleteNode(String alias) {
		
	}


}
