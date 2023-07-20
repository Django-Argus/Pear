package net.argus.pear.server;

import java.util.List;

import net.argus.beta.com.CardinalSocket;
import net.argus.cjson.CJSON;

public abstract class Dispacher {

	private int size;
	private int depth;
	
	public Dispacher(int size, int depth) {
		this.size = size;
		this.depth = depth;
	}
	
	public abstract int dispatch(CJSON parameters, WaitingList waiting);
	
	public abstract Link link(List<CardinalSocket> lists);

	public int getSize() {
		return size;
	}
	
	public int getDepth() {
		return depth;
	}
	
}
