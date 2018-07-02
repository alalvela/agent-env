package client2node;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import com.google.gson.Gson;

import domain.AID;
import domain.Agent;
import repository.NodeRepositoryBeanLocal;
import util.UrlBuilder;


@Stateless
@LocalBean
public class RestClientMaster {

	@EJB
	private NodeRepositoryBeanLocal nodeRepo;
	
	private Client client;
	
	@PostConstruct
	private void init() {
		client = ClientBuilder.newClient();
	}
	
	public void notifyOfNewRunning(AID aid) {
		nodeRepo.getAgentCenters()
			.forEach(ac -> {
				if(!aid.getAgentCenter().equals(ac)) {
					String targetUrl = UrlBuilder.getUrl(ac.getAddress(), "agents/slave", "running");
					client.target(targetUrl).request().post(Entity.json(aid));
				}
			});
	}
	
	public void notifyOfKillRunning(AID aid) {
		nodeRepo.getAgentCenters()
		.forEach(ac -> {
			if(!aid.getAgentCenter().equals(ac)) {	
				String targetUrl = UrlBuilder.getUrl(ac.getAddress(), "agents/slave", "running/" + aid.formatAID());
				client.target(targetUrl).request().delete();
			}
		});
	}
   
}
