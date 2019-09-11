package library;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public abstract class frame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public frame(String string) {
		super(string);
	}
}

 class start extends frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Container c;
	static JLabel lbl_id;
	static JLabel lbl_pass;
	static JTextField txt_id;
	static JPasswordField txt_pass;
	static JButton login;
	String id;
	char[] pass;

	public start()
	{
		super("library");
		c = getContentPane();
		c.setLayout(null);
		lbl_id = new JLabel("ID");
		lbl_pass = new JLabel("password");
		txt_id = new JTextField();
		txt_pass = new JPasswordField();
		login = new JButton("login");
		login.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
            {
                id=txt_id.getText();
                pass=txt_pass.getPassword();
                System.out.println("login click");
            }
			});
		lbl_id.setBounds(50,30,130,20);
		lbl_pass.setBounds(30,60,130,20);
		txt_id.setBounds(100,30,130,20);
		txt_pass.setBounds(100,60,130,20);
		login.setBounds(100,100,100,40);
		c.add(lbl_id);
		c.add(lbl_pass);
		c.add(txt_id);
		c.add(txt_pass);
		c.add(login);

		setBounds(600,300,300,200);
		setVisible(true);
	}
}