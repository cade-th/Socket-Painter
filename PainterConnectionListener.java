

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PainterConnectionListener implements Runnable{
	ArrayList<Socket> sockL;
	ArrayList<PaintingPrimitive> shapes;
	ArrayList<String> messages;
	ServerSocket ss;
	
	public PainterConnectionListener(ArrayList<Socket> sockL, ServerSocket ss, ArrayList<PaintingPrimitive> shapes, ArrayList<String> messages) {
		this.sockL = sockL;
		this.ss = ss;
		this.shapes = shapes;
		this.messages = messages;
				
	}

	// Accepts new connections and gives them a copy of the state of the program
	@Override
	public void run() {
		while (true){
			System.out.println("Waiting on a call");
			try {
				Socket ns = ss.accept();
				sockL.add(ns);
				ObjectOutputStream oos = new ObjectOutputStream(ns.getOutputStream());
				System.out.println("Attempting to write shapes list to newly connected painter, it contains:");
				System.out.println(shapes);
				oos.writeObject(shapes);
				oos.writeObject(messages);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Painter added");
		}
	}
}
