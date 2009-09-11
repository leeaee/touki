package cn.touki.web.service.dao;

import org.springframework.stereotype.Repository;

import cn.touki.web.core.orm.hibernate.HibernateDao;
import cn.touki.web.entity.csadmin.Authority;

/**
 * 授权对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class AuthorityDao extends HibernateDao<Authority, Long> {
}
