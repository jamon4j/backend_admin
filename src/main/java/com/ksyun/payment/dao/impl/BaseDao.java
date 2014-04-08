package com.ksyun.payment.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ksyun.payment.common.SysContext;
import com.ksyun.payment.dao.IBaseDao;
/**
 * 基础DAO类 ，（提供基本的方法，由于mybatis需要有配置文件mapper ID对应，所以在mapper文件中还是要有相应的insert deleteById findById, findALL配置）
 * @author ZhangYanchun
 * @param <T>  DTO对象
 * @param <PK> 主键类型
 * @date 2013-08-08
 */
public abstract class BaseDao<T, PK> implements IBaseDao<T, PK>{
	
	@Resource
	protected SqlSession sqlSession;
	
	protected String nameSpace;
	
    
    {
        //由于泛型擦除规则，暂时只能以此方式获取，由于不能类型转换为Class<T>,只能取得simpleName。留待以后更好的解决办法（2013-08-15 ）
    	String t_name = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0].toString();   
        nameSpace = String.format("com.ksyun.payment.dto.%s", t_name);
    }
	
	@Override
	public void save(T t) {
		sqlSession.insert(String.format("%s.insert", nameSpace), t);
	}

	@Override
	public void deleteById(PK pk) {
		sqlSession.delete(String.format("%s.deleteById", nameSpace), pk);
	}

	@Override
	public T findById(PK pk) {
		return sqlSession.selectOne(String.format("%s.findById", nameSpace), pk);
	}

	@Override
	public List<T> findAll(Map<String, Object> params) {
		return sqlSession.selectList(String.format("%s.findAll", nameSpace), params);
		
	}
	@Override
	public void updateAll(T t){
		sqlSession.update(String.format("%s.updateAll", nameSpace), t);
	}
	
	/**
	 * 执行按照对象的更新操作
	 * @param mapperId
	 * @param t
	 */
	protected void update(String mapperId, T t) {
		sqlSession.update(String.format("%s.%s", nameSpace, mapperId), t);
	}

	/**
	 * 查询记录，并分页
	 * @param mapperId     mapper文件对应的id
	 * @param params       查询参数
	 * @param sortString   排序方式 reg_time desc,user_id asc
	 * @return
	 */
	protected final List<T> find(String mapperId, Map<String, Object> params, String sortString) {
		int page = SysContext.getPage(); //页号
		int pageSize = SysContext.getPageSize(); //每页数据条数
		SysContext.removePage();
		SysContext.removePageSize();
		PageBounds pageBounds = new PageBounds(page, pageSize , Order.formString(sortString));
	    return sqlSession.selectList(String.format("%s.%s", nameSpace, mapperId), params, pageBounds);
	}
	
}
