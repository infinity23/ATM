package bank;

import java.io.Serializable;

//’Àªß–≈œ¢
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	private String balance;

	public Account(String user, String password, String balance) {
		this.user = user;
		this.password = password;
		this.balance = balance;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return user + "&" + password + "&" + balance;
	}

}
