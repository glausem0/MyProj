package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JToolBar;

import compAndInt.*;

import javax.swing.JTextPane;
import javax.swing.JMenuItem;
import javax.swing.*;

public class view {

	private JFrame frame;
	private JTextField textFieldR0;
	private JTextField textFieldR1;
	private JTextField textFieldR2;
	private JTextField textFieldR3;
	private JTextField textFieldR4;
	private JTextField textFieldR5;
	private JTextField textFieldR7;
	private JTextField textFieldR6;
	private JTextField textFieldR8;
	private JTextField textFieldR9;
	private JTextField textFieldR10;
	private JTextField textFieldR11;
	private JTextField textFieldR12;
	private JTextField textFieldR13;
	private JTextField textFieldR14;
	private JTextField textFieldR15;
	private JTextField textField_N;
	private JTextField textField_Z;
	private JTextField textField_C;
	private JTextField textField_V;
	private JTextField textField_I;
	private JTextField textField_F;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view window = new view();
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
	public view() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1119, 669);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Menu bar:
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnFile.add(mnNew);
		
		JMenuItem mntmNewtxt = new JMenuItem("New text file");
		mnNew.add(mntmNewtxt);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File...");
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mnFile.add(mntmSaveAs);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnRun = new JMenu("Run");
		menuBar.add(mnRun);
		
		JMenuItem mntmRun = new JMenuItem(new AbstractAction("Run"){
			public void actionPerformed(ActionEvent ae){
				 MyTest parser = null;
				try {
					parser = new MyTest(new FileReader("c:/Users/moi/Documents/GitHub/MyProj/Myproj/src/compAndInt/test.txt"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    //MyTest parser = new MyTest(new FileReader("c:/Users/Mélanie/Documents/GitHub/MyProj/Myproj/src/compAndInt/test.txt"));
					SimpleNode root = null;
					try {
						root = parser.prog();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				    System.out.println("Abstract Syntax Tree:");
				    root.dump(" ");

				    System.out.println("Prog:");
				    Visitors vi = new Visitors();
				    root.jjtAccept(vi,null);

				    vi.print();
			}
		});
		mnRun.add(mntmRun);
		
		JMenu mnWindow = new JMenu("Window");
		menuBar.add(mnWindow);
		
		JCheckBox chckbxLl = new JCheckBox("registers");
		mnWindow.add(chckbxLl);
		
		JCheckBox chckbxMemory = new JCheckBox("memory");
		mnWindow.add(chckbxMemory);
		
		JCheckBox chckbxCpsr = new JCheckBox("cpsr");
		mnWindow.add(chckbxCpsr);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		frame.getContentPane().setLayout(null);
		
		
		
		JPanel forTxtFile = new JPanel();
		forTxtFile.setBounds(10, 11, 432, 523);
		frame.getContentPane().add(forTxtFile);
		forTxtFile.setLayout(null);
		
		JPanel viewElements = new JPanel();
		viewElements.setBounds(493, 11, 587, 538);
		frame.getContentPane().add(viewElements);
		viewElements.setBackground(Color.WHITE);
		viewElements.setLayout(null);
		
		JPanel registersPanel = new JPanel();
		registersPanel.setBackground(Color.GRAY);
		registersPanel.setBounds(0, 0, 333, 283);
		viewElements.add(registersPanel);
		registersPanel.setLayout(null);
		
		JTextPane txtpnRegisters = new JTextPane();
		txtpnRegisters.setBounds(0, 0, 51, 20);
		registersPanel.add(txtpnRegisters);
		txtpnRegisters.setText("Registers");
		
		JLabel lblR_r0 = new JLabel("r0");
		lblR_r0.setBounds(10, 33, 41, 14);
		registersPanel.add(lblR_r0);
		
		textFieldR0 = new JTextField();
		textFieldR0.setBounds(62, 30, 86, 20);
		registersPanel.add(textFieldR0);
		textFieldR0.setColumns(10);
		
		textFieldR1 = new JTextField();
		textFieldR1.setColumns(10);
		textFieldR1.setBounds(62, 58, 86, 20);
		registersPanel.add(textFieldR1);
		
		JLabel lblR_r1 = new JLabel("r1");
		lblR_r1.setBounds(10, 61, 41, 14);
		registersPanel.add(lblR_r1);
		
		JLabel lblR_r2 = new JLabel("r2");
		lblR_r2.setBounds(10, 89, 41, 14);
		registersPanel.add(lblR_r2);
		
		textFieldR2 = new JTextField();
		textFieldR2.setColumns(10);
		textFieldR2.setBounds(62, 86, 86, 20);
		registersPanel.add(textFieldR2);
		
		JLabel lblR_r3 = new JLabel("r3");
		lblR_r3.setBounds(10, 117, 41, 14);
		registersPanel.add(lblR_r3);
		
		textFieldR3 = new JTextField();
		textFieldR3.setColumns(10);
		textFieldR3.setBounds(62, 114, 86, 20);
		registersPanel.add(textFieldR3);
		
		JLabel lblR_r4 = new JLabel("r4");
		lblR_r4.setBounds(10, 145, 41, 14);
		registersPanel.add(lblR_r4);
		
		textFieldR4 = new JTextField();
		textFieldR4.setColumns(10);
		textFieldR4.setBounds(62, 142, 86, 20);
		registersPanel.add(textFieldR4);
		
		JLabel lblR_r5 = new JLabel("r5");
		lblR_r5.setBounds(10, 173, 41, 14);
		registersPanel.add(lblR_r5);
		
		textFieldR5 = new JTextField();
		textFieldR5.setColumns(10);
		textFieldR5.setBounds(62, 170, 86, 20);
		registersPanel.add(textFieldR5);
		
		JLabel lblR_r7 = new JLabel("r7");
		lblR_r7.setBounds(10, 229, 41, 14);
		registersPanel.add(lblR_r7);
		
		textFieldR7 = new JTextField();
		textFieldR7.setColumns(10);
		textFieldR7.setBounds(62, 226, 86, 20);
		registersPanel.add(textFieldR7);
		
		textFieldR6 = new JTextField();
		textFieldR6.setColumns(10);
		textFieldR6.setBounds(62, 198, 86, 20);
		registersPanel.add(textFieldR6);
		
		JLabel lblR_r6 = new JLabel("r6");
		lblR_r6.setBounds(10, 201, 41, 14);
		registersPanel.add(lblR_r6);
		
		JLabel lblR_r8 = new JLabel("r8");
		lblR_r8.setBounds(181, 33, 41, 14);
		registersPanel.add(lblR_r8);
		
		textFieldR8 = new JTextField();
		textFieldR8.setColumns(10);
		textFieldR8.setBounds(233, 30, 86, 20);
		registersPanel.add(textFieldR8);
		
		JLabel lblR_r9 = new JLabel("r9");
		lblR_r9.setBounds(181, 61, 41, 14);
		registersPanel.add(lblR_r9);
		
		textFieldR9 = new JTextField();
		textFieldR9.setColumns(10);
		textFieldR9.setBounds(233, 58, 86, 20);
		registersPanel.add(textFieldR9);
		
		JLabel lblR_r10 = new JLabel("r10");
		lblR_r10.setBounds(181, 89, 41, 14);
		registersPanel.add(lblR_r10);
		
		textFieldR10 = new JTextField();
		textFieldR10.setColumns(10);
		textFieldR10.setBounds(233, 86, 86, 20);
		registersPanel.add(textFieldR10);
		
		JLabel lblR_r11 = new JLabel("r11");
		lblR_r11.setBounds(181, 117, 41, 14);
		registersPanel.add(lblR_r11);
		
		textFieldR11 = new JTextField();
		textFieldR11.setColumns(10);
		textFieldR11.setBounds(233, 114, 86, 20);
		registersPanel.add(textFieldR11);
		
		JLabel lblR_r12 = new JLabel("r12");
		lblR_r12.setBounds(181, 145, 41, 14);
		registersPanel.add(lblR_r12);
		
		textFieldR12 = new JTextField();
		textFieldR12.setColumns(10);
		textFieldR12.setBounds(233, 142, 86, 20);
		registersPanel.add(textFieldR12);
		
		JLabel lblR_r13 = new JLabel("r13");
		lblR_r13.setBounds(181, 173, 41, 14);
		registersPanel.add(lblR_r13);
		
		textFieldR13 = new JTextField();
		textFieldR13.setColumns(10);
		textFieldR13.setBounds(233, 170, 86, 20);
		registersPanel.add(textFieldR13);
		
		JLabel lblR_r14 = new JLabel("r14");
		lblR_r14.setBounds(181, 201, 41, 14);
		registersPanel.add(lblR_r14);
		
		textFieldR14 = new JTextField();
		textFieldR14.setColumns(10);
		textFieldR14.setBounds(233, 198, 86, 20);
		registersPanel.add(textFieldR14);
		
		JLabel lblR_r15 = new JLabel("r15");
		lblR_r15.setBounds(181, 229, 41, 14);
		registersPanel.add(lblR_r15);
		
		textFieldR15 = new JTextField();
		textFieldR15.setColumns(10);
		textFieldR15.setBounds(233, 226, 86, 20);
		registersPanel.add(textFieldR15);
		
		JPanel cpsrPanel = new JPanel();
		cpsrPanel.setBackground(Color.GRAY);
		cpsrPanel.setBounds(332, 0, 255, 283);
		viewElements.add(cpsrPanel);
		cpsrPanel.setLayout(null);
		
		JTextPane txtpnCpsr = new JTextPane();
		txtpnCpsr.setText("CPSR");
		txtpnCpsr.setBounds(0, 0, 51, 20);
		cpsrPanel.add(txtpnCpsr);
		
		JLabel lbl_N = new JLabel("N");
		lbl_N.setBounds(54, 36, 41, 14);
		cpsrPanel.add(lbl_N);
		
		textField_N = new JTextField();
		textField_N.setColumns(10);
		textField_N.setBounds(106, 33, 86, 20);
		cpsrPanel.add(textField_N);
		
		JLabel lbl_Z = new JLabel("Z");
		lbl_Z.setBounds(54, 64, 41, 14);
		cpsrPanel.add(lbl_Z);
		
		textField_Z = new JTextField();
		textField_Z.setColumns(10);
		textField_Z.setBounds(106, 61, 86, 20);
		cpsrPanel.add(textField_Z);
		
		JLabel lbl_C = new JLabel("C");
		lbl_C.setBounds(54, 92, 41, 14);
		cpsrPanel.add(lbl_C);
		
		textField_C = new JTextField();
		textField_C.setColumns(10);
		textField_C.setBounds(106, 89, 86, 20);
		cpsrPanel.add(textField_C);
		
		JLabel lbl_V = new JLabel("V");
		lbl_V.setBounds(54, 120, 41, 14);
		cpsrPanel.add(lbl_V);
		
		textField_V = new JTextField();
		textField_V.setColumns(10);
		textField_V.setBounds(106, 117, 86, 20);
		cpsrPanel.add(textField_V);
		
		JLabel lbl_I = new JLabel("I");
		lbl_I.setBounds(54, 173, 41, 14);
		cpsrPanel.add(lbl_I);
		
		textField_I = new JTextField();
		textField_I.setColumns(10);
		textField_I.setBounds(106, 170, 86, 20);
		cpsrPanel.add(textField_I);
		
		JLabel lbl_F = new JLabel("F");
		lbl_F.setBounds(54, 201, 41, 14);
		cpsrPanel.add(lbl_F);
		
		textField_F = new JTextField();
		textField_F.setColumns(10);
		textField_F.setBounds(106, 198, 86, 20);
		cpsrPanel.add(textField_F);
	}
}
