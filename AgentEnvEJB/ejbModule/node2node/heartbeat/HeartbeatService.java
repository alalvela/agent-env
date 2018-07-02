package node2node.heartbeat;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import domain.AgentCenter;
import repository.NodeRepositoryBeanLocal;
import util.PropertyBean;
import util.UrlBuilder;

@Stateless
public class HeartbeatService {
	
	@EJB
	private NodeRepositoryBeanLocal nodeRepo;
	
	@EJB
	private PropertyBean props;
	
	private Client client;

	@PostConstruct
	private void init() {
		client = ClientBuilder.newClient();
	}

 
	@SuppressWarnings("unused")
	@Schedule(second="*/45", minute="*", hour="*", info="MyTimer")
    private void scheduledTimeout(final Timer t) {
        System.out.println("Checking nodes from " + props.getLocal().getAlias());
        this.checkNodes();
    }
	
	private void checkNodes() {
		nodeRepo.getAgentCenters().forEach(ac -> {
			if(!ac.equals(props.getLocal())) {	
				if(!isAlive(ac)) {
					if(!isAlive(ac)) {
						removeNode(ac);
					}
				}
			}
		});
	}


	private boolean isAlive(AgentCenter ac) {
		String targetUrl = UrlBuilder.getUrl(ac.getAddress(), "node", "");
		Response response = client.target(targetUrl).request().get();
		int status = response.getStatus();
		return status > 199 && status < 300;
	}


	private void removeNode(AgentCenter ac) {
		String target = UrlBuilder.getUrl(ac.getAddress(), "master", "unregister/" + ac.getAlias());
		client.target(target).request().delete();
		System.out.println("Node " + ac.getAlias() + " dead!");
	}
}