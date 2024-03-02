//package base.api.system.cache;
//
//import org.infinispan.configuration.cache.CacheMode;
//import org.infinispan.configuration.cache.ConfigurationBuilder;
//import org.infinispan.configuration.cache.StorageType;
//import org.infinispan.eviction.EvictionStrategy;
//import org.infinispan.spring.starter.embedded.InfinispanGlobalConfigurationCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class CacheConfig {
//
//  @Bean
//  public InfinispanGlobalConfigurationCustomizer globalCustomizer() {
//    return builder -> builder
//      .transport()
//      .defaultTransport()
//      .build();
//  }
//
//  @Bean(name = "checkinTokenCache")
//  public org.infinispan.configuration.cache.Configuration checkinTokenCache() {
//    return new ConfigurationBuilder()
//      .memory()
//      .storage(StorageType.OFF_HEAP)
//      .maxSize("1MB")
//      .whenFull(EvictionStrategy.REMOVE)
//      .clustering()
//      .cacheMode(CacheMode.REPL_SYNC)
//      .expiration()
//      .lifespan(10L, TimeUnit.SECONDS)
//      .build();
//  }
//
//  @Bean(name = "testCache")
//  public org.infinispan.configuration.cache.Configuration testCache() {
//    return new ConfigurationBuilder()
//      .memory()
//        .storage(StorageType.OFF_HEAP)
//        .maxSize("1MB")
//        .whenFull(EvictionStrategy.REMOVE)
//      .clustering()
//        .cacheMode(CacheMode.REPL_SYNC)
//      .expiration()
//        .lifespan(10L, TimeUnit.SECONDS)
//      .build();
//  }
//}
