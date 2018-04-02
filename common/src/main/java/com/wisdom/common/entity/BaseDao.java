package com.wisdom.common.entity;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * <b>dto基类：</b>
 * 
 * @author zhaodeliang
 * @since 2017-4-02
 */
public interface BaseDao<T,E,K> extends Serializable{
	
    int countByCriteria(E criteria);

    int deleteByCriteria(E criteria);

    int deleteByPrimaryKey(K key);

    int insert(T entity);

    int insertSelective(T entity);

    List<T> selectByCriteria(E criteria);

    T selectByPrimaryKey(K key);

    int updateByCriteriaSelective(@Param("record") T entity, @Param("example") E criteria);

    //int updateByCriteria(@Param("record") T entity, @Param("example") E criteria);

    int updateByPrimaryKeySelective(T entity);

    //int updateByPrimaryKey(T entity);
}