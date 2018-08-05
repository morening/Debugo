package com.morening.debugo.debugo_runtime;

import android.util.Log;

import com.morening.debugo.debugo_annotations.Sequence;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by morening on 2018/8/4.
 */

@Aspect
public class Debugo {

    private static volatile boolean enabled = true;

    @Pointcut("execution(@com.morening.debugo.debugo_annotations.Sequence * *(..))")
    public void method() {}

    @Pointcut("execution(@com.morening.debugo.debugo_annotations.Sequence *.new(..))")
    public void constructor() {}

    public static void setEnabled(boolean enabled) {
        Debugo.enabled = enabled;
    }

    @Before("method() || constructor()")
    public void beforeExecution(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        Log.d(TAG, String.format("Before Execution: %s", methodName));
    }

    @After("method() || constructor()")
    public void afterExecution(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        Log.d(TAG, String.format("After Execution: %s", methodName));
    }

    private <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clazz){
        Method method = getMethod(joinPoint);
        T t = method.getAnnotation(clazz);

        return t;
    }

    private Method getMethod(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        return method;
    }
}
