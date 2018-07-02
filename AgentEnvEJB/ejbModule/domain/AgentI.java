package domain;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface AgentI extends Serializable {
	
	void start();
	
	void stop();
	
}
