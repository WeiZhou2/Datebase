import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import javax.swing.UIManager;


@SuppressWarnings("serial")
public class ItemTrackingProject extends JFrame {

	private JPanel home,addRecord,viewRecord,Items,Employees, returnFrame;
	JTextField userNam;
	JPasswordField pwd;
	static Connection conn;
	private JTextField textField_STU_ID;
	private JTextField textField_StufNam;
	private JTextField textField_StuLName;
	private JTextField textField_RoomNumber;
	private JTextField textFieldPhone;
	private JTable ItemTable, EmployeeTable, ReportTable, returnFrameTable;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxItem_chkout,comboBoxEmployee_chkout;
    private JTextArea textArea_1 = new JTextArea();
    private JDatePickerImpl datePicker, datePicker1;
    private String empID, itemID, chkID;
	
 
	
	static CallableStatement cs ;
	static Statement stmt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
	
		
		ItemTrackingProject frame = new ItemTrackingProject();
		frame.setVisible(true);
		
		
	}

	/**
	 * Create the frame.
	 */
	public ItemTrackingProject() {
		setTitle("Item Check out Tracking");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		session();
		start();
		
		setBounds(100, 100, 472, 299);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//try to close the connection to the database
				{try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
				System.exit(0);
				}
			});
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "This program helps keep track of item that have been checked out\n" +
						"by University Height residents\nDevelopper: Olivier Avd\n" +
						"Contact: Phone: 308-627-1277 E-mail: av.reivilo@gmail.com  ");
			}
		});
		mnAbout.add(mntmAbout);
		
		
		setVisible(true);
	}



		public void home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 924, 495);
		home = new JPanel();
		home.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(home);
		home.setLayout(null);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Check out: Add residents, employees and items " +
						"checked out information into the database\nView report: show a summary of data in the database(All items that have " +
						"been checked out and by who)");
			}
		});
		btnHelp.setBounds(774, 362, 97, 25);
		home.add(btnHelp);
		
		JButton btnaddRecordButton = new JButton("Add record");
		btnaddRecordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addRecord();
			}
			});
	
		btnaddRecordButton.setBounds(32, 39, 194, 73);
		
		home.add(btnaddRecordButton);
		
		JButton btnviewReportButton = new JButton("View report");
		btnviewReportButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				ViewReport();
			}
		});
		btnviewReportButton.setBounds(32, 168, 194, 73);
		home.add(btnviewReportButton);
		
		JButton btnaddNewItemButton = new JButton("Items");
		btnaddNewItemButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				Items();
			}
		});
		btnaddNewItemButton.setBounds(32, 363, 89, 23);
		home.add(btnaddNewItemButton);
		
		JButton btnNewEmployeeButton = new JButton("Employee");
		btnNewEmployeeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				Employees();
			}
		});
		btnNewEmployeeButton.setBounds(153, 363, 122, 23);
		
		home.add(btnNewEmployeeButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(627, 13, 244, 166);
		home.add(panel);
		

		JButton AddReturnDatebutton = new JButton("Add Return Date");
		AddReturnDatebutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				returnFrame();
			}
		});
		AddReturnDatebutton.setBounds(264, 39, 194, 73);
		home.add(AddReturnDatebutton);
		
		
		JLabel lblImage = new JLabel("");
		//lblImage.setIcon(new ImageIcon("C:\\Users\\Olivier\\Documents\\University_of_Nebraska_at_Kearney_(logo).png"));
	//	panel.add(lblImage);
		revalidate();
		repaint();
	}
	
		//establish the session
		public void session(){
			

			// Components related to "login" field
			JLabel labelLoginName = new JLabel("Enter your server user name:");
			JTextField loginName = new JTextField(15);

			// Components related to "password" field
			JLabel labelPassword = new JLabel("Enter your server password:");
			JPasswordField password = new JPasswordField();

			Object[] array = { labelLoginName, 
					loginName,
					labelPassword,
					password
			};

			int res = JOptionPane.showConfirmDialog(null, array, "Login", 
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);

			int assigned_port;   
			final int local_port=3306;

			// Remote host and port
			final int remote_port=3306;
			final String remote_host="cs.unk.edu";

			try {
				JSch jsch = new JSch(); 

				// Create SSH session.  Port 22 is your SSH port which
				// is open in your fire-wall setup.
				Session session = jsch.getSession(loginName.getText(), remote_host, 22);
				String passwordString = new String(password.getPassword());
				session.setPassword(passwordString);

				// Additional SSH options.  See your ssh_config manual for
				// more options.  Set options according to your requirements.
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				config.put("Compression", "yes");
				config.put("ConnectionAttempts","2");

				session.setConfig(config);

				// Connect
				session.connect();       
				//        System.out.println("Host: "+session.getHost());

				// Create the tunnel through port forwarding.  
				// This is basically instructing jsch session to send 
				// data received from local_port in the local machine to 
				// remote_port of the remote_host
				// assigned_port is the port assigned by jsch for use,
				// it may not always be the same as
				// local_port.

				assigned_port = session.setPortForwardingL(local_port, 
						"127.0.0.1", remote_port);

			} catch (JSchException e1) {            
				System.out.println(Level.SEVERE + " " + e1.getMessage()); 
				System.exit(0);
				return;
			}

			if (assigned_port == 0) {
				System.out.println(Level.SEVERE + " " + "Port forwarding failed !"); 
				System.exit(0);
				return;
			}

			System.out.println("-Connected to the server!!");
		}
		
	
		//Starting login screen
		public void start()
			{
				JPanel cp = new JPanel();
		        cp.setLayout(new BorderLayout(5, 5));      

		        JPanel bp = new JPanel();
		        //bp.setOpaque(false);
		        bp.setLayout(new BorderLayout(5, 5));
		        JPanel tp = new JPanel();
		        
		        JLabel lln = new JLabel("Enter your user name :");
		        lln.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lln.setBounds(0, 35, 131, 33);
			    userNam = new JTextField(10);
			    userNam.setFont(new Font("Tahoma", Font.BOLD, 12));
			    userNam.setBounds(152, 34, 268, 33);
			    lln.setForeground(Color.black);

			    JLabel lp = new JLabel("Enter your password :");
			    lp.setFont(new Font("Tahoma", Font.BOLD, 11));
			    lp.setBounds(0, 101, 150, 33);
			    pwd = new JPasswordField(10);
			    pwd.setBounds(152, 101, 268, 33);
			    lp.setForeground(Color.black);
			    pwd.setEchoChar('*');
			    tp.setLayout(null);
			    tp.add(lln);tp.add(userNam);
			    tp.add(lp);tp.add(pwd);
			    JLabel label = new JLabel("");
			    label.setBounds(0, 134, 456, 33);
			    tp.add(label);
			    
			    JPanel botp = new JPanel();
		        //botp.setOpaque(false);
		        
		        JButton logbut = new JButton("Login");
			    logbut.addActionListener(new loginbutlistener());
			    botp.add(logbut);
			    
			    bp.add(tp, BorderLayout.CENTER);
		        bp.add(botp, BorderLayout.PAGE_END);
		 
		        cp.add(bp, BorderLayout.CENTER);
		        getContentPane().add(cp);
			}
		
		
		
		public void addRecord() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 924, 495);
			addRecord = new JPanel();
			addRecord.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(addRecord);
			addRecord.setLayout(null);
			
			JButton btnBack = new JButton("Back");
		
			btnBack.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//add listerner for the back button
					home();
				}
			});
			btnBack.setBounds(10, 376, 141, 46);
			addRecord.add(btnBack);
			
			JLabel lblStuID = new JLabel("Enter student ID: ");
			lblStuID.setBounds(28, 27, 175, 14);
			addRecord.add(lblStuID);
			
			JLabel lblEnterStudentName = new JLabel("Enter student first name:");
			lblEnterStudentName.setBounds(28, 68, 175, 14);
			addRecord.add(lblEnterStudentName);
			
			JLabel lblEnterStudentLast = new JLabel("Enter student last name:");
			lblEnterStudentLast.setBounds(28, 107, 175, 14);
			addRecord.add(lblEnterStudentLast);
			
			JLabel lblEnterStudentRoom = new JLabel("Enter student room number:");
			lblEnterStudentRoom.setBounds(28, 141, 175, 14);
			addRecord.add(lblEnterStudentRoom);
			
			JLabel lblEnterStudentPhone = new JLabel("Enter student phone number:");
			lblEnterStudentPhone.setBounds(28, 177, 175, 14);
			addRecord.add(lblEnterStudentPhone);
			
			JLabel lblItemCheckedOut = new JLabel("Item checked out:");
			lblItemCheckedOut.setBounds(28, 213, 175, 14);
			addRecord.add(lblItemCheckedOut);
			
			JLabel lblEmployeeLName = new JLabel("Choose employee:");
			lblEmployeeLName.setBounds(28, 257, 175, 14);
			addRecord.add(lblEmployeeLName);
			
			String[] empdata = populateEmployeeCombobox();
			comboBoxEmployee_chkout = new JComboBox(empdata);
			//comboBoxEmployee_chkout.setEditable(true);
			comboBoxEmployee_chkout.setBounds(266, 254, 272, 20);
			addRecord.add(comboBoxEmployee_chkout);
			
			textField_STU_ID = new JTextField();
			textField_STU_ID.setFont(new Font("Calibri", Font.BOLD, 13));
			textField_STU_ID.setBounds(266, 24, 272, 20);
			addRecord.add(textField_STU_ID);
			textField_STU_ID.setColumns(10);
			
			textField_StufNam = new JTextField();
			textField_StufNam.setFont(new Font("Calibri", Font.BOLD, 13));
			textField_StufNam.setColumns(10);
			textField_StufNam.setBounds(266, 65, 272, 20);
			addRecord.add(textField_StufNam);
			
			textField_StuLName = new JTextField();
			textField_StuLName.setFont(new Font("Calibri", Font.BOLD, 13));
			textField_StuLName.setColumns(10);
			textField_StuLName.setBounds(266, 104, 272, 20);
			addRecord.add(textField_StuLName);
			
			textField_RoomNumber = new JTextField();
			textField_RoomNumber.setFont(new Font("Calibri", Font.BOLD, 13));
			textField_RoomNumber.setColumns(10);
			textField_RoomNumber.setBounds(266, 138, 272, 20);
			addRecord.add(textField_RoomNumber);
			
			textFieldPhone = new JTextField();
			textFieldPhone.setFont(new Font("Calibri", Font.BOLD, 13));
			textFieldPhone.setColumns(10);
			textFieldPhone.setBounds(266, 174, 272, 20);
			addRecord.add(textFieldPhone);
			
			String[] data = populateItemCombobox();			
			comboBoxItem_chkout = new JComboBox(data);
			//comboBoxItem_chkout.setEditable(true);
			comboBoxItem_chkout.setBounds(266, 210, 272, 20);
			addRecord.add(comboBoxItem_chkout);
			
			JButton btnHelp = new JButton("Help");
			btnHelp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//Add a JOptionPaneMessage box to for the help
					JOptionPane.showMessageDialog(null, "Please enter checkout information.\n" +
							"The note field is optional");
				}
			});
			btnHelp.setBounds(657, 388, 89, 23);
			addRecord.add(btnHelp);
			
			JLabel label = new JLabel("Date:");
			label.setBounds(28, 300, 92, 14);
			addRecord.add(label);
			
			SqlDateModel model = new SqlDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			
			JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
			datePanel.setBounds(266, 300, 272, 20);
			
			datePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
			datePicker.setBounds(266, 300, 272, 20);
			
			addRecord.add(datePicker);
			
			
			JButton btnOk = new JButton("OK");
			btnOk.addActionListener(new checkoutListener());
				
			btnOk.setBounds(657, 322, 89, 23);
			addRecord.add(btnOk);
			
			JPanel NotePanel = new JPanel();
			NotePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Note", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			NotePanel.setBounds(579, 27, 250, 242);
			addRecord.add(NotePanel);
			NotePanel.setLayout(null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(6, 16, 238, 219);
			NotePanel.add(scrollPane);
			textArea_1.setLineWrap(true);
			scrollPane.setViewportView(textArea_1);
			
		
			revalidate();
			repaint();
			
		}
		
		public void clearfield(){
			
			textField_STU_ID.setText("");
			textField_StufNam.setText("");
			textField_StuLName.setText("");
			textField_RoomNumber.setText("");
			textFieldPhone.setText("");
			textArea_1.setText("");
			
			
		}
		

		
		public class DateLabelFormatter extends AbstractFormatter {

		    private String datePattern = "yyyy-MM-dd";
		    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		    @Override
		    public Object stringToValue(String text) throws ParseException {
		        return dateFormatter.parseObject(text);
		    }

		    @Override
		    public String valueToString(Object value) throws ParseException {
		        if (value != null) {
		            Calendar cal = (Calendar) value;
		            return dateFormatter.format(cal.getTime());
		        }

		        return "";
		    }

		}
		
		
		public void Items() {
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 924, 495);
			Items = new JPanel();
			Items.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(Items);
			Items.setLayout(null);
			
			JButton btnNewItemButton = new JButton("Add Item");
			btnNewItemButton.addActionListener(new addNewItemListener());
			
		
			
			btnNewItemButton.setBounds(642, 50, 196, 73);
			Items.add(btnNewItemButton);
			
			JButton buttonDeleteItem = new JButton("Delete Item");
			buttonDeleteItem.addActionListener(new delItem());
			buttonDeleteItem.setFont(new Font("Tahoma", Font.BOLD, 11));
			buttonDeleteItem.setForeground(Color.RED);
			buttonDeleteItem.setBounds(642, 155, 196, 73);
			Items.add(buttonDeleteItem);
			
			
			JButton btnNewButton = new JButton("Help");
			btnNewButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "You can add a new Item or Delete an Item (with care!!");
				}
			});
			btnNewButton.setBounds(749, 374, 89, 23);
			Items.add(btnNewButton);
			JButton btnBack = new JButton("Back");
			btnBack.setBounds(626, 374, 89, 23);
			btnBack.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0){
					home();
				}
			});
			Items.add(btnBack);
			
			populateItemData();
			
			ItemTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					try{
						
						int row = ItemTable.getSelectedRow();
						itemID = (ItemTable.getModel().getValueAt(row, 0).toString());
					//	JOptionPane.showMessageDialog(null, itemID);
						
					}catch(Exception e){
						System.out.println(e);
					}
					
					
				}
			});
				
			
			
			revalidate();
			repaint();
		}
		
		public void Employees() {
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 924, 495);
			Employees = new JPanel();
			Employees.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(Employees);
			Employees.setLayout(null);
			
			
			
			JButton btnNewEmployeeButton = new JButton("Add Employee");
			btnNewEmployeeButton.addActionListener(new addNewEmployeeListener());
		
			
			btnNewEmployeeButton.setBounds(642, 50, 196, 73);
			Employees.add(btnNewEmployeeButton);
			
			JButton buttonDeleteEmp = new JButton("Delete Employee");
			buttonDeleteEmp.addActionListener(new delEmp());
			buttonDeleteEmp.setFont(new Font("Tahoma", Font.BOLD, 11));
			buttonDeleteEmp.setForeground(Color.RED);
			buttonDeleteEmp.setBounds(642, 155, 196, 73);
			Employees.add(buttonDeleteEmp);
			
				
			JButton btnHelpButton = new JButton("Help");
			btnHelpButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "You can add a new Employee or Delete an Employee (with care!!");
				}
			});
			btnHelpButton.setBounds(749, 374, 89, 23);
			Employees.add(btnHelpButton);
			
			JButton btnBack = new JButton("Back");
			btnBack.setBounds(626, 374, 89, 23);
			btnBack.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0){
					home();
				}
			});
			
			Employees.add(btnBack);
			
			populateEmployeeData();
			
			EmployeeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try{
					
					int row = EmployeeTable.getSelectedRow();
					empID = (EmployeeTable.getModel().getValueAt(row, 0).toString());
				//	JOptionPane.showMessageDialog(null, empID);
					
				}catch(Exception e){
					System.out.println(e);
				}
				
				
			}
		});
			
			
			revalidate();
			repaint();
		}
		
		
		public void ViewReport(){
			
			setBounds(100, 100, 924, 495);
			viewRecord = new JPanel();
			viewRecord.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(viewRecord);
			viewRecord.setLayout(null);
			
				
			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					home();
				}
			});
			
			btnBack.setBounds(767, 393, 97, 25);
			viewRecord.add(btnBack);
			populateRecord();
			
			revalidate();
			repaint();
		}
		
		public void returnFrame(){
			
			setBounds(100, 100, 924, 495);
			returnFrame = new JPanel();
			returnFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(returnFrame);
			returnFrame.setLayout(null);
			
				
			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					home();
				}
			});
			
			btnBack.setBounds(767, 393, 97, 25);
			returnFrame.add(btnBack);
			
			JButton btnAddDate = new JButton("Add date");
			btnAddDate.addActionListener(new addDatelistener());
				
			btnAddDate.setBounds(767, 353, 97, 25);
			returnFrame.add(btnAddDate);
			
			JLabel lblChooseDate = new JLabel("Choose Date:");
			lblChooseDate.setBounds(775, 42, 97, 16);
			returnFrame.add(lblChooseDate);
			
			SqlDateModel model = new SqlDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
			datePanel.setBounds(775, 78, 121, 25);
			datePicker1 = new JDatePickerImpl(datePanel,new DateLabelFormatter());
			datePicker1.setBounds(775, 78, 121, 25);
			returnFrame.add(datePicker1);
			
			populateReturn();
			
			returnFrameTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					try{
						
						int row = returnFrameTable.getSelectedRow();
						chkID = (returnFrameTable.getModel().getValueAt(row, 0).toString());
						//JOptionPane.showMessageDialog(null, chkID);
						
					}catch(Exception e){
						System.out.println(e);
					}
					
					
				}
			});
			
			revalidate();
			repaint();
		}
		
		
		
		
		
		//Listeners
		
		private class checkoutListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e){
				//Write code for listerner for the 'OK' button in the add record panel
				
				String sID = textField_STU_ID.getText();
				String sfName = textField_StufNam.getText();
				String slName = textField_StuLName.getText();
				String srNumber = textField_RoomNumber.getText();
				String PhoneNumber = textFieldPhone.getText();
				String areaCode = PhoneNumber.substring(0, 3);
				String SubCode = PhoneNumber.substring(3);
				String ides = (String) comboBoxItem_chkout.getSelectedItem();
				String empname = (String) comboBoxEmployee_chkout.getSelectedItem();
				String note = textArea_1.getText();
				
				
				java.sql.Date selectedDate = (java.sql.Date) datePicker.getModel().getValue();
				/*
				System.out.println(sID);
				System.out.println(sfName);
				System.out.println(slName);
				System.out.println(srNumber);
				System.out.println(areaCode);
				System.out.println(SubCode);
				System.out.println(ides);
				System.out.println(empname);
				System.out.println(note);
				System.out.println(selectedDate.toString());
				*/
				
				try{
					cs = conn.prepareCall("{Call newRental(?,?,?,?,?,?,?,?,?,?)}");
					cs.setString(1, sID);
					cs.setString(2, sfName);
					cs.setString(3, slName);
					cs.setString(4, srNumber);
					cs.setString(5, areaCode);
					cs.setString(6, SubCode);
					cs.setString(7, ides);
					cs.setString(8, empname);
					cs.setString(9, note);
					cs.setDate(10, selectedDate);
					cs.executeQuery();
					
					System.out.println("Rental created");
					JOptionPane.showMessageDialog(null, "Record Added");
					clearfield();
					
					
				}catch(SQLException ex){
					System.out.println(ex);
				}
				
				
				
				
				
				
				
			}
		}
		
		
		
		private class addNewItemListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e){
				//Write code for listerner for the 'add new item' button in the add record panel
				//Component related to new item field
				
				JLabel labelItemDescription = new JLabel("Enter the Item description :");
				JTextField txtFieldItemDescription = new JTextField(50);
				JLabel labelItemReplacementCost = new JLabel("Enter the Item replacement cost :");
				JTextField txtFieldItemreplacementCost = new JTextField(5);
				
				Object[] newItemArray = {labelItemDescription,txtFieldItemDescription,
						labelItemReplacementCost,txtFieldItemreplacementCost};
				
				int   status = JOptionPane.showConfirmDialog(null, newItemArray, "New Item", 
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);
				String varItemDescript = txtFieldItemDescription.getText();
				String varItemReplacementCostString=txtFieldItemreplacementCost.getText();
				
				while(varItemDescript.isEmpty() || varItemReplacementCostString.isEmpty() ) {
					JOptionPane.showMessageDialog(null, "Please enter a new item description and replacement cost");
					status = JOptionPane.showConfirmDialog(null, newItemArray, "New Item", 
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE);
					 if(status == JOptionPane.CANCEL_OPTION)
						 break;
				}
				
				if(status == JOptionPane.OK_OPTION){
					insertNewItem(varItemDescript, varItemReplacementCostString);
					Items();
				}
				
			}
		}
		
		
		
		private class addNewEmployeeListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e){
				//Write code for listerner for the add new employee button in the add record panel
				JLabel labelEmpFName = new  JLabel("Enter new employee first name");
				JTextField txtFieldNewEmpFName = new JTextField(50);
				JLabel labelEmpLName = new JLabel("Enter new employee last name");
				JTextField txtFieldNewEmpLName = new JTextField(50);
				
				
				
				Object[] newEmpArray = {labelEmpFName,txtFieldNewEmpFName,labelEmpLName,txtFieldNewEmpLName};
				
				int status = JOptionPane.showConfirmDialog(null, newEmpArray,"New Employee",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
				
				String varEmpFname = txtFieldNewEmpFName.getText();
				String varEmpLname = txtFieldNewEmpLName.getText();
				
				while(varEmpFname.isEmpty() || varEmpLname.isEmpty() ) {
					JOptionPane.showMessageDialog(null, "Please enter a new employee name");
					 status = JOptionPane.showConfirmDialog(null, newEmpArray,"New Employee",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
					 if(status == JOptionPane.CANCEL_OPTION)
						 break;
				}
				
				if(status == JOptionPane.OK_OPTION){
					insertNewEmployee(varEmpFname,varEmpLname);
					Employees();

				}
					
				
					
			}
		}
		
		public void populateEmployeeData(){
			

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(47, 50, 508, 298);
			Employees.add(scrollPane);
			
			int row = 0;
			try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "Select count(*) from employee where active is null";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = rs.getInt(1);
				rs.close();
				stmt1.close();
			}catch(SQLException ex)
			{}
			
			try{
				
				Statement stmt = conn.createStatement();
				String varSQL = "SELECT * FROM employee where active is null";
				ResultSet rs = stmt.executeQuery(varSQL);
				ResultSetMetaData rsMeta = rs.getMetaData();
				int column = rsMeta.getColumnCount();
				//System.out.println(column);
				String Name="";
				String[][] rowData = new String[row][column];
				int b=0;
		
				while(rs.next()){
					
						
		                  for (int c=1;c<=column;c++)
		                   {	
		                	  Name += rs.getString(c)+" ";
		                	  rowData[b][c-1]= Name;
		                	  Name = "";
		                	  //System.out.println("row");
		                	  //System.out.println(rowData[b][c-1]);
		                   
		                 
					}
		                  
		                  b++;
				}
				
				
				EmployeeTable = new JTable();
				DefaultTableModel mymodel = new DefaultTableModel( rowData,new String[] {"Employee ID", "Employee First Name", "Employee Last Name"}){
					@Override
					public boolean isCellEditable (int row, int column){
						return false;
					}
				};
				
				EmployeeTable.setModel(mymodel);
				
				EmployeeTable.getColumnModel().getColumn(0).setPreferredWidth(103);
				EmployeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(EmployeeTable);
				
				rs.close();
				stmt.close();
				
			}catch(SQLException ex){
				System.out.println(ex);
			}
	
			
		}
		
		
		
		public void populateItemData(){
			
			ItemTable = new JTable();
			
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(47, 50, 508, 298);
			Items.add(scrollPane);
			
			int row = 0;
			
			try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "Select count(*) from item where available is null";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = rs.getInt(1);
				//System.out.println(row);
				rs.close();
				stmt1.close();
			}catch(SQLException ex)
			{}
			
			try{
				
				Statement stmt = conn.createStatement();
				String varSQL = "SELECT * FROM item where available is null";
				ResultSet rs = stmt.executeQuery(varSQL);
				ResultSetMetaData rsMeta = rs.getMetaData();
				int column = rsMeta.getColumnCount();
				//System.out.println(column);
				
				String Name="";
				String[][] rowData = new String[row][column-1];
				int b=0;
		
				while(rs.next()){
					
						
		                 for (int c=1;c<column;c++)
		                 {	
		              	  Name += rs.getString(c)+" ";
		                	  rowData[b][c-1]= Name;
		                	  Name = "";
		                	  //System.out.println("row");
		                	  //System.out.println(rowData[b][c-1]);
		                   
		                 
					}
		                 b++;
				}
				
				DefaultTableModel mymodel = new DefaultTableModel( rowData,new String[] {"Item ID", "Item Description"}){
					@Override
					public boolean isCellEditable (int row, int column){
						return false;
					}
				};
				

				ItemTable.setModel(mymodel);
				ItemTable.getColumnModel().getColumn(0).setPreferredWidth(103);
				ItemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(ItemTable);	
				
				rs.close();
				stmt.close();
				
			}catch(SQLException ex){
				System.out.println(ex);
			}
	
			
		}
		
		public String[] populateItemCombobox(){

			
			
			String[] xdata;
			int row = 0;
			 try{
				 Statement stmt = conn.createStatement();
				 String varSQL = "select count(*) from item where available is null";
				 ResultSet rs = stmt.executeQuery(varSQL);
				 rs.next();
				 row = rs.getInt(1);
				 rs.close();
				 stmt.close();
				 
			 }catch(SQLException ex){
				 System.out.println(ex);
			 }
			 
			 xdata = new String[row];
			 
			 try{
				 Statement stmt1 = conn.createStatement();
				 String varSQL1 = "select Item_description from item where available is null";
				 ResultSet rs = stmt1.executeQuery(varSQL1);
				 
				 int b =0;
				  
				 while(rs.next()){
					 xdata[b] = rs.getString(1);
					 b++;
				 }
				 
			 }catch(SQLException ex){
				 System.out.println(ex);
			 }
			
			return xdata;
			
		}
		
		
		
		public String[] populateEmployeeCombobox(){
			
			String[] xdata;
			
			int row = 0;
			 try{
				 Statement stmt = conn.createStatement();
				 String varSQL = "select count(*) from empview";
				 ResultSet rs = stmt.executeQuery(varSQL);
				 rs.next();
				 row = rs.getInt(1);
				 rs.close();
				 stmt.close();
				 
			 }catch(SQLException ex){
				 System.out.println(ex);
			 }
			 
			 xdata = new String[row];
			 
			 try{
				 Statement stmt1 = conn.createStatement();
				 String varSQL1 = "select empName from empview";
				 ResultSet rs = stmt1.executeQuery(varSQL1);
				 
				 int b =0;
				  
				 while(rs.next()){
					 xdata[b] = rs.getString(1);
					 b++;
				 }
				 
			 }catch(SQLException ex){
				 System.out.println(ex);
			 }
			
			
			return xdata;
		}
		
		
		public void populateRecord(){
			
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(30, 42, 733, 345);
			viewRecord.add(scrollPane);
		
			
			int row = 0;
			try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "Select count(*) from report";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = rs.getInt(1);
				//System.out.println(row);
				rs.close();
				stmt1.close();
			}catch(SQLException ex)
			{}
			
			try{
				
				Statement stmt = conn.createStatement();
				String varSQL = "SELECT * FROM report";
				ResultSet rs = stmt.executeQuery(varSQL);
				ResultSetMetaData rsMeta = rs.getMetaData();
				int column = rsMeta.getColumnCount();
				//System.out.println(column);
				
				String Name="";
				String[][] rowData = new String[row][column];
				int b=0;
		
				while(rs.next()){
					
						
		                 for (int c=1;c<=column;c++)
		                 {	
		              	  Name += rs.getString(c)+" ";
		                	  rowData[b][c-1]= Name;
		                	  Name = "";
		                	  //System.out.println("row");
		                	  //System.out.println(rowData[b][c-1]);
		                   
		                 
					}
		                 b++;
				}
				
				
				ReportTable = new JTable();
				
				DefaultTableModel mymodel = new DefaultTableModel( rowData,new String[] {"Stu FName", "Stu LName", "Room N","Date chkout","Return Date",
						"Item Des","Note","Emp Fname","Emp Lname"}){
					@Override
					public boolean isCellEditable (int row, int column){
						return false;
					}

				};
				
			
				ReportTable.setModel(mymodel);
				
			//	ReportTable.getColumnModel().getColumn(0).setPreferredWidth(103);
				ReportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(ReportTable);	
				
				rs.close();
				stmt.close();
				
			}catch(SQLException ex){
				System.out.println(ex);
			}
	
			
		}
		
		public void populateReturn(){
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(30, 42, 733, 345);
			returnFrame.add(scrollPane);
		
			
			int row = 0;
			try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "Select count(*) from report2";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = rs.getInt(1);
				//System.out.println(row);
				rs.close();
				stmt1.close();
			}catch(SQLException ex)
			{}
			
			try{
				
				Statement stmt = conn.createStatement();
				String varSQL = "SELECT * FROM report2";
				ResultSet rs = stmt.executeQuery(varSQL);
				ResultSetMetaData rsMeta = rs.getMetaData();
				int column = rsMeta.getColumnCount();
				//System.out.println(column);
				
				String Name="";
				String[][] rowData = new String[row][column];
				int b=0;
		
				while(rs.next()){
					
						
		                 for (int c=1;c<=column;c++)
		                 {	
		              	  Name += rs.getString(c)+" ";
		                	  rowData[b][c-1]= Name;
		                	  Name = "";
		                	  //System.out.println("row");
		                	  //System.out.println(rowData[b][c-1]);
		                   
		                 
					}
		                 b++;
				}
				
				
				returnFrameTable = new JTable();
				
				DefaultTableModel mymodel = new DefaultTableModel( rowData,new String[] {"CheckoutID","Stu FName", "Stu LName", "Room N","Date chkout","Return Date",
						"Item Des","Note","Emp Fname","Emp Lname"}){
					@Override
					public boolean isCellEditable (int row, int column){
						return false;
					}

				};
				
			
				returnFrameTable.setModel(mymodel);
				
			//	returnFrameTable.getColumnModel().getColumn(0).setPreferredWidth(103);
				returnFrameTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(returnFrameTable);	
				
				rs.close();
				stmt.close();
				
			}catch(SQLException ex){
				System.out.println(ex);
			}
	
			
			
		}
		
		public static void insertNewItem(String xItemDes,String xItemReplCost){
			
			
			try {
			
			cs = conn.prepareCall("11 2Call insert_New_Item(?,?,?)}");
			cs.setString(1, xItemDes);
			cs.setFloat(2, Float.parseFloat(xItemReplCost));
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.executeQuery();
			String message = cs.getString(3);
			
			System.out.println(message);
			
			} catch (SQLException ex) {
				
				ex.printStackTrace();
			}
			
		}
		
		public static void insertNewEmployee(String xEmpFname,String xEmpLname){
			
			
			try{
				
			
			cs = conn.prepareCall("{Call insert_New_Employee(?,?,?)}");
			cs.setString(1,xEmpFname);
			cs.setString(2, xEmpLname);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.executeQuery();
			String message = cs.getString(3);
			
			System.out.println(message);
			
			}catch (SQLException ex){
				ex.printStackTrace();
			}
			
			
		}
		
		
		
		private class addDatelistener implements ActionListener{
			
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				java.sql.Date selectedDate = (java.sql.Date) datePicker1.getModel().getValue();
				
				try{
					cs = conn.prepareCall("{Call addReturn(?,?,?)}");
					cs.setString(1,chkID);
					cs.setDate(2, selectedDate);
					cs.registerOutParameter(3, Types.VARCHAR);
					cs.executeQuery();
					String message = cs.getString(3);
					
					JOptionPane.showMessageDialog(null, message);
					
					
				}catch(SQLException ex){
					System.out.println(ex);
				}
				
			populateReturn();
				
				
			}
		}
		
		private class delItem implements ActionListener{

			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
					
					cs = conn.prepareCall("{Call delItem(?,?)}");
					cs.setString(1, itemID);
					cs.registerOutParameter(2, Types.VARCHAR);
					cs.executeQuery();
					String mess = cs.getString(2);
					
					JOptionPane.showMessageDialog(null, mess);
					
				}catch(SQLException ex){
					System.out.println(ex);
				}
				Items();
				
			}
			
		}
		
		private class delEmp implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e){
				
				try{
					
					cs = conn.prepareCall("{Call delEmployee(?,?)}");
					cs.setString(1, empID);
					cs.registerOutParameter(2, Types.VARCHAR);
					cs.executeQuery();
					String mess = cs.getString(2);
					
					JOptionPane.showMessageDialog(null, mess);
					
				}catch(SQLException ex){
					System.out.println(ex);
				}
				
				Employees();

			}
		}
	
		
		
		private class loginbutlistener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String user = "ItemCHKGuest";
				String passwordString = "Itemchk!";
				//String user = ln.getText();
				//String passwordString = new String(p.getPassword());
				if(user.equals("ItemCHKGuest") && passwordString.equals("Itemchk!")){
					try{
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						try {
							//String url = "jdbc:mysql://cs.unk.edu:22/ItemCheckoutUH";
							String url = "jdbc:mysql://localhost/ItemCheckoutUH?noAccessToProcedureBodies=true";
							conn = DriverManager.getConnection(url,user,passwordString);
							System.out.println("-Connected to the ItemCheckoutUH database");
							home();
							
							
			        	}
					catch (SQLException ex) 
				       {
							System.out.println("SQLException: " + ex.getMessage());
					        System.out.println("SQLState: " + ex.getSQLState());
					        System.out.println("VendorError: " + ex.getErrorCode());
					        JOptionPane.showMessageDialog(null, "Error!");
				       }
					}
				catch (Exception ex){
					ex.printStackTrace();
					}
				}
			}
		}


}











