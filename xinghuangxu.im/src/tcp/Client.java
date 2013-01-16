package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable {

	private String[] serverAddrs;
	private int servPort;

	public Client(String[] serverAddrs, int servPort) {
		this.serverAddrs = serverAddrs;
		this.servPort = servPort;
	}

	@Override
	public void run() {
		// Connect to servers
		List<Socket> sockets = new ArrayList<Socket>();
		// System.out.println("Create Connections:");

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
				System.in));
		for (;;) {

			String input;
			try {
				while ((input = bufferRead.readLine()) != "") {
					for (String server : serverAddrs) {

						try {
							Socket socket = new Socket(server, servPort);
							// sockets.add(socket);

							//String input = bufferRead.readLine();
							byte[] byteBuffer = input.getBytes();

							OutputStream out = socket.getOutputStream();
							out.write(byteBuffer);
							InputStream in = socket.getInputStream();
							if (in.read(byteBuffer) != -1)
								System.out.println("Message: "
										+ new String(byteBuffer) + " Received");
						} catch (Exception ex) {
							System.out.println("Cannot connect with Server: "
									+ server);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
