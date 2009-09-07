package cn.touki.web.entity.admin;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import cn.touki.web.core.utils.ReflectionUtils;
import cn.touki.web.entity.common.Identity;
import cn.touki.web.entity.common.Stateful;

@Entity
@Table(name="cs_admin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Admin extends Identity implements Stateful, Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String KEY = "entity.admin";
	
	/* -- Bean Properties -- */
	private String adminId;
	private String password;
	private String trueName;
	private String phone;
	private String mobile;
	private String email;
	private Integer state;
	private String description;
	private Long lastLogin;
	private Long lastModify;
	private Long createTime;
	
	private Set<Role> roles = new LinkedHashSet<Role>(); //有序的关联对象集合.	
	
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	@Column(name="last_modify")
	public Long getLastModify() {
		return lastModify;
	}

	public void setLastModify(Long lastModify) {
		this.lastModify = lastModify;
	}

	@Column(name="create_time")
	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}	
	
	//多对多定义
	@ManyToMany
	//中间表定义,表名采用默认命名规则
	@JoinTable(name = "cs_admin_x_role", joinColumns = { @JoinColumn(name = "admin_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("id")
	//集合中对象id的缓存.
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	//非持久化属性.
	@Transient
	public String getRoleNames() {
		return ReflectionUtils.fetchElementPropertyToString(roles, "roleName", ", ");
	}

	//非持久化属性.
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getRoleIds() {
		return ReflectionUtils.fetchElementPropertyToList(roles, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	

}
