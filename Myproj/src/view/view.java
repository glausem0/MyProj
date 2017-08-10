package view;

import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

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
	Visitors vi;

	File selectedFile;

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
		frame.setBounds(100, 100, 1220, 872);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(1, 1, 473, 589);
		frame.getContentPane().add(textArea);

		JScrollPane scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(10, 11, 582, 493);
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
		scrollBarOutPut.setBounds(10, 517, 582, 165);
		frame.getContentPane().add(scrollBarOutPut);

		JPanel viewElements = new JPanel();
		viewElements.setBounds(602, 11, 592, 504);
		frame.getContentPane().add(viewElements);
		viewElements.setBackground(Color.WHITE);
		viewElements.setLayout(null);

		JPanel registersPanel = new JPanel();
		registersPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		registersPanel.setBackground(new Color(248, 248, 255));
		registersPanel.setBounds(0, 0, 395, 304);
		viewElements.add(registersPanel);
		registersPanel.setLayout(null);

		JTextPane txtpnRegisters = new JTextPane();
		txtpnRegisters.setBackground(new Color(248, 248, 255));
		txtpnRegisters.setBounds(3, 3, 51, 20);
		registersPanel.add(txtpnRegisters);
		txtpnRegisters.setText("Registers");

		JLabel lblR_r0 = new JLabel("r0");
		lblR_r0.setBounds(28, 30, 41, 14);
		registersPanel.add(lblR_r0);

		textFieldR0 = new JTextField();
		textFieldR0.setBounds(80, 27, 100, 20);
		registersPanel.add(textFieldR0);
		textFieldR0.setColumns(10);

		JLabel lblR_r1 = new JLabel("r1");
		lblR_r1.setBounds(28, 58, 41, 14);
		registersPanel.add(lblR_r1);

		textFieldR1 = new JTextField();
		textFieldR1.setColumns(10);
		textFieldR1.setBounds(80, 55, 100, 20);
		registersPanel.add(textFieldR1);

		JLabel lblR_r2 = new JLabel("r2");
		lblR_r2.setBounds(28, 86, 41, 14);
		registersPanel.add(lblR_r2);

		textFieldR2 = new JTextField();
		textFieldR2.setColumns(10);
		textFieldR2.setBounds(80, 83, 100, 20);
		registersPanel.add(textFieldR2);

		JLabel lblR_r3 = new JLabel("r3");
		lblR_r3.setBounds(28, 114, 41, 14);
		registersPanel.add(lblR_r3);

		textFieldR3 = new JTextField();
		textFieldR3.setColumns(10);
		textFieldR3.setBounds(80, 111, 100, 20);
		registersPanel.add(textFieldR3);

		JLabel lblR_r4 = new JLabel("r4");
		lblR_r4.setBounds(28, 142, 41, 14);
		registersPanel.add(lblR_r4);

		textFieldR4 = new JTextField();
		textFieldR4.setColumns(10);
		textFieldR4.setBounds(80, 139, 100, 20);
		registersPanel.add(textFieldR4);

		JLabel lblR_r5 = new JLabel("r5");
		lblR_r5.setBounds(28, 170, 41, 14);
		registersPanel.add(lblR_r5);

		textFieldR5 = new JTextField();
		textFieldR5.setColumns(10);
		textFieldR5.setBounds(80, 167, 100, 20);
		registersPanel.add(textFieldR5);

		JLabel lblR_r6 = new JLabel("r6");
		lblR_r6.setBounds(28, 198, 41, 14);
		registersPanel.add(lblR_r6);

		textFieldR6 = new JTextField();
		textFieldR6.setColumns(10);
		textFieldR6.setBounds(80, 195, 100, 20);
		registersPanel.add(textFieldR6);

		JLabel lblR_r7 = new JLabel("r7");
		lblR_r7.setBounds(28, 226, 41, 14);
		registersPanel.add(lblR_r7);

		textFieldR7 = new JTextField();
		textFieldR7.setColumns(10);
		textFieldR7.setBounds(80, 223, 100, 20);
		registersPanel.add(textFieldR7);

		JLabel lblR_r8 = new JLabel("r8");
		lblR_r8.setBounds(228, 33, 41, 14);
		registersPanel.add(lblR_r8);

		textFieldR8 = new JTextField();
		textFieldR8.setColumns(10);
		textFieldR8.setBounds(280, 30, 100, 20);
		registersPanel.add(textFieldR8);

		JLabel lblR_r9 = new JLabel("r9");
		lblR_r9.setBounds(228, 61, 41, 14);
		registersPanel.add(lblR_r9);

		textFieldR9 = new JTextField();
		textFieldR9.setColumns(10);
		textFieldR9.setBounds(280, 55, 100, 20);
		registersPanel.add(textFieldR9);

		JLabel lblR_r10 = new JLabel("r10");
		lblR_r10.setBounds(228, 89, 41, 14);
		registersPanel.add(lblR_r10);

		textFieldR10 = new JTextField();
		textFieldR10.setColumns(10);
		textFieldR10.setBounds(280, 86, 100, 20);
		registersPanel.add(textFieldR10);

		JLabel lblR_r11 = new JLabel("r11");
		lblR_r11.setBounds(228, 117, 41, 14);
		registersPanel.add(lblR_r11);

		textFieldR11 = new JTextField();
		textFieldR11.setColumns(10);
		textFieldR11.setBounds(280, 114, 100, 20);
		registersPanel.add(textFieldR11);

		JLabel lblR_r12 = new JLabel("r12");
		lblR_r12.setBounds(228, 145, 41, 14);
		registersPanel.add(lblR_r12);

		textFieldR12 = new JTextField();
		textFieldR12.setColumns(10);
		textFieldR12.setBounds(280, 142, 100, 20);
		registersPanel.add(textFieldR12);

		JLabel lblR_r13 = new JLabel("r13/sp");
		lblR_r13.setBounds(228, 173, 41, 14);
		registersPanel.add(lblR_r13);

		textFieldR13 = new JTextField();
		textFieldR13.setColumns(10);
		textFieldR13.setBounds(280, 170, 100, 20);
		registersPanel.add(textFieldR13);

		JLabel lblR_r14 = new JLabel("r14/lr");
		lblR_r14.setBounds(228, 201, 41, 14);
		registersPanel.add(lblR_r14);

		textFieldR14 = new JTextField();
		textFieldR14.setColumns(10);
		textFieldR14.setBounds(280, 198, 100, 20);
		registersPanel.add(textFieldR14);

		JLabel lblR_r15 = new JLabel("r15/pc");
		lblR_r15.setBounds(228, 229, 41, 14);
		registersPanel.add(lblR_r15);

		textFieldR15 = new JTextField();
		textFieldR15.setColumns(10);
		textFieldR15.setBounds(280, 226, 100, 20);
		registersPanel.add(textFieldR15);

		JButton btnInteger = new JButton(new AbstractAction("Integer"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				IntegerFields();
			}
		});
		btnInteger.setBounds(61, 270, 139, 23);
		registersPanel.add(btnInteger);

		JButton btnHexadecimal = new JButton(new AbstractAction("Hexadecimal"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				hexFields();
			}
		});
		btnHexadecimal.setBounds(224, 270, 139, 23);
		registersPanel.add(btnHexadecimal);
		
		JButton btnNextStep = new JButton(new AbstractAction("Next step"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				vi.setWaitbool(false);
			}
		});
		btnNextStep.setBounds(20, 703, 141, 35);
		frame.getContentPane().add(btnNextStep);

		JPanel cpsrPanel = new JPanel();
		cpsrPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		cpsrPanel.setBackground(new Color(248, 248, 255));
		cpsrPanel.setBounds(394, 0, 193, 304);
		viewElements.add(cpsrPanel);
		cpsrPanel.setLayout(null);

		JTextPane txtpnCpsr = new JTextPane();
		txtpnCpsr.setBackground(new Color(248, 248, 255));
		txtpnCpsr.setText("CPSR");
		txtpnCpsr.setBounds(3, 3, 51, 20);
		cpsrPanel.add(txtpnCpsr);

		JLabel lbl_N = new JLabel("N");
		lbl_N.setBounds(30, 37, 41, 14);
		cpsrPanel.add(lbl_N);

		textField_N = new JTextField();
		textField_N.setColumns(10);
		textField_N.setBounds(82, 34, 86, 20);
		cpsrPanel.add(textField_N);

		JLabel lbl_Z = new JLabel("Z");
		lbl_Z.setBounds(30, 65, 41, 14);
		cpsrPanel.add(lbl_Z);

		textField_Z = new JTextField();
		textField_Z.setColumns(10);
		textField_Z.setBounds(82, 62, 86, 20);
		cpsrPanel.add(textField_Z);

		JLabel lbl_C = new JLabel("C");
		lbl_C.setBounds(30, 93, 41, 14);
		cpsrPanel.add(lbl_C);

		textField_C = new JTextField();
		textField_C.setColumns(10);
		textField_C.setBounds(82, 90, 86, 20);
		cpsrPanel.add(textField_C);

		JLabel lbl_V = new JLabel("V");
		lbl_V.setBounds(30, 121, 41, 14);
		cpsrPanel.add(lbl_V);

		textField_V = new JTextField();
		textField_V.setColumns(10);
		textField_V.setBounds(82, 118, 86, 20);
		cpsrPanel.add(textField_V);

		JLabel lbl_I = new JLabel("I");
		lbl_I.setBounds(30, 174, 41, 14);
		cpsrPanel.add(lbl_I);

		textField_I = new JTextField();
		textField_I.setColumns(10);
		textField_I.setBounds(82, 171, 86, 20);
		cpsrPanel.add(textField_I);

		JLabel lbl_F = new JLabel("F");
		lbl_F.setBounds(30, 202, 41, 14);
		cpsrPanel.add(lbl_F);

		textField_F = new JTextField();
		textField_F.setColumns(10);
		textField_F.setBounds(82, 199, 86, 20);
		cpsrPanel.add(textField_F);

		JPanel memoryPanel = new JPanel();
		memoryPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		memoryPanel.setBackground(new Color(248, 248, 255));
		memoryPanel.setBounds(0, 303, 587, 201);
		viewElements.add(memoryPanel);
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

		//Menu bar:
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

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
					//init fields for rerun:
					initfieldsVal();
					textPane.setText("");

					try {
						if(parser == null){
							parser = new MyParser(new FileReader(selectedFile));
						}
						else{
							parser.ReInit(new FileReader(selectedFile));
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.err.println(e);
					}
					SimpleNode root = null;
					try {
						root = parser.prog();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.err.println(e);
					}
					/*
				System.out.println("Abstract Syntax Tree:");
				root.dump(" ");
					 */
					//System.out.println("Prog:");
					vi = new Visitors(regData, reg, cpsr, cpsrReg, memory, memor, condition, upCpsr, AMem, inst);
					root.jjtAccept(vi,null);


					//set fields memory:
					fillVal();
					textPane.setText(memory.printView());
				}catch (RuntimeException e){
					System.err.println(e);
				}

			}
		});
		mnRun.add(mntmRun);

		JMenuItem mntmDebug = new JMenuItem(new AbstractAction("Debug"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae){
				//TODO action for debug:copy/modify/execute/when finish delete tmp file.

				//copy current text file:
				String tmp = selectedFile.toString();
				tmp = tmp.replace(".txt", "tmpDebug.txt");

				File tmpFile = new File(tmp);

			
				FileReader fr = null;
				FileWriter fw = null;

				try {
					fr = new FileReader(selectedFile.toString());
					fw = new FileWriter(tmpFile.toString());
					int line = 0;
					while((line = fr.read()) != -1){
						fw.write(line);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					try {
						if( (fr != null) || (fw != null) ){	
							fr.close();
							fw.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				
				
				//modify the copy file:
				BufferedReader br = null;
				BufferedWriter bw = null;

				try {
					br = new BufferedReader(new FileReader(selectedFile.toString()));
					bw = new BufferedWriter(new FileWriter(tmpFile.toString()));
					String line;
					while ( (line = br.readLine()) != null ){
						bw.write(line+" \n ");
						bw.write(" WAIT \n ");
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					try {
						if(br != null){
							br.close();
							bw.close();}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				
				//TODO erase System.out
				if(tmpFile.delete()){
					System.out.println("deleted ok");
				}
				else{
					System.out.println("not deleted");
				}
				
			}	

		});
		mnRun.add(mntmDebug);

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
		
	}

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
}
