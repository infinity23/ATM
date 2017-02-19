package bank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//socketͨ�ŵĿͻ��ˣ���ATM�ն�
public class ATMClint implements Runnable {

	public static final String IP_ADDR = "localhost";// ��������ַ
	public static final int PORT = 54321;// �������˿ں�

	private ATM atm;
	private Operation operation;

	public ATMClint(ATM atm, Operation operation) {
		this.atm = atm;
		this.operation = operation;
	}

	@Override
	public void run() {
		System.out.println("�ͻ�������...");
		Socket socket = null;
		try {
			// ����һ�����׽��ֲ��������ӵ�ָ�������ϵ�ָ���˿ں�
			socket = new Socket(IP_ADDR, PORT);	

			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

//			��������
			out.writeObject(operation);
			System.out.println("��������"+operation.getOperation());
			

			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
//			��������
			Operation o = (Operation) input.readObject();
			System.out.println("���շ��������ݣ� " + o.getOperation());
			if(o.getAccount()!=null)
			atm.atmGui.getBalance().setText(o.getAccount().getBalance());
			atm.showMessage(o.getOperation());
			if(o.isClearATM())
				atm.clearATM();

			input.close();
			out.close();


		} catch (Exception e) {
			System.out.println("�ͻ����쳣:" + e.getMessage());
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					socket = null;
					System.out.println("�ͻ��� finally �쳣:" + e.getMessage());
				}
			}
		}
	}
}
