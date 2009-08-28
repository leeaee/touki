package cn.touki.web.entity.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.touki.web.entity.common.Common;
import cn.touki.web.entity.common.Stateful;

@Entity
@Table(name="admin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Admin extends Common implements Stateful, Serializable {
	
	private static final long serialVersionUID = -3312361139361161036L;
	public static final String KEY = "entity.admin";
	
	/* -- Bean Properties -- */
	private String adminId;
	private String password;
	private String trueName;
	private String phone;
	private String mobile;
	private String email;
	private Integer state;
	private Long lastLogin;
	
	//Constructor
	public Admin() {
	}

	public Admin(String adminId) {
    	this.adminId = adminId;
    }
	
	//Methods
	@Column(name="admin_id")
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="true_name")
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="last_login")
	public Long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
