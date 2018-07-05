package ws;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import domain.AID;
import domain.Performative;

public class WSMessage implements Serializable {

	private String action;
	private String object;
	
	public WSMessage(String action, String object) {
		super();
		this.action = action;
		this.object = object;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static String getNewTypeMessage(String alias, List<String> agentClasses) {
		Map<String, List<String>> ret = new HashMap<>();
		ret.put(alias, agentClasses);
		return new Gson().toJson(ret);
	}
	
	public static String getNewMessage(AID sender, AID reciever, String content, Performative performative) {
		return String.format("Message from %s to %s\nPerformative: %s\nContent: %s", sender.toString(), reciever.toString(),
								performative, content);
	}
}
