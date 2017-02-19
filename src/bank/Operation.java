package bank;

import java.io.Serializable;

//定义了相关操作
public class Operation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private  String operation;
	private  Account account;
	private  String money;
	private boolean clearATM;
	

	public boolean isClearATM() {
		return clearATM;
	}

	public void setClearATM(boolean clearATM) {
		this.clearATM = clearATM;
	}

	public  String getOperation() {
		return operation;
	}

	public  void setOperation(String operation) {
		this.operation = operation;
	}

	public  String getMoney() {
		return money;
	}

	public  void setMoney(String money) {
		this.money = money;
	}

	public  Account getAccount() {
		return account;
	}

	public  void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return operation;
	}

}
