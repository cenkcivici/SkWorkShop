package com.sk.util.builder;

import org.hibernate.Session;

import com.sk.domain.BaseEntity;

public abstract class BaseBuilder<T extends BaseEntity, B extends BaseBuilder<T, B>> {

	private Long id;

	@SuppressWarnings("unchecked")
	public B id(Long id) {
		this.id = id;
		return (B) this;
	}

	@SuppressWarnings("unchecked")
	public T persist(Session session) {
		T toPersist = build();
		T persisted = (T) session.merge(toPersist);
		return persisted;
	}

	public T build() {
		T o = doBuild();
		o.setId(id);
		return o;
	}

	protected abstract T doBuild();

}
