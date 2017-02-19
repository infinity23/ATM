package bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//socket通信的客户端，即ATM终端
public class ATMClint implements Runnable {

	public static final String IP_ADDR = "localhost";// 服务器地址
	public static final int PORT = 54321;// 服务器端口号

	private ATM atm;
	private Operation operation;

	public ATMClint(ATM atm, Operation operation) {
		this.atm = atm;
		this.operation = operation;
	}

	@Override
	public void run() {
		System.out.println("客户端启动...");
		Socket socket = null;
		try {
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			socket = new Socket(IP_ADDR, PORT);	

			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

//			发送数据
			out.writeObject(operation);
			System.out.println("发送数据"+operation.getOperation());
			

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
//			接收数据
			Operation o = (Operation) input.readObject();
			System.out.println("接收服务器数据： " + o.getOperation());
			if(o.getAccount()!=null)
			atm.atmGui.getBalance().setText(o.getAccount().getBalance());
			atm.showMessage(o.getOperation());
			if(o.isClearATM())
				atm.clearATM();

			input.close();
			out.close();


		} catch (Exception e) {
			System.out.println("客户端异常:" + e.getMessage());
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					socket = null;
					System.out.println("客户端 finally 异常:" + e.getMessage());
				}
			}
		}
	}
}
