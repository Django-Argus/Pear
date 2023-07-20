package net.argus.pear.client.event;

import net.argus.event.Event;

public class EventPear extends Event<PearListener> {
	
	public static final int NEW_MESSAGE = 0;

	@Override
	public void event(PearListener listener, int event, Object... objs) {
		switch(event) {
			case NEW_MESSAGE:
				listener.newMessage((PearEvent) objs[0]);
				break;
		}
	}

}
