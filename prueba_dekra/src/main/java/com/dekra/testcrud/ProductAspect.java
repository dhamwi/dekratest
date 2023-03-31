package com.dekra.testcrud;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAspect {

	private static final String TEXT_EJECUTION = "El método '%s' se ha ejecutado en %d milisegundos.\n";

	@Around("execution(* com.dekra.testcrud.controller.*.*(..))")
	public Object runtimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

		Long timeStart = System.currentTimeMillis();
		Object joinObject = joinPoint.proceed();
		Long timeFinish = System.currentTimeMillis();
		System.out.format(TEXT_EJECUTION, 
				joinPoint.getSignature().getName(),
				timeFinish - timeStart);
		return joinObject;
	}
}
