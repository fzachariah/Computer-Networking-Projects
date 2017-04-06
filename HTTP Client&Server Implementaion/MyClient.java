
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class MyClient {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		String hostName = "";
		String method = "";
		String file = "";
		int port = 0;

		Socket client = null;
		String response = "";
		String request = "";
		String data = "";
		DataInputStream dataInputStream = null;
		PrintStream printStream = null;
		byte[] bs = new byte[2048];
		int i = 0;

		if (args.length == 4) {
			hostName = args[0];
			port = Integer.parseInt(args[1]);
			method = args[2];
			file = args[3];

		} else {
			System.out.println("Invalid Parameters");
		}

		try {
			System.out.println("Connecting to: " + hostName + " on port " + port);
			if (method.toLowerCase().equals("get")) {
				request = method.toUpperCase() + " " + file + " HTTP/1.1\r\nHost: " + hostName + "\r\n";
				client = new Socket(hostName, port);
				printStream = new PrintStream(client.getOutputStream());
				System.out.println(request);
				printStream.println(request);
				dataInputStream = new DataInputStream(client.getInputStream());
				System.out.println("Response from the server: ");
				while ((data = dataInputStream.readLine()) != null) {
					System.out.println(data);
				}
			}

			else if (method.toLowerCase().equals("put")) {
				request = method.toUpperCase() + " " + file + " HTTP/1.1\r\nHost: " + hostName + "\r\n";
				client = new Socket(hostName, port);
				printStream = new PrintStream(client.getOutputStream());
				dataInputStream = new DataInputStream(client.getInputStream());
				File f = new File(file);
				int number = (int) Math.ceil((double) f.length() / bs.length);
				if (!f.exists() || !f.isFile()) {
					System.out.println("File does not exist in the given Path: " + file);
					return;
				}
				printStream.println(request);
				FileInputStream fileInputStream = new FileInputStream(args[3]);
				printStream.println(number);
				while ((i = fileInputStream.read(bs)) != -1) {
					printStream.write(bs, 0, i);
				}
				while ((data = dataInputStream.readLine()) != null) {
					response += data + "\n";
				}
				fileInputStream.close();
				System.out.println("Response from the Server :\n" + response);
			} else {
				System.out.println("Invalid Method!!!!!");
				return;
			}

		} catch (IOException e) {
			System.out.println("IO Exception Occured wiht the connection");

		} finally {

			try {
				dataInputStream.close();
				printStream.close();
				client.close();
			} catch (IOException e) {
				System.out.println("Error Occured:" + e.toString());
			}

		}

	}

}
