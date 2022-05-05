//package com.epam.elastic.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableLoadTimeWeaving;
//import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
//import org.springframework.instrument.classloading.LoadTimeWeaver;
//import org.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;
//
//@Configuration
//@EnableLoadTimeWeaving
//public class AppConfig implements LoadTimeWeavingConfigurer {
//
//    @Override
//    public LoadTimeWeaver getLoadTimeWeaver() {
//        return new TomcatLoadTimeWeaver();
//    }
//}