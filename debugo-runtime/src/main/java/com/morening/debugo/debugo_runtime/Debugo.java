package com.morening.debugo.debugo_runtime;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.morening.debugo.debugo_annotations.Sequence;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
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
    public void methodSequence() {}

    @Before("methodSequence()")
    public void beforeMethodSequence(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getMethodAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        Log.d(TAG, String.format("Before Execute Method [%s]", methodName));
    }

    @AfterReturning(pointcut = "methodSequence()", returning = "ret")
    public void afterReturningMethodSequence(JoinPoint joinPoint, Object ret) throws Throwable {
        Sequence sequence = getMethodAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        if (ret == null){
            Log.d(TAG, String.format("After Method [%s] Executed", methodName));
        } else {
            Log.d(TAG, String.format("After Method [%s] Executed With Return Value [%s]", methodName, ret.toString()));
        }
    }

    @AfterThrowing(pointcut = "methodSequence()", throwing = "thw")
    public void afterThrowingMethodSequence(JoinPoint joinPoint, Object thw){
        Sequence sequence = getMethodAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        int line = joinPoint.getSourceLocation().getLine();
        Log.d(TAG, String.format("Method [%s] Terminated By Exception [%s] at line [%s]", methodName, thw, line));
    }



    @Pointcut("execution(@com.morening.debugo.debugo_annotations.Sequence *.new(..))")
    public void constructorSequence() {}

    @Before("constructorSequence()")
    public void beforeConstructorSequence(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getConstructorAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Constructor constructor = getConstructor(joinPoint);
        String constructorName = constructor.getName();
        Log.d(TAG, String.format("Before Construct Constructor [%s]", constructorName));
    }

    @After("constructorSequence()")
    public void afterConstructorSequence(JoinPoint joinPoint) throws Throwable {
        Sequence sequence = getConstructorAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Constructor constructor = getConstructor(joinPoint);
        String constructorName = constructor.getName();
        Log.d(TAG, String.format("After Constructor [%s] Constructed", constructorName));
    }

    @AfterThrowing(pointcut = "constructorSequence()", throwing = "thw")
    public void afterThrowingConstructorSequence(JoinPoint joinPoint, Object thw){
        Sequence sequence = getMethodAnnotation(joinPoint, Sequence.class);
        String TAG = sequence.TAG();
        Method method = getMethod(joinPoint);
        String methodName = method.getName();
        int line = joinPoint.getSourceLocation().getLine();
        Log.d(TAG, String.format("Constructor [%s] Terminated By Exception [%s] at line [%s]", methodName, thw, line));
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
