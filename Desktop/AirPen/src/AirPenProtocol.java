import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class AirPenProtocol {
	private TransparentPanel panel;
	private Robot mouse;
	int minX, minY, maxX, maxY;
	
	public AirPenProtocol(TransparentPanel panel) throws AWTException{
		this.panel = panel;
		this.mouse = new Robot();
		minX = 0;
		minY = 0;
		maxX = 0; 
		maxY = 0;
	}
	
	public void execute(String command){
		if(command.startsWith("s")){
			String stroke = command.split(" ")[1];
			float f = Float.parseFloat(stroke);
			panel.setLineStroke(new BasicStroke(f));
		} else if(command.startsWith("c")){
			String color = command.split(" ")[1];
			if(color.equals("red"))
				panel.setLineColor(Color.RED);
			else if(color.equals("yellow"))
				panel.setLineColor(Color.YELLOW);
			else if(color.equals("blue"))
				panel.setLineColor(Color.BLUE);
		} else if(command.startsWith("on")){
			mouse.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		} else if(command.startsWith("off")){
			mouse.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		} else {
			String[] parts = command.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			
			if(x<minX || x > maxX || y<minY || y>maxY){
				mouse.mouseMove(x, y);
				minX = x - 30;
				minY = y - 30;
				maxX = x + 30;
				maxY = y + 30;
			}
		}
	}
}
