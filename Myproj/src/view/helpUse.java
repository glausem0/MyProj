package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class helpUse {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void helpFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					helpUse window = new helpUse();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public helpUse() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 788, 723);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 21, 740, 619);
		frame.getContentPane().add(textArea);

		JScrollPane scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(10, 21, 740, 619);
		frame.getContentPane().add(scrollBar);
		
		textArea.setText("Instructions: \n \n"
				+"Create a file: \n"
				+ "File the area with desire texte and save contant. \n \n"
				+"Open a file: \n"
				+ "Go to ''File'', ''Open file'', selecte file and open. \n"
				+ "Only .txt are alowed \n \n"
				+"Save a file: \n"
				+ "Go to ''File'', enter the name of the new file, and click ''save''\n \n"
				+ "Run the programm: \n"
				+ "Make sure the file is save first, or open a file. Then go to ''Run'' and click ''Run''. \n"
				+ "The content of registers will be display in ''Register'' window. \n"
				+ "Flags's status are display in ''CPSR'' window.\n"
				+ "If the Memory accessed or a value is set, the content will be display in ''Memory'' window,\n"
				+ "othewise it remained empty. ");

	}

}
