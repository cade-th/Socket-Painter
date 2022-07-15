

import java.awt.*;
import java.io.Serializable;

public class Circle extends PaintingPrimitive implements Serializable{
	private static final long serialVersionUID = 6307508081804280634L;

	private Point center;
	private Point radiusPoint;

	public Circle(Color c, Point center, Point radiusPoint) {
		super(c);
		this.center = center;
		this.radiusPoint = radiusPoint;

	}

	@Override
	protected void drawGeometry(Graphics g) {
		int radius = (int) Math.abs(center.distance(radiusPoint));
        g.drawOval(center.x - radius, center.y - radius, radius*2, radius*2);    
	}

}

