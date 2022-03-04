package com.diegotobalina.framework.core.multitenant;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Aspect
@Component
public class TenantFilterAdvisor {

  @PersistenceContext private EntityManager entityManager;

  public TenantFilterAdvisor() {}

  @Pointcut(value = "@annotation(com.diegotobalina.framework.core.multitenant.MultiTenant)")
  public void methodAnnotatedWithMultiTenant() {}

  @Pointcut(value = "execution(public * * (..))")
  public void allPublicMethods() {}

  @Around(value = "methodAnnotatedWithMultiTenant() && allPublicMethods()")
  public Object enableTenantFilter(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    if (entityManager != null) {
      Session session = entityManager.unwrap(Session.class);
      Filter filter = session.enableFilter("tenantFilter");
      filter.setParameter("tenantId", TenantContext.getCurrentTenant());
    }
    return proceedingJoinPoint.proceed();
  }
}
