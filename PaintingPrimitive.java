

import java.awt.*;
import java.io.Serializable;

public abstract class PaintingPrimitive implements Serializable {
	private static final long serialVersionUID = -4639051841283550961L;

	Color color;
	
	public PaintingPrimitive(Color c) {
		color = c;
	}
	
	public final void draw(Graphics g) {
	    g.setColor(this.color);
	    drawGeometry(g);
	}

	protected abstract void drawGeometry(Graphics g);
	
}
