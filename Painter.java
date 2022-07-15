

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Painter extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
	// Probably should have these instantiated in the constructor instead
	private static final long serialVersionUID = 6587076914158540385L;

	protected Color penColor;
	protected String shape;
	
	protected Point shapeStart;
	protected PaintingPrimitive currentShape;

	protected PaintingPanel paintingPanel;
	
	protected JTextArea chat;
	private JTextArea input;
	private String name;
	

	public static void main(String[] args) {
		new Painter();
	}

	public Painter(){
		// Get username from user
		name = JOptionPane.showInputDialog("Enter your name");
		
		// Instantiating instance variables to default values
		penColor = Color.RED;
		shape = "line";

		// Set some painter values
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(720, 720);

		JPanel holder = new JPanel();
		holder.setLayout(new BorderLayout());

		// Create the paints 
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1)); // 3 by 1

		// add red paint button
		JButton redPaint = new JButton();
		redPaint.setBackground(Color.RED);
		redPaint.setOpaque(true);
		redPaint.setBorderPainted(true);
		// Action Listener
		redPaint.addActionListener(this);
		redPaint.setActionCommand("red");
		// Add to panel
		leftPanel.add(redPaint);  // Added in next open cell in the grid
		
		// add green paint button
		JButton greenPaint = new JButton();
		greenPaint.setBackground(Color.GREEN);
		greenPaint.setOpaque(true);
		greenPaint.setBorderPainted(true);
		// Action Listener
		greenPaint.addActionListener(this);
		greenPaint.setActionCommand("green");
		// Add to panel
		leftPanel.add(greenPaint);  // Added in next open cell in the grid

		// add blue paint button
		JButton bluePaint = new JButton();
		bluePaint.setBackground(Color.BLUE);
		bluePaint.setOpaque(true);
		bluePaint.setBorderPainted(true);
		// Action Listener
		bluePaint.addActionListener(this);
		bluePaint.setActionCommand("blue");
		// Add to panel
		leftPanel.add(bluePaint);  // Added in next open cell in the grid

		// Add leftPanel to holder
		holder.add(leftPanel, BorderLayout.WEST);

		// topPanel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));
		
		// Add Line button
		JButton line = new JButton();
		line.setOpaque(true);
		line.setBorderPainted(true);
		line.setLabel("Line");
		// Action Listener
		line.addActionListener(this);
		line.setActionCommand("line");
		// Add to panel
		topPanel.add(line);

		// Add Circle button
		JButton circle = new JButton();
		circle.setOpaque(true);
		circle.setBorderPainted(true);
		circle.setLabel("Circle");
		// Action Listener
		circle.addActionListener(this);
		circle.setActionCommand("circle");
		// Add to panel
		topPanel.add(circle);
		
		// add panels to the overall panel holder
		holder.add(topPanel, BorderLayout.NORTH);

		// PaintingPanel
		paintingPanel = new PaintingPanel();
		paintingPanel.addMouseListener(this);
		paintingPanel.addMouseMotionListener(this);
		holder.add(paintingPanel, BorderLayout.CENTER);

		// chatPanel
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		
		// Creating text box for typing messages
		input = new JTextArea("", 2, 52);
		input.setBorder(BorderFactory.createLineBorder(Color.black));
		input.setBackground(new Color(140, 140, 140));

		// Creating Send Message button
		JButton submit = new JButton();
		submit.setLabel("Send Message");
		submit.addActionListener(this);
		submit.setActionCommand("send");

		// Creating chat
		chat = new JTextArea("", 5, 100);
		chat.setBackground(new Color(110,110,110));
		chat.setBorder(BorderFactory.createLineBorder(Color.black));
		chat.setEditable(false);
		
		// Making chat window scrollable
		chat.setAutoscrolls(true);
		JScrollPane scrollChat = new JScrollPane (chat);
		scrollChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        // Adding to chatPanel
		chatPanel.add(input, BorderLayout.WEST);
		chatPanel.add(submit, BorderLayout.EAST);
		chatPanel.add(scrollChat, BorderLayout.SOUTH);
		
		// Adding chatPanel to holder
		holder.add(chatPanel, BorderLayout.SOUTH);

		// Lastly, connect the holder to the JFrame
		setContentPane(holder);

		// And make it visible to layout all the components on the screen
		setVisible(true);
	}

	// simply adds passed shapes to paintingPanel without having to have access to it
	public void addShape(PaintingPrimitive shape) {
		paintingPanel.addPrimitive(shape);
	}

	// adds messages to chat box
	public void sendMessage(String message) {
		chat.append(message);;
	}

	// Creating initial shapes on mouse press
	@Override
	public void mousePressed(MouseEvent e) {
		shapeStart = e.getPoint();
		switch (shape) {
			case "line":
				currentShape = new Line(penColor, e.getPoint(), e.getPoint());
				this.paintingPanel.addPrimitive(currentShape);
				break;
			case "circle":
				currentShape = new Circle(penColor, e.getPoint(), e.getPoint());
				this.paintingPanel.addPrimitive(currentShape);
				break;
		}
	}

	// Updating shapes on mouse drag
	@Override
	public void mouseDragged(MouseEvent e) {
		PaintingPrimitive oldShape = currentShape;
		switch (shape) {
			case "line":
				currentShape = new Line(penColor, shapeStart, e.getPoint());
				this.paintingPanel.updatePrimitive(oldShape, currentShape);
				break;
			case "circle":
				currentShape = new Circle(penColor, shapeStart, e.getPoint());
				this.paintingPanel.updatePrimitive(oldShape, currentShape);
				break;
		}
		repaint();

	}

	// Drawing final shapes
	@Override
	public void mouseReleased(MouseEvent e) {
		PaintingPrimitive oldShape = currentShape;
		switch (shape) {
			case "line":
				currentShape = new Line(penColor, shapeStart, e.getPoint());
				this.paintingPanel.updatePrimitive(oldShape, currentShape);
				break;
			case "circle":
				currentShape = new Circle(penColor, shapeStart, e.getPoint());
				this.paintingPanel.updatePrimitive(oldShape, currentShape);
				break;
		}
		repaint();
	}

	// Button handling
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "red":
				//System.out.println("Setting Red");
				penColor = Color.RED;
				break;
			case "green":
				//System.out.println("Setting Green");
				penColor = Color.GREEN;
				break;
			case "blue":
				//System.out.println("Setting Blue");
				penColor = Color.BLUE;
				break;
			case "line":
				//System.out.println("Setting Line");
				shape = "line";
				break;
			case "circle":
				//System.out.println("Setting Circle");
				shape = "circle";
				break;
			case "send":
				//System.out.println("Sending message");
				String message = "\n" + name + ": " + input.getText();
				input.setText("");
				sendMessage(message);
				break;
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// NOT USED
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// NOT USED
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// NOT USED
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// NOT USED
	}
}
