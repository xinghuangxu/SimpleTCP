package tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	
	public static void main(String[] args) throws IOException {
		//Get Ip address from files
		BufferedReader in=new BufferedReader(new FileReader("conf"));
		String line=in.readLine();
		int num=line.indexOf(':');
		int port=Integer.parseInt(line.substring(num+1));
		line=in.readLine();
		num=line.indexOf(':');
		String ips= line.substring(num+1);
		String[] ipAddrs=ips.split("\\|");
		
		Runnable client=new Client(ipAddrs,port);
		Runnable server=new Server(port);
		
		Thread cThread=new Thread(client);
		Thread sThread=new Thread(server);
		
		sThread.run();
		
		cThread.run();
		

	}

}
