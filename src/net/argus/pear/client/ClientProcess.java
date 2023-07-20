package net.argus.pear.client;

import java.io.IOException;

import net.argus.beta.com.CardinalSocket;
import net.argus.pear.PackagePear;
import net.argus.pear.client.event.EventPear;
import net.argus.pear.client.event.PearEvent;
import net.argus.pear.client.event.PearListener;
import net.argus.util.debug.Debug;

public class ClientProcess extends Thread {
	
	private CardinalSocket socket;
	private EventPear event = new EventPear();
	
	private Object[] objs;
	
	public ClientProcess(CardinalSocket socket, Object ... objs) {
		this.socket = socket;
		this.objs = objs;
	}
	
	@Override
	public void run() {
		try {
			socket.send(PackagePear.getParametersPackage(objs));
			
			while(!socket.isClosed()) {
				String message = socket.nextString();
				if(message.equals(PackagePear.getPingPackage()))
					continue;
				event.startEvent(EventPear.NEW_MESSAGE, new PearEvent(message));
			}
		}catch(IOException e) {Debug.log("Close");}
	}
	
	public void addPearListener(PearListener listener) {
		event.addListener(listener);
	}
	
	public CardinalSocket getSocket() {
		return socket;
	}

}
