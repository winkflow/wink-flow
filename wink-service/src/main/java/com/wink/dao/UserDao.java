package com.wink.dao;

import com.wink.model.User;
import com.wink.support.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends MyBatisDao<User> {

    public User find(User find) {
        return this.sqlSession.selectOne(this.sqlId("find"), find);
    }
}
