package starter;

import javax.ejb.Local;

import domain.AgentCenter;

@Local
public interface SlaveStarterServiceBeanLocal {

	void register();
	
}
