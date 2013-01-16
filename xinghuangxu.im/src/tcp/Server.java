package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	private final int BUFSIZE = 32;

	private int servPort;

	public Server(int servPort) {
		this.servPort = servPort;
	}

	@Override
	public void run() {
		ServerSocket servSock;
		try {
			servSock = new ServerSocket(servPort);

			byte[] byteBuffer = new byte[BUFSIZE]; // Receive buffer

			for (;;) { // Run forever, accepting and servicing connections
				Socket clntSock = servSock.accept(); // Get client connection

				byteBuffer = new byte[BUFSIZE];

				System.out.println("Handling client at "
						+ clntSock.getInetAddress().getHostAddress()
						+ " on port " + clntSock.getPort());

				InputStream in = clntSock.getInputStream();
				OutputStream out = clntSock.getOutputStream();

				// Receive until client closes connection, indicated by -1
				// return
				while ((in.read(byteBuffer)) != -1) {
					out.write(byteBuffer);
					System.out.println("New Message: "+new String(byteBuffer));
					byteBuffer = new byte[BUFSIZE];
				}

				clntSock.close(); // Close the socket. We are done with this
									// client!
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
