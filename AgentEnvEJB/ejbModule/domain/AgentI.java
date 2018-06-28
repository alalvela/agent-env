package domain;

import java.io.Serializable;

public interface AgentI extends Serializable {
	
	void start();

	void handleMessage(ACLMessage message);
	
	void stop();
	
}
