package node2node;

import javax.ejb.Local;


@Local
public interface SlaveStarterServiceBeanLocal {

	boolean register();
	
	void unregister();
	
}
