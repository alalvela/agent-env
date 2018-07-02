package client2node;

import java.lang.reflect.InvocationTargetException;

import javax.ejb.Local;

import domain.Agent;

@Local
public interface AgentBuilderBeanLocal {

	Agent buildAgentFromClassName(String agentName, String nodeAlias, String module, String agentType) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
