package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();
    private RouteImageDao routeImageDao=new RouteImageDaoImpl();
    private SellDao sellDao=new SellDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装pageBean
        PageBean<Route> pb = new PageBean<>();
        //当前页码
        pb.setCurrentPage(currentPage);
        //每页显示条数
        pb.setPageSize(pageSize);

        int totalCount=routeDao.findTotalCount(cid,rname);

        pb.setTotalCount(totalCount);

        int start=(currentPage-1)*pageSize;

        List<Route> list=routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        int totalPage= totalCount%pageSize==0 ? totalCount/pageSize:totalCount/pageSize+1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //1 route
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2 根据route id 查询图片集合信息
        List<RouteImg> list = routeImageDao.findByRid(Integer.parseInt(rid));
        route.setRouteImgList(list);
        Seller seller = sellDao.findById(route.getSid());
        route.setSeller(seller);
        return route;
    }
}
