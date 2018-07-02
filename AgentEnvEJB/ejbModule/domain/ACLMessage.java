package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ACLMessage implements Serializable {

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
	
	@Override
	public String toString() {
		return "ACLMessage [performative=" + performative + ", sender=" + sender + ", recievers=" + recievers
				+ ", replyTo=" + replyTo + ", content=" + content + "]";
	}

}
