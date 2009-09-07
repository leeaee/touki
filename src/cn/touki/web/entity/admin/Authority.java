package cn.touki.web.entity.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.touki.web.entity.common.Identity;

/**
 * @author Liyi
 *
 */
@Entity
@Table(name="cs_authority")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Authority extends Identity implements Serializable {
	
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
