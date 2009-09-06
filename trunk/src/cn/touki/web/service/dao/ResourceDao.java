package cn.touki.web.service.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.touki.web.core.orm.hibernate.HibernateDao;
import cn.touki.web.entity.admin.Resource;

/**
 * 受保护资源对象的泛型DAO.
 * 
 * @author liyi
 */
@Repository
public class ResourceDao extends HibernateDao<Resource, Long> {

	public static final String QUERY_BY_RESOURCETYPE_WITH_AUTHORITY = "from Resource r left join fetch r.authorities WHERE r.resourceType=? ORDER BY r.position ASC";

	/**
	 * 查询URL类型的资源并预加载可访问该资源的授权信息.
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getUrlResourceWithAuthorities() {
		Query query = createQuery(QUERY_BY_RESOURCETYPE_WITH_AUTHORITY, Resource.URL_TYPE);
		return distinct(query).list();
	}
}
