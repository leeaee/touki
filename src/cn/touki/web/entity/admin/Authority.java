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
@Table(name="cs_authority")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Authority extends Common implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String KEY = "entity.authority";
	
	/* -- Bean Properties -- */
	private String name;
	private String displayName;
	
	//Constructor
	public Authority() {
	}

	//Methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="display_name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
