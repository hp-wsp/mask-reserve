package com.ts.server.mask.service;

import com.ts.server.mask.BaseException;
import com.ts.server.mask.dao.PharmacyDao;
import com.ts.server.mask.domain.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 药店业务服务
 *
 * @author TS Group
 */
@Service
@Transactional(readOnly = true)
public class PharmacyService {
    private final PharmacyDao dao;

    @Autowired
    public PharmacyService(PharmacyDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Pharmacy save(Pharmacy t){
        int id = dao.insert(t);
        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Pharmacy update(Pharmacy t){
        if(!dao.update(t)){
            throw new BaseException("修改药房信息失败");
        }
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(int id){
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void incStack(int id, int count){
        for(int i = 0; i < 3; i++){
            Pharmacy t = get(id);
            int remain = t.getStack() + count;
            if(remain < 0){
                throw new BaseException(501, "没有库存");
            }
            if(dao.incStack(id, count, t.getVersion())){
                return;
            }
        }
        throw new BaseException("修改库存失败");
    }

    public Pharmacy get(int id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("药房不存在");
        }
    }

    public List<Pharmacy> queryOfArea(String area, int min){
        return dao.findOfArea(area, min);
    }

    public Long count(String area, String name){
        return dao.count(area, name);
    }

    public List<Pharmacy> query(String area, String name, int offset, int limit){
        return dao.find(area, name, offset, limit);
    }
}
