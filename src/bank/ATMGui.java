package bank;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

//ATM图形界面
public class ATMGui implements Runnable {
	

	private ATM atm;

	private JFrame frmAtm;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JFormattedTextField formattedTextField;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();

	public JFormattedTextField getMessage() {
		return formattedTextField;
	}

	public void setMessage(JFormattedTextField formattedTextField) {
		this.formattedTextField = formattedTextField;
	}

	public JTextField getUser() {
		return textField;
	}

	public void setUser(JTextField textField) {
		this.textField = textField;
	}

	public JTextField getBalance() {
		return textField_2;
	}

	public void setBalance(JTextField textField_2) {
		this.textField_2 = textField_2;
	}

	public JTextField getMoney() {
		return textField_3;
	}

	public void setMoney(JTextField textField_3) {
		this.textField_3 = textField_3;
	}

	public JPasswordField getPassword() {
		return passwordField;
	}

	public void setPassword(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			frmAtm.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 * 
	 * @param bank
	 */
	public ATMGui(ATM atm) {
		this.atm = atm;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmAtm = new JFrame();
		frmAtm.setTitle("ATM\u7EC8\u7AEF");
		frmAtm.setBounds(100, 100, 450, 368);
		frmAtm.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(148, 30, 156, 21);
		frmAtm.getContentPane().add(textField);
		textField.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(148, 107, 156, 21);
		frmAtm.getContentPane().add(textField_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(148, 142, 156, 21);
		frmAtm.getContentPane().add(textField_3);

		JLabel lblNewLabel = new JLabel("\u59D3\u540D");
		lblNewLabel.setBounds(39, 33, 54, 15);
		frmAtm.getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("\u5BC6\u7801");
		label.setBounds(39, 73, 54, 15);
		frmAtm.getContentPane().add(label);

		JLabel label_1 = new JLabel("\u8D26\u6237\u4F59\u989D");
		label_1.setBounds(39, 110, 54, 15);
		frmAtm.getContentPane().add(label_1);

		JLabel label_2 = new JLabel("\u64CD\u4F5C\u91D1\u989D");
		label_2.setBounds(39, 145, 54, 15);
		frmAtm.getContentPane().add(label_2);

		
		//登陆按钮
		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().trim().equals("") || passwordField.getPassword().length == 0)
					JOptionPane.showMessageDialog(null, "请输入用户名和密码！");
				else
					atm.sendMessage(atm.login());
			}
		});
		btnNewButton.setAction(action);
		btnNewButton.setBounds(67, 236, 93, 23);
		frmAtm.getContentPane().add(btnNewButton);

		//退出按钮
		JButton button = new JButton("\u9000\u51FA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atm.sendMessage(atm.logout());
			}
		});
		button.setBounds(176, 236, 93, 23);
		frmAtm.getContentPane().add(button);

		//注册按钮
		JButton button_1 = new JButton("\u6CE8\u518C");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().trim().equals("") || passwordField.getPassword().length == 0)
					JOptionPane.showMessageDialog(null, "请输入用户名和密码！");
				else
					atm.sendMessage(atm.reg());

			}
		});
		button_1.setAction(action_1);
		button_1.setBounds(279, 236, 93, 23);
		frmAtm.getContentPane().add(button_1);

		//存钱按钮
		JButton button_2 = new JButton("\u5B58\u94B1");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_3.getText().trim().equals(""))
					JOptionPane.showMessageDialog(null, "请输入金额！");
				else if (Integer.parseInt(textField_3.getText()) <= 0)
					JOptionPane.showMessageDialog(null, "输入错误！");
				else {
					atm.sendMessage(atm.deposit());
				}

			}
		});
		button_2.setBounds(67, 277, 93, 23);
		frmAtm.getContentPane().add(button_2);

		//取钱按钮
		JButton button_3 = new JButton("\u53D6\u94B1");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_3.getText().trim().equals(""))
					JOptionPane.showMessageDialog(null, "请输入金额！");
				else if (Integer.parseInt(textField_3.getText()) <= 0)
					JOptionPane.showMessageDialog(null, "输入错误！");
				else {
					atm.sendMessage(atm.withdraw());
				}
			}
		});
		button_3.setBounds(176, 277, 93, 23);
		frmAtm.getContentPane().add(button_3);

		//查询按钮
		JButton button_4 = new JButton("\u67E5\u8BE2");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atm.sendMessage(atm.inquiry());
			}
		});
		button_4.setBounds(279, 277, 93, 23);
		frmAtm.getContentPane().add(button_4);

		formattedTextField = new JFormattedTextField();
		formattedTextField.setEditable(false);
		formattedTextField.setText("\u63D0\u793A\u4FE1\u606F");
		formattedTextField.setBounds(67, 188, 306, 30);
		frmAtm.getContentPane().add(formattedTextField);

		passwordField = new JPasswordField();
		passwordField.setBounds(148, 76, 156, 21);
		frmAtm.getContentPane().add(passwordField);
		
//		关闭窗口时发送退出命令
		frmAtm.addWindowListener(new WindowAdapter(){   
			public void windowClosing(WindowEvent e){   
			atm.sendMessage(atm.exit()); 
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}   
		}); 
	}


	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public SwingAction() {
			putValue(NAME, "登录");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	private class SwingAction_1 extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public SwingAction_1() {
			putValue(NAME, "注册");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}
}
