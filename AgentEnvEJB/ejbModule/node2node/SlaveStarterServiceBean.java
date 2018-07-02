package node2node;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import domain.AgentCenter;
import util.PropertyBean;
import util.UrlBuilder;

/**
 * Session Bean implementation class SlaveNodeServiceBean
 */
@Stateless
@LocalBean
public class SlaveStarterServiceBean implements SlaveStarterServiceBeanLocal {
	
	@EJB
	private PropertyBean props;

	private Client client;
	
	@PostConstruct
	private void init() {
		client = ClientBuilder.newClient();
	}
	
	@Override
	public boolean register() {
		AgentCenter local = props.getLocal();
		String targetUrl = UrlBuilder.getUrl(props.getMaster().getAddress(), "master", "register");
		Response response = client.target(targetUrl).request().post(Entity.json(local));
		
		int status = response.getStatus();
		return status > 199 && status < 300;
	}

	@Override
	public void unregister() {	
		String targetUrl = UrlBuilder.getUrl(props.getMaster().getAddress(), "master", "unregister/" + props.getLocal().getAlias());
		client.target(targetUrl).request().delete();
	}
   
}
