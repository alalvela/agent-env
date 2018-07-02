package client2node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import domain.AID;
import domain.Agent;
import domain.AgentCenter;
import domain.AgentType;
import repository.NodeRepositoryBeanLocal;
import util.PropertyBean;

@Stateless
@LocalBean
public class AgentBuilderBean implements AgentBuilderBeanLocal {

	private final String SEPARATOR = ":";
	private final String AGENT_PACKAGE = "agents.";
	
	@EJB
	private PropertyBean props;
	
	@EJB
	private NodeRepositoryBeanLocal nodeRepo;

	@Override
	public Agent buildAgentFromClassName(String agentName, String nodeAlias, String module, String agentType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String className = AGENT_PACKAGE.concat(agentType);
		
		AgentCenter ac = nodeRepo.getNode(nodeAlias);
		if (ac == null && nodeAlias.equals(props.getMaster().getAlias())) {
			ac = props.getMaster();
		} else {
			ac = props.getLocal();
		}
		
		AgentType at = new AgentType(agentType, module);
		AID aid = new AID(agentName, ac, at);
		
		Class<?> clazz = Class.forName(className);
		Constructor<?> constr = clazz.getConstructor(AID.class);
		Agent agObj = (Agent)constr.newInstance(new Object[] { aid });

		return agObj;
	}

}
