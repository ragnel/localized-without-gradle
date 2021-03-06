package de.malkusch.localized.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.SharedSessionContract;

import de.malkusch.localized.LocalizedProperty;

/**
 * Abstract DAO.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 * @since 0.2.8
 */
abstract public class LocalizedDAO<T extends SharedSessionContract> {
	
	protected T session;

	public LocalizedDAO(T session) {
		this.session = session;
	}
	
	public LocalizedProperty find(Class<?> type, String field, Locale locale, Serializable id) {
		Query query = session
				.createQuery("FROM LocalizedProperty WHERE instance = :instance"
						+ " AND locale = :locale"
						+ " AND field = :field" + " AND type = :type");
		query.setParameter("instance", id.toString());
		query.setParameter("locale", locale);
		query.setParameter("field", field);
		query.setParameter("type", type);
		
		List<?> results = query.list();
		switch (results.size()) {

		case 0:
			return null;

		case 1:
			return (LocalizedProperty) results.get(0);

		default:
			throw new IllegalStateException(
					"This query should not return more than 1 result.");

		}
	}

}
