package cn.touki.web.entity.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.touki.web.entity.common.Common;

/**
 * @author Liyi
 *
 */
@Entity
@Table(name="cs_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends Common implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String KEY = "entity.role";
	
	/* -- Bean Properties -- */
	private String roleName;
	
	//Constructor
	public Role() {
	}
	
	//Methods
	@Column(name="role_name")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
