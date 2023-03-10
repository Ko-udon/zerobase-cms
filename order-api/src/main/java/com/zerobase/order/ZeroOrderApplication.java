package com.zerobase.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJdbcAuditing
@ServletComponentScan
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class ZeroOrderApplication {
  public static void main(String[] args) {
    SpringApplication.run(ZeroOrderApplication.class, args);
  }
}
