package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Client implements Runnable{

	private String[] serverAddrs;
	private int servPort;
	
	public Client(String[]serverAddrs,int servPort) {
		this.serverAddrs=serverAddrs;
		this.servPort=servPort;
	}
	
	@Override
	public void run() {
		//Connect to servers
		List<Socket> sockets=new ArrayList<Socket>();
		System.out.println("Create Connections:");
		for(String server : serverAddrs){
			try{
			Socket socket=new Socket(server,servPort);
			sockets.add(socket);
			}catch(Exception e){
				System.out.println("Cannot connect to server: "+server);
			}
		}
		
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		for(;;){
			try {
				String input = bufferRead.readLine();
				byte[] byteBuffer=input.getBytes();
				for(Socket s : sockets){
					OutputStream out=s.getOutputStream();
					out.write(byteBuffer);
					InputStream in = s.getInputStream();
					if(in.read(byteBuffer)!=-1)
						System.out.println("Message: "+new String(byteBuffer)+" Received");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
