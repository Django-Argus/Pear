package net.argus.pear.server;

import java.io.IOException;

import net.argus.beta.com.server.Server;
import net.argus.cjson.CJSONParser;
import net.argus.event.com.server.ServerEvent;
import net.argus.event.com.server.ServerListener;

public class ServerPear extends Server implements ServerListener {
	
	private Dispacher dispacher;
	
	private WaitingList waiting;

	public ServerPear(int port, Dispacher dispacher) throws IOException {
		super(port);
		this.dispacher = dispacher;
		this.waiting = new WaitingList(dispacher);
		
		open();
		addServerListener(this);
	}

	@Override
	public void newClient(ServerEvent e) {
		try {
			int d = dispacher.dispatch(CJSONParser.getCJSON(e.getSocket().nextString()), waiting);
			if(d < 0 || d >= dispacher.getSize())
				return;
			
			waiting.add(d, e.getSocket());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public WaitingList getWaiting() {
		return waiting;
	}
	
	public Dispacher getDispacher() {
		return dispacher;
	}

}
