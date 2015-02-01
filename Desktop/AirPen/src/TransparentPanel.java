import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class TransparentPanel extends JPanel implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private Point point1;
	private Point point2;
	private Line2D line2d;
	private Color lineColor;
	private BasicStroke lineStroke;

	public TransparentPanel() {
		super();
		
		line2d = new Line2D.Double();
		lineColor = Color.YELLOW;
		lineStroke = new BasicStroke(5.0f);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public Color getLineColor(){
		return this.lineColor;
	}
	
	public void setLineColor(Color c){
		this.lineColor = c;
	}
	
	public BasicStroke getLineStroke(){
		return this.lineStroke;
	}
	
	public void setLineStroke(BasicStroke stroke){
		this.lineStroke = stroke;
	}
	
	public void clearScreen(){
		
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (point1 != null && point2 != null) {
			g2d.setPaint(lineColor);
			g2d.setStroke(lineStroke);
			g2d.draw(line2d);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		point2 = e.getPoint();
		line2d.setLine(point1, point2);
		point1 = e.getPoint();

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2)
			clearScreen();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		point1 = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}