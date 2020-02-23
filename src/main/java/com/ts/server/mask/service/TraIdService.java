package com.ts.server.mask.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.ts.server.mask.configure.MaskProperties;
import com.ts.server.mask.dao.PharmacyDao;
import com.ts.server.mask.dao.TraIdDao;
import com.ts.server.mask.domain.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 交易编号生产服务
 *
 * @author TS Group
 */
@Service
@Transactional(readOnly = true)
public class TraIdService {
    private final TraIdDao dao;
    private final PharmacyDao pharmacyDao;
    private final MaskProperties properties;

    private final ConcurrentMap<Integer, AtomicInteger> seqs = new ConcurrentHashMap<>();

    @Autowired
    public TraIdService(TraIdDao dao, PharmacyDao pharmacyDao, MaskProperties properties) {
        this.dao = dao;
        this.pharmacyDao = pharmacyDao;
        this.properties = properties;
    }

    @PostConstruct
    public void init(){
        List<Pharmacy> all = pharmacyDao.find("", "", 0, 5000);
        for (Pharmacy pharmacy: all){
            int count = getCount(pharmacy.getId(), getDay());
            seqs.put(pharmacy.getId(), new AtomicInteger(count));
        }
    }

    private int getDay(){
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        return month * 1000 + day * 10 + properties.getTraIdOffset();
    }

    private int getCount(int phaId, int day){
        try{
            return dao.findCount(phaId, day);
        }catch (DataAccessException e){
            save(phaId, day);
            //none instance
        }
        return 0;
    }

    private void save(int phaId, int day){
        try{
            dao.insert(phaId, day);
        }catch (DataAccessException e){
            //none instance
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int incTraIdIndex(int phaId){
        int index = seqs.get(phaId).addAndGet(properties.getTraIdSkip());
        dao.update(phaId, getDay(), index);
        return index;
    }

    @Scheduled(cron = "5 0 0 ? * *")
    @Transactional(propagation = Propagation.REQUIRED)
    public void reset(){
        List<Pharmacy> all = pharmacyDao.find("", "", 0, 5000);
        seqs.clear();
        for (Pharmacy pharmacy: all){
            dao.insert(pharmacy.getId(), getDay());
            seqs.put(pharmacy.getId(), new AtomicInteger(0));
        }
    }
}
