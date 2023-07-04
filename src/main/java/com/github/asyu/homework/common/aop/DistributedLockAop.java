package com.github.asyu.homework.common.aop;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class DistributedLockAop {

  private static final String LOCK_PREFIX = "LOCK:";

  private final RedissonClient redissonClient;

  private final AopForTransaction aopForTransaction;

  @Around("@annotation(com.github.asyu.homework.common.aop.DistributedLock)")
  public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

    String key = LOCK_PREFIX + this.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
    RLock rLock = redissonClient.getLock(key);

    try {
      boolean available = rLock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
      if (!available) {
        return false;
      }

      return aopForTransaction.proceed(joinPoint);
    } catch (InterruptedException e) {
      throw new InterruptedException();
    } finally {
      try {
        rLock.unlock();
      } catch (IllegalMonitorStateException e) {
        log.error("Redisson Lock Already Unlock method : {}, key : {}", method.getName(), key);
      }
    }
  }

  private Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
    ExpressionParser parser = new SpelExpressionParser();
    StandardEvaluationContext context = new StandardEvaluationContext();

    for (int i = 0; i < parameterNames.length; i++) {
      context.setVariable(parameterNames[i], args[i]);
    }

    return parser.parseExpression(key).getValue(context, Object.class);
  }

}
