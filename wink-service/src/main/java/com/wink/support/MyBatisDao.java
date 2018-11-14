package com.wink.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class MyBatisDao<T> {
    @Autowired
    protected SqlSession sqlSession;
    protected static final String CREATE = "create";
    protected static final String CREATES = "creates";
    protected static final String DELETE = "delete";
    protected static final String DELETES = "deletes";
    protected static final String UPDATE = "update";
    protected static final String LOAD = "load";
    protected static final String LOADS = "loads";
    protected static final String FIND_BY_ID = "findById";
    protected static final String FIND_BY_IDS = "findByIds";
    protected static final String LIST = "list";
    protected static final String COUNT = "count";
    protected static final String PAGING = "paging";
    public final String nameSpace;

    public MyBatisDao() {
        if (this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
            this.nameSpace = ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
        } else {
            this.nameSpace = ((Class) ((ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
        }

    }

    public Boolean create(T t) {
        return this.sqlSession.insert(this.sqlId("create"), t) == 1;
    }

    public Integer creates(List<T> ts) {
        return this.sqlSession.insert(this.sqlId("creates"), ts);
    }

    public Integer creates(T t0, T t1, T... tn) {
        return this.sqlSession.insert(this.sqlId("creates"), Arrays.asList(t0, t1, tn));
    }

    public Boolean delete(Long id) {
        return this.sqlSession.delete(this.sqlId("delete"), id) == 1;
    }

    public Integer deletes(List<Long> ids) {
        return this.sqlSession.delete(this.sqlId("deletes"), ids);
    }

    public Integer deletes(Long id0, Long id1, Long... idn) {
        return this.sqlSession.delete(this.sqlId("deletes"), Arrays.asList(id0, id1, idn));
    }

    public Boolean update(T t) {
        return this.sqlSession.update(this.sqlId("update"), t) == 1;
    }

    public T load(Integer id) {
        return this.load((long) id);
    }

    public T load(Long id) {
        return this.sqlSession.selectOne(this.sqlId("load"), id);
    }

    public List<T> loads(List<Long> ids) {
        return (ids == null || ids.isEmpty()) ? Collections.emptyList() : this.sqlSession.selectList(this.sqlId("loads"), ids);
    }

    public List<T> loads(Long id0, Long id1, Long... idn) {
        return this.sqlSession.selectList(this.sqlId("loads"), Arrays.asList(id0, id1, idn));
    }

    public T findById(Integer id) {
        return this.findById((long) id);
    }

    public T findById(Long id) {
        return this.sqlSession.selectOne(this.sqlId("findById"), id);
    }

    public T findOne(T t) {
        return this.sqlSession.selectOne(this.sqlId("find"), t);
    }

    public List<T> findByIds(List<Long> ids) {
        return (ids == null || ids.isEmpty()) ? Collections.emptyList() : this.sqlSession.selectList(this.sqlId("findByIds"), ids);
    }

    public List<T> findByIds(Long id0, Long id1, Long... idn) {
        return this.sqlSession.selectList(this.sqlId("findByIds"), Arrays.asList(id0, id1, idn));
    }

    public List<T> list(T t) {
        return this.sqlSession.selectList(this.sqlId("list"), t);
    }

    public List<T> list() {
        return this.sqlSession.selectList(this.sqlId("list"));
    }

    public List<T> list(Map<?, ?> criteria) {
        return this.sqlSession.selectList(this.sqlId("list"), criteria);
    }

    public Paging<T> paging(Integer pageNo, Integer pageSize) {
        return this.paging(pageNo, pageSize, (new HashMap()));
    }

    public Paging<T> paging(Integer pageNo, Integer pageSize, T criteria) {
        Map<String, Object> params = new HashMap<>();
        if (criteria != null) {
            final ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> objMap = objectMapper.convertValue(criteria, Map.class);
            params.putAll(objMap);
        }

        return this.paging(pageNo, pageSize, params);
    }

    public Paging<T> paging(Integer pageNo, Integer pageSize, Map<String, Object> criteria) {
        if (criteria == null) {
            criteria = new HashMap<>();
        }

        Long total = this.sqlSession.selectOne(this.sqlId("count"), criteria);
        if (total <= 0L) {
            return new Paging(0L, Collections.emptyList());
        } else {
            criteria.put("offset", (pageNo - 1) * pageSize);
            criteria.put("limit", pageSize);
            List<T> datas = this.sqlSession.selectList(this.sqlId("paging"), criteria);
            return new Paging(total, datas);
        }
    }

    protected String sqlId(String id) {
        return this.nameSpace + "." + id;
    }

    protected SqlSession getSqlSession() {
        return this.sqlSession;
    }
}
