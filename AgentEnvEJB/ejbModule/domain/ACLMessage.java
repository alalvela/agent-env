package domain;

import java.util.List;
import java.util.Map;

public class ACLMessage {

	public Performative performative;
	public AID sender;
	public List<AID> recievers;
	public AID replyTo;
	public String content;
	public Object contentObj;
	public Map<String, Object> userArgs;
	public String language;
	public String encoding;
	public String ontology;
	public String protocol;
	public String conversationId;
	public String replyWith;
	public String inReplyTo;
	public long replyBy;

}
