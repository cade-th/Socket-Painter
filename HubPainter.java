

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class HubPainter extends Painter{
	private static final long serialVersionUID = 8363375127565168235L;

	private static ArrayList<Thread> thsL = new ArrayList<Thread>();
	static boolean running = true;

	public static void main(String[] args) {
		ArrayList<Socket> sockL = new ArrayList<Socket>();
		ArrayList<PaintingPrimitive> shapesDrawn = new ArrayList<PaintingPrimitive>();
		ArrayList<String> messagesSent = new ArrayList<String>();
		
		// Create Server Socket
		try {
			ServerSocket ss = new ServerSocket(7000);
			Thread th = new Thread(new PainterConnectionListener(sockL, ss, shapesDrawn, messagesSent));
			thsL.add(th);
			th.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Painter Listeners
		ArrayList<Socket> ManagedSockets = new ArrayList<Socket>();
		while (running) {
			for (Socket s : sockL) {
				if (!ManagedSockets.contains(s)) {
					Thread th = new Thread(new PainterListener(s, sockL, shapesDrawn, messagesSent));
					thsL.add(th);
					th.start();
					ManagedSockets.add(s);
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
}
