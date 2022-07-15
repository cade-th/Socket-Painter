

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class HubListener implements Runnable{
	Painter p;
	Socket s;
	ArrayList<PaintingPrimitive> shapes;
	
	public HubListener(Painter p, Socket s, ArrayList<PaintingPrimitive> shapes) {
		this.p = p;
		this.s = s;
		this.shapes = shapes;
		
	}

	@Override
	public void run() {
		while (s.isConnected()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Object o = ois.readObject();
				if (o instanceof PaintingPrimitive) {
					PaintingPrimitive shape = (PaintingPrimitive)o;
					if (!shapes.contains(shape)) {
						p.addShape((PaintingPrimitive)o);
						p.repaint();
					}
				}else if (o instanceof String) {
					p.chat.append((String)o);
				}
			} catch (IOException e) {
				System.out.println("Disconnected from Hub");
				System.exit(-1);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		
		
	}

}
