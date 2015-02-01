import static java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AirPenServer {
	public static void main(String[] args) throws AWTException, IOException{
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		boolean isPerPixelTranslucencySupported = gd
				.isWindowTranslucencySupported(PERPIXEL_TRANSLUCENT);

		// If translucent windows aren't supported, exit.
		if (!isPerPixelTranslucencySupported) {
			System.out.println("Per-pixel translucency is not supported");
			System.exit(0);
		}

		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create the GUI on the event-dispatching thread
		final AirPenFrame frame = new AirPenFrame();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(1008);
			Socket client = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String coord = null;
			AirPenProtocol protocol = new AirPenProtocol(frame.getTransparentPanel());
			while(true){
				coord = in.readLine();
				protocol.execute(coord);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(server!= null)
				server.close();
		}
	}
}
