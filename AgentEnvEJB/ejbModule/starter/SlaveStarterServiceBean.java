package starter;

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
	public void register() {
		AgentCenter local = props.getLocal();
		String targetUrl = UrlBuilder.getUrl(props.getMaster().getAddress(), "master", "register");
		client.target(targetUrl).request().post(Entity.json(local));
	}

   
}
