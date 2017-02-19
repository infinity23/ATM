package bank;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

//bank的显示界面
public class BankGui implements Runnable {
	private Bank bank;
	private JFrame frame;
	private JPanel contentPane;
	private JTextArea textArea;

	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Launch the application.
	 */

	public void run() {
		try {
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public BankGui(Bank bank) {
		this.bank = bank;
		frame = new JFrame();
		frame.setTitle("\u94F6\u884C\u540E\u53F0\u64CD\u4F5C\u8BB0\u5F55");
		frame.setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u83DC\u5355");
		mnNewMenu.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnNewMenu);
		
		JButton btnatm = new JButton("\u6253\u5F00ATM");
		btnatm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ATM();
			}
		});
		mnNewMenu.add(btnatm);
		
		JButton button = new JButton("\u9000\u51FA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(button);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 5, 424, 252);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
//		关闭窗口时发送退出命令
		frame.addWindowListener(new WindowAdapter(){   
			public void windowClosing(WindowEvent e){   
				try {
					bank.writeInfo();
				} catch (IOException e1) {
					System.out.println("存入信息失败");		
				}
			}   
		});
		
	}
}
