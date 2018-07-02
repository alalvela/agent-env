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
import util.PropertyBean;
import util.UrlBuilder;


@Stateless
@LocalBean
public class RestClientSlave {
	
	@EJB
	private PropertyBean props;

	private Client client;
	
	@PostConstruct
	private void init() {
		client = ClientBuilder.newClient();
	}

	public void notifyMasterOfNewRunning(AID aid) {
		String targetUrl = UrlBuilder.getUrl(props.getMaster().getAddress(), "agents/master", "running");
		client.target(targetUrl).request().post(Entity.json(aid));
	}
	
	public void notifyMasterOfKillRunning(AID aid) {
		String targetUrl = UrlBuilder.getUrl(props.getMaster().getAddress(), "agents/master", "running/" + aid.formatAID());
		client.target(targetUrl).request().delete();
	}

}