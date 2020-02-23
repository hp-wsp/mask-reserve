package com.ts.server.mask.controller.client;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.ts.server.mask.configure.MaskProperties;
import com.ts.server.mask.domain.Area;
import com.ts.server.mask.domain.Pharmacy;
import com.ts.server.mask.service.AreaService;
import com.ts.server.mask.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 缓存数据服务
 *
 * @author TS Group
 */
@Service
public class CacheDataService {
    private final AreaService areaService;
    private final PharmacyService pharmacyService;

    private final AtomicReference<List<Area>> areasRef = new AtomicReference<>();
    private final ConcurrentMap<String, List<SimplePharmacy>> phaMap = new ConcurrentHashMap<>();
    private final MaskProperties properties;

    @Autowired
    public CacheDataService(AreaService areaService, PharmacyService pharmacyService,
                            MaskProperties properties) {

        this.areaService = areaService;
        this.pharmacyService = pharmacyService;
        this.properties = properties;
    }

    @PostConstruct
    public void init(){
        List<Area> areas = areaService.query("", 0, 200);
        areasRef.set(areas);
        for(Area area: areas){
            List<Pharmacy> pharmacies = pharmacyService.queryOfArea(area.getName(), properties.getLimit());
            phaMap.put(area.getName(), buildSimplePharmacies(pharmacies));
        }
    }

    @Scheduled(fixedDelay = 15 * 60 * 1000L)
    public void updateAreas(){
        List<Area> areas = areaService.query("", 0, 200);
        areasRef.set(areas);
    }

    @Scheduled(fixedDelay = 3 * 60 * 1000L)
    public void updatePharmacy(){
        List<Area> areas = areasRef.get();
        for(Area area: areas){
            List<Pharmacy> pharmacies = pharmacyService.queryOfArea(area.getName(), properties.getLimit());
            phaMap.put(area.getName(), buildSimplePharmacies(pharmacies));
        }
    }

    private List<SimplePharmacy> buildSimplePharmacies(List<Pharmacy> pharmacies){

        return pharmacies.stream().map(e -> {
            SimplePharmacy t = new SimplePharmacy();
            t.id = e.getId();
            t.area = e.getArea();
            t.name = e.getName();
            t.address = e.getAddress();
            return t;
        }).collect(Collectors.toList());

    }

    public List<Area> getAreas(){
        return areasRef.get();
    }

    public List<SimplePharmacy> getPharmacies(String area){
        return phaMap.get(area);
    }

    public static class SimplePharmacy {
        public int id;
        public String area;
        public String name;
        public String address;
    }
}
