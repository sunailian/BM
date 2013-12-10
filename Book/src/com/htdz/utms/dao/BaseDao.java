package com.htdz.utms.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

@Scope("prototype")
@Repository
public class BaseDao extends SqlMapClientDaoSupport {

    public Object insert(String statementName,Object parameterObject){
		return this.getSqlMapClientTemplate().insert(statementName, parameterObject);
	}
	
	public List queryForList(String statementName){
		return this.getSqlMapClientTemplate().queryForList(statementName);
	}
	
	public List queryForList(String statementName,Object parameterObject){
		return this.getSqlMapClientTemplate().queryForList(statementName, parameterObject);
	}
	
	public Object delete(String statementName,Object parameterObject){
		return this.getSqlMapClientTemplate().delete(statementName, parameterObject);
	}
	
	public Object queryForObject(String statementName){
		return this.getSqlMapClientTemplate().queryForObject(statementName);
	}
	
	public Object queryForObject(String statementName,Object parameterObject){
		return this.getSqlMapClientTemplate().queryForObject(statementName,parameterObject);
	}
	
	public Map queryForMap(String statementName,String keyProperty,String valueProperty){
		return this.getSqlMapClientTemplate().queryForMap(statementName,null,keyProperty,valueProperty);
	}
	
	public Map queryForMap(String statementName,Object parameterObject,String keyProperty,String valueProperty){
		return this.getSqlMapClientTemplate().queryForMap(statementName,parameterObject,keyProperty,valueProperty);
	}
	
	public int update(String statementName){
		return this.getSqlMapClientTemplate().update(statementName);
	}
	
	public int update(String statementName,Object parameterObject){
		return this.getSqlMapClientTemplate().update(statementName, parameterObject);
	}
	
	public void update(String statementName,Object parameterObject,int requiredRowsAffected){
		this.getSqlMapClientTemplate().update(statementName, parameterObject, requiredRowsAffected);
	}

    @Resource()
    @Qualifier(value = "sqlMapClient")
    public void setSqlMapClientForAutowire(SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }


}
