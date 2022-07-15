

import java.awt.*;
import java.io.Serializable;

public class Line extends PaintingPrimitive implements Serializable{
	private static final long serialVersionUID = 8994150413384487926L;

	Point startP;
	Point endP;
	
	public Line(Color c, Point s, Point e) {
		super(c);
		startP = s;
		endP = e;
	}

	@Override
	protected void drawGeometry(Graphics g) {
		g.drawLine(startP.x, startP.y, endP.x, endP.y);
		
	}
	
	
}


