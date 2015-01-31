import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Window;


public class Main {

	public static void main(String[] args) throws AWTException {
		Robot mouse = new Robot();
		mouse.mouseMove(100, 100);
		
		
		
		Window w=new Window(null)
		{
		  @Override
		  public void paint(Graphics g)
		  {
		    final Font font = getFont().deriveFont(48f);
		    g.setFont(font);
		    g.setColor(Color.GREEN);
		    final String message = "Hello";
		    FontMetrics metrics = g.getFontMetrics();
		    g.drawString(message,
		      (getWidth()-metrics.stringWidth(message))/2,
		      (getHeight()-metrics.getHeight())/2);
		  }
		  @Override
		  public void update(Graphics g)
		  {
		    paint(g);
		  }
		};
		w.setAlwaysOnTop(true);
		w.setBounds(w.getGraphicsConfiguration().getBounds());
		w.setBackground(new Color(0, true));
		w.setVisible(true);
	}
}
