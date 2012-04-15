package com.sk.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.AccessType;
import org.hibernate.proxy.HibernateProxyHelper;

@MappedSuperclass
@AccessType("field")
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
	@TableGenerator(name = "tableGenerator", allocationSize = 1, table = "hibernate_sequences")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPersisted() {
		return getId() != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (!HibernateProxyHelper.getClassWithoutInitializingProxy(obj).equals(
				HibernateProxyHelper.getClassWithoutInitializingProxy(this))){
			return false;
		}

		BaseEntity other = (BaseEntity) obj;

		if (getId() == null) {
			return super.equals(obj);
		}

		if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + "_" + getId();
	}
}
