import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class MyWebServer {
	
	public static void main(String[] args) throws IOException {
		final int port = 8080;
		try (ServerSocket server = new ServerSocket(port)) {
			System.out.println("MiniWebServer listening on http://localhost:" + port);
			int counter = 0;
			while (true) {
				Socket client = server.accept(); // <-- blocking
				System.out.println("\nAccepted connection #" + counter +
					" from " + client.getInetAddress() + ":" + client.getPort());
				handleClient(client, counter);
				counter++;
			}
		}
	}
	
	private static void handleClient(Socket client, int counter) {
		try {
			BufferedReader in = new BufferedReader(
				new InputStreamReader(client.getInputStream(), StandardCharsets.US_ASCII));
			OutputStream out = client.getOutputStream();
			// Read request line + headers until blank line
			String requestLine = in.readLine();
			if (requestLine == null || requestLine.isBlank()) return;
			System.out.println("Request-Line: " + requestLine);
			String line;
			while ((line = in.readLine()) != null && !line.isBlank()) {
				// Print a couple of headers so students see it's "real" HTTP
				if (line.toLowerCase().startsWith("host:")
					|| line.toLowerCase().startsWith("user-agent:")
					|| line.toLowerCase().startsWith("accept:")) {
					System.out.println("Header: " + line);
				}
			}
			Thread.sleep(8000);
			String body = """
				<!doctype html>
				<html>
				  <head><title>MiniWebServer</title></head>
				  <body>
				    <h1>Hello from Java 👋</h1>
				    <p>Request # %d</p>
				    <p>Time: %s</p>
				    <p>This server is single-threaded. Open two tabs quickly and watch the delay.</p>
				  </body>
				</html>
				""".formatted(counter, Instant.now());
			byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
			String headers =
				"HTTP/1.1 200 OK\r\n" +
					"Content-Type: text/html; charset=utf-8\r\n" +
					"Content-Length: " + bodyBytes.length + "\r\n" +
					"Connection: close\r\n" +
					"\r\n";
			out.write(headers.getBytes(StandardCharsets.US_ASCII));
			out.write(bodyBytes);
			out.flush();
		} catch (Exception e) {
			System.out.println("Error handling client: " + e);
		}
	}
	
}

