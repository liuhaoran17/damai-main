package com.damai.initialize.impl.composite;

import com.damai.initialize.impl.composite.init.CompositeInit;
import org.springframework.context.annotation.Bean;


public class CompositeAutoConfiguration {
    
    @Bean
    public CompositeContainer compositeContainer(){
        return new CompositeContainer();
    }
    
    @Bean
    public CompositeInit compositeInit(CompositeContainer compositeContainer){
        return new CompositeInit(compositeContainer);
    }
}
