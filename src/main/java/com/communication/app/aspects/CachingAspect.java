package com.communication.app.aspects;

import com.communication.app.entities.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.communication.app.services.EmployeeService.REDIS_KEY;

@Aspect
@Component
public class CachingAspect {
    @Autowired
    private  RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisTemplate<String , Employee> redisEmployeeTemplate;



    @Pointcut("execution(* com.communication.app.services.EmployeeService.addEmployee(..)) || " +
            "execution(* com.communication.app.services.EmployeeService.deleteEmployee(..)) || " +
            "execution(* com.communication.app.services.EmployeeService.updateEmployee(..))")
    public void employeeOperation() {}

    @After("employeeOperation()")
    public void clearCache() {
        Set<String> keys = redisTemplate.keys(REDIS_KEY + "*");
        redisTemplate.delete(keys);
    }

}
