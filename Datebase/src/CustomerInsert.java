
/*
 * CustomerInsert.java
 *
 * Shows how to add data to a database in a Java program
 * Note - you first must have made your SSH connection to the db server BEFORE running this program.
 */

/**
 *
 * @author  harmssk
 */

import java.sql.*;
import java.util.logging.Level;

import javax.swing.*;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
IMPORTANT NOTE:
 * You will need to change the database to your own database to run this sample.
 * Must have the CUSTOMER table in your own database
 * 
 * We need the following jar files on the buildpath for this program to run:
 * mysql-connector-java-XXX.jar //to connect Java to MySQL
 * jsch-XXX.jar //for the sessions
 */

public class CustomerInsert
{
	public static final String database = "robproductdb";

	public static void main(String[] args)
	{

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

		int assigned_port=0;   
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
		}

		if (assigned_port == 0) {
			System.out.println(Level.SEVERE + " " + "Port forwarding failed !");
			System.exit(0);
			return;
		}

		System.out.println("Server connected!!");
		// Components related to database "login" field
		JLabel labelLoginName1 = new JLabel("Enter your database user name:");
		JTextField loginName1 = new JTextField(15);

		// Components related to database "password" field
		JLabel labelPassword1 = new JLabel("Enter your database password:");
		JPasswordField password1 = new JPasswordField();

		Object[] array1 = { labelLoginName1, 
				loginName1,
				labelPassword1,
				password1
		};

		int res1 = JOptionPane.showConfirmDialog(null, array1, "Login", 
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);


		String user = loginName1.getText();
		String passwordString1 = new String(password1.getPassword());

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			try {

				StringBuilder url1 =
						new StringBuilder("jdbc:mysql://localhost:3306");
				url1.append ("/").append(database);
				System.out.println(url1);
				Connection conn = DriverManager.getConnection(url1.toString(),user,passwordString1);
				System.out.println("Should be connected");

				// Components related to customer  fields
				JLabel labelCustFName = new JLabel("Enter the customer's first name:");
				JTextField custFName = new JTextField(15);
				JLabel labelCustLName = new JLabel("Enter the customer's last name:");
				JTextField custLName = new JTextField(15);
				JLabel labelAreaCode = new JLabel("Enter the customer's area code:");
				JTextField areaCode = new JTextField(3);
				JLabel labelphone = new JLabel("Enter the customer's phone number:");
				JTextField phone = new JTextField(8);


				Object[] newCustomerArray = { labelCustFName, custFName,
						labelCustLName,	custLName,
						labelAreaCode, areaCode,
						labelphone, phone
				};

				int   status = JOptionPane.showConfirmDialog(null, newCustomerArray, "New Customer", 
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);


				String varFName = custFName.getText();
				String varLName = custLName.getText();
				String varAreaCode = areaCode.getText();
				String varLocalNumber = phone.getText();

				// Get result set
				Statement stmt = conn.createStatement();
				String varSQL = "Select Cus_FName from CUSTOMER WHERE Cus_FName = '"
						+ varFName + "' AND Cus_LName = '"+
						varLName + "' AND Cus_AreaCode = '" +varAreaCode
						+ "'AND Cus_Phone = '" + varLocalNumber + "'";

				ResultSet rs = stmt.executeQuery(varSQL);

				//check for duplicate
				if (rs.next())
				{
					System.out.println("Data duplicates an existing customer."
							+ " No changes made.");
					rs.close();
					stmt.close();
					conn.close();
				}

				varSQL = "Select Max(Cus_Code)from CUSTOMER ";

				rs = stmt.executeQuery(varSQL);

				int CID = 0;

				while (rs.next())
				{
					CID = rs.getInt(1);
				}

				CID = CID +1;

				//OK to insert new data
				varSQL = "INSERT INTO CUSTOMER (Cus_Code,Cus_LName,Cus_FName, Cus_AreaCode, Cus_Phone, Cus_Balance)"
						+ "VALUES ('"+ CID + "', "
						+ "'" + varLName + "', "
						+ "'" + varFName + "', "
						+ "'" + varAreaCode + "', "
						+ "'" + varLocalNumber + "',0)";

				int result = stmt.executeUpdate(varSQL);
				if (result == 0)
				{
					System.out.println("Problem with insert");
				}
				else
				{
					System.out.println("Record added for " + varFName+" "+varLName);
				}
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ex) 
			{
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}

		}
		catch (Exception ex) 
		{
			// handle the error
			ex.printStackTrace();
		}
		System.exit(0);
	}
}


