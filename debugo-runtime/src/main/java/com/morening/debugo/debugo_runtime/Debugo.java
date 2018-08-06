package com.morening.debugo.debugo_runtime;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.morening.debugo.debugo_annotations.Sequence;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by morening on 2018/8/4.
 */

@Aspect
public class Debugo {

    private static volatile boolean enabled = true;

    public static void setEnabled(boolean enabled) {
        Debugo.enabled = enabled;
    }



    @Pointcut("execution(@com.morening.debugo.debugo_annotations.Sequence * *(..))")
    public void methodExecution4Sequence() {}

    @Before("methodExecution4Sequence()")
    public void beforeMethodExecution4Sequence(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getMethodAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        Log.d(TAG, String.format("Before Method Execution: %s", methodName));
    }

    @After("methodExecution4Sequence()")
    public void afterMethodExecution4Sequence(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getMethodAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        Log.d(TAG, String.format("After Method Execution: %s", methodName));
    }



    @Pointcut("execution(@com.morening.debugo.debugo_annotations.Sequence *.new(..))")
    public void constructorExecution4Sequence() {}

    @Before("constructorExecution4Sequence()")
    public void beforeConstructorExecution4Sequence(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getConstructorAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Constructor constructor = getConstructor(joinPoint);
        String constructorName = constructor.getName();
        Log.d(TAG, String.format("Before Constructor Execution: %s", constructorName));
    }

    @After("constructorExecution4Sequence()")
    public void afterConstructorExecution4Sequence(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getConstructorAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Constructor constructor = getConstructor(joinPoint);
        String constructorName = constructor.getName();
        Log.d(TAG, String.format("After Constructor Execution: %s", constructorName));
    }



    @AfterThrowing("methodExecution4Sequence() || constructorExecution4Sequence()")
    public void afterThrowingExecution4Sequence(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature){
            Sequence sequence = getMethodAnnotation(joinPoint, Sequence.class);
            String TAG = sequence.TAG();
            Method method = getMethod(joinPoint);
            String methodName = method.getName();
            Log.d(TAG, String.format("AfterThrowing Method Execution: %s", methodName));
        } else if (signature instanceof ConstructorSignature){
            Sequence sequence = getConstructorAnnotation(joinPoint, Sequence.class);
            String TAG = sequence.TAG();
            Constructor constructor = getConstructor(joinPoint);
            String constructorName = constructor.getName();
            Log.d(TAG, String.format("AfterThrowing Constructor Execution: %s", constructorName));
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    private <T extends Annotation> T getConstructorAnnotation(JoinPoint joinPoint, Class<T> clazz){
        Constructor constructor = getConstructor(joinPoint);
        T t = constructor.getDeclaredAnnotation(clazz);

        return t;
    }

    private Constructor getConstructor(JoinPoint joinPoint){
        ConstructorSignature constructorSignature = (ConstructorSignature)joinPoint.getSignature();
        Constructor constructor = constructorSignature.getConstructor();

        return constructor;
    }

    private <T extends Annotation> T getMethodAnnotation(JoinPoint joinPoint, Class<T> clazz){
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
