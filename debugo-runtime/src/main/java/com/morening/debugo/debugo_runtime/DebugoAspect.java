package com.morening.debugo.debugo_runtime;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import com.morening.debugo.debugo_annotations.Debugo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by morening on 2018/8/4.
 */

@Aspect
public class DebugoAspect {

    private static volatile boolean enabled = true;

    public static void setEnabled(boolean enabled) {
        DebugoAspect.enabled = enabled;
    }

    @Pointcut("execution(@com.morening.debugo.debugo_annotations.Debugo * *(..))")
    public void method() {}

    @Pointcut("execution(@com.morening.debugo.debugo_annotations.Debugo *.new(..))")
    public void constructor() {}

    @Around("method() || constructor()")
    public void executeAndLog(ProceedingJoinPoint joinPoint){
        long start = enter(joinPoint);
        try {
            Object result = joinPoint.proceed();
            exit(joinPoint, result, start);
        } catch (Throwable throwable) {
            exitThrowable(joinPoint, throwable);
        }
    }

    private static long enter(ProceedingJoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        sb.append("->").append(" ");
        sb.append(getMethodNameWithParameters(joinPoint));

        if (Looper.myLooper() != Looper.getMainLooper()){
            sb.append(" ").append("Thread:").append(Thread.currentThread().getName());
        }

        Log.d(getTag(joinPoint), sb.toString());

        return System.currentTimeMillis();
    }

    private static String getTag(JoinPoint joinPoint){
        Debugo debugo = getAnnotation(joinPoint, Debugo.class);
        String Tag = "Debugo";
        if (debugo != null){
            Tag = debugo.TAG();
        }

        return Tag;
    }

    private static String getMethodNameWithParameters(JoinPoint joinPoint){
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        String name = signature.getName();
        String[] parameterNames = signature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("(");
        for (int k=0; k<parameterNames.length; k++){
            if (k > 0){
                sb.append(", ");
            }
            sb.append(String.format("%s=%s", parameterNames[k], Strings.toString(parameterValues[k])));
        }
        sb.append(")");

        return sb.toString();
    }

    private static void exit(ProceedingJoinPoint joinPoint, Object result, long start) {
        long duration = System.currentTimeMillis() - start;
        StringBuilder sb = new StringBuilder();
        sb.append(getMethodNameWithParameters(joinPoint))
                .append(" ").append("[").append(duration).append("ms").append("]")
                .append(" ").append("->");
        boolean hasReturnValue = joinPoint.getSignature() instanceof MethodSignature
                && ((MethodSignature)joinPoint.getSignature()).getReturnType() != void.class;
        if (hasReturnValue){
            sb.append(" ").append(Strings.toString(result));
        }

        Log.d(getTag(joinPoint), sb.toString());
    }

    private static void exitThrowable(ProceedingJoinPoint joinPoint, Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(getMethodNameWithParameters(joinPoint))
                .append(" ").append("terminated due to")
                .append(" ").append(throwable.getCause())
                .append(" ").append(throwable.getMessage());

        Log.d(getTag(joinPoint), sb.toString());
    }

    private static <T extends Annotation> T getAnnotation(JoinPoint joinPoint, Class<T> clazz){
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature){
            return getMethodAnnotation(joinPoint, clazz);
        } else if (signature instanceof ConstructorSignature){
            return getConstructorAnnotation(joinPoint, clazz);
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static <T extends Annotation> T getConstructorAnnotation(JoinPoint joinPoint, Class<T> clazz){
        Constructor constructor = getConstructor(joinPoint);
        T t = constructor.getDeclaredAnnotation(clazz);

        return t;
    }

    private static Constructor getConstructor(JoinPoint joinPoint){
        ConstructorSignature constructorSignature = (ConstructorSignature)joinPoint.getSignature();
        Constructor constructor = constructorSignature.getConstructor();

        return constructor;
    }

    private static <T extends Annotation> T getMethodAnnotation(JoinPoint joinPoint, Class<T> clazz){
        Method method = getMethod(joinPoint);
        T t = method.getAnnotation(clazz);

        return t;
    }

    private static Method getMethod(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        return method;
    }
}
