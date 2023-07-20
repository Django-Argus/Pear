package net.argus.pear.client;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class MainClient {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		ClientPear pear = new ClientPear("localhost", 2354);
		pear.open("team", "black");
	}

}
