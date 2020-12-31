package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao dao=new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        User byUsername = dao.findByUsername(user.getUsername());
        if (byUsername==null){
            dao.save(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        return dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
