package starter;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import util.PropertyBean;


@Singleton
@Startup
@LocalBean
@DependsOn("PropertyBean")
public class StarterBean {
	
	@EJB
	private PropertyBean propertyBean;
	
	@EJB
	private SlaveStarterServiceBeanLocal slaveService;
	
	private boolean repeat;		//TODO
	
	Timer timer;
	
	@PostConstruct
	private void init() {	
		repeat = true;
		
		if (!propertyBean.getLocal().equals(propertyBean.getMaster())) {
			startRegistration(10);
		} else {
			System.out.println("This is master");
		}
	}
	
	private void startRegistration(int sec) {
		timer = new Timer();
		timer.schedule(new InitRegistrationTask(), sec*1000);
	}
	
	class InitRegistrationTask extends TimerTask {
        public void run() {
            System.out.println("Starting registration...");
            slaveService.register();	
            timer.cancel();
        }
    }
   
}
