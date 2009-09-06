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
@Table(name="cs_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends Common implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String KEY = "entity.resource";
	
	/* -- Bean Properties -- */
	private String resourceType;
	private String value;
	private Double position;
	
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

	public Double getPosition() {
		return position;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

}
