package com.ksyun.vm.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.ksyun.vm.dao.interfaces.IBaseDao;
import com.ksyun.vm.utils.datasource.CustomerContextHolder;

/**
 * 基础DAO类 ，（提供基本的方法，由于mybatis需要有配置文件mapper ID对应，所以在mapper文件中还是要有相应的insert deleteById findById, findALL配置）
 * @author ZhangYanchun
 * @param <T>  DTO对象
 * @param <PK> 主键类型
 * @date 2013-08-08
 */
public abstract class BaseDao<T, PK> implements IBaseDao<T, PK> {
	
	@Resource
	protected SqlSession sqlSession;
	
	protected String nameSpace;

    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#getNameSpace()
	 */
    @Override
	public String getNameSpace() {
        return nameSpace;
    }

    /* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#setNameSpace(java.lang.String)
	 */
    @Override
	public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    {
        //由于泛型擦除规则，暂时只能以此方式获取，由于不能类型转换为Class<T>,只能取得simpleName。留待以后更好的解决办法（2013-08-15 ）
    	String t_name = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0].toString();
        nameSpace = String.format("com.ksyun.vm.pojo.chart.%s", t_name);
    }
	
	/* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#save(T)
	 */
	@Override
	public void save(T t) {
		sqlSession.insert(String.format("%s.insert", nameSpace), t);
	}

	/* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#deleteById(PK)
	 */
	@Override
	public void deleteById(PK pk) {
		sqlSession.delete(String.format("%s.deleteById", nameSpace), pk);
	}

	/* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#findById(PK)
	 */
	@Override
	public T findById(PK pk) {
		return (T)sqlSession.selectOne(String.format("%s.findById", nameSpace), pk);
	}

	/* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#findAll()
	 */
	@Override
	public List<T> findAll() {
		return sqlSession.selectList(String.format("%s.findAll", nameSpace));
		
	}
	
	protected void update(String mapperId, T t) {
		sqlSession.update(mapperId, t);
	}
	
	/* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#update(T)
	 */
	@Override
	public int update(T t){
		return sqlSession.update(String.format("%s.update", nameSpace), t);
	}
	
	/* (non-Javadoc)
	 * @see com.ksyun.vm.dao.IBaseDao#updateIfNecessary(T)
	 */
	@Override
	public int updateIfNecessary(T t){
		return sqlSession.update(String.format("%s.updateIfNecessary", nameSpace), t);
	} 

}
