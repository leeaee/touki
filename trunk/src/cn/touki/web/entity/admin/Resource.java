package cn.touki.web.entity.admin;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import cn.touki.web.core.utils.ReflectionUtils;
import cn.touki.web.entity.common.Common;

/**
 * @author Liyi
 *
 */
@Entity
@Table(name="cs_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends Common implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String KEY = "entity.resource";
	
	//resourceType常量
	public static final String URL_TYPE = "url";
	public static final String MENU_TYPE = "menu";
	
	/* -- Bean Properties -- */
	private String resourceType;
	private String value;
	private double position;
	private Set<Authority> authorities = new LinkedHashSet<Authority>();
	
	//Constructor
	public Resource() {
	}

	//Methods
	@Column(name="resource_type")
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}
	
	/**
	 * 可访问该资源的授权集合.
	 */
	@ManyToMany
	@JoinTable(name = "cs_resource_x_authority", joinColumns = { @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
	@Fetch(FetchMode.JOIN)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * 可访问该资源的授权名称字符串, 多个授权用','分隔.
	 */
	@Transient
	public String getAuthNames() {
		return ReflectionUtils.fetchElementPropertyToString(authorities, "name", ",");
	}	

}
