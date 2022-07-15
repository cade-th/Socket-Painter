

import java.util.ArrayList;

import javax.swing.JPanel;

import java.awt.*;

public class PaintingPanel extends JPanel{
	private static final long serialVersionUID = 3486673461302348660L;
	ArrayList<PaintingPrimitive> shapes = new ArrayList<PaintingPrimitive>();
	
	public PaintingPanel() {
		this.setBackground(Color.WHITE);
	}
	
	public void addPrimitive(PaintingPrimitive shape) {
		this.shapes.add(shape);
	}
	
	public void updatePrimitive(PaintingPrimitive oldShape, PaintingPrimitive shape) {
		this.shapes.remove(oldShape);
		this.shapes.add(shape);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(PaintingPrimitive shape : shapes) { 
        	shape.draw(g);
        }
	}
}
