package bank;

import java.io.Serializable;

//定义ATM相关操作
public class ATM implements Serializable{
	private static final long serialVersionUID = 1L;
	Operation operation;
	ATMGui atmGui;
	Thread threadOfATM;

	public ATM() {
		operation = new Operation();
		atmGui = new ATMGui(this);
		threadOfATM = new Thread(atmGui);
		threadOfATM.start();
	}

	public Operation reg() {
		String user = atmGui.getUser().getText();
		char[] password = atmGui.getPassword().getPassword();
		operation.setAccount(new Account(user, new String(password), "0"));
		operation.setOperation("REG");

		return operation;
	}

	public Operation login() {
		String user = atmGui.getUser().getText();
		char[] password = atmGui.getPassword().getPassword();
		operation.setAccount(new Account(user, new String(password), ""));
		operation.setOperation("LOGIN");
		return operation;
	}
	

	public Operation exit() {
		operation.setOperation("EXIT");

		return operation;
		
	}
	public Operation logout() {
		operation.setOperation("LOGOUT");

		return operation;
	}

	public Operation inquiry() {
		operation.setOperation("INQUIRY");

		return operation;
	}

	public Operation withdraw() {
		operation.setMoney(atmGui.getMoney().getText());
		operation.setOperation("WITHDRAW");

		return operation;
	}

	public Operation deposit() {
		operation.setMoney(atmGui.getMoney().getText());
		operation.setOperation("DEPOSIT");

		return operation;
	}

//	向ATM界面发送提示信息
	public void showMessage(String s) {
		atmGui.getMessage().setText(s);
	}

//	向bank服务器发送信息
	public void sendMessage(Operation o) {
		new Thread(new ATMClint(this, o)).start();
	}
	
	void clearATM(){
		atmGui.getUser().setText("");
		atmGui.getPassword().setText("");
		atmGui.getBalance().setText("");
		atmGui.getMoney().setText("");
	}


	public static void main(String[] args){
		new ATM();
	}

}
