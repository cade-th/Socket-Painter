

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;

public class SocketPainter extends Painter{
	private static final long serialVersionUID = -1119855006017643471L;

	private Socket s = null;
	private ArrayList<Thread> thsL;

	public SocketPainter() {
		// super and variable instantiation
		super();
		ArrayList<PaintingPrimitive> shapes = new ArrayList<PaintingPrimitive>();
		ArrayList<String> messages = new ArrayList<String>();
		thsL = new ArrayList<Thread>();

		// Connecting to hub and getting state of program
		try {
			s = new Socket("localhost", 7000);
			System.out.println("Attempting to read shapes from Hub");
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			shapes = (ArrayList<PaintingPrimitive>)ois.readObject();
			messages = (ArrayList<String>)ois.readObject();
			
		}catch (IOException | ClassNotFoundException e) {
			System.out.println("Connection to Hub refused.");
			System.out.println("Exiting...");
			System.exit(-1);
		}
		
		// Setting state of program according to what was given by hub
		if (!shapes.isEmpty()) {
			System.out.println("Attempting to initialize shapes from hub containing:");
			System.out.println(shapes);
			for (PaintingPrimitive shape : shapes) {
				addShape(shape);
			}
		}
		
		if (!messages.isEmpty()) {
			for (String message : messages) {
				chat.append(message);
			}
		}
		repaint();

		// Listening for updates from hub
		Thread th = new Thread(new HubListener(this, s, shapes));		
		thsL.add(th);
		th.start();
	}
	
	
	public static void main(String[] args) {
		new SocketPainter();
	}
	
	// Overwritten method that also updates the hub
	@Override
	public void sendMessage(String message) {
		chat.append(message);
		Thread thLine = new Thread(new UpdateHub(this.s, message));
		thsL.add(thLine);
		thLine.start();
	}
	
	// Overwritten method that also updates the hub
	@Override
	public void mouseReleased(MouseEvent e) {
		switch (shape) {
			case "line":
				//System.out.println("Adding line object to array list of primitives");
				Line line = new Line(penColor, shapeStart, e.getPoint());
				this.paintingPanel.addPrimitive(line);
				Thread thLine = new Thread(new UpdateHub(this.s, line));
				thsL.add(thLine);
				thLine.start();
				break;
			case "circle":
				//System.out.println("Adding circle object to array list of primitives");
				Circle circle = new Circle(penColor, shapeStart, e.getPoint());
				this.paintingPanel.addPrimitive(circle);
				Thread thCircle = new Thread(new UpdateHub(this.s, circle));
				thsL.add(thCircle);
				thCircle.start();
				
				break;
		}
		repaint();
	}
}
