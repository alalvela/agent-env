package util;

public class UrlBuilder {
	
	public static String getUrl(String address, String classSegment, String methodSegment) {
		return "http://" + address + "/AgentEnvWeb/api/" + classSegment + "/" + methodSegment ;
	}

}
