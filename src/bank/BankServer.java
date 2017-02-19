package bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer implements Runnable {
	Bank bank;
	ServerSocket serverSocket;

	public static final int PORT = 54321;// 监听的端口号

	public BankServer(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				// 监听atm终端请求
				Socket client = serverSocket.accept();
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("服务器异常: " + e.getMessage());
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;

		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		public void run() {
			try {
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				// 读取客户端数据
				Operation operation = (Operation) input.readObject();

				System.out.println("读取客户端数据: " + operation);
				// 处理客户端数据
				Operation o = bank.OperationHandler(operation);
				System.out.println("回复客户端数据: " + o.getOperation());

				// 向客户端回复信息
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(o);
				
				
				input.close();
				out.close();

			} catch (Exception e) {
				System.out.println("服务器 run 异常: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("服务端 finally 异常:" + e.getMessage());
					}
				}
			}
		}
	}

}