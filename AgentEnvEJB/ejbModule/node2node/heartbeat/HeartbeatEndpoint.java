package node2node.heartbeat;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.AgentCenter;
import util.PropertyBean;

@Stateless
@LocalBean
@Path("/node")
public class HeartbeatEndpoint {
	
	@EJB
	private PropertyBean props;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public AgentCenter getNode() {
		return props.getLocal();
	}

	
}
