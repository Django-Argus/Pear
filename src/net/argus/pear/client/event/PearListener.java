package net.argus.pear.client.event;

import net.argus.util.Listener;

public interface PearListener extends Listener {
	
	public void newMessage(PearEvent e);

}
