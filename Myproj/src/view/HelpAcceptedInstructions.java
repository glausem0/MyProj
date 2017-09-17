package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpAcceptedInstructions {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void helpFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpAcceptedInstructions window = new HelpAcceptedInstructions();
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
	public HelpAcceptedInstructions() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 788, 723);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 21, 740, 619);
		frame.getContentPane().add(textArea);
		
		JScrollPane scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(10, 21, 740, 619);
		frame.getContentPane().add(scrollBar);
		
		textArea.setText("Accepted instructions\n \n"
						+"Movement: \n"
						+"MOV<cond>{S} \n"
						+"MVN<cond>{S} \n \n"
						+"Arithmetique: \n"
						+"ADD<cond>{S} \n"
						+"ADC<cond>{S} \n"
						+"SUB<cond>{S} \n"
						+"SBC<cond>{S} \n"
						+"RSB<cond>{S} \n"
						+"RSC<cond>{S} \n"
						+"MLA<cond>{S} \n"
						+"MUL<cond>{S} \n"
						+"SMLAL<cond>{S} \n"
						+"SMULL<cond>{S} \n"
						+"UMLAL<cond>{S} \n"
						+"UMULL<cond>{S} \n"
						+"\n"
						+"Comparison: \n"
						+"CMP<cond> \n"
						+"CMN<cond> \n"
						+"TEQ<cond> \n"
						+"TST<cond> \n"
						+"\n"
						+"Logic: \n"
						+"AND<cond>{S} \n"
						+"BIC<cond>{S} \n"
						+"EOR<cond>{S} \n"
						+"ORR<cond>{S} \n"
						+"\n"
						+"Load and store: \n"
						+"LDR<cond>{S}{B|H} \n"
						+"STR<cond>{S}{B|H} \n \n"
						+"Load multiple words and store multiple words: \n"
						+"LDM<cond><amode> \n"
						+"STM<cond><amode> \n"
						+"\n"
						+"Swap a word: \n"
						+"SWP<cond> \n"
						+"\n"
						+"Branch: \n"
						+"B<cond> .label\n"
						+"BL<cond> .label\n"
						+"\n"
						+"SWI\n"
						+"SWI<cond> \n"
						
						);
		
	}
}
