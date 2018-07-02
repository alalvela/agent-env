package node2node;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import util.PropertyBean;
import util.UrlBuilder;


@Singleton
@Startup
@LocalBean
@DependsOn("PropertyBean")
public class StarterBean {
	
	@EJB
	private PropertyBean propertyBean;
	
	@EJB
	private SlaveStarterServiceBeanLocal slaveService;
	
	Timer timer;
	
	@PostConstruct
	private void init() {	
		
		if (!propertyBean.getLocal().equals(propertyBean.getMaster())) {
			startRegistration(10);
		} else {
			System.out.println("This is master");
		}
	}
	
	@PreDestroy
	private void destroy() {
		
		if (!propertyBean.getLocal().equals(propertyBean.getMaster())) {
//			slaveService.unregister();   NE MOZE JER UNISTI OVAJ BEAN PRIJE POZIVA!!
		
			unregister();
			//i makni running
		}
	}
	
	private void startRegistration(int sec) {
		timer = new Timer();
		timer.schedule(new InitRegistrationTask(), sec*1000);
	}
	
	private void unregister() {
		Client client = ClientBuilder.newClient();
		String targetUrl = UrlBuilder.getUrl(propertyBean.getMaster().getAddress(), "master", "unregister/" + propertyBean.getLocal().getAlias());
		client.target(targetUrl).request().delete();
	}
	
	class InitRegistrationTask extends TimerTask {
        public void run() {
            System.out.println("Starting registration...");
            if (!slaveService.register()) {
            	slaveService.unregister();
            }
            timer.cancel();
        }
    }
   
}
