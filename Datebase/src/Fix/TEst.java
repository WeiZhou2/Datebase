package Fix;

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

        import java.awt.GridLayout;
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

public class TEst
{
    public static final String database = "zhouw2";

    public static void main(String[] args)
    {

		String user="zhouw2";
		String passwordString="24083455";
        int assigned_port=0;
        final int local_port=3306;

        // Remote host and port
        final int remote_port=3306;
        final String remote_host="cs.unk.edu";

        try {
            JSch jsch = new JSch();

            // Create SSH session.  Port 22 is your SSH port which
            // is open in your fire-wall setup.
            Session session = jsch.getSession(user, remote_host, 22);
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
     
    
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            try {

                StringBuilder url1 =
                        new StringBuilder("jdbc:mysql://localhost:3306");
                url1.append ("/").append(database);
                System.out.println(url1);
                Connection conn = DriverManager.getConnection(url1.toString(),user,passwordString);
                System.out.println("Should be connected");

				addHome();


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
    
    public static void addHome( ){
    	JPanel	home = new JPanel(new GridLayout(5,7));
		
		JButton view = new JButton("View");
		JButton update = new JButton("Update");
		JButton delete = new JButton("Delete");
		JButton Add = new JButton("Add");
		JButton misc = new JButton("Misc.");
		JButton exit = new JButton("Exit");
		
	//	exit.addActionListener(new ExitListener());
//		misc.addActionListener(new MiscListener());
//		view.addActionListener(new viewListener());
//		Add.addActionListener(new AddListener());
//		update.addActionListener(new UpdateListener());
//		delete.addActionListener(new DeleteListener());
		
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
		
		}

}


