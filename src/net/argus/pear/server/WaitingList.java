package net.argus.pear.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.argus.beta.com.CardinalSocket;
import net.argus.pear.PackagePear;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class WaitingList  {
	
	private Dispacher dispacher;
	
	private List<List<CardinalSocket>> socks = new ArrayList<List<CardinalSocket>>();
	
	public WaitingList(Dispacher dispacher) {
		this.dispacher = dispacher;
		
		for(int i = 0; i < dispacher.getSize(); i++)
			socks.add(new ArrayList<CardinalSocket>());
		
		ThreadManager.startThread(new Thread(getDaemon()));
	}
	
	public void add(int list, CardinalSocket socket) {
		if(list < socks.size())
			socks.get(list).add(socket);
	}
	
	public void remove(int list, CardinalSocket socket) {
		if(list < socks.size())
			socks.get(list).remove(socket);
	}

	public void remove(int list) {
		if(list < socks.size())
			remove(list, getFirst(list));
	}
	
	public synchronized CardinalSocket getFirst(int list) {
		if(!socks.isEmpty() && list < socks.size())
			return socks.get(list).get(0);
		return null;
	}
	
	
	public synchronized int indexList(CardinalSocket cs) {
		for(int i = 0; i < socks.size(); i++)
			if(socks.get(i).contains(cs))
				return i;
		return -1;
	}
	public void clear() {
		socks.clear();
	}
	
	public List<CardinalSocket> getList(int list) {
		if(list < socks.size())
			return socks.get(list);
		return null;
	}
	
	public List<List<CardinalSocket>> getLists() {
		return socks;
	}
	
	public boolean isEmpty() {
		return socks.isEmpty();
	}
	
	private void killIdle() {
		Map<Integer, CardinalSocket> rm = new HashMap<Integer, CardinalSocket>();
		for(int i = 0; i < socks.size(); i++) {
			for(CardinalSocket socket : socks.get(i)) {
				try {
					socket.send(PackagePear.getPingPackage().toString());
				}catch(IOException e) {
					rm.put(i, socket);
					Debug.log("Client disconnected: kick");
				}
			}
		}
		
		for(Entry<Integer, CardinalSocket> r : rm.entrySet())
			remove(r.getKey(), r.getValue());
	}
	
	private Runnable getDaemon() {
		return () -> {
			int c = 0;
			while(true) {
				List<CardinalSocket> s = new ArrayList<CardinalSocket>();
				for(List<CardinalSocket> sc : socks)
					for(int i = 0; i < dispacher.getDepth(); i++)
						if(!sc.isEmpty())
							s.add(sc.get(i));
						else
							s.add(null);
	
				
				Link link = dispacher.link(s);
				if(link != null) {
					int i = indexList(link.getFirstCardinalSocket());
					if(i >= 0)
						remove(i, link.getFirstCardinalSocket());
					
					i = indexList(link.getSecondCardinalSocket());
					if(i >= 0)
						remove(i, link.getSecondCardinalSocket());
				}
				ThreadManager.sleep(1000);
				
				c++;
				if(c == 10) {
					killIdle();
					c = 0;
				}
			}
		};
	}
	
	public Dispacher getDispacher() {
		return dispacher;
	}
	
	@Override
	public String toString() {
		return socks.toString();
	}

}
