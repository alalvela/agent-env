package domain;

public enum Performative {
	ACCEPT_PROPOSAL,
	AGREE, 
	CANCEL, 
	CALL_FOR_PROPOSAL, 
	CONFIRM, 
	DISCONFIRM, 
	FAILURE, 
	INFORM, 
	INFORM_IF, 
	INFORM_REF, 
	NOT_UNDERSTOOD, 
	PROPAGATE, 
	PROPOSE, 
	PROXY, 
	QUERY_IF, 
	QUERY_REF,
	REFUSE, 
	REJECT_PROPOSAL, 
	REQUEST, 
	REQUEST_WHEN, 
	REQUEST_WHENEVER, 
	SUBSCRIBE;
	
	public static String toString(Performative performative) { 
		return performative.name(); 
	}
	
	public static Performative fromString(String performativeStr) { 
		return Performative.valueOf(performativeStr); 
	}
}
