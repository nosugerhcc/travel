package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao= new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
//        //1 从redis查询
//        //2 判断对否为空
//        //3 空 从数据库查询 存入
//        //4 不为空 直接返回
//        Jedis jedis= JedisUtil.getJedis();
//        Set<String> categorys = jedis.zrange("category", 0, -1);
//        List<Category> all=null;
//        if (categorys==null || categorys.size()==0){
//            all = categoryDao.findAll();
//            for (int i = 0; i < all.size() ; i++) {
//                jedis.zadd("category",all.get(i).getCid(),all.get(i).getCname());
//            }
//        }else {
//            all=new ArrayList<Category>();
//            for (String name:categorys){
//                Category category1 = new Category();
//                category1.setCname(name);
//                all.add(category1);
//            }
//        }
//
//        return all;
        return categoryDao.findAll();
    }
}
