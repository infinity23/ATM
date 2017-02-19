package bank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import static bank.Operation.*;

//������Bank��ز���
public class Bank {
	Thread threadOfBank;
	Thread threadOfRates;
	Thread threadOfServer;

	BankGui bankGui;
	BankServer bankServer;

	HashMap<String, Account> data;
	Properties info;
	Account account;
	Operation operation;
	String money;
//	ATM atm;
	String id;
	boolean login;

	public Bank() {
		data = new HashMap<String, Account>();
//		atm = new ATM();
		info = new Properties();
		login = false;
		operation = new Operation();

		try {
			readInfo(info);
		} catch (IOException e) {
			throw new RuntimeException(e);
			// System.out.println("�����ļ�����");
		}

		bankServer = new BankServer(this);
		bankGui = new BankGui(this);
		threadOfServer = new Thread(bankServer);
		threadOfBank = new Thread(bankGui);
		threadOfRates = new Thread(new Rates());
		threadOfServer.start();
		threadOfBank.start();
		threadOfRates.start();
	}

//	����ʱ��ȡ�ļ�����
	public void readInfo(Properties info) throws IOException {
		InputStreamReader in = new InputStreamReader(new FileInputStream("./accountinfo.properties"),"UTF-8");
		info.load(in);
		for (Entry<Object, Object> entry : info.entrySet()) {
			String[] s = ((String) entry.getValue()).split("&");
			Account a = new Account(s[0], s[1], s[2]);
			data.put((String) entry.getKey(), a);
		}
		in.close();
	}

//	����ʱд���ļ�����
	public void writeInfo() throws IOException {
		Properties newInfo = new Properties();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("./accountinfo.properties"),"UTF-8");
		for (Map.Entry<String, Account> entry : data.entrySet()) {
			newInfo.setProperty(entry.getKey(), entry.getValue().toString());
		}
		newInfo.store(out, null);
		out.close();
	}

//	���atm���͵���Ϣ������ز���
	public Operation OperationHandler(Operation o) {
		switch (o.getOperation()) {
		case "REG":
			account = o.getAccount();
			id = Math.abs(account.getUser().hashCode()) + "";
			if(data.containsKey(id)){
				operation.setClearATM(true);
				operation.setOperation("�˻��Ѵ���");
			}
			data.put(id + "", account);
			login = true;
			showOperation(o);
			operation.setOperation("�����ɹ�");
			return operation;

		case "LOGIN":
			account = o.getAccount();
			id = Math.abs(account.getUser().hashCode()) + "";
			if (data.containsKey(id)&&
					data.get(id).getPassword().equals(account.getPassword())){
				login = true;
				account = data.get(id);
				operation.setAccount(account);
				showOperation(o);
				operation.setOperation("��½�ɹ�");
			} else {
				account = null;
				operation.setClearATM(true);
				operation.setOperation("��½ʧ��");
			}
			return operation;


		case "LOGOUT":
			if (login) {
				login = false;
				showOperation(o);
				account = null;
				operation.setClearATM(true);
				operation.setOperation("�˻����˳�");
			} else{
				operation.setClearATM(true);
				operation.setOperation("δ��¼");
			}
			return operation;

			
		case "EXIT":
			 money = null;
			 id = null;
			 login = false;
			 operation.setClearATM(false);
			 operation.setOperation("���˳�");
			 return operation;
			

		case "INQUIRY":
			if (login) {
				operation.getAccount().setBalance(account.getBalance());
				showOperation(o);
				operation.setOperation("��ѯ�ɹ�");
			} else
				operation.setOperation("δ��¼");
			return operation;


		case "WITHDRAW":
			if (login) {
				double ballance = Double.parseDouble(account.getBalance());
				double money = Double.parseDouble(o.getMoney());
				if (money > ballance) {
					operation.setOperation("����");
				} else {
					account.setBalance(String.format("%.2f", (ballance - money)));
					operation.getAccount().setBalance(account.getBalance());
					showOperation(o);
					operation.setOperation("ȡǮ�ɹ�");
				}
			} else
				operation.setOperation("δ��¼");
			return operation;


		case "DEPOSIT":
			if (login) {
				double ballance = Double.parseDouble(account.getBalance());
				double money = Double.parseDouble(o.getMoney());
				account.setBalance(String.format("%.2f", (ballance + money)));
				operation.getAccount().setBalance(account.getBalance());
				showOperation(o);
				operation.setOperation("��Ǯ�ɹ�");

			} else {
				operation.setOperation("δ��¼");			
			}
			return operation;

		}
		return null;
	}

//	���ɺ�̨������¼
	public void showOperation(Operation o) {
		bankGui.getTextArea().append(o.toString()+":  "+account.toString()+"\n");
	}

//	�Զ���Ϣ����
	private class Rates implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					TimeUnit.SECONDS.sleep(20);
					if (data.size() != 0) {
						for (Map.Entry<String, Account> entry : data.entrySet()) {
							Account a = entry.getValue();
							if (a != null) {
								double d = (Double.parseDouble(a.getBalance())) * 1.03;
								a.setBalance(String.format("%.2f", d) + "");
							}
						}
					}
				}
			} catch (InterruptedException e) {
				System.out.println("rates interrupt");
			}
		}

	}

//	������
	public static void main(String[] args) {
		new Bank();
	}

}
