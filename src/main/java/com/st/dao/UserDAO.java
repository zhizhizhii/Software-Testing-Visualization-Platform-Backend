package com.st.dao;

import com.st.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO  extends CrudRepository<User,Integer> {
    User findUserByUsername(String username);
    User findUserByUserId(Integer userId);
}
