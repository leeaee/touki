package cn.touki.web.entity.csadmin;

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

/**
 * @author Liyi
 *
 */
@Entity
@Table(name="cs_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends Identity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String KEY = "entity.role";
	
	/* -- Bean Properties -- */
	private String name;
	private String description;
	private Long lastModify;
	private Long createTime;
	
	private Set<Authority> authorities = new LinkedHashSet<Authority>();
	
	//Constructor
	public Role() {
	}
	
	//Methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@ManyToMany
	@JoinTable(name = "cs_role_x_authority", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Transient
	public String getAuthNames() {
		return ReflectionUtils.fetchElementPropertyToString(authorities, "displayName", "<br />");
	}

	/**
	 * 角色拥有的授权id.
	 */
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getAuthIds() {
		return ReflectionUtils.fetchElementPropertyToList(authorities, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
