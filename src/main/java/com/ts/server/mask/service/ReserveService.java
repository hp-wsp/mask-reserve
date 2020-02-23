package com.ts.server.mask.service;

import com.ts.server.mask.BaseException;
import com.ts.server.mask.configure.MaskProperties;
import com.ts.server.mask.dao.ReserveDao;
import com.ts.server.mask.domain.Pharmacy;
import com.ts.server.mask.domain.Reserve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 预约服务
 *
 * @author TS Group
 */
@Service
@Transactional(readOnly = true)
public class ReserveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReserveService.class);

    private static final int DAY_MILLS = 24 * 3600 * 1000;

    private final ReserveDao dao;
    private final TraIdService traIdService;
    private final PharmacyService pharmacyService;
    private final HisReserveService hisReserveService;
    private final MaskProperties properties;

    @Autowired
    public ReserveService(ReserveDao dao, TraIdService traIdService,
                          PharmacyService pharmacyService, HisReserveService hisReserveService,
                          MaskProperties properties) {
        this.dao = dao;
        this.traIdService = traIdService;
        this.pharmacyService = pharmacyService;
        this.hisReserveService = hisReserveService;
        this.properties = properties;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Reserve reserve(Reserve t){
        long startTime = System.currentTimeMillis();
        int lastDay = hisReserveService.getLastDay(t.getIdCard());
        int nowDay = (int)(startTime / DAY_MILLS);
        if(lastDay + properties.getIntDay() > nowDay){
            throw new BaseException(405, "已经预约");
        }

        pharmacyService.incStack(t.getPhaId(), properties.getLimit() * -1);

        Pharmacy pharmacy = pharmacyService.get(t.getPhaId());
        t.setPharmacy(pharmacy.getName());
        t.setPhaAddress(pharmacy.getAddress());
        String traId = genTraId(t.getPhaId());
        t.setTarId(traId);
        t.setCount(properties.getLimit());
        t.setCycle(nowDay);
        t.setCreateTime(new Date());

        dao.insert(t);

        hisReserveService.save(t.getIdCard());

        long useTime = System.currentTimeMillis() - startTime;

        LOGGER.debug("Reserve user mills={}...", useTime);

        return t;
    }

    private String genTraId(int phaId){
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int count = traIdService.incTraIdIndex(phaId);
        return String.format("%02d%02d%05d", month, day, count);
    }

    public Optional<Reserve> get(String idCard){
        return dao.findLast(idCard);
    }

    public Long count(String name, String pharmacy, Date fromDate, Date toDate){
        return dao.count(name, pharmacy, fromDate, toDate);
    }

    public List<Reserve> query(String name, String pharmacy, Date fromDate, Date toDate, int offset, int limit){
        return dao.find(name, pharmacy, fromDate, toDate, offset, limit);
    }
}
