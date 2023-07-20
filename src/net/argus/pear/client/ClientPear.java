package net.argus.pear.client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import net.argus.beta.com.CardinalSocket;
import net.argus.beta.com.client.Client;
import net.argus.pear.client.event.PearListener;

public class ClientPear extends Client {
	
	private ClientProcess process;
	
	public ClientPear(String host, int port) throws UnknownHostException {
		super(host, port);
	}

	
	public CardinalSocket open(Object ... objs) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		CardinalSocket soc = super.open();
		
		process = new ClientProcess(soc, objs);
		process.start();
		
		return soc;
	}
	
	@Override
	public CardinalSocket open() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		throw new RuntimeException("call open(parametres) !");
	}
	
	public void send(String message) throws IOException {
		if(process == null)
			throw new NullPointerException("You must open the client before");
		
		process.getSocket().send(message);
	}
	
	public void addPearListener(PearListener listener) {
		if(process == null)
			throw new NullPointerException("You must open the client before");
		
		process.addPearListener(listener);
	}
	
	public ClientProcess getProcess() {
		return process;
	}
}
