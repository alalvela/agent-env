package beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import domain.AgentCenter;

@Singleton
@LocalBean
public class NodeRepositoryBean implements NodeRepositoryBeanLocal {

	Map<String, AgentCenter> nodes;
	
	@PostConstruct
	private void init() {
		nodes = new HashMap<>();
	}

	@Override
	@Lock(LockType.WRITE)
	public void addNode(AgentCenter ac) {
		nodes.put(ac.getAlias(), ac);
	}

	@Override
	@Lock(LockType.WRITE)
	public void removeNode(String acAlias) {
		nodes.remove(acAlias);
	}

	@Override
	@Lock(LockType.READ)
	public AgentCenter getNode(String alias) {
		return nodes.get(alias);
	}

	@Override
	public boolean containsNode(String alias) {
		return nodes.containsKey(alias);
	}
	
	
	//TEST -------------
	@Override
	public Map<String, AgentCenter> getAll() {
		return nodes;
	}

	@Override
	public List<AgentCenter> getAgentCenters() {
		return nodes.values().stream().collect(Collectors.toList());
	}

}
