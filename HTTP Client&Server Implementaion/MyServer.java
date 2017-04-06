import java.util.*;
import java.net.*;
import java.io.*;

class Server extends Thread {
	Socket client;
	PrintStream printStream;
	DataInputStream dataInputStream;

	Server(Socket client) {
		this.client = client;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		String request = "";
		String response = "";
		String lines = "";
		String filename = "";
		String method = "";
		String temp[];
		String path = "Server_Directory";
		byte[] b = new byte[2048];
		int i;
		try {
			System.out.println("New Connection created: " + client.getRemoteSocketAddress());
			dataInputStream = new DataInputStream(client.getInputStream());
			request = dataInputStream.readLine();
			dataInputStream.readLine();
			dataInputStream.readLine();
			lines = request.split("\n")[0];
			temp = lines.split(" ");
			method = temp[0];
			temp = temp[1].split("/");
			filename = temp[temp.length - 1];
			File f = new File(path);
			if (!f.exists()) {
				f.mkdir();
			}
			path = path + "/" + filename;
			f = new File(path);
			if (method.equalsIgnoreCase("get")) {
				if (f.exists()) {
					response = "HTTP/1.1 200 OK\r\nDate: " + new Date() + "\r\n\r\n";
					FileInputStream fileInputStream = new FileInputStream(path);
					while ((i = fileInputStream.read(b)) != -1) {
						response += new String(b, 0, i) + "\n";
					}
					fileInputStream.close();

				} else {
					response = "HTTP/1.1 404 Not Found\r\nDate: " + new Date() + "\r\n";
				}
			} else if (method.equalsIgnoreCase("put")) {
				f = new File(path);
				if (!f.exists()) {
					f.createNewFile();
				}
				int size = Integer.parseInt(dataInputStream.readLine());
				FileOutputStream fileOutputStream = new FileOutputStream(path);
				while (size > 0) {
					i = dataInputStream.read(b);
					fileOutputStream.write(b, 0, i);
					size--;
				}
				fileOutputStream.close();
				response = "HTTP/1.1 200 OK File Created Successfully\r\nDate: " + new Date() + "\r\n\r\n";
			} else {
				response = "HTTP/1.1 301 Bad Request\r\nDate: " + new Date() + "\r\n\r\n";
			}
			printStream = new PrintStream(client.getOutputStream());
			printStream.println(response);
			Thread.sleep(1000);
			System.out.println("Connection: " + client.getRemoteSocketAddress() + " Closed");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				printStream.close();
				dataInputStream.close();
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MyServer.remove(this);
	}
}

public class MyServer {

	private static ArrayList<Server> arrayList;
	static {
		arrayList = new ArrayList<Server>();
	}

	private static void stopResources(ServerSocket serverSocket) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("!!!!!Closing the all Threads!!!!!");
					for (int i = 0; i < arrayList.size(); i++) {
						Server server = (Server) arrayList.get(i);
						server.printStream.close();
						server.dataInputStream.close();
						server.client.close();
					}
					System.out.println("!!!!!!Shutting down the Server!!!");
					Thread.sleep(1500);
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}));
	}

	public static void remove(Server t) {
		arrayList.remove(t);
	}

	public static void main(String[] args) {
		int port = 0;
		if (args.length != 1) {
			System.out.println("Please enter in the correct format");
			return;
		} else {
			port = Integer.parseInt(args[0]);
		}
		Server connection;
		ServerSocket serverSocket = null;
		Socket client = null;
		try {
			// opening the server socket
			serverSocket = new ServerSocket(port);
			stopResources(serverSocket);
			System.out.println("!!!!!!!!!!!!!!!!Server is Ready!!!!");
			while (true) {
				client = serverSocket.accept();
				if (client != null) {
					connection = new Server(client);
					arrayList.add(connection);
					connection.start();
				} else {
					System.out.println("!!!!!!!!!!!Error!!!!!!!!!!!!!!");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
