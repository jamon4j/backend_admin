package com.ksyun.vm.dao.rds;

import com.ksyun.vm.routedatasource.DataSourceInstances;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基础DAO类 ，（提供基本的方法，由于mybatis需要有配置文件mapper ID对应，所以在mapper文件中还是要有相应的insert
 * deleteById findById, findALL配置）
 * 
 * @author ZhangYanchun
 * @param <T>
 *            DTO对象
 * @param <PK>
 *            主键类型
 * @date 2013-08-08
 */
public abstract class BaseDao<T, PK> {

	@Resource
	private SqlSessionTemplate sqlSession_1;

	@Resource
	private SqlSessionTemplate sqlSession_2;

	// protected SqlSession sqlSession;

	protected SqlSessionTemplate sqlSession;

	protected String nameSpace;

	@Resource
	protected SqlSessionFactory sqlSessionFactory;

	@Resource
	protected SqlSessionFactory sqlSessionFactory_2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#getNameSpace()
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#setNameSpace(java.lang.String)
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	{
		// 由于泛型擦除规则，暂时只能以此方式获取，由于不能类型转换为Class<T>,只能取得simpleName。留待以后更好的解决办法（2013-08-15
		// ）
		String t_name = ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0].toString();
		nameSpace = String.format("com.ksyun.vm.pojo.rds.%s", t_name);
	}

	/**
	 * 设置数据源
	 * 
	 * @param ds
	 */
	public void setDataSource(String ds) {
		if (ds.equals(DataSourceInstances.DS1)) {
			// sqlSession = sqlSessionFactory.openSession();
			sqlSession = sqlSession_1;
		}

		else {
			// sqlSession = sqlSessionFactory_2.openSession();
			sqlSession = sqlSession_2;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#save(T)
	 */
	public void save(T t) {
		sqlSession.insert(String.format("%s.insert", nameSpace), t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#deleteById(PK)
	 */
	public void deleteById(PK pk) {
		sqlSession.delete(String.format("%s.deleteById", nameSpace), pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#findById(PK)
	 */
	public T findById(PK pk) {
		return (T) sqlSession.selectOne(
				String.format("%s.findById", nameSpace), pk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#findAll()
	 */
	public List<T> findAll() {
		return sqlSession.selectList(String.format("%s.findAll", nameSpace));

	}

	protected void update(String mapperId, T t) {
		sqlSession.update(mapperId, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#update(T)
	 */
	public int update(T t) {
		return sqlSession.update(String.format("%s.update", nameSpace), t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ksyun.vm.dao.IBaseDao#updateIfNecessary(T)
	 */
	public int updateIfNecessary(T t) {
		return sqlSession.update(
				String.format("%s.updateIfNecessary", nameSpace), t);
	}

}
