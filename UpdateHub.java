

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UpdateHub implements Runnable{
	private Socket s;
	private Object o;

	// Simply updates the hub with an object
	public UpdateHub(Socket s, Object o) {
		this.s = s;
		this.o = o;
	}

	@Override
	public void run() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
