package net.argus.pear.client.event;

public class PearEvent {
	
	private String message;
	
	public PearEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
