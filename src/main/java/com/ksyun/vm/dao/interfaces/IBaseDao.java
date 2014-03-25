package com.ksyun.vm.dao.interfaces;

import java.util.List;

public interface IBaseDao<T, PK> {

	public abstract String getNameSpace();

	public abstract void setNameSpace(String nameSpace);

	public abstract void save(T t);

	public abstract void deleteById(PK pk);

	public abstract T findById(PK pk);

	public abstract List<T> findAll();

	public abstract int update(T t);

	public abstract int updateIfNecessary(T t);

}