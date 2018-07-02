package util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import domain.AgentCenter;

@Singleton
@LocalBean
@Startup
public class PropertyBean {

	private final String DELIMITER = ":";
	private Properties props;
	private AgentCenter master;
	private AgentCenter local;
	private List<String> agentTypes;
	
	@PostConstruct
    private void startup() {
		try {
			InputStream propsStream = getClass().getClassLoader().getResourceAsStream("app.properties");
            props = new Properties();
            props.load(propsStream);
            
            String localAddress = props.getProperty("ip-address-local");
            String masterAddress = props.getProperty("ip-address-master");
            String aliasLocal = props.getProperty("alias-local", "master");
            String aliasMaster = props.getProperty("alias-master", "master");
            int portMaster = Integer.valueOf(props.getProperty("port-master", "8080"));
            int portOffset = Integer.valueOf(props.getProperty("port-offset", "0"));
            String agentTypesStr = props.getProperty("agent-types", "Test");	//napravi Test agenta
            
            master = new AgentCenter(masterAddress + ":" + portMaster, aliasMaster);
            local = new AgentCenter(localAddress + ":" + (portMaster + portOffset), aliasLocal);
            
            agentTypes = Arrays.asList(agentTypesStr.split("\\s*,\\s*"));
            int lastIdx = agentTypes.size()-1; 
            agentTypes.set(lastIdx, agentTypes.get(lastIdx).trim());	//remove whitespaces of last if any
            
            System.out.println("master : " + master);
            System.out.println("local : " + local);
            System.out.println("agent types : "  + agentTypes.toString());

		} catch(Exception e) {
			throw new EJBException("PropertyBean initialization error", e);
		}
	}

	public AgentCenter getMaster() {
		return master;
	}

	public AgentCenter getLocal() {
		return local;
	}

	public List<String> getAgentTypes() {
		return agentTypes;
	}
	
	
}
