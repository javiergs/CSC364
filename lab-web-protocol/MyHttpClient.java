import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpClient {
	
	public static void main(String[] args) {
		final String host = "www.javiergs.info";
		try {
			URL url = new URL("http://" + host + "/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			InputStream inputStream = connection.getInputStream();
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
