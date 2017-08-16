package view;

import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.io.*;
import java.nio.file.Files;

import compAndInt.*;
import controlView.MessageConsole;
import controlView.TextLineNumber;
import instructions.*;
import registers.*;
import java.awt.*;
import java.util.*;
import memory.Memory;

public class View {

	Register regData = new Register();
	private HashMap<Object, Object> reg = regData.init();

	Cpsr cpsr = new Cpsr();
	private HashMap<Object, Object> cpsrReg = cpsr.init();

	Memory memory = new Memory();
	private LinkedHashMap<Object, Object> memor = memory.init();

	Condition condition = new Condition(cpsrReg);
	UpdateCPSR upCpsr = new UpdateCPSR(cpsrReg);
	AccessMemory AMem = new AccessMemory(memor);
	Instruction inst = new Instruction(reg, AMem);

	MyParser parser = null;
	Visitors vi= new Visitors(regData, reg, cpsr, cpsrReg, memory, memor, condition, upCpsr, AMem, inst);

	static File selectedFile;
	File tmpFile;

	//for debug mode:
	int line;
	BufferedReader br = null;
	BufferedWriter bw = null;

	public JFrame frame;
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
	private JTextField textFieldR8FI;
	private JTextField textFieldR9FI;
	private JTextField textFieldR10FI;
	private JTextField textFieldR11FI;
	private JTextField textFieldR12FI;
	private JTextField textFieldR13FI;
	private JTextField textFieldR14FI;
	private JTextField textField_N_FI;
	private JTextField textField_Z_FI;
	private JTextField textField_C_FI;
	private JTextField textField_V_FI;
	private JTextField textField_I_FI;
	private JTextField textField_F_FI;
	private JTextField textFieldR13I;
	private JTextField textFieldR14I;
	private JTextField textField_N_I;
	private JTextField textField_Z_I;
	private JTextField textField_C_I;
	private JTextField textField_V_I;
	private JTextField textField_I_I;
	private JTextField textField_F_I;

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setBounds(100, 100, 1208, 1041);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(226, 127, 582, 493);
		frame.getContentPane().add(textArea);

		JScrollPane scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(10, 61, 578, 720);
		frame.getContentPane().add(scrollBar);
		/*
		TextLineNumber tln = new TextLineNumber(textArea);
		scrollBar.setRowHeaderView(tln);
		 */
		JTextArea outputTextArea = new JTextArea();
		outputTextArea.setBounds(10, 477, 478, 158);
		frame.getContentPane().add(outputTextArea);

		MessageConsole mc = new MessageConsole(outputTextArea);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);

		JScrollPane scrollBarOutPut = new JScrollPane(outputTextArea);
		scrollBarOutPut.setBounds(6, 792, 582, 165);
		frame.getContentPane().add(scrollBarOutPut);

		//Menu bar:
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		

		JPanel registersUserPanel = new JPanel();
		registersUserPanel.setBounds(602, 61, 193, 482);
		frame.getContentPane().add(registersUserPanel);
		registersUserPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		registersUserPanel.setBackground(new Color(248, 248, 255));
		registersUserPanel.setLayout(null);

		JTextPane txtpnRegisters = new JTextPane();
		txtpnRegisters.setBackground(new Color(248, 248, 255));
		txtpnRegisters.setBounds(3, 3, 51, 20);
		registersUserPanel.add(txtpnRegisters);
		txtpnRegisters.setText("Registers");

		JLabel lblR_r0 = new JLabel("r0");
		lblR_r0.setBounds(28, 30, 41, 14);
		registersUserPanel.add(lblR_r0);

		textFieldR0 = new JTextField();
		textFieldR0.setBounds(80, 27, 100, 20);
		registersUserPanel.add(textFieldR0);
		textFieldR0.setColumns(10);

		JLabel lblR_r1 = new JLabel("r1");
		lblR_r1.setBounds(28, 58, 41, 14);
		registersUserPanel.add(lblR_r1);

		textFieldR1 = new JTextField();
		textFieldR1.setColumns(10);
		textFieldR1.setBounds(80, 55, 100, 20);
		registersUserPanel.add(textFieldR1);

		JLabel lblR_r2 = new JLabel("r2");
		lblR_r2.setBounds(28, 86, 41, 14);
		registersUserPanel.add(lblR_r2);

		textFieldR2 = new JTextField();
		textFieldR2.setColumns(10);
		textFieldR2.setBounds(80, 83, 100, 20);
		registersUserPanel.add(textFieldR2);

		JLabel lblR_r3 = new JLabel("r3");
		lblR_r3.setBounds(28, 114, 41, 14);
		registersUserPanel.add(lblR_r3);

		textFieldR3 = new JTextField();
		textFieldR3.setColumns(10);
		textFieldR3.setBounds(80, 111, 100, 20);
		registersUserPanel.add(textFieldR3);

		JLabel lblR_r4 = new JLabel("r4");
		lblR_r4.setBounds(28, 142, 41, 14);
		registersUserPanel.add(lblR_r4);

		textFieldR4 = new JTextField();
		textFieldR4.setColumns(10);
		textFieldR4.setBounds(80, 139, 100, 20);
		registersUserPanel.add(textFieldR4);

		JLabel lblR_r5 = new JLabel("r5");
		lblR_r5.setBounds(28, 170, 41, 14);
		registersUserPanel.add(lblR_r5);

		textFieldR5 = new JTextField();
		textFieldR5.setColumns(10);
		textFieldR5.setBounds(80, 167, 100, 20);
		registersUserPanel.add(textFieldR5);

		JLabel lblR_r6 = new JLabel("r6");
		lblR_r6.setBounds(28, 198, 41, 14);
		registersUserPanel.add(lblR_r6);

		textFieldR6 = new JTextField();
		textFieldR6.setColumns(10);
		textFieldR6.setBounds(80, 195, 100, 20);
		registersUserPanel.add(textFieldR6);

		JLabel lblR_r7 = new JLabel("r7");
		lblR_r7.setBounds(28, 226, 41, 14);
		registersUserPanel.add(lblR_r7);

		textFieldR7 = new JTextField();
		textFieldR7.setColumns(10);
		textFieldR7.setBounds(80, 223, 100, 20);
		registersUserPanel.add(textFieldR7);

		JLabel lblR_r8 = new JLabel("r8");
		lblR_r8.setBounds(28, 257, 41, 14);
		registersUserPanel.add(lblR_r8);

		textFieldR8 = new JTextField();
		textFieldR8.setColumns(10);
		textFieldR8.setBounds(80, 251, 100, 20);
		registersUserPanel.add(textFieldR8);

		JLabel lblR_r9 = new JLabel("r9");
		lblR_r9.setBounds(28, 285, 41, 14);
		registersUserPanel.add(lblR_r9);

		textFieldR9 = new JTextField();
		textFieldR9.setColumns(10);
		textFieldR9.setBounds(80, 279, 100, 20);
		registersUserPanel.add(textFieldR9);

		JLabel lblR_r10 = new JLabel("r10");
		lblR_r10.setBounds(28, 313, 41, 14);
		registersUserPanel.add(lblR_r10);

		textFieldR10 = new JTextField();
		textFieldR10.setColumns(10);
		textFieldR10.setBounds(80, 310, 100, 20);
		registersUserPanel.add(textFieldR10);

		JLabel lblR_r11 = new JLabel("r11");
		lblR_r11.setBounds(28, 341, 41, 14);
		registersUserPanel.add(lblR_r11);

		textFieldR11 = new JTextField();
		textFieldR11.setColumns(10);
		textFieldR11.setBounds(80, 338, 100, 20);
		registersUserPanel.add(textFieldR11);

		JLabel lblR_r12 = new JLabel("r12");
		lblR_r12.setBounds(28, 369, 41, 14);
		registersUserPanel.add(lblR_r12);

		textFieldR12 = new JTextField();
		textFieldR12.setColumns(10);
		textFieldR12.setBounds(80, 366, 100, 20);
		registersUserPanel.add(textFieldR12);

		JLabel lblR_r13 = new JLabel("r13/sp");
		lblR_r13.setBounds(28, 397, 41, 14);
		registersUserPanel.add(lblR_r13);

		textFieldR13 = new JTextField();
		textFieldR13.setColumns(10);
		textFieldR13.setBounds(80, 394, 100, 20);
		registersUserPanel.add(textFieldR13);

		JLabel lblR_r14 = new JLabel("r14/lr");
		lblR_r14.setBounds(28, 425, 41, 14);
		registersUserPanel.add(lblR_r14);

		textFieldR14 = new JTextField();
		textFieldR14.setColumns(10);
		textFieldR14.setBounds(80, 422, 100, 20);
		registersUserPanel.add(textFieldR14);

		JLabel lblR_r15 = new JLabel("r15/pc");
		lblR_r15.setBounds(28, 453, 41, 14);
		registersUserPanel.add(lblR_r15);

		textFieldR15 = new JTextField();
		textFieldR15.setColumns(10);
		textFieldR15.setBounds(80, 450, 100, 20);
		registersUserPanel.add(textFieldR15);

		JPanel cpsrUserPanel = new JPanel();
		cpsrUserPanel.setBounds(602, 546, 193, 235);
		frame.getContentPane().add(cpsrUserPanel);
		cpsrUserPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		cpsrUserPanel.setBackground(new Color(248, 248, 255));
		cpsrUserPanel.setLayout(null);

		JTextPane txtpnCpsr = new JTextPane();
		txtpnCpsr.setBackground(new Color(248, 248, 255));
		txtpnCpsr.setText("CPSR");
		txtpnCpsr.setBounds(3, 3, 51, 20);
		cpsrUserPanel.add(txtpnCpsr);

		JLabel lbl_N = new JLabel("N");
		lbl_N.setBounds(30, 37, 41, 14);
		cpsrUserPanel.add(lbl_N);

		textField_N = new JTextField();
		textField_N.setColumns(10);
		textField_N.setBounds(82, 34, 86, 20);
		cpsrUserPanel.add(textField_N);

		JLabel lbl_Z = new JLabel("Z");
		lbl_Z.setBounds(30, 65, 41, 14);
		cpsrUserPanel.add(lbl_Z);

		textField_Z = new JTextField();
		textField_Z.setColumns(10);
		textField_Z.setBounds(82, 62, 86, 20);
		cpsrUserPanel.add(textField_Z);

		JLabel lbl_C = new JLabel("C");
		lbl_C.setBounds(30, 93, 41, 14);
		cpsrUserPanel.add(lbl_C);

		textField_C = new JTextField();
		textField_C.setColumns(10);
		textField_C.setBounds(82, 90, 86, 20);
		cpsrUserPanel.add(textField_C);

		JLabel lbl_V = new JLabel("V");
		lbl_V.setBounds(30, 121, 41, 14);
		cpsrUserPanel.add(lbl_V);

		textField_V = new JTextField();
		textField_V.setColumns(10);
		textField_V.setBounds(82, 118, 86, 20);
		cpsrUserPanel.add(textField_V);

		JLabel lbl_I = new JLabel("I");
		lbl_I.setBounds(30, 174, 41, 14);
		cpsrUserPanel.add(lbl_I);

		textField_I = new JTextField();
		textField_I.setColumns(10);
		textField_I.setBounds(82, 171, 86, 20);
		cpsrUserPanel.add(textField_I);

		JLabel lbl_F = new JLabel("F");
		lbl_F.setBounds(30, 202, 41, 14);
		cpsrUserPanel.add(lbl_F);

		textField_F = new JTextField();
		textField_F.setColumns(10);
		textField_F.setBounds(82, 199, 86, 20);
		cpsrUserPanel.add(textField_F);

		JPanel memoryPanel = new JPanel();
		memoryPanel.setBounds(600, 784, 585, 201);
		frame.getContentPane().add(memoryPanel);
		memoryPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		memoryPanel.setBackground(new Color(248, 248, 255));
		memoryPanel.setLayout(null);

		JTextPane txtpnMemory = new JTextPane();
		txtpnMemory.setBounds(3, 3, 51, 20);
		txtpnMemory.setBackground(new Color(248, 248, 255));
		txtpnMemory.setText("Memory");
		memoryPanel.add(txtpnMemory);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(1, 1, 552, 157);
		memoryPanel.add(textPane);

		JScrollPane scrollBar_1 = new JScrollPane(textPane);
		scrollBar_1.setBounds(13, 31, 564, 159);
		memoryPanel.add(scrollBar_1);

		JPanel registersFIPanel = new JPanel();
		registersFIPanel.setLayout(null);
		registersFIPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		registersFIPanel.setBackground(new Color(248, 248, 255));
		registersFIPanel.setBounds(797, 61, 193, 482);
		frame.getContentPane().add(registersFIPanel);

		JTextPane txtpnRegistersFastInterrupt = new JTextPane();
		txtpnRegistersFastInterrupt.setText("Registers, Fast interrupt mode");
		txtpnRegistersFastInterrupt.setBackground(new Color(248, 248, 255));
		txtpnRegistersFastInterrupt.setBounds(3, 3, 182, 20);
		registersFIPanel.add(txtpnRegistersFastInterrupt);

		JLabel lblR_r8FI = new JLabel("r8");
		lblR_r8FI.setBounds(28, 257, 41, 14);
		registersFIPanel.add(lblR_r8FI);

		textFieldR8FI = new JTextField();
		textFieldR8FI.setColumns(10);
		textFieldR8FI.setBounds(80, 251, 100, 20);
		registersFIPanel.add(textFieldR8FI);

		JLabel lblR_r9FI = new JLabel("r9");
		lblR_r9FI.setBounds(28, 285, 41, 14);
		registersFIPanel.add(lblR_r9FI);

		textFieldR9FI = new JTextField();
		textFieldR9FI.setColumns(10);
		textFieldR9FI.setBounds(80, 279, 100, 20);
		registersFIPanel.add(textFieldR9FI);

		JLabel lblR_r10FI = new JLabel("r10");
		lblR_r10FI.setBounds(28, 313, 41, 14);
		registersFIPanel.add(lblR_r10FI);

		textFieldR10FI = new JTextField();
		textFieldR10FI.setColumns(10);
		textFieldR10FI.setBounds(80, 310, 100, 20);
		registersFIPanel.add(textFieldR10FI);

		JLabel lblR_r11FI = new JLabel("r11");
		lblR_r11FI.setBounds(28, 341, 41, 14);
		registersFIPanel.add(lblR_r11FI);

		textFieldR11FI = new JTextField();
		textFieldR11FI.setColumns(10);
		textFieldR11FI.setBounds(80, 338, 100, 20);
		registersFIPanel.add(textFieldR11FI);

		JLabel lblR_r12FI = new JLabel("r12");
		lblR_r12FI.setBounds(28, 369, 41, 14);
		registersFIPanel.add(lblR_r12FI);

		textFieldR12FI = new JTextField();
		textFieldR12FI.setColumns(10);
		textFieldR12FI.setBounds(80, 366, 100, 20);
		registersFIPanel.add(textFieldR12FI);

		JLabel lblR_r13FI = new JLabel("r13/sp");
		lblR_r13FI.setBounds(28, 397, 41, 14);
		registersFIPanel.add(lblR_r13FI);

		textFieldR13FI = new JTextField();
		textFieldR13FI.setColumns(10);
		textFieldR13FI.setBounds(80, 394, 100, 20);
		registersFIPanel.add(textFieldR13FI);

		JLabel lblR_r14FI = new JLabel("r4/lr");
		lblR_r14FI.setBounds(28, 425, 41, 14);
		registersFIPanel.add(lblR_r14FI);

		textFieldR14FI = new JTextField();
		textFieldR14FI.setColumns(10);
		textFieldR14FI.setBounds(80, 422, 100, 20);
		registersFIPanel.add(textFieldR14FI);

		JPanel spsrFIPanel = new JPanel();
		spsrFIPanel.setLayout(null);
		spsrFIPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		spsrFIPanel.setBackground(new Color(248, 248, 255));
		spsrFIPanel.setBounds(797, 546, 193, 235);
		frame.getContentPane().add(spsrFIPanel);

		JTextPane txtpnSpsrFastInterrupt = new JTextPane();
		txtpnSpsrFastInterrupt.setText("SPSR, Fast interrupt mode");
		txtpnSpsrFastInterrupt.setBackground(new Color(248, 248, 255));
		txtpnSpsrFastInterrupt.setBounds(3, 3, 180, 20);
		spsrFIPanel.add(txtpnSpsrFastInterrupt);

		JLabel lbl_N_FI = new JLabel("N");
		lbl_N_FI.setBounds(30, 37, 41, 14);
		spsrFIPanel.add(lbl_N_FI);

		textField_N_FI = new JTextField();
		textField_N_FI.setColumns(10);
		textField_N_FI.setBounds(82, 34, 86, 20);
		spsrFIPanel.add(textField_N_FI);

		JLabel lbl_Z_FI = new JLabel("Z");
		lbl_Z_FI.setBounds(30, 65, 41, 14);
		spsrFIPanel.add(lbl_Z_FI);

		textField_Z_FI = new JTextField();
		textField_Z_FI.setColumns(10);
		textField_Z_FI.setBounds(82, 62, 86, 20);
		spsrFIPanel.add(textField_Z_FI);

		JLabel lbl_C_FI = new JLabel("C");
		lbl_C_FI.setBounds(30, 93, 41, 14);
		spsrFIPanel.add(lbl_C_FI);

		textField_C_FI = new JTextField();
		textField_C_FI.setColumns(10);
		textField_C_FI.setBounds(82, 90, 86, 20);
		spsrFIPanel.add(textField_C_FI);

		JLabel lbl_V_FI = new JLabel("V");
		lbl_V_FI.setBounds(30, 121, 41, 14);
		spsrFIPanel.add(lbl_V_FI);

		textField_V_FI = new JTextField();
		textField_V_FI.setColumns(10);
		textField_V_FI.setBounds(82, 118, 86, 20);
		spsrFIPanel.add(textField_V_FI);

		JLabel lbl_I_FI = new JLabel("I");
		lbl_I_FI.setBounds(30, 174, 41, 14);
		spsrFIPanel.add(lbl_I_FI);

		textField_I_FI = new JTextField();
		textField_I_FI.setColumns(10);
		textField_I_FI.setBounds(82, 171, 86, 20);
		spsrFIPanel.add(textField_I_FI);

		JLabel lbl_F_FI = new JLabel("F");
		lbl_F_FI.setBounds(30, 202, 41, 14);
		spsrFIPanel.add(lbl_F_FI);

		textField_F_FI = new JTextField();
		textField_F_FI.setColumns(10);
		textField_F_FI.setBounds(82, 199, 86, 20);
		spsrFIPanel.add(textField_F_FI);

		JPanel registersIntPanel = new JPanel();
		registersIntPanel.setLayout(null);
		registersIntPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		registersIntPanel.setBackground(new Color(248, 248, 255));
		registersIntPanel.setBounds(993, 61, 193, 482);
		frame.getContentPane().add(registersIntPanel);

		JTextPane txtpnRegistersInterruptMode = new JTextPane();
		txtpnRegistersInterruptMode.setText("Registers, Interrupt mode");
		txtpnRegistersInterruptMode.setBackground(new Color(248, 248, 255));
		txtpnRegistersInterruptMode.setBounds(3, 3, 192, 20);
		registersIntPanel.add(txtpnRegistersInterruptMode);

		JLabel lblR_r13I = new JLabel("r13/sp");
		lblR_r13I.setBounds(28, 397, 41, 14);
		registersIntPanel.add(lblR_r13I);

		textFieldR13I = new JTextField();
		textFieldR13I.setColumns(10);
		textFieldR13I.setBounds(80, 394, 100, 20);
		registersIntPanel.add(textFieldR13I);

		JLabel lblR_r14I = new JLabel("r4/lr");
		lblR_r14I.setBounds(28, 425, 41, 14);
		registersIntPanel.add(lblR_r14I);

		textFieldR14I = new JTextField();
		textFieldR14I.setColumns(10);
		textFieldR14I.setBounds(80, 422, 100, 20);
		registersIntPanel.add(textFieldR14I);

		JPanel spsrIntPanel = new JPanel();
		spsrIntPanel.setLayout(null);
		spsrIntPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		spsrIntPanel.setBackground(new Color(248, 248, 255));
		spsrIntPanel.setBounds(993, 546, 193, 235);
		frame.getContentPane().add(spsrIntPanel);

		JTextPane txtpnSpsrInterruptMode = new JTextPane();
		txtpnSpsrInterruptMode.setText("SPSR, Interrupt mode");
		txtpnSpsrInterruptMode.setBackground(new Color(248, 248, 255));
		txtpnSpsrInterruptMode.setBounds(3, 3, 180, 20);
		spsrIntPanel.add(txtpnSpsrInterruptMode);

		JLabel lbl_N_I = new JLabel("N");
		lbl_N_I.setBounds(30, 37, 41, 14);
		spsrIntPanel.add(lbl_N_I);

		textField_N_I = new JTextField();
		textField_N_I.setColumns(10);
		textField_N_I.setBounds(82, 34, 86, 20);
		spsrIntPanel.add(textField_N_I);

		JLabel lbl_Z_I = new JLabel("Z");
		lbl_Z_I.setBounds(30, 65, 41, 14);
		spsrIntPanel.add(lbl_Z_I);

		textField_Z_I = new JTextField();
		textField_Z_I.setColumns(10);
		textField_Z_I.setBounds(82, 62, 86, 20);
		spsrIntPanel.add(textField_Z_I);

		JLabel lbl_C_I = new JLabel("C");
		lbl_C_I.setBounds(30, 93, 41, 14);
		spsrIntPanel.add(lbl_C_I);

		textField_C_I = new JTextField();
		textField_C_I.setColumns(10);
		textField_C_I.setBounds(82, 90, 86, 20);
		spsrIntPanel.add(textField_C_I);

		JLabel lbl_V_I = new JLabel("V");
		lbl_V_I.setBounds(30, 121, 41, 14);
		spsrIntPanel.add(lbl_V_I);

		textField_V_I = new JTextField();
		textField_V_I.setColumns(10);
		textField_V_I.setBounds(82, 118, 86, 20);
		spsrIntPanel.add(textField_V_I);

		JLabel lbl_I_I = new JLabel("I");
		lbl_I_I.setBounds(30, 174, 41, 14);
		spsrIntPanel.add(lbl_I_I);

		textField_I_I = new JTextField();
		textField_I_I.setColumns(10);
		textField_I_I.setBounds(82, 171, 86, 20);
		spsrIntPanel.add(textField_I_I);

		JLabel lbl_F_I = new JLabel("F");
		lbl_F_I.setBounds(30, 202, 41, 14);
		spsrIntPanel.add(lbl_F_I);

		textField_F_I = new JTextField();
		textField_F_I.setColumns(10);
		textField_F_I.setBounds(82, 199, 86, 20);
		spsrIntPanel.add(textField_F_I);
		
		JButton btnInteger = new JButton(new AbstractAction("Integer"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				IntegerFields();
			}
		});
		btnInteger.setBounds(602, 1, 139, 23);
		frame.getContentPane().add(btnInteger);

		JButton btnHexadecimal = new JButton(new AbstractAction("Hexadecimal"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				hexFields();
			}
		});
		btnHexadecimal.setBounds(741, 1, 139, 23);
		frame.getContentPane().add(btnHexadecimal);
		
		JMenuItem mntmOpenFile = new JMenuItem(new AbstractAction("Open File..."){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a text file");

				//select only text files, hide others format.
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
				}

				//when selected text file, open it in textArea (clean before):
				textArea.setText("");
				BufferedReader in;
				try {
					in = new BufferedReader(new FileReader(selectedFile));

					String line = in.readLine();
					while(line != null){
						textArea.append(line + "\n");
						line = in.readLine();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.err.println(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.err.println(e);
				}

			}
		});
		mnFile.add(mntmOpenFile);

		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

		JMenuItem mntmSave = new JMenuItem(new AbstractAction("Save"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Save text file");

				//select only text files, hide others format.
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showDialog(null, "Save");
				// int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = jfc.getSelectedFile();
					if(! selectedFile.getName().endsWith(".txt")){
						selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
					}
					try{
						textArea.write(new OutputStreamWriter(new FileOutputStream(selectedFile), "utf-8"));
					}catch	(IOException e) {
						//e.printStackTrace();
						System.err.println(e);
					}
				}

			}
		});
		mnFile.add(mntmSave);

		JMenu mnRun = new JMenu("Run");
		menuBar.add(mnRun);

		JMenuItem mntmRun = new JMenuItem(new AbstractAction("Run"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){

				try{
					//init fields and elements:
					initfieldsVal();
					textPane.setText("");
					vi.setPc(2);
					vi.setChild(0);
					HashMap<String, Integer> branches = new HashMap<String, Integer>();
					vi.setBranches(branches);
					BufferedReader br = null;
					Object[] progArray = null;
					try {
						br = new BufferedReader(new FileReader(selectedFile));
						progArray = br.lines().toArray();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					vi.setProgArray(progArray);

					regData.clearRegister();
					cpsr.clearCpsr();
					memory.clearMemory();

					RunFile(selectedFile);

					//set fields memory:
					fillVal();
					textPane.setText(memory.printView());
				}catch (RuntimeException e){
					System.err.println(e);
				} 

			}
		});
		mnRun.add(mntmRun);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAcceptedInstructions = new JMenuItem(new AbstractAction("Accepted instructions"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				HelpAcceptedInstructions.helpFrame();
			}
		});
		mnHelp.add(mntmAcceptedInstructions);

		JMenuItem mntmHowToUse = new JMenuItem(new AbstractAction("How to use"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				HelpUse.helpFrame();
			}
		});
		mnHelp.add(mntmHowToUse);
		frame.getContentPane().setLayout(null);

		JButton btnDebug = new JButton(new AbstractAction("Debug"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				//Debug initial all variable needed:
				//Run first time to field hashmap
				initfieldsVal();
				textPane.setText("");
				vi.setPc(2);
				vi.setChild(0);
				HashMap<String, Integer> branches = new HashMap<String, Integer>();
				vi.setBranches(branches);
				BufferedReader br = null;
				Object[] progArray = null;
				try {
					br = new BufferedReader(new FileReader(selectedFile));
					progArray = br.lines().toArray();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vi.setProgArray(progArray);

				regData.clearRegister();
				cpsr.clearCpsr();
				memory.clearMemory();

				RunFile(selectedFile);


				//init filds:
				initfieldsVal();
				textPane.setText("");

				regData.clearRegister();
				cpsr.clearCpsr();
				memory.clearMemory();

				//Create tmp file:
				String tmp = selectedFile.toString() ;
				tmp = tmp.replace(".txt", "tmpDebug.txt");
				if(tmpFile == null){
					tmpFile = new File(tmp);
				}
				else{
					try {
						Files.delete(tmpFile.toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				//init counter:
				line = 0;

				vi.setPc(2);
				vi.setChild(0);
				progArray = null;
				try {
					br = new BufferedReader(new FileReader(selectedFile));
					progArray = br.lines().toArray();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vi.setProgArray(progArray);


				System.out.println("Debug mode, press Next step");
			}
		});
		btnDebug.setBounds(90, 0, 89, 23);
		frame.getContentPane().add(btnDebug);

		JButton btnNextStep = new JButton(new AbstractAction("Next step"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				/*Each time button hit -> increment count:
				  and execute programm with unique line*/

				//create bufferers:
				try {
					br = new BufferedReader(new FileReader(selectedFile.toString()));
					bw = new BufferedWriter(new FileWriter(tmpFile.toString()));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//convert content file into Array
				Object[] fileArray = br.lines().toArray(); 
				//count lines:
				int lineFile = fileArray.length;

				line = vi.getPc() -2;

				//verify if line < number of line:
				if(line < lineFile){
					//if line ok, 
					String linestr = fileArray[line].toString();
					if ( linestr != null ){
						try {
							bw.write(linestr);
							br.close();
							bw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					vi.setChild(0);
					RunFile(tmpFile);

					//set fields memory:
					fillVal();
					textPane.setText(memory.printView());

					System.out.println("Line "+(line+1)+" executed...");
					line += 1;
				}
				else{//else end of debug and delete file
					System.err.println("End of debug mode");
					try {
						br.close();
						bw.close();
						Files.delete(tmpFile.toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//see to make button disable
				}

			}
		});
		btnNextStep.setBounds(179, 0, 89, 23);
		frame.getContentPane().add(btnNextStep);

		JButton btnRun = new JButton(new AbstractAction("Run"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){

				try{
					//init fields and elements:
					initfieldsVal();
					textPane.setText("");
					vi.setPc(2);
					vi.setChild(0);
					HashMap<String, Integer> branches = new HashMap<String, Integer>();
					vi.setBranches(branches);
					BufferedReader br = null;
					Object[] progArray = null;
					try {
						br = new BufferedReader(new FileReader(selectedFile));
						progArray = br.lines().toArray();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					vi.setProgArray(progArray);

					regData.clearRegister();
					cpsr.clearCpsr();
					memory.clearMemory();

					RunFile(selectedFile);

					//set fields memory:
					fillVal();
					textPane.setText(memory.printView());
				}catch (RuntimeException e){
					System.err.println(e);
				} 

			}
		});
		btnRun.setBounds(0, 0, 89, 23);
		frame.getContentPane().add(btnRun);

	}//end initialize

	private void fillVal(){
		//Set fields register:
		hexFields();

		//set fields cpsr:
		textField_N.setText(cpsrReg.get("N").toString());
		textField_Z.setText(cpsrReg.get("Z").toString());
		textField_C.setText(cpsrReg.get("C").toString());
		textField_V.setText(cpsrReg.get("V").toString());
		textField_I.setText(cpsrReg.get("I").toString());
		textField_F.setText(cpsrReg.get("F").toString());   
	}

	private void IntegerFields(){
		textFieldR0.setText(reg.get("r0").toString());
		textFieldR1.setText(reg.get("r1").toString());
		textFieldR2.setText(reg.get("r2").toString());
		textFieldR3.setText(reg.get("r3").toString());
		textFieldR4.setText(reg.get("r4").toString());
		textFieldR5.setText(reg.get("r5").toString());
		textFieldR6.setText(reg.get("r6").toString());
		textFieldR7.setText(reg.get("r7").toString());
		textFieldR8.setText(reg.get("r8").toString());
		textFieldR9.setText(reg.get("r9").toString());
		textFieldR10.setText(reg.get("r10").toString());
		textFieldR11.setText(reg.get("r11").toString());
		textFieldR12.setText(reg.get("r12").toString());
		textFieldR13.setText(reg.get("r13").toString());
		textFieldR14.setText(reg.get("r14").toString());
		textFieldR15.setText(reg.get("r15").toString());
	}

	public String toHex(String el){
		String hex = Integer.toHexString(Integer.parseInt(el));

		if(hex.length() < 8){
			while (!(hex.length() == 8 )){
				hex = "0"+hex;
			}
		}

		hex = "0x"+hex;
		return hex;
	}

	private void hexFields(){
		textFieldR0.setText(toHex(reg.get("r0").toString()));
		textFieldR1.setText(toHex(reg.get("r1").toString()));
		textFieldR2.setText(toHex(reg.get("r2").toString()));
		textFieldR3.setText(toHex(reg.get("r3").toString()));
		textFieldR4.setText(toHex(reg.get("r4").toString()));
		textFieldR5.setText(toHex(reg.get("r5").toString()));
		textFieldR6.setText(toHex(reg.get("r6").toString()));
		textFieldR7.setText(toHex(reg.get("r7").toString()));
		textFieldR8.setText(toHex(reg.get("r8").toString()));
		textFieldR9.setText(toHex(reg.get("r9").toString()));
		textFieldR10.setText(toHex(reg.get("r10").toString()));
		textFieldR11.setText(toHex(reg.get("r11").toString()));
		textFieldR12.setText(toHex(reg.get("r12").toString()));
		textFieldR13.setText(toHex(reg.get("r13").toString()));
		textFieldR14.setText(toHex(reg.get("r14").toString()));
		textFieldR15.setText(toHex(reg.get("r15").toString()));
	}

	private void initfieldsVal(){
		//Set fields register:
		textFieldR0.setText("0");
		textFieldR1.setText("0");
		textFieldR2.setText("0");
		textFieldR3.setText("0");
		textFieldR4.setText("0");
		textFieldR5.setText("0");
		textFieldR6.setText("0");
		textFieldR7.setText("0");
		textFieldR8.setText("0");
		textFieldR9.setText("0");
		textFieldR10.setText("0");
		textFieldR11.setText("0");
		textFieldR12.setText("0");
		textFieldR13.setText("0");
		textFieldR14.setText("0");
		textFieldR15.setText("0");

		//set fields cpsr:
		textField_N.setText("0");
		textField_Z.setText("0");
		textField_C.setText("0");
		textField_V.setText("0");
		textField_I.setText("0");
		textField_F.setText("0");   
	}

	private void RunFile(File file){
		SimpleNode root = null;
		try {
			if(parser == null){
				parser = new MyParser(new FileReader(file));
			}
			else{
				parser.ReInit(new FileReader(file));
			}
			root = parser.prog();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println(e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		root.jjtAccept(vi,null);
	}
}
