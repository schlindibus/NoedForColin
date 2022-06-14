package ch.bbw.pr.sospri.member;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * To regist a new Member
 * @author peter.rutschmann
 * @version 27.04.2020
 */
public class RegisterMember {
	@NotEmpty
	@Size(min = 3, max = 20, message = "Firstname must be minimum 3 and maximum 20 letters.")
	private String prename;
	@NotEmpty
	@Size(min = 3, max = 20, message = "Lastname must be minimum 3 and maximum 20 letters.")
	private String lastname;
	@NotEmpty
	@Pattern( regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$" , message = "Password needs to include a small, a uppercase letter, a Number and a special char.")
	private String password;
	private String confirmation;
	private String message;
	
	public String getPrename() {
		return prename;
	}
	public void setPrename(String prename) {
		this.prename = prename;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "RegisterMember [prename=" + prename + ", lastname=" + lastname + ", password=" + password
				+ ", confirmation=" + confirmation + "]";
	}
}
