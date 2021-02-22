/*
 * Copyright (c) 2020. BEST S.A. and/or its affiliates. All rights reserved.
 */
package com.example.springdata.jpa.advanced;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

public class ExtendedJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements ExtendedJpaRepository<T,ID> {

	private final JpaEntityInformation<T, ?> entityInformation;
	private final EntityManager entityManager;

	public ExtendedJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}

	@Override
	public List<T> seekByIds(ID... idList) {
		TypedQuery<T> query = entityManager.createQuery("SELECT t FROM " + entityInformation.getEntityName() + " t " +
				"WHERE t.id IN (:idki)", entityInformation.getJavaType());
		query.setParameter("idki", Arrays.asList(idList));
		return query.getResultList();
	}
}
