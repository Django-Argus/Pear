package net.argus.pear.server;

import java.io.IOException;

import net.argus.beta.com.CardinalSocket;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Link {
	
	private CardinalSocket first, second;
	
	public Link(CardinalSocket first, CardinalSocket second) {
		this.first = first;
		this.second = second;
		ThreadManager.startThread(new Thread(getFirstRunnable()));
		ThreadManager.startThread(new Thread(getSecondRunnable()));
	}
	
	private Runnable getFirstRunnable() {
		return () -> {
			try {
				while(true) {
					String n = first.nextString();
					if(accept(n))
						second.send(n);
				}
			}catch(IOException e) {Debug.log("Connection close");}
		};
	}
	
	private Runnable getSecondRunnable() {
		return () -> {
			try {
				while(true) {
					String n = second.nextString();
					if(accept(n))
						first.send(n);
				}
			}catch(IOException e) {Debug.log("Connection close");}

		};
	}
	
	public boolean accept(String message) {
		return true;
	}
	
	public CardinalSocket getFirstCardinalSocket() {
		return first;
	}
	
	public CardinalSocket getSecondCardinalSocket() {
		return second;
	}

}
