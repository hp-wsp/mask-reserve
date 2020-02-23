package com.ts.server.mask.service;

import com.ts.server.mask.BaseException;
import com.ts.server.mask.dao.AreaDao;
import com.ts.server.mask.domain.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域服务
 *
 * @author TS Group
 */
@Service
@Transactional(readOnly = true)
public class AreaService {
    private final AreaDao dao;

    @Autowired
    public AreaService(AreaDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Area save(Area t){
        int id = dao.insert(t);
        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Area update(Area t){
        if(!dao.update(t)){
            throw new BaseException("修改区域失败");
        }
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(int id){
        return dao.delete(id);
    }

    public Area get(int id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("区域不存在");
        }
    }

    public Long count(String name){
        return dao.count(name);
    }

    public List<Area> query(String name, int offset, int limit){
        return dao.find(name, offset, limit);
    }
}
