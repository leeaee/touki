package cn.touki.web.core.orm.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import cn.touki.web.core.orm.Page;
import cn.touki.web.core.orm.PropertyFilter;
import cn.touki.web.core.utils.ReflectionUtils;

/**
 * Hibernate针对Web应用的Utils函数集合.
 * 
 * @author liyi
 */
public class HibernateWebUtils {

	private HibernateWebUtils() {
	}

	/**
	 * 根据对象ID集合,整理合并集合.
	 * 
	 * 默认对象主键的名称名为"id".
	 * 
	 * @see #mergeByCheckedIds(Collection, Collection, Class, String)
	 */
	public static <T, ID> void mergeByCheckedIds(final Collection<T> srcObjects, final Collection<ID> checkedIds,
			final Class<T> clazz) {
		mergeByCheckedIds(srcObjects, checkedIds, clazz, "id");
	}

	/**
	 * 根据对象ID集合,整理合并集合.
	 * 
	 * 页面发送变更后的子对象id列表时,删除原来的子对象集合再根据页面id列表创建一个全新的集合这种看似最简单的做法是不行的.
	 * 因此需采用如此的整合算法：在源集合中删除id不在ID集合中的对象,根据ID集合中的id创建对象并添加到源集合中.
	 * 
	 * @param srcObjects 源对象集合
	 * @param checkedIds  目标ID集合
	 * @param clazz  集合中对象的类型
	 * @param idName 对象主键的名称
	 */
	public static <T, ID> void mergeByCheckedIds(final Collection<T> srcObjects, final Collection<ID> checkedIds,
			final Class<T> clazz, final String idName) {

		//参数校验
		Assert.notNull(srcObjects, "scrObjects不能为空");
		Assert.hasText(idName, "idName不能为空");
		Assert.notNull(clazz, "clazz不能为空");

		//目标ID集合为空,删除源集合中所有对象后直接返回.
		if (checkedIds == null) {
			srcObjects.clear();
			return;
		}

		//遍历源集合,如果其id不在目标ID集合中的对象,进行删除.
		//同时,在目标ID集合中删除已在源集合中的id,使得目标ID集合中剩下的id均为源集合中没有的ID.
		Iterator<T> srcIterator = srcObjects.iterator();
		try {

			while (srcIterator.hasNext()) {
				T element = srcIterator.next();
				Object id;
				id = PropertyUtils.getProperty(element, idName);

				if (!checkedIds.contains(id)) {
					srcIterator.remove();
				} else {
					checkedIds.remove(id);
				}
			}

			//ID集合目前剩余的id均不在源集合中,创建对象,为id属性赋值并添加到源集合中.
			for (ID id : checkedIds) {
				T obj = clazz.newInstance();
				PropertyUtils.setProperty(obj, idName, id);
				srcObjects.add(obj);
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertToUncheckedException(e);
		}
	}

	/**
	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
	 * 默认Filter属性名前缀为filter_.
	 * 
	 * @see #buildPropertyFilters(HttpServletRequest, String)
	 */
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request) {
		return buildPropertyFilters(request, "filter_");
	}

	/**
	 * 根据按PropertyFilter命名规则的Request参数,创建PropertyFilter列表.
	 * PropertyFilter命名规则为Filter属性前缀_比较类型_属性名.
	 * 
	 * eg.
	 * filter_EQUAL_name
	 * filter_LIKE_name_OR_email
	 */
	@SuppressWarnings("unchecked")
	public static List<PropertyFilter> buildPropertyFilters(final HttpServletRequest request, final String filterPrefix) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();

		//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, String> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix);

		//分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue();
			//如果value值为空,则忽略此filter.
			boolean omit = StringUtils.isBlank(value);
			if (!omit) {
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}
		return filterList;
	}
	
	/**
	 * 通过 page.orderBy, page.order, page.pageIndex 获取page属性参数
	 * 
	 * @param request
	 * @param filterPrefix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> Page<T> buildPageParameters(final HttpServletRequest request, final int pageSize) {
		
		Map<String, String> pageParamMap = WebUtils.getParametersStartingWith(request, "page.");
		
		Page<T> page = new Page<T>(pageSize);
		
		if (pageParamMap.get("pageIndex") != null) {
			page.setPageIndex(Integer.valueOf(pageParamMap.get("pageIndex")));
		} else {
			page.setPageIndex(1);
		}
		
		if (pageParamMap.get("sortStyle") != null && !pageParamMap.get("sortStyle").equals("")) {
			String[] sortStyle = pageParamMap.get("sortStyle").split(",");
			page.setOrderBy(sortStyle[0]);
			page.setOrder(sortStyle[1]);
		}
		
		return page;
	}		
	
}
