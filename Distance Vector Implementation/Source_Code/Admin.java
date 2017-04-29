import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class Admin {

	public static void main(String[] args) {

		HashSet<Integer> hashSetPorts = new HashSet<>();
		if (args.length == 0) {
			System.out.println("Please Include Directory Path");
			return;
		} else if (args.length > 1) {
			System.out.println("Incorrect Input Format.! Only one parameter is required");
			return;
		}

		String PATH = args[0];
		File directory = new File(PATH);

		if (!directory.isDirectory()) {
			System.out.println("Directoy Path is Incorrect");
			return;
		}
		File data[] = directory.listFiles();
		int size = data.length;
		int[] ports = new int[size];
		String allNodes = "";

		System.out.println("Initilization of Port Number to " + size + " Routers");
		Scanner scanner = new Scanner(System.in);

		for (int i = 0; i < size; i++) {
			String val = data[i].getName();
			val = val.substring(0, val.indexOf(".dat"));
			System.out.println("Enter Port No: for Router: " + val);

			boolean status = true;

			while (status) {
				try {
					int num = Integer.parseInt(scanner.nextLine());

					if (num <= 1024 || num >= 65536) {
						throw new NumberFormatException();
					}
					if (hashSetPorts.contains(num)) {
						throw new Exception();
					}
					ports[i] = num;
					hashSetPorts.add(num);
					status = false;
				} catch (NumberFormatException e) {
					System.out.println("Enter a valid Port Number > 1024 && < 65536");
					status = true;
				} catch (Exception e) {
					System.out.println("Address is Already in Use:");
					status = true;
				}
			}
			allNodes += " " + val + ":" + ports[i];
		}

		scanner.close();

		for (int i = 0; i < size; i++) {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start java MainRouter " + (i + 1) + " \""
					+ data[i].getParent().replace("\\", "/") + "\" " + size + allNodes);
			try {
				processBuilder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("DV Algorithm Started");

	}

}
