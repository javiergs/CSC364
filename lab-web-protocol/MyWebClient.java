package old;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyWebClient {
	
	public static void main(String[] args) {
		final String host = "www.javiergs.info";
		final int port = 80;
		try {
			Socket socket = new Socket(host, port);
			
			// requesting a document from the server
			OutputStream out = socket.getOutputStream();
			out.write(("GET / HTTP/1.1\r\n" +
				"Host: " + host + "\r\n" +
				"Connection: close\r\n" +
				"\r\n").getBytes());
			out.flush();
			
			InputStream inputStream = socket.getInputStream();
			DataInputStream in = new DataInputStream(inputStream);
			int b;
			while ((b = in.read()) != -1) {
				System.out.print((char) b);
			}
		} catch (Exception ex) {
			System.out.println("Something went wrong: " + ex);
		}
	}

}



