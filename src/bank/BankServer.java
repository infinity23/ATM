package bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer implements Runnable {
	Bank bank;
	ServerSocket serverSocket;

	public static final int PORT = 54321;// �����Ķ˿ں�

	public BankServer(Bank bank) {
		this.bank = bank;
	}

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				// ����atm�ն�����
				Socket client = serverSocket.accept();
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("�������쳣: " + e.getMessage());
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
				// ��ȡ�ͻ�������
				Operation operation = (Operation) input.readObject();

				System.out.println("��ȡ�ͻ�������: " + operation);
				// ����ͻ�������
				Operation o = bank.OperationHandler(operation);
				System.out.println("�ظ��ͻ�������: " + o.getOperation());

				// ��ͻ��˻ظ���Ϣ
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(o);
				
				
				input.close();
				out.close();

			} catch (Exception e) {
				System.out.println("������ run �쳣: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("����� finally �쳣:" + e.getMessage());
					}
				}
			}
		}
	}

}