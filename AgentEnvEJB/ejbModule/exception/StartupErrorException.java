package exception;

public class StartupErrorException extends RuntimeException {

	public StartupErrorException() {}
	
	public StartupErrorException(String message) {
		super(message);
	}
	
}
