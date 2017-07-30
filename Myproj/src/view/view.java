package view;

import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

import java.io.*;
import compAndInt.*;
import instructions.*;
import registers.*;
import java.awt.*;
import java.awt.List;
import java.util.*;

import memory.Memory;
import javax.swing.table.DefaultTableModel;


public class view {
	
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
	
	MyTest parser = null;
	
	File selectedFile;
	
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
		frame.setBounds(100, 100, 1108, 620);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 11, 473, 538);
		frame.getContentPane().add(textArea);
		
		JScrollPane scrollBar = new JScrollPane(textArea);
		scrollBar.setBounds(10, 11, 473, 538);
		frame.getContentPane().add(scrollBar);
		
		JPanel viewElements = new JPanel();
		viewElements.setBounds(493, 11, 587, 538);
		frame.getContentPane().add(viewElements);
		viewElements.setBackground(Color.WHITE);
		viewElements.setLayout(null);
		
		JPanel registersPanel = new JPanel();
		registersPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		
		JLabel lblR_r1 = new JLabel("r1");
		lblR_r1.setBounds(10, 61, 41, 14);
		registersPanel.add(lblR_r1);
		
		textFieldR1 = new JTextField();
		textFieldR1.setColumns(10);
		textFieldR1.setBounds(62, 58, 86, 20);
		registersPanel.add(textFieldR1);
		
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
		
		JLabel lblR_r6 = new JLabel("r6");
		lblR_r6.setBounds(10, 201, 41, 14);
		registersPanel.add(lblR_r6);
		
		textFieldR6 = new JTextField();
		textFieldR6.setColumns(10);
		textFieldR6.setBounds(62, 198, 86, 20);
		registersPanel.add(textFieldR6);
		
		JLabel lblR_r7 = new JLabel("r7");
		lblR_r7.setBounds(10, 229, 41, 14);
		registersPanel.add(lblR_r7);
		
		textFieldR7 = new JTextField();
		textFieldR7.setColumns(10);
		textFieldR7.setBounds(62, 226, 86, 20);
		registersPanel.add(textFieldR7);
		
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
		
		JLabel lblR_r13 = new JLabel("r13/sp");
		lblR_r13.setBounds(181, 173, 41, 14);
		registersPanel.add(lblR_r13);
		
		textFieldR13 = new JTextField();
		textFieldR13.setColumns(10);
		textFieldR13.setBounds(233, 170, 86, 20);
		registersPanel.add(textFieldR13);
		
		JLabel lblR_r14 = new JLabel("r14/lr");
		lblR_r14.setBounds(181, 201, 41, 14);
		registersPanel.add(lblR_r14);
		
		textFieldR14 = new JTextField();
		textFieldR14.setColumns(10);
		textFieldR14.setBounds(233, 198, 86, 20);
		registersPanel.add(textFieldR14);
		
		JLabel lblR_r15 = new JLabel("r15/pc");
		lblR_r15.setBounds(181, 229, 41, 14);
		registersPanel.add(lblR_r15);
		
		textFieldR15 = new JTextField();
		textFieldR15.setColumns(10);
		textFieldR15.setBounds(233, 226, 86, 20);
		registersPanel.add(textFieldR15);
		
		JPanel cpsrPanel = new JPanel();
		cpsrPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 282, 587, 256);
		viewElements.add(panel);
		panel.setLayout(null);
		
		JTextPane txtpnMemory = new JTextPane();
		txtpnMemory.setBounds(0, 0, 51, 20);
		txtpnMemory.setText("Memory");
		panel.add(txtpnMemory);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 27, 308, 218);
		panel.add(textPane);
		
		JScrollPane scrollBar_1 = new JScrollPane(textPane);
		scrollBar_1.setBounds(10, 27, 308, 218);
		panel.add(scrollBar_1);
	
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
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
			                    e.printStackTrace();
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
						
						//init fields for rerun:
						initfieldsVal();
						textPane.setText("");
						
						try {
							if(parser == null){
								parser = new MyTest(new FileReader(selectedFile));
							}
							else{
								parser.ReInit(new FileReader(selectedFile));
							}
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
						    Visitors vi = new Visitors(regData, reg, cpsr, cpsrReg, memory, memor, condition, upCpsr, AMem, inst);
						    root.jjtAccept(vi,null);

						    
						  //set fields memory:
						    fillVal();
						    textPane.setText(memory.printView());
						    
					}
				});
				mnRun.add(mntmRun);
				
				JMenu mnHelp = new JMenu("Help");
				menuBar.add(mnHelp);
				frame.getContentPane().setLayout(null);
		
				
	}
	
	private void fillVal(){
		//Set fields register:
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
	    
	    //set fields cpsr:
	    textField_N.setText(cpsrReg.get("N").toString());
	    textField_Z.setText(cpsrReg.get("Z").toString());
	    textField_C.setText(cpsrReg.get("C").toString());
	    textField_V.setText(cpsrReg.get("V").toString());
	    textField_I.setText(cpsrReg.get("I").toString());
	    textField_F.setText(cpsrReg.get("F").toString());   
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
