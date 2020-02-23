package com.ts.server.mask.service;

import com.ts.server.mask.dao.HisReserveDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预约历史
 *
 * @author TS Group
 */
@Service
@Transactional(readOnly = true)
public class HisReserveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HisReserveService.class);
    private static final int DAY_MILLS = 24 * 3600 * 1000;

    private final HisReserveDao dao;

    @Autowired
    public HisReserveService(HisReserveDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String idCard){
        int day = (int)(System.currentTimeMillis() / DAY_MILLS);
        try{
            if(dao.has(idCard)){
                dao.update(idCard, day);
            }else{
                dao.insert(idCard, day);
            }
        }catch (Exception e){
            LOGGER.warn("Save his reserve fail idCard={}, throw={}", idCard, e.getMessage());
        }
    }

    public int getLastDay(String idCard){
        try{
            return dao.getDay(idCard);
        }catch (Exception e){
            LOGGER.warn("Get last day fail idCard={}, throw={}", idCard, e.getMessage());
            return 0;
        }
    }
}
