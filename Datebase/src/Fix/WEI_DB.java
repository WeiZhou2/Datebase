package Fix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

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

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@SuppressWarnings("serial")
public class WEI_DB extends JFrame {

	public static final String database = "zhouw2";
	JTextArea status;
	JPanel home;
	BorderLayout board;
	ImageIcon imageFile;
	JLabel imageLabel;
	JTextField ln, sn, jn;
	JPasswordField p;
	public Connection conn;
	JScrollPane scrollpane;
	JPanel bpView, overview, Deli, Uppy;
	JTextField t1, t2, t3, t5, t6, t7;
	JComboBox t4, t8, tableview1,tableview2, tableview3;
	JLabel l1, l2, l3, l4, l5, l6, l7, l8;
	JCheckBox c1, c2, c3, c4, c5;
	boolean flag;
	double fee = 0;
	CallableStatement cs;
	
	
	
	public WEI_DB() {
		setTitle("WetChat Manage Database");
		board = new BorderLayout();
		this.setLayout(board);
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start();
		setVisible(true);
	}

	// Starting login screen
	public void start() {
		JPanel cp = new JPanel();
		cp.setLayout(new BorderLayout(5, 5));
		JPanel bp = new JPanel();
		bp.setLayout(new BorderLayout(5, 5));
		JPanel tp = new JPanel();
		tp.setLayout(new GridLayout(6, 2));

		JLabel Sn = new JLabel("Enter your Severlocate:");
		sn = new JTextField(10);
		sn.setText("cs.unk.edu");

		JLabel lln = new JLabel("Enter your user name:");
		ln = new JTextField(10);
		ln.setText("zhouw2");

		JLabel lp = new JLabel("Enter your password:");
		p = new JPasswordField(10);
		p.setText("24083455");
		
		p.setEchoChar('*');
		Sn.setForeground(Color.black);
		lln.setForeground(Color.black);
		lp.setForeground(Color.black);
		tp.add(new JLabel(""));
		tp.add(new JLabel(""));

		tp.add(Sn);
		tp.add(sn);
		tp.add(lln);
		tp.add(ln);
		tp.add(lp);
		tp.add(p);

		JPanel botp = new JPanel();
		// botp.setOpaque(false);

		JButton logbut = new JButton("Login");
		logbut.addActionListener(new loginbutlistener());
		botp.add(logbut);

		bp.add(tp, BorderLayout.CENTER);
		bp.add(botp, BorderLayout.PAGE_END);

		cp.add(bp, BorderLayout.CENTER);
		add(cp);
	}

	private class loginbutlistener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			String user = "zhouw2";
			String passwordString = "24083455";
			int assigned_port = 0;
			final int local_port = 3306;

			// Remote host and port
			final int remote_port = 3306;
			final String remote_host = "cs.unk.edu";

			try {
				JSch jsch = new JSch();

				Session session = jsch.getSession(user, remote_host, 22);
				session.setPassword(passwordString);
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				config.put("Compression", "yes");
				config.put("ConnectionAttempts", "2");

				session.setConfig(config);

				session.connect();

				assigned_port = session.setPortForwardingL(local_port, "127.0.0.1", remote_port);

			} catch (JSchException e1) {
				System.out.println(Level.SEVERE + " " + e1.getMessage());
				System.exit(0);
			}

			if (assigned_port == 0) {
				System.out.println(Level.SEVERE + " " + "Port forwarding failed !");
				System.exit(0);
				return;
			}

			System.out.println("Server connected!!");

			try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				try {

					StringBuilder url1 = new StringBuilder("jdbc:mysql://localhost:3306");
					// url1.append("/").append(database);
					System.out.println(url1);
					conn = DriverManager.getConnection(url1.toString(), user, passwordString);
					System.out.println("Should be connected");

					// conn.close();
				} catch (SQLException ex) {

					System.out.println("SQLException: " + ex.getMessage());
					System.out.println("SQLState: " + ex.getSQLState());
					System.out.println("VendorError: " + ex.getErrorCode());
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			addHome();
			// System.exit(0);
		}
	}

	public void addHome() {
		home = new JPanel(new GridLayout(5, 7));

		JButton view = new JButton("View Table");
		JButton update = new JButton("Add Message");
//		JButton delete = new JButton("Delete...");
		JButton exit = new JButton("Exit");

		exit.addActionListener(new ExitListener());
		// misc.addActionListener(new MiscListener());
		view.addActionListener(new viewListener());
		// Add.addActionListener(new AddListener());
		update.addActionListener(new UpdateListener());
		// delete.addActionListener(new DeleteListener());

		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));	
		home.add(new JLabel(""));
		home.add(new JLabel(""));	
		home.add(new JLabel(""));

		home.add(new JLabel(""));
		home.add(new JLabel(""));	
		home.add(view);
//		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(update);
		home.add(new JLabel(""));
//		home.add(delete);
		home.add(new JLabel(""));

		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));

		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(exit);
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));
		home.add(new JLabel(""));	
		home.add(new JLabel(""));
		home.add(new JLabel(""));	
		home.add(new JLabel(""));

		try {
			status.setText("Welcome Home!");
		} catch (Exception e) {
		}
		if (board.getLayoutComponent(BorderLayout.CENTER) != null) {
			remove(board.getLayoutComponent(BorderLayout.CENTER));
		}
		if (board.getLayoutComponent(BorderLayout.SOUTH) == null) {
			JPanel Panel = new JPanel(new GridLayout(1, 1));
			status = new JTextArea("Welcome to the UNK Band Database", 3, 15);
			status.setLineWrap(true);
			status.setBackground(Color.black);
			status.setForeground(Color.white);
			Panel.add(status);

			add(Panel, BorderLayout.SOUTH);
		}
		add(home, BorderLayout.CENTER);
		this.setSize(700, 590);
		revalidate();
		repaint();
	}

	private class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

//	private class MiscListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			addMiscScreen();
//		}
//	}

	private class homeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			addHome();
		}
	}

	private class viewListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			addView();
		}
	}

	public void addView() {
		bpView = new JPanel(new BorderLayout());
		JPanel table = new JPanel(new GridLayout(1, 2));
		String[] tables = { "(Select Option)", "Childrens", "Class", "Parents", "Teacher", "Message" };
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

	private class tableviewListener implements ActionListener {
		@Override
		@SuppressWarnings("tableView")
		public void actionPerformed(ActionEvent e) {
			JComboBox comb = (JComboBox) e.getSource();
			String newChoice = (String) comb.getSelectedItem();
			if (newChoice != "(Select Option)") {
				try {
					if (bpView.getComponent(1) != null)
						bpView.remove(1);

				} catch (Exception ex) {
					System.out.println("1");
				}

				status.setText("You are viewing the " + database + "." + newChoice + " table.");

				int row = 0;
				try {
					Statement stmt1 = conn.createStatement();
					String varSQL1 = "SELECT COUNT(*) FROM " + database + "." + newChoice;
					ResultSet rs = stmt1.executeQuery(varSQL1);
					rs.next();
					row = rs.getInt(1);
					rs.close();
					stmt1.close();
				} catch (SQLException ex) {
					System.out.println("2");
				}

				try {
					Statement stmt = conn.createStatement();
					String varSQL = "";
					varSQL = "Select * from " + database + "." + newChoice;
					ResultSet rs = stmt.executeQuery(varSQL);
					ResultSetMetaData rsMeta = rs.getMetaData();
					int columns = rsMeta.getColumnCount();
					String[][] rowData = new String[row][columns];
					String[] columnNames = new String[columns];
					String Name = "";
					for (int c = 1; c <= columns; c++) {
						Name += rsMeta.getColumnName(c) + " ";
						columnNames[c - 1] = Name;
						Name = "";
						// System.out.println(columnNames[c-1]);
					}
					int b = 0;
					while (rs.next()) {
						for (int c = 1; c <= columns; c++) {
							Name += rs.getString(c) + " ";
							rowData[b][c - 1] = Name;
							Name = "";
							// System.out.println("row");
							// System.out.println(rowData[b][c-1]);
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

				} catch (SQLException ex) {
					System.out.println(ex);
				}
			} else
				status.setText("You must select an option to view a table.");
		}
	}

	private class AddListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			addAdd();
		}
	}

	

	public void addAdd() {
		Uppy =new JPanel(new BorderLayout());
		JPanel table = new JPanel(new GridLayout(1, 2));
		JPanel tp = new JPanel();
		tp.setLayout(new GridLayout(10, 2));

		JLabel Sn = new JLabel("Enter Teacher ID:");
		sn= new JTextField(10);

		JLabel lln = new JLabel("Enter Child ID:");
		ln = new JTextField(10);

		JLabel lb =new JLabel("Enter Message");
		jn= new JTextField(1000);
		
		p.setEchoChar('*');
		Sn.setForeground(Color.black);
		lln.setForeground(Color.black);
		lb.setForeground(Color.black);
		tp.add(new JLabel(""));
		tp.add(new JLabel(""));

		tp.add(Sn);
		tp.add(sn);
		tp.add(lln);
		tp.add(ln);
		tp.add(lb);
		tp.add(jn);
		tp.add(new JLabel(""));
		tp.add(new JLabel(""));
		
		JPanel botp = new JPanel();
		// botp.setOpaque(false);

		JButton add = new JButton("Add");
		add.addActionListener(new addMessage());
		botp.add(add);
		JButton back = new JButton("Home");
		back.addActionListener(new homeListener());
		botp.add(back);
		Uppy.add(tp, BorderLayout.CENTER);
		Uppy.add(botp, BorderLayout.PAGE_END);
		
		remove(home);
		add(Uppy);
		
		
		this.setSize(500, 390);
		revalidate();
		repaint();
	}
	
	
	
	public String[][] ClassList() {
		String[][] rowData=null;
		
		int row = 0;
		try {
			Statement stmt1 = conn.createStatement();
			String varSQL1 = "SELECT COUNT(*) FROM " + database + "." + "";
			ResultSet rs = stmt1.executeQuery(varSQL1);
			rs.next();
			row = rs.getInt(1);
			rs.close();
			stmt1.close();
		} catch (SQLException ex) {
			System.out.println("ClassList Roll/n");
		}

		try {
			Statement stmt = conn.createStatement();
			String varSQL = "";
			varSQL = "Select * from " + database + "." + "Class";
			ResultSet rs = stmt.executeQuery(varSQL);
			ResultSetMetaData rsMeta = rs.getMetaData();
			int columns = rsMeta.getColumnCount();
			rowData = new String[row][columns];
			String[] columnNames = new String[columns];
			String Name = "";
			for (int c = 1; c <= columns; c++) {
				Name += rsMeta.getColumnName(c) + " ";
				columnNames[c - 1] = Name;
				Name = "";
				// System.out.println(columnNames[c-1]);
			}
			int b = 0;
			while (rs.next()) {
				for (int c = 1; c <= columns; c++) {
					Name += rs.getString(c) + " ";
					rowData[b][c - 1] = Name;
					Name = "";
					// System.out.println("row");
					// System.out.println(rowData[b][c-1]);
				}
				b++;
			}
			
			
			
			revalidate();
			repaint();
			rs.close();
			stmt.close();
			
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		
		
		return rowData;
		
	
	
	
	}
	public class addMessage implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String teacherID = sn.getText();
			String childID = ln.getText();
			String Message = jn.getText();
			int teacher=0;
			int child=0;
			
			
			
			boolean teacherIDCheck=IntigerCheck(teacherID);
			boolean childIDCheck=IntigerCheck(childID);
			if(teacherIDCheck)
			{
				teacher=  Integer.parseInt(teacherID);
				
			}
			if(teacherIDCheck)
			{
				child=  Integer.parseInt(childID);
			}
			
			
			
			
			if (teacherIDCheck && childIDCheck&& Message.length()<1000) {
				try {
//					StringBuilder url1 = new StringBuilder("jdbc:mysql://localhost:3306");
//					// url1.append("/").append(database);
//					System.out.println(url1);
//					conn = DriverManager.getConnection(url1.toString(), z, passwordString);

					StringBuilder url1 = new StringBuilder("jdbc:mysql://localhost:3306");
					// url1.append("/").append(database);
					System.out.println(url1);
					conn = DriverManager.getConnection(url1.toString(), "zhouw2", "24083455");
					
					
					cs = conn.prepareCall("{Call 425ProjectADDMessage(?,?,?,@msg)}");
					cs.setInt(1, child);
					cs.setInt(2, teacher);
					cs.setString(3, Message);
					cs.executeQuery();
					
					
//					
//					Statement stmt1 = conn.createStatement();
//					String varSQL1 = "CALL 425ProjectADDMessage("+child+","+teacher+","+Message+",@msg );";
//					int result = stmt1.executeUpdate(varSQL1);
//					rs.close();
					cs.close();
					
					JOptionPane.showMessageDialog(null, " The "+child+","+teacher+","+Message+"Message added");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		
		
		
	}

	public boolean IntigerCheck(String s){
		
		
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
		return true;
		
	}
		
		
		
	private class UpdateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			addAdd();
		}
	}

//	private class DeleteListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			addDelete();
//		}
//	}

	public static void main(String[] args) {
		@SuppressWarnings("Start")
		WEI_DB DB = new WEI_DB();
	}

}