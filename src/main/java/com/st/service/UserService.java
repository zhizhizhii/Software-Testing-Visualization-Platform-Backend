package com.st.service;

import com.st.dao.UserDAO;
import com.st.model.User;
import com.st.utils.HashHelper;
import com.st.utils.JWTUtils;
import com.st.view.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public Status register(String username,String password){
        Status status = new Status();
        User user = userDAO.findUserByUsername(username);
        if(user!=null){
            status.setState(false);
            status.setMsg("用户名重复，注册失败！");
            return status;
        }
        User newUser = new User();
        newUser.setUsername(username);

        double seed = ThreadLocalRandom.current().nextDouble();
        String salt = HashHelper.computeSha256Hash(username + seed);
        newUser.setSalt(salt);
        newUser.setPassword(HashHelper.computeSha256Hash(password + salt));
        userDAO.save(newUser);
        status.setState(true);
        status.setMsg("注册成功");
        return status;
    }

    public Status login(String username, String password, HttpServletResponse response){
        Status status = new Status();
        try{
            User user = userDAO.findUserByUsername(username);
            if(user == null) throw new Exception();

            String passwordHashed= HashHelper.computeSha256Hash(password + user.getSalt());
            if(!user.getPassword().equals(passwordHashed)) throw new Exception();

            //此时验证通过，签发Token
            Map<String,String> payload = new HashMap<String,String>();
            payload.put("user_id",user.getUserId().toString());
            String token = JWTUtils.getToken(payload);
            response.setHeader("token",token);
            status.setState(true);
            status.setMsg("登录成功");
            return status;
        }catch (Exception e){
            e.printStackTrace();
            status.setState(false);
            status.setMsg("账号/密码错误，登录失败");
            return status;
        }
    }

    public Status changePassword(String oldPassword, String newPassword, HttpServletRequest request){

        Status status = new Status();

        try{
            Integer userId = Integer.valueOf(JWTUtils.getTokenInfo(request.getHeader("token")).getClaim("user_id").asString());
            User user = userDAO.findUserByUserId(userId);
            if(user==null) throw new Exception();
            String passwordHashed = HashHelper.computeSha256Hash(oldPassword + user.getSalt());
            if(!user.getPassword().equals(passwordHashed)) throw new Exception();
            double seed = ThreadLocalRandom.current().nextDouble();
            String newSalt = HashHelper.computeSha256Hash(user.getUsername() + seed);
            user.setSalt(newSalt);
            user.setPassword(HashHelper.computeSha256Hash(newPassword + newSalt));
            userDAO.save(user);
            status.setState(true);
            status.setMsg("修改密码成功！");
            return status;
        }catch (Exception e){
            e.printStackTrace();
            status.setState(false);
            status.setMsg("用户不存在或密码错误，登录失败！");
            return status;
        }
    }
}
