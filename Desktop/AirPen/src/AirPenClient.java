import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AirPenClient {
	public static void main(String[] args) throws IOException{
		Socket client = null;
		try {
			client = new Socket("localhost", 1008);
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			Scanner scanner = new Scanner(System.in);
			String str = null;
			
			while(true){
				System.out.println("Input an coordinate");
				str = scanner.nextLine();
				
				out.println(str);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
	}
}
