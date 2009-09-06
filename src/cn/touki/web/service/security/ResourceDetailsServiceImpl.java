package cn.touki.web.service.security;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.touki.web.core.security.ResourceDetailsService;
import cn.touki.web.entity.admin.Resource;

/**
 * 从数据库查询URL--授权定义Map的实现类.
 * 
 * @author liyi
 */
@Transactional(readOnly = true)
public class ResourceDetailsServiceImpl implements ResourceDetailsService {
	@Autowired
	private SecurityManager securityManager;

	/**
	 * @see ResourceDetailsService#getRequestMap()
	 */
	public LinkedHashMap<String, String> getRequestMap() throws Exception {
		List<Resource> resourceList = securityManager.getUrlResourceWithAuthorities();

		LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>(resourceList.size());
		for (Resource resource : resourceList) {
			requestMap.put(resource.getValue(), resource.getAuthNames());
		}
		return requestMap;
	}
}
