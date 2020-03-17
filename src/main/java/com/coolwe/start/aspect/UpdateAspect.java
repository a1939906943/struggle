package com.coolwe.start.aspect;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.coolwe.start.annotation.*;
import com.coolwe.start.util.JDBCUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UpdateAspect {

    @Pointcut("@annotation(com.coolwe.start.annotation.Update)")
    private void pointcut() {
    }

    @Around("pointcut() && @annotation(req)")
    private Boolean advice(ProceedingJoinPoint j, Update req) throws Exception {
        String sql = req.value();
        if (j.getArgs().length != 0) {
            HashMap<String, Object> hm = JSON.parseObject(j.getArgs()[0].toString(), HashMap.class);
            for (String key : hm.keySet()) {
                sql = sql.replace("#{" + key + "}", (String) hm.get(key));
            }
        }
        return JDBCUtil.exeUpdate(sql);
    }
}