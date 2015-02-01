import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class AirPenFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private TransparentPanel panel;

	public AirPenFrame() {
		setBackground(new Color(0, 0, 0, 1));
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new TransparentPanel();
		setContentPane(panel);
	}
	
	public TransparentPanel getTransparentPanel(){
		return this.panel;
	}
}