package com.internetitem.config.server.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public class AbstractDao {

	@PersistenceContext
	protected EntityManager entityManager;

	protected <ReturnType, FieldType> List<ReturnType> query(Class<ReturnType> clazz, SingularAttribute<? super ReturnType, ? extends Object> attribute, FieldType value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ReturnType> c = cb.createQuery(clazz);
		Root<ReturnType> p = c.from(clazz);
		c.where(cb.equal(p.get(attribute), value));
		return entityManager.createQuery(c).getResultList();
	}

	protected <ReturnType, FieldType> ReturnType queryOne(Class<ReturnType> clazz, SingularAttribute<? super ReturnType, ? extends Object> attribute, FieldType value) {
		List<ReturnType> items = query(clazz, attribute, value);
		if (items.isEmpty()) {
			return null;
		}
		return items.iterator().next();
	}

	protected <ReturnType> ReturnType fetchZeroOrOne(TypedQuery<ReturnType> query) {
		List<ReturnType> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		}
		return results.iterator().next();
	}

}
