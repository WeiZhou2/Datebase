
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DB_window extends JFrame{

	JTextArea status;
	JPanel home;
	BorderLayout board;
	ImageIcon imageFile;
	JLabel imageLabel;
	JTextField ln;
	JPasswordField p;
	Connection conn;
	JScrollPane scrollpane; 
	JPanel bpView, overview, Deli, Uppy;
	JTextField t1,t2,t3,t5,t6,t7;
	JComboBox t4,t8, tableview1, tableview3;
	JLabel l1,l2,l3,l4,l5,l6, l7, l8;
	JCheckBox c1,c2,c3,c4,c5;
	boolean flag;double fee=0;
	
	public DB_window(){
		setTitle("UNK Band Database");
		board = new BorderLayout();
		this.setLayout(board);
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		start();
		setVisible(true);
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
	        //tp.setOpaque(false);
	        tp.setLayout(new GridLayout(6, 2));
	        
	        JLabel lln = new JLabel("Enter your user name:");
		    ln = new JTextField(10);
		    lln.setForeground(Color.black);

		    JLabel lp = new JLabel("Enter your password:");
		    p = new JPasswordField(10);
		    lp.setForeground(Color.black);
		    p.setEchoChar('*');
		    
		    tp.add(new JLabel(""));tp.add(new JLabel(""));
		    tp.add(new JLabel(""));tp.add(new JLabel(""));
		    tp.add(lln);tp.add(ln);
		    tp.add(lp);tp.add(p);
		    tp.add(new JLabel(""));tp.add(new JLabel(""));
		    tp.add(new JLabel(""));tp.add(new JLabel(""));
		    
		    JPanel botp = new JPanel();
	        //botp.setOpaque(false);
	        
	        JButton logbut = new JButton("Login");
		    logbut.addActionListener(new loginbutlistener());
		    botp.add(logbut);
		    
		    bp.add(tp, BorderLayout.CENTER);
	        bp.add(botp, BorderLayout.PAGE_END);
	 
	        cp.add(bp, BorderLayout.CENTER);
	        add(cp);
		}

	//add the home screen
	public void addHome(){
		home = new JPanel(new GridLayout(5,7));
		
		JButton view = new JButton("View");
		JButton update = new JButton("Update");
		JButton delete = new JButton("Delete");
		JButton Add = new JButton("Add");
		JButton misc = new JButton("Misc.");
		JButton exit = new JButton("Exit");
		
		exit.addActionListener(new ExitListener());
		misc.addActionListener(new MiscListener());
		view.addActionListener(new viewListener());
		Add.addActionListener(new AddListener());
		update.addActionListener(new UpdateListener());
		delete.addActionListener(new DeleteListener());
		
		home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));
		home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));
		
		home.add(new JLabel(""));home.add(view);home.add(new JLabel(""));home.add(update);home.add(new JLabel(""));
		home.add(delete);home.add(new JLabel(""));
		
		home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));
		home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));
		
		home.add(new JLabel(""));home.add(Add);home.add(new JLabel(""));home.add(misc);home.add(new JLabel(""));
		home.add(exit);home.add(new JLabel(""));
	
		home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));
		home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));home.add(new JLabel(""));
		
		try{
			status.setText("Welcome Home!");
		}
		catch (Exception e){
		}
		if(board.getLayoutComponent(BorderLayout.CENTER) != null)
			{
				remove(board.getLayoutComponent(BorderLayout.CENTER));
			}
		if(board.getLayoutComponent(BorderLayout.SOUTH)== null)
			{
				JPanel Panel = new JPanel(new GridLayout(1,1));
				status = new JTextArea("Welcome to the UNK Band Database",3,15);
				status.setLineWrap(true);
				status.setBackground(Color.black);
				status.setForeground(Color.white);
				Panel.add(status);
				
				add(Panel, BorderLayout.SOUTH);
			}
		add(home, BorderLayout.CENTER);
		this.setSize(535, 390);
		revalidate();
		repaint();
	}
	
	//add the miscellaneous screen
	public void addMiscScreen(){
		status.setText("This is the miscellaneous screen!");
		JPanel miscel = new JPanel(new GridLayout(3,5));
		JButton NewYear = new JButton("New Year");
		JButton Help = new JButton("Help");
		JButton back = new JButton("Home");
		back.addActionListener(new homeListener());
		Help.addActionListener(new HelpListener());
		NewYear.addActionListener(new NYListener());
		miscel.add(new JLabel(""));miscel.add(new JLabel(""));miscel.add(new JLabel(""));
		miscel.add(new JLabel(""));miscel.add(new JLabel(""));
		miscel.add(Help);miscel.add(new JLabel(""));miscel.add(NewYear);miscel.add(new JLabel(""));
		miscel.add(back);miscel.add(new JLabel(""));miscel.add(new JLabel(""));
		miscel.add(new JLabel(""));miscel.add(new JLabel(""));miscel.add(new JLabel(""));
		remove(home);
		add(miscel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	//add the view table section
	public void addView(){
		bpView = new JPanel(new BorderLayout());
		JPanel table = new JPanel(new GridLayout(1,2));
		String[] tables = {"(Select Option)","Alumni","Fees","InstrumentRental","LockerRental","Section","Student","Uniform","UNKLocker","UNKInstrument"};
		JComboBox tableview = new JComboBox(tables);
		tableview.addActionListener(new tableviewListener());
		JButton back = new JButton("Home");
		back.addActionListener(new homeListener());
		remove(home);
		table.add(tableview);
		table.add(back);
		bpView.add(table, BorderLayout.SOUTH);
		add(bpView, BorderLayout.CENTER);
		status.setText("Select a table from the drop down box to view it.");
		this.setSize(750, 390);
		revalidate();
		repaint();
	}
	
	//add the add screen
	public void addAdd(){
		overview = new JPanel(new BorderLayout());
		JPanel table = new JPanel(new GridLayout(1,2));
		String[] tables = {"(Select Option)","Alumni","Fees","InstrumentRental","LockerRental","Student","Uniform","UNKInstrument"};
		tableview1 = new JComboBox(tables);
		tableview1.addActionListener(new tableviewListener1());
		JButton back = new JButton("Home");
		back.addActionListener(new homeListener());
		remove(home);
		table.add(tableview1);
		table.add(back);
		overview.add(table, BorderLayout.SOUTH);
		add(overview, BorderLayout.CENTER);
		status.setText("Select which table you want to add a record to.");
		revalidate();
		repaint();
	}
	
	//add the update screen
	public void addUpdate(){
		Uppy = new JPanel(new BorderLayout());
		JPanel table = new JPanel(new GridLayout(1,2));
		String[] tables = {"(Select Option)","Alumni","Section","Student"};
		JComboBox tableview2 = new JComboBox(tables);
		tableview2.addActionListener(new tableviewListener2());
		JButton back = new JButton("Home");
		back.addActionListener(new homeListener());
		remove(home);
		table.add(tableview2);
		table.add(back);
		Uppy.add(table, BorderLayout.SOUTH);
		add(Uppy, BorderLayout.CENTER);
		status.setText("Select either the Alumni, Section, or Student table to update a record in. Some tables you will have to delete the record and add a new one if you wish to change the data.");
		revalidate();
		repaint();
	}
	
	//add the delete screen
	public void addDelete(){
		Deli = new JPanel(new BorderLayout());
		JPanel table = new JPanel(new GridLayout(1,2));
		String[] tables = {"(Select Option)","Alumni","Fees","InstrumentRental","LockerRental","Student","Uniform","UNKInstrument"};
		tableview3 = new JComboBox(tables);
		tableview3.addActionListener(new tableviewListener3());
		JButton back = new JButton("Home");
		back.addActionListener(new homeListener());
		remove(home);
		table.add(tableview3);
		table.add(back);
		Deli.add(table, BorderLayout.SOUTH);
		add(Deli, BorderLayout.CENTER);
		status.setText("Select which table you want to delete a record in.");
		revalidate();
		repaint();
	}
	
	
	//Listeners for my buttons
	private class NYListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to erase all current records?", "New Year", JOptionPane.YES_NO_OPTION);
        	if (reply == JOptionPane.YES_OPTION)
        		{
	        		try{
						Statement stmt1 = conn.createStatement();
						String varSQL1 = "CALL NewYear();";
						stmt1.executeQuery(varSQL1);
						stmt1.close();
						status.setText("The temporary records have been reset.");
					}
					catch (Exception ex)
					{status.setText("There was an error.");
					System.out.println(ex);}
        		}
        	else 
        		{}
			
		}
		
	}
	private class HelpListener implements ActionListener{

		

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "UNK Band Database" +
			"\n\nDecription:  This is a program to help input information " +
			"and better organize it for future use." +
			"\n\nDeveloper Info:\nChevy Smith" +
			"\nPhone: 308-883-1983" +
			"\nEmail: smitchev@gmail.com" +
			"\n\nFeel free to call or email with any questions you may have.");
		}
		
	}
	private class loginbutlistener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String user = "UNKBandDBGuest";
			String passwordString = "LoperBand!";
			//String user = ln.getText();
			//String passwordString = new String(p.getPassword());
			if(user.equals("UNKBandDBGuest") && passwordString.equals("LoperBand!")){
				try{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					try {
						//String url = "jdbc:mysql://cs.unk.edu:22/BandDatabase";
						String url = "jdbc:mysql://localhost/UNKBandDB";
						conn = DriverManager.getConnection(url,user,passwordString);
						addHome();
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
	private class ExitListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{try {
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.exit(0);}
	}
	private class MiscListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{addMiscScreen();}
	}
	private class homeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{addHome();}
	}
	private class viewListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{addView();}
	}
	private class AddListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{addAdd();}
	}
	private class UpdateListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{addUpdate();}
	}
	private class DeleteListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{addDelete();}
	}
	private class addAlumni implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{	
			String temp = (String) t4.getSelectedItem();
			flag = true;
			checker("Alumni");
			if (flag)
				runAddAlum(t1.getText(), t2.getText(), t3.getText(), temp, t6.getText());
		}
	}
	private class addInstRent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae){
			flag = true;
			checker("InstrumentRental");
			String[] tipi2 = new String[2];
			String[] tipi = t4.getSelectedItem().toString().split(" ");
			try{tipi2 = t8.getSelectedItem().toString().split(" ");}catch (Exception ex){}
			if (flag)
				runAddInstRent(tipi[0],tipi2,t1.getText());
		}
	}
	private class addLockRent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae){
			flag = true;
			checker("LockerRental");
			String[] tipi2 = new String[2];
			String[] tipi = t4.getSelectedItem().toString().split(" ");
			try{tipi2 = t8.getSelectedItem().toString().split(" ");}catch (Exception ex){}
			if (flag)
				runAddLockRent(tipi[0],tipi2[0],t1.getText());
		}
	}
	private class addFee implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			flag = true;
			checker("Fees");
			boolean ca1,ca2,ca3,ca4,ca5;int ia1 = 0,ia2 = 0,ia3 = 0,ia4 = 0,ia5 = 0;
			ca1 = c1.isSelected();ca2 = c2.isSelected();ca3 = c3.isSelected();
			ca4 = c4.isSelected();ca5 = c5.isSelected();
			if(ca1)
				ia1 = 1;
			if(ca2)
				ia2 = 1;
			if(ca3)
				ia3 = 1;
			if(ca4)
				ia4 = 1;
			if(ca5)
				ia5 = 1;
			String[] tipi = t4.getSelectedItem().toString().split(" ");
			if (flag)
				runAddFee(tipi[0],ia1,ia2,ia3,ia4,ia5,fee);
		}
		
	}
	private class addStudent implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			String temp1 = (String) t4.getSelectedItem();
			String temp2 = (String) t8.getSelectedItem();
			flag = true;
			checker("Student");
			if (flag)
				runAddStudent(t1.getText(), t2.getText(), t3.getText(), temp1, t5.getText(), t7.getText(), temp2);
		}
	}
	private class addInstrument implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			flag = true;
			checker("Instrument");
			if (flag)
				runAddInstrument(t1.getText(), t2.getText());
		}
	}
	private class addUniform implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ae) {
			flag = true;
			checker("Uniform");
			String[] tipi = t4.getSelectedItem().toString().split(" ");
			if(flag){
				int stu = Integer.parseInt(tipi[0]);
				int a = Integer.parseInt(t1.getText());
				int b = Integer.parseInt(t2.getText());
				int c = Integer.parseInt(t3.getText());
				int d = Integer.parseInt(t5.getText());
				runAddUniform(stu,a, b, c, d);
			}
		}	
	}	
	
	private class DelFee implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			String temp = "(Select Option)";l1.setForeground(Color.BLACK);
			try{temp = t4.getSelectedItem().toString();} catch (Exception ex){status.setText("There are no records to delete.");flag = false;}
			String[] tipi = temp.split(" ");
			if(temp.equalsIgnoreCase("(Select Option)"))
				{
					status.setText("Please select an option.");
					flag = false;
					l1.setForeground(Color.RED);
				}
			if (flag)
			{
				try{
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "CALL DELFee( "+ tipi[0] + ");";
					stmt1.executeQuery(varSQL1);
					stmt1.close();
					tableview3.setSelectedIndex(2);
					status.setText("Your record has been deleted");
				}
				catch (Exception ex)
				{status.setText("There was an error when deleting your record.");
				System.out.println(ex);}
			}
		}
		
	}
	private class DelAlumni implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			String temp = "(Select Option)";l1.setForeground(Color.BLACK);
			try{temp = t4.getSelectedItem().toString();} catch (Exception ex){status.setText("There are no records to delete.");flag = false;}
			String[] tipi = temp.split(" ");
			if(temp.equalsIgnoreCase("(Select Option)"))
				{
					status.setText("Please select an option.");
					flag = false;
					l1.setForeground(Color.RED);
				}
			if (flag)
			{
				try{
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "CALL DELAlum( "+ tipi[0] + ");";
					stmt1.executeQuery(varSQL1);
					stmt1.close();
					tableview3.setSelectedIndex(1);
					status.setText("Your record has been deleted");
				}
				catch (Exception ex)
				{status.setText("There was an error when deleting your record.");
				System.out.println(ex);}
			}
		}
		
	}
	private class DelIR implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			String temp = "(Select Option)";l1.setForeground(Color.BLACK);
			try{temp = t4.getSelectedItem().toString();} catch (Exception ex){status.setText("There are no records to delete.");flag = false;}
			String[] tipi = temp.split(" ");
			if(temp.equalsIgnoreCase("(Select Option)"))
				{
					status.setText("Please select an option.");
					flag = false;
					l1.setForeground(Color.RED);
				}
			if (flag)
			{
				try{
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "CALL DELIR( "+ tipi[0] + "," + tipi[3] + ");";
					stmt1.executeQuery(varSQL1);
					stmt1.close();
					tableview3.setSelectedIndex(3);
					status.setText("Your record has been deleted");
				}
				catch (Exception ex)
				{status.setText("There was an error when deleting your record.");
				System.out.println(ex);}
			}
		}
		
	}
	private class DelLR implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			String temp = "(Select Option)";l1.setForeground(Color.BLACK);
			try{temp = t4.getSelectedItem().toString();} catch (Exception ex){status.setText("There are no records to delete.");flag = false;}
			String[] tipi = temp.split(" ");
			if(temp.equalsIgnoreCase("(Select Option)"))
				{
					status.setText("Please select an option.");
					flag = false;
					l1.setForeground(Color.RED);
				}
			if (flag)
			{
				try{
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "CALL DELLR( "+ tipi[0] + "," + tipi[3] + ");";
					stmt1.executeQuery(varSQL1);
					stmt1.close();
					tableview3.setSelectedIndex(4);
					status.setText("Your record has been deleted");
				}
				catch (Exception ex)
				{status.setText("There was an error when deleting your record.");
				System.out.println(ex);}
			}
		}
		
	}
	private class DelStu implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			String temp = "(Select Option)";l1.setForeground(Color.BLACK);
			try{temp = t4.getSelectedItem().toString();} catch (Exception ex){status.setText("There are no records to delete.");flag = false;}
			String[] tipi = temp.split(" ");
			if(temp.equalsIgnoreCase("(Select Option)"))
				{
					status.setText("Please select an option.");
					flag = false;
					l1.setForeground(Color.RED);
				}
			if (flag)
			{
				try{
					int val = 2;
					int reply = JOptionPane.showConfirmDialog(null, "Did this student graduate?", "Graduation", JOptionPane.YES_NO_OPTION);
		        	if (reply == JOptionPane.YES_OPTION)
		        		{val = 1;}
		        	else 
		        		{val = 0;}
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "CALL DELStu( "+ tipi[0] + "," + val + ");";
					stmt1.executeQuery(varSQL1);
					stmt1.close();
					tableview3.setSelectedIndex(5);
					if(val == 1)
						status.setText("Your record has been moved to the alumni table.");
					else
						status.setText("Your record has been deleted");
				}
				catch (Exception ex)
				{
					status.setText("There was an error when deleting your record.");
					System.out.println(ex);
				}
			}
		}
		
	}
	private class DelInst implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			String temp = "(Select Option)";l1.setForeground(Color.BLACK);
			try{temp = t4.getSelectedItem().toString();} catch (Exception ex){status.setText("There are no records to delete.");flag = false;}
			String[] tipi = temp.split(" ");
			if(temp.equalsIgnoreCase("(Select Option)"))
				{
					status.setText("Please select an option.");
					flag = false;
					l1.setForeground(Color.RED);
				}
			if (flag)
			{
				try{
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "CALL DELInst( '" + tipi[1] + "');";
					stmt1.executeQuery(varSQL1);
					stmt1.close();
					tableview3.setSelectedIndex(7);
					status.setText("Your record has been deleted");
				}
				catch (Exception ex)
				{status.setText("There was an error when deleting your record.");
				System.out.println(ex);}
			}
		}
		
	}
	private class DelUni implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			String temp = "(Select Option)";l1.setForeground(Color.BLACK);
			try{temp = t4.getSelectedItem().toString();} catch (Exception ex){status.setText("There are no records to delete.");flag = false;}
			String[] tipi = temp.split(" ");
			if(temp.equalsIgnoreCase("(Select Option)"))
				{
					status.setText("Please select an option.");
					flag = false;
					l1.setForeground(Color.RED);
				}
			if (flag)
			{
				try{
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "CALL DELUni( '" + tipi[0] + "');";
					stmt1.executeQuery(varSQL1);
					stmt1.close();
					tableview3.setSelectedIndex(6);
					status.setText("Your record has been deleted");
				}
				catch (Exception ex)
				{status.setText("There was an error when deleting your record.");
				System.out.println(ex);}
			}
		}
		
	}
	
	private class caf implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{clearbox();}
	}
	private class tableviewListener implements ActionListener
	{
		@Override
		@SuppressWarnings("rawtypes")
		public void actionPerformed(ActionEvent e)
		{
			JComboBox comb = (JComboBox)e.getSource();
			String newChoice = (String)comb.getSelectedItem();
			if(newChoice != "(Select Option)")
				{
					try{
					if(bpView.getComponent(1) != null)
						bpView.remove(1);}
					catch (Exception ex)
					{}
					status.setText("You are viewing the " + newChoice + " table.");
					int row = 0;
					try{
						Statement stmt1 = conn.createStatement();
						String varSQL1 = "SELECT COUNT(*) FROM " + newChoice;
						ResultSet rs = stmt1.executeQuery(varSQL1);
						rs.next();
						row = rs.getInt(1);
						rs.close();
						stmt1.close();
					}
					catch(SQLException ex)
					{}
					try{
						Statement stmt = conn.createStatement();
						String varSQL = "";
						if(newChoice.equals("Fees") || newChoice.equals("InstrumentRental") || newChoice.equals("LockerRental") || newChoice.equals("Uniform"))
							varSQL = "Select " + newChoice + ".*, FirstName, LastName from Student, " + newChoice + " where " + newChoice +".StudentID = Student.StudentID;";
						else if(newChoice.equals("Section"))
							varSQL = "Select " + newChoice + ".*, FirstName, LastName from Student, " + newChoice + " where " + newChoice +".SectionLeader = Student.StudentID;";
						else
							varSQL = "Select * from " + newChoice;
						ResultSet rs = stmt.executeQuery(varSQL);
						ResultSetMetaData rsMeta = rs.getMetaData();
						int columns = rsMeta.getColumnCount();
						String[][] rowData = new String[row][columns];
						String[] columnNames = new String[columns];
						String Name = "";
						for(int c=1;c<=columns;c++)
		                {
		                    Name += rsMeta.getColumnName(c)+" ";		     
		                    columnNames[c-1] = Name;
		                    Name = "";
		                   // System.out.println(columnNames[c-1]);
		                }
						int b=0;
						while (rs.next())
							{
			                  for (int c=1;c<=columns;c++)
			                   {	
			                	  Name += rs.getString(c)+" ";
			                	  rowData[b][c-1]= Name;
			                	  Name = "";
			                	  //System.out.println("row");
			                	  //System.out.println(rowData[b][c-1]);
			                   }
			                  b++;
							}
						JTable table = new JTable(rowData, columnNames);
						scrollpane = new JScrollPane(table);
						bpView.add(scrollpane, BorderLayout.CENTER);
						revalidate();
						repaint();
						rs.close();
						stmt.close();
						
					}
					catch(SQLException ex)
					{System.out.println(ex);}
				
			
				
				}
			else
				status.setText("You must select an option to view a table.");
        }
	}
	private class tableviewListener1 implements ActionListener
	{
		@SuppressWarnings("rawtypes")
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox comb = (JComboBox)e.getSource();
			String newChoice = (String)comb.getSelectedItem();
			try{
				if(overview.getComponent(1) != null)
					overview.remove(1);}
				catch (Exception ex)
				{}
			switch(newChoice)
			{
				case "Alumni":
				{
					JPanel addPan = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("FirstName");
					t1 = new JTextField();
					l2 = new JLabel("LastName");
					t2 = new JTextField();
					l3 = new JLabel("Hometown");
					t3 = new JTextField();
					l4 = new JLabel("SectionID");
					t4 = new JComboBox(sec());
					l6 = new JLabel("Email");
					t6 = new JTextField();
					addPan.add(l1);addPan.add(t1);
					addPan.add(l2);addPan.add(t2);
					addPan.add(l3);addPan.add(t3);
					addPan.add(l4);addPan.add(t4);
					addPan.add(l6);addPan.add(t6);
					JButton aab = new JButton("Add");aab.addActionListener(new addAlumni());
					JButton cl = new JButton("Clear");cl.addActionListener(new caf());
					addPan.add(aab);
					addPan.add(cl);
					overview.add(addPan, BorderLayout.CENTER);
					status.setText("Fill in the fields and hit the add button to add a record to the alumni table.");
					revalidate();
					repaint();
					break;
				}
				case "Fees":
				{
					JPanel addPan = new JPanel(new GridLayout(7,2));
					l6 = new JLabel("Student");t4 = new JComboBox(stu());
					l1 = new JLabel("UniformClean");c1 = new JCheckBox();
					l2 = new JLabel("BandPolo");c2 = new JCheckBox();
					l3 = new JLabel("BaseballHat");c3 = new JCheckBox();
					l4 = new JLabel("Gloves");c4 = new JCheckBox();
					l5 = new JLabel("Shoes");c5 = new JCheckBox();
					JButton aab = new JButton("Add"); t7=new JTextField();
					aab.addActionListener(new addFee());t7.setEditable(false);
					addPan.add(l6);addPan.add(t4);
					addPan.add(l1);addPan.add(c1);c1.addItemListener(itemListener);
					addPan.add(l2);addPan.add(c2);c2.addItemListener(itemListener);
					addPan.add(l3);addPan.add(c3);c3.addItemListener(itemListener);
					addPan.add(l4);addPan.add(c4);c4.addItemListener(itemListener);
					addPan.add(l5);addPan.add(c5);c5.addItemListener(itemListener);
					addPan.add(aab);addPan.add(t7);fee = 0;t7.setText("$" + fee);
					overview.add(addPan, BorderLayout.CENTER);
					status.setText("Select a student and check the boxes to record a fee.");
					revalidate();
					repaint();
					break;
				}
				case "InstrumentRental":
				{
					JPanel addPan = new JPanel(new GridLayout(4,2));
					l1 = new JLabel("StudentID");t4 = new JComboBox(stu());
					l2 = new JLabel("UniversityInstrument");t8 = new JComboBox(inst());
					l3 = new JLabel("RentalDate (YYYY-MM-DD)");t1 = new JTextField();
					JLabel q = new JLabel("");JButton aab = new JButton("Add");aab.addActionListener(new addInstRent());
					addPan.add(l1);addPan.add(t4);addPan.add(l2);addPan.add(t8);
					addPan.add(l3);addPan.add(t1);addPan.add(q);addPan.add(aab);
					overview.add(addPan, BorderLayout.CENTER);
					status.setText("Select a student and an instrument the boxes to record an Instrument Rental.");
					revalidate();
					repaint();
					break;
				}
				case "LockerRental":
				{
					JPanel addPan = new JPanel(new GridLayout(4,2));
					l1 = new JLabel("StudentID");t4 = new JComboBox(stu());
					l2 = new JLabel("UniversityLocker");t8 = new JComboBox(lock());
					l3 = new JLabel("RentalDate (YYYY-MM-DD)");t1 = new JTextField();
					JLabel q = new JLabel("");JButton aab = new JButton("Add");aab.addActionListener(new addLockRent());
					addPan.add(l1);addPan.add(t4);addPan.add(l2);addPan.add(t8);
					addPan.add(l3);addPan.add(t1);addPan.add(q);addPan.add(aab);
					overview.add(addPan, BorderLayout.CENTER);
					status.setText("Select a student and a locker from the boxes to record a locker rental.");
					revalidate();
					repaint();
					break;
				}
				case "Student":
				{
					JPanel addPan = new JPanel(new GridLayout(8,2));
					String[] g = {"(Select Option)","Freshman", "Sophomore", "Junior", "Senior", "Super-Senior"};
					l1 = new JLabel("StudentID");t1 = new JTextField();
					l2 = new JLabel("FirstName");t2 = new JTextField();
					l3 = new JLabel("LastName");t3 = new JTextField();
					l4 = new JLabel("YearInBand");t4 = new JComboBox(g);
					l5 = new JLabel("Hometown");t5 = new JTextField();
					l7 = new JLabel("Major");t7 = new JTextField();
					l8 = new JLabel("SectionID");t8 = new JComboBox(sec());
					JButton aab = new JButton("Add");aab.addActionListener(new addStudent());
					JButton cl = new JButton("Clear");cl.addActionListener(new caf());
					addPan.add(l1);addPan.add(t1);
					addPan.add(l2);addPan.add(t2);
					addPan.add(l3);addPan.add(t3);
					addPan.add(l4);addPan.add(t4);
					addPan.add(l5);addPan.add(t5);
					addPan.add(l7);addPan.add(t7);
					addPan.add(l8);addPan.add(t8);
					addPan.add(aab);addPan.add(cl);
					overview.add(addPan, BorderLayout.CENTER);
					status.setText("Fill in the fields and hit the add button to add a record to the student table.");
					revalidate();
					repaint();
					break;
				}
				case "UNKInstrument":
				{
					JPanel addPan = new JPanel(new GridLayout(3,2));
					l1 = new JLabel("Instrument Name");t1 = new JTextField();
					l2 = new JLabel("Serial Number");t2 = new JTextField();
					JLabel q = new JLabel("");JButton aab = new JButton("Add");aab.addActionListener(new addInstrument());
					addPan.add(l1);addPan.add(t1);
					addPan.add(l2);addPan.add(t2);
					addPan.add(q);addPan.add(aab);
					overview.add(addPan, BorderLayout.CENTER);
					status.setText("If the Instrument is two words please eliminate the space and make it one word.");
					revalidate();
					repaint();
					break;
				}
				case "Uniform":
				{
					JPanel addPan = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Student ID");t4 = new JComboBox(stu());
					l2 = new JLabel("Jacket Number");t1 = new JTextField();
					l3 = new JLabel("Pants Number");t2 = new JTextField();
					l4 = new JLabel("Hat Number");t3 = new JTextField();
					l5 = new JLabel("Bag Number");t5 = new JTextField();
					JButton aab = new JButton("Add");aab.addActionListener(new addUniform());
					JButton cl = new JButton("Clear");cl.addActionListener(new caf());
					addPan.add(l1);addPan.add(t4);
					addPan.add(l2);addPan.add(t1);
					addPan.add(l3);addPan.add(t2);
					addPan.add(l4);addPan.add(t3);
					addPan.add(l5);addPan.add(t5);
					addPan.add(aab);addPan.add(cl);
					overview.add(addPan, BorderLayout.CENTER);
					status.setText("Enter the number for each uniform part. You will get an error if one is a duplicate.");
					revalidate();
					repaint();
					break;
				}
			}
		}
	}
	private class tableviewListener2 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class tableviewListener3 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox comb = (JComboBox)e.getSource();
			String newChoice = (String)comb.getSelectedItem();
			try{
				if(Deli.getComponent(1) != null)
					Deli.remove(1);}
				catch (Exception ex)
				{}
			switch(newChoice)
			{
				case "Alumni":
				{
					JPanel ad = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Record"); t4 = new JComboBox(Dalumni());
					JButton del = new JButton("Delete");del.addActionListener(new DelAlumni());
					ad.add(new JLabel("Alumni Table"));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(l1);ad.add(t4);
					ad.add(new JLabel(""));ad.add(del);
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					Deli.add(ad, BorderLayout.CENTER);
					status.setText("Select a record and hit the button to delete it.");
					revalidate();
					repaint();
					break;
				}
				case "Fees":
				{
					JPanel ad = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Record"); t4 = new JComboBox(Dfee());
					JButton del = new JButton("Delete");del.addActionListener(new DelFee());
					ad.add(new JLabel("Fee Table"));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(l1);ad.add(t4);
					ad.add(new JLabel(""));ad.add(del);
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					Deli.add(ad, BorderLayout.CENTER);
					status.setText("Select a record and hit the button to delete it.");
					revalidate();
					repaint();
					break;
				}
				case "InstrumentRental":
				{
					JPanel ad = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Record"); t4 = new JComboBox(DInstRent());
					JButton del = new JButton("Delete");del.addActionListener(new DelIR());
					ad.add(new JLabel("InstrumentRental Table"));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(l1);ad.add(t4);
					ad.add(new JLabel(""));ad.add(del);
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					Deli.add(ad, BorderLayout.CENTER);
					status.setText("The first number is the Instrument Rental ID number. The next number is the University Instrument Number.");
					revalidate();
					repaint();
					break;
				}
				case "LockerRental":
				{
					JPanel ad = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Record"); t4 = new JComboBox(DLockRent());
					JButton del = new JButton("Delete");del.addActionListener(new DelLR());
					ad.add(new JLabel("LockerRental Table"));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(l1);ad.add(t4);
					ad.add(new JLabel(""));ad.add(del);
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					Deli.add(ad, BorderLayout.CENTER);
					status.setText("The first number is the locker rental ID and the next number is the locker number.");
					revalidate();
					repaint();
					break;
				}
				case "Student":
				{
					JPanel ad = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Record"); 
					t4 = new JComboBox(stu());
					JButton del = new JButton("Delete");del.addActionListener(new DelStu());
					ad.add(new JLabel("Student Table"));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(l1);ad.add(t4);
					ad.add(new JLabel(""));ad.add(del);
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					Deli.add(ad, BorderLayout.CENTER);
					status.setText("Before you delete a student please make sure that all fees, uniform, and rental records are deleted.");
					revalidate();
					repaint();
					break;
				}
				case "UNKInstrument":
				{
					JPanel ad = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Record"); t4 = new JComboBox(inst());
					JButton del = new JButton("Delete");del.addActionListener(new DelInst());
					ad.add(new JLabel("UNKInstrument Table"));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(l1);ad.add(t4);
					ad.add(new JLabel(""));ad.add(del);
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					Deli.add(ad, BorderLayout.CENTER);
					status.setText("Please make sure that all rentals for this instrument are deleted before you delete this the instrument.");
					revalidate();
					repaint();
					break;
				}
				case "Uniform":
				{
					JPanel ad = new JPanel(new GridLayout(6,2));
					l1 = new JLabel("Record"); t4 = new JComboBox(Duniform());
					JButton del = new JButton("Delete");del.addActionListener(new DelUni());
					ad.add(new JLabel("Uniform Table"));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(l1);ad.add(t4);
					ad.add(new JLabel(""));ad.add(del);
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					ad.add(new JLabel(""));ad.add(new JLabel(""));
					Deli.add(ad, BorderLayout.CENTER);
					status.setText("The first number is the UniformID number. The next numbers are (Jacket, Pants, Hat, and Bag).");
					revalidate();
					repaint();
					break;
				}
			}
		}
		
	}
	
	
	
	ItemListener itemListener = new ItemListener(){
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getItemSelectable();
		int state = e.getStateChange();
		if(state == ItemEvent.SELECTED){
			 if (source == c1) {
			    	fee += 65;
			    } else if (source == c2) {
			    	fee += 40;
			    } else if (source == c3) {
			    	fee += 20;
			    } else if (source == c4) {
			    	fee += 10;
			    } else if (source == c5) {
			    	fee += 35;
			    }
			}
	    
	    if (e.getStateChange() == ItemEvent.DESELECTED){
	    	 if (source == c1) {
	    		 	fee -= 65;
			    } else if (source == c2) {
			    	fee -= 40;
			    } else if (source == c3) {
			    	fee -= 20;
			    } else if (source == c4) {
			    	fee -= 10;
			    } else if (source == c5) {
			    	fee -= 35;
			    }
	    	}
	    
	    t7.setText("$" + fee);
		}
	};
	
	
	public void checker(String table){
		switch(table)
		{
			case "Alumni":
			{
				String temp = t4.getSelectedItem().toString();
				String q = t1.getText();String q2 = t2.getText();
				l1.setForeground(Color.black);l2.setForeground(Color.black);
				l4.setForeground(Color.black);
				if(q.isEmpty())
					{l1.setForeground(Color.RED);flag = false;}
				if(q2.isEmpty())
					{l2.setForeground(Color.RED);flag = false;}
				if(temp.isEmpty() || temp.equalsIgnoreCase("(Select Option)"))
					{l4.setForeground(Color.RED);flag = false;}
				if(!flag)
					status.setText("Please enter information into the fields indicated by red text.");
				break;
			}
			case "Fees":
			{
				l6.setForeground(Color.BLACK);
				String temp = t4.getSelectedItem().toString();
				if(temp.equalsIgnoreCase("(Select Option)"))
						flag = false;
				if(!flag){
					status.setText("Please select a student.");
					l6.setForeground(Color.RED);
				}
				break;
			}
			case "InstrumentRental":
			{
				String temp2 = "(Select Option)";
				l1.setForeground(Color.BLACK);l2.setForeground(Color.BLACK);l3.setForeground(Color.BLACK);
				String temp = t4.getSelectedItem().toString();
				try{temp2 = t8.getSelectedItem().toString();}catch (Exception ex){status.setText("No instruments can be rented");}
				String temp3 = t1.getText();
				if(temp.equalsIgnoreCase("(Select Option)"))
					{flag = false;l1.setForeground(Color.RED);status.setText("Please select an option.");}
				if(temp2.equalsIgnoreCase("(Select Option)") || temp2.isEmpty())
					{flag = false;l2.setForeground(Color.RED);status.setText("Please select an option.");}
				if(!dateformat(temp3))
					{flag = false;l3.setForeground(Color.RED);status.setText("Your date formating is incorrect.");}
				break;
			}
			case "LockerRental":
			{
				String temp2 = "(Select Option)";
				l1.setForeground(Color.BLACK);l2.setForeground(Color.BLACK);l3.setForeground(Color.BLACK);
				String temp = t4.getSelectedItem().toString();
				try{temp2 = t8.getSelectedItem().toString();}catch (Exception ex){status.setText("No lockers can be rented");}
				String temp3 = t1.getText();
				if(temp.equalsIgnoreCase("(Select Option)"))
					{flag = false;l1.setForeground(Color.RED);status.setText("Please select an option.");}
				if(temp2.equalsIgnoreCase("(Select Option)") || temp2.isEmpty())
					{flag = false;l2.setForeground(Color.RED);status.setText("Please select an option.");}
				if(!dateformat(temp3))
					{flag = false;l3.setForeground(Color.RED);status.setText("Your date formating is incorrect.");}
				break;
			}
			case "Student":
			{
				l1.setForeground(Color.BLACK);l2.setForeground(Color.BLACK);l3.setForeground(Color.BLACK);
				l4.setForeground(Color.BLACK);l8.setForeground(Color.BLACK);
				String temp = t4.getSelectedItem().toString();
				String temp2 = t8.getSelectedItem().toString();
				if(t1.getText().isEmpty() || t1.getText().toString().length() < 8 || t1.getText().toString().length() > 8)
					{flag = false;l1.setForeground(Color.RED);}
				try{int bear = Integer.parseInt(t1.getText());}catch (Exception ex){status.setText("Please enter a valid student ID number.");l1.setForeground(Color.RED);}
				if(t2.getText().isEmpty())
					{flag = false;l2.setForeground(Color.RED);}
				if(t3.getText().isEmpty())
					{flag = false;l3.setForeground(Color.RED);}
				if(temp.equalsIgnoreCase("(Select Option)"))
					{flag = false;l4.setForeground(Color.RED);status.setText("Please select an option.");}
				if(temp2.equalsIgnoreCase("(Select Option)"))
					{flag = false;l8.setForeground(Color.RED);status.setText("Please select an option.");}
				break;
			}
			case "Instrument":
			{
				l1.setForeground(Color.BLACK);l2.setForeground(Color.BLACK);
				if(t1.getText().isEmpty())
					{flag = false;l1.setForeground(Color.RED);}
				if(t2.getText().isEmpty())
					{flag = false;l2.setForeground(Color.RED);}
				break;
			}
			case "Uniform":
			{
				l1.setForeground(Color.BLACK);l2.setForeground(Color.BLACK);
				l3.setForeground(Color.BLACK);l4.setForeground(Color.BLACK);
				l5.setForeground(Color.BLACK);
				String temp = t4.getSelectedItem().toString();
				if(temp.equalsIgnoreCase("(Select Option)"))
					{flag = false;l1.setForeground(Color.RED);status.setText("Please select an option.");}
				try{Integer.parseInt(t1.getText());} catch (Exception ex){flag = false;l2.setForeground(Color.RED);status.setText("Please enter a number.");}
				try{Integer.parseInt(t2.getText());} catch (Exception ex){flag = false;l3.setForeground(Color.RED);status.setText("Please enter a number.");}
				try{Integer.parseInt(t3.getText());} catch (Exception ex){flag = false;l4.setForeground(Color.RED);status.setText("Please enter a number.");}
				try{Integer.parseInt(t5.getText());} catch (Exception ex){flag = false;l5.setForeground(Color.RED);status.setText("Please enter a number.");}
				break;
			}
		}
	}

	public boolean dateformat(String date){
		boolean value = false; 
		String[] dats = date.split("-");
		try{
			value = true;
			@SuppressWarnings("unused")
			int temp = Integer.parseInt(dats[0]);
			int temp2 = Integer.parseInt(dats[1]);
			int temp3 = Integer.parseInt(dats[2]);
			if(temp2<=0||temp2>12)
				{value = false;status.setText("Your date formating is incorrect.");}
			if(temp3<=0||temp3>31)
				{value = false;status.setText("Your date formating is incorrect.");}
		}
		catch (Exception ex){
			value = false;
			status.setText("Your date formating is incorrect.");
		}
		return value;
	}
	
	public void clearbox(){
		t1.setText("");t2.setText("");t3.setText("");
		try{t5.setText("");}catch (Exception ex){}try{t6.setText("");}catch (Exception ex){}
		try{t7.setText("");}catch (Exception ex){}
		t4.setSelectedIndex(0);try{t8.setSelectedIndex(0);}catch (Exception ex){}
		status.setText("");
		l1.setForeground(Color.BLACK);l2.setForeground(Color.BLACK);l3.setForeground(Color.BLACK);
		l4.setForeground(Color.BLACK);
		try{l5.setForeground(Color.BLACK);}catch (Exception ex){}
		try{l7.setForeground(Color.BLACK);}catch (Exception ex){}
		try{l8.setForeground(Color.BLACK);}catch (Exception ex){}
		try{l6.setForeground(Color.BLACK);}catch (Exception ex){}
	}
	
	public void runAddAlum(String t1, String t2, String t3,
			String t4, String t6) {
		try{
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "CALL AlumniAdd('"+t1+"','"+t2+"','"+t3+"','"+t4+"','"+t6+"')";
			stmt1.executeQuery(varSQL1);
			stmt1.close();
			clearbox();
			status.setText("Your record has been added");
		}
		catch (Exception ex)
		{status.setText("There was an error when adding your record. Please check your inputs.");}
	}
	public void runAddFee(String a, int b, int c, int d, int e, int f, double g){
		int stu = Integer.parseInt(a);
		try{
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "CALL FeeAdd("+stu+",'"+b+"','"+c+"','"+d+"','"+e+"','" + f+"','" + g+"')";
			stmt1.executeQuery(varSQL1);
			stmt1.close();
			status.setText("Your record has been added");
			t4.setSelectedIndex(0);c1.setSelected(false);c2.setSelected(false);c3.setSelected(false);
			c4.setSelected(false);c5.setSelected(false);
		}
		catch (Exception ex)
		{status.setText("There was an error when adding your record. Please check your inputs.");}
	}
	public void runAddInstRent(String a, String [] b, String date){
		int stu = Integer.parseInt(a);
		String ba = b[0]; String bb = b[1];
		try{
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "CALL InstRentAdd("+stu+",'"+ba+"','"+bb+"','"+date+"')";
			stmt1.executeQuery(varSQL1);
			stmt1.close();
			t4.setSelectedIndex(0);t8.setSelectedIndex(0);t1.setText("");
			t8 = new JComboBox(inst());
			tableview1.setSelectedIndex(3);
			status.setText("Your record has been added");
		}
		catch (Exception ex)
		{status.setText("There was an error when adding your record. Please check your inputs.");}
	}
	public void runAddLockRent(String a, String b, String date){
		int stu = Integer.parseInt(a);
		try{
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "CALL LockRentAdd("+stu+",'"+b+"','"+date+"')";
			stmt1.executeQuery(varSQL1);
			stmt1.close();
			t4.setSelectedIndex(0);t8.setSelectedIndex(0);t1.setText("");
			t8 = new JComboBox(lock());
			tableview1.setSelectedIndex(4);
			status.setText("Your record has been added");
		}
		catch (Exception ex)
		{status.setText("There was an error when adding your record. Please check your inputs.");}
	}
	public void runAddStudent(String a, String b, String c,
			String d, String e, String f, String g) {
		try{
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "CALL StudentAdd(" +a+ ",'" +b+"','"+c+"','"+d+"','"+e+"','"+f+"','"+g+"')";
			stmt1.executeQuery(varSQL1);
			stmt1.close();
			clearbox();
			status.setText("Your record has been added");
		}
		catch (Exception ex)
		{status.setText("There was an error when adding your record. Please check your inputs. The student ID might already exist.");}
	}
	public void runAddInstrument(String a, String b) {
		try{
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "CALL InstrumentAdd('" +a+ "','" +b+"')";
			stmt1.executeQuery(varSQL1);
			stmt1.close();
			status.setText("Your record has been added");
			t1.setText("");t2.setText("");
		}
		catch (Exception ex)
		{status.setText("There was an error when adding your record. Please check your inputs.");
		System.out.println(ex);}
	}
	public void runAddUniform(int stunum,int a, int b, int c, int d) {
		try{
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "CALL UniformAdd("+stunum+","+a+","+b+","+c+","+d+")";
			stmt1.executeQuery(varSQL1);
			stmt1.close();
			clearbox();
			status.setText("Your record has been added");
		}
		catch (Exception ex)
		{status.setText("There was an error when adding your record. Please check your inputs. You might have a duplicate number.");
		System.out.println(ex);}
	}
	
	public String[] sec(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM Section";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] secs = new String[row]; secs[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT SectionID FROM Section;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + "";
					secs[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return secs;
	}
	public String[] stu(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM Student";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] stud = new String[row]; stud[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT StudentID,FirstName,LastName FROM Student;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
					stud[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return stud;
	}
	public String[] inst(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM UNKInstrument";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] in = new String[row]; in[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT InstrumentName,SerialNum FROM UNKInstrument WHERE IsRented = 0;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2);
					in[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return in;
	}
	public String[] lock(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM UNKLocker";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] com = new String[row]; com[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT UnvLockerNum,LockerSize FROM UNKLocker WHERE IsRented = 0;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2);
					com[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return com;
	}

	public String[] Dalumni(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM Alumni";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] com = new String[row]; com[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT GraduateID,FirstName,LastName FROM Alumni;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
					com[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return com;
	}
	public String[] Dfee(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM Fees";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] com = new String[row]; com[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT Fees.FeeID, FirstName, LastName FROM Fees, Student WHERE Fees.StudentID = Student.StudentID;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
					com[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return com;
	}
	public String[] DInstRent(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM InstrumentRental";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] com = new String[row]; com[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT InstrumentRental.InstRentalID, FirstName, LastName, InstrumentRental.UnvInstrumentNum, InstrumentName FROM InstrumentRental, Student, UNKInstrument WHERE InstrumentRental.StudentID = Student.StudentID AND InstrumentRental.UnvInstrumentNum = UNKInstrument.UnvInstrumentNum;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5);
					com[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return com;
	}
	public String[] DLockRent(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM LockerRental";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] com = new String[row]; com[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT LockRentalID, FirstName, LastName, UnvLockerNum FROM LockerRental, Student WHERE LockerRental.StudentID = Student.StudentID;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
					com[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return com;
	}
	public String[] Duniform(){
		int row = 0;
		int i = 1;
		String name = "";
		try{
				Statement stmt1 = conn.createStatement();
				String varSQL1 = "SELECT COUNT(*) FROM Uniform";
				ResultSet rs = stmt1.executeQuery(varSQL1);
				rs.next();
				row = (rs.getInt(1) + 1);
				rs.close();
				stmt1.close();
		}
		catch(SQLException ex)
		{}
		String[] com = new String[row]; com[0]=("(Select Option)");
		try{
			
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT UniformID, Uniform.StudentID, FirstName, LastName, JacketNum, PantsNum, HatNum, BagNum FROM Uniform, Student WHERE Uniform.StudentID = Student.StudentID;";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			while(rs.next())
			{
					name += rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6);
					com[i]=name;
					name="";
					i++;
			}
			rs.close();
			stmt1.close();
		}
		catch (Exception ex)
		{System.out.println(ex);}
		return com;
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DB_window DB = new DB_window();
	}
}
