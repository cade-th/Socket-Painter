

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class PainterListener implements Runnable{
	Socket s;
	ArrayList<Socket> sockL;
	ArrayList<PaintingPrimitive> shapes;
	ArrayList<String> messages;
	
	public PainterListener(Socket s, ArrayList<Socket> sockL, ArrayList<PaintingPrimitive> shapes, ArrayList<String> messages) {
		this.s = s;
		this.sockL = sockL;
		this.shapes = shapes;
		this.messages = messages;
	}

	// Listens for incoming objects then updates all other painters
	@Override
	public void run() {
		while (sockL.contains(s)) {
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Object o = ois.readObject();

				if (o instanceof PaintingPrimitive) {
					//System.out.println("Adding a shape from a SocketPainter to Hub");
					shapes.add((PaintingPrimitive)o);
				}else if (o instanceof String) {
					messages.add((String)o);
				}
				
				for (Socket sock : sockL) {
					if (!sock.equals(s)) {
						ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
						oos.writeObject(o);
					}
				}
				
			} catch (IOException e) {
				System.out.println("User disconnected");
				sockL.remove(s);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
