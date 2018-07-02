package node2agent;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;

import domain.ACLMessage;


@Stateless
@LocalBean
public class JMSClient {

	private final String CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private final String DESTINATION = "jms/queue/TestQueue";

    //test
    public void sendStringMessage(String hostAddress, String message) throws NamingException, JMSException {
    	InitialContext ctx = getInitialContext(hostAddress);
    
    	ActiveMQConnectionFactory cf = (ActiveMQConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
    	ActiveMQQueue queue = (ActiveMQQueue) ctx.lookup(DESTINATION);
    	
    	JMSContext jmsCtx = cf.createContext();

    	Message msg = jmsCtx.createMessage();	//switch to ObjectMessage
    	msg.setStringProperty("message", message);
    	jmsCtx.createProducer().send(queue, msg);
    }
    
    public void sendMessageToAgent(String hostAddress, ACLMessage aclMsg, int ordinal) throws NamingException, JMSException {
    	InitialContext ctx = getInitialContext(hostAddress);
    
    	ActiveMQConnectionFactory cf = (ActiveMQConnectionFactory) ctx.lookup(CONNECTION_FACTORY);
    	ActiveMQQueue queue = (ActiveMQQueue) ctx.lookup(DESTINATION);
    	
    	JMSContext jmsCtx = cf.createContext();

    	ObjectMessage msg = jmsCtx.createObjectMessage();	//switch to ObjectMessage
    	msg.setObject(aclMsg);
    	msg.setIntProperty("ordinal", ordinal);
    	jmsCtx.createProducer().send(queue, msg);
    }

    private InitialContext getInitialContext(String hostAddress) throws NamingException {
    	String port = hostAddress.split(":")[1];
    	
    	final Properties env = new Properties();
    	env.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
    	env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:" + port);
    	
    	return new InitialContext(env);
    }

}
