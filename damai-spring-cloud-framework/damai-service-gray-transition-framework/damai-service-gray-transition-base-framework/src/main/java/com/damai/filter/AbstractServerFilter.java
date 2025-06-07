package com.damai.filter;


import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.Ordered;

import java.util.Iterator;
import java.util.List;


public abstract class AbstractServerFilter implements Ordered {
    
    public void filter(List<? extends ServiceInstance> servers) {
        Iterator<? extends ServiceInstance> iterator = servers.iterator();
        while (iterator.hasNext()) {
            ServiceInstance server = iterator.next();
            boolean enabled = doFilter(servers, server);
            if (!enabled) {
                iterator.remove();
            }
        }
    }

    /**
     * 执行真正地过滤行为
     * @param servers 被调用的所有服务列表
     * @param server 当前被调用的服务
     * @return 过滤的结果
     * */
    public abstract boolean doFilter(List<? extends ServiceInstance> servers, ServiceInstance server);
}