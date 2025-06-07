package com.damai.balance;

import org.springframework.cache.CacheManager;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ExtCachingServiceInstanceListSupplier extends CachingServiceInstanceListSupplier {
    
    private final FilterLoadBalance filterLoadBalance;
    
    public ExtCachingServiceInstanceListSupplier(ServiceInstanceListSupplier delegate, 
                                                 CacheManager cacheManager,
                                                 FilterLoadBalance filterLoadBalance) {
        super(delegate, cacheManager);
        this.filterLoadBalance = filterLoadBalance;
    }
    
    @Override
    public Flux<List<ServiceInstance>> get() {
        Flux<List<ServiceInstance>> listFlux = super.get();
        listFlux = listFlux.map(serviceInstances -> {
            List<ServiceInstance> allServers = new ArrayList<>();
            Optional.ofNullable(serviceInstances).ifPresent(allServers::addAll);
            filterLoadBalance.selectServer(allServers);
            return allServers;
        });
        return listFlux;
    }
}
