package com.zumin.dc.common.web.log;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理日志切面类
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

  /**
   * 基础日志模板
   */
  private static final String BASIC_LOG_TEMPLATE = "[{}]";

  /**
   * 时间日志模板
   */
  private static final String TIME_LOG_TEMPLATE = "[共计用时: {} ms]";

  /**
   * 配置织入点 处理所有带有Log注解的方法
   */
  @Pointcut("@annotation(Log)")
  public void logPointCut() {
  }

  /**
   * 环绕处理方法
   *
   * @param point 连接点
   * @return 方法执行后的返回值
   * @throws Throwable 异常
   */
  @Around("logPointCut()")
  public Object around(ProceedingJoinPoint point) throws Throwable {
    long startTime = System.currentTimeMillis();
    try {
      Object result = point.proceed();
      handleSuccessLog(point, startTime);
      return result;
    } catch (Throwable throwable) {
      handleErrorLog(point, startTime, throwable);
      throw throwable;
    }
  }

  /**
   * 处理执行成功的日志
   *
   * @param joinPoint 连接点
   * @param startTime 开始时间
   */
  private void handleSuccessLog(JoinPoint joinPoint, long startTime) {
    Log log = getAnnotationLog(joinPoint);
    if (log == null) {
      return;
    }
    long spendTime = computeSpendTime(startTime);
    String logContent = buildSuccessLogContent(log, joinPoint.getArgs(), spendTime);
    recordSuccessLog(logContent, spendTime);
  }

  /**
   * 处理执行失败的日志
   *
   * @param joinPoint 连接点
   * @param startTime 开始时间
   * @param throwable 异常
   */
  private void handleErrorLog(JoinPoint joinPoint, long startTime, Throwable throwable) {
    Log log = getAnnotationLog(joinPoint);
    if (log == null) {
      return;
    }
    long spendTime = computeSpendTime(startTime);
    String logContent = buildErrorLogContent(log, joinPoint.getArgs(), spendTime, throwable);
    recordErrorLog(logContent);
  }

  /**
   * 记录执行成功的日志 根据方法的执行时间，设置日志的级别
   *
   * @param logContent 日志内容
   * @param spendTime  方法执行时间
   */
  private void recordSuccessLog(String logContent, long spendTime) {
    if (spendTime < 1000) {
      log.info(logContent);
      return;
    }
    if (spendTime < 5000) {
      log.warn(logContent);
      return;
    }
    recordErrorLog(logContent);
  }

  /**
   * 记录执行失败的日志
   *
   * @param logContent 日志内容
   */
  private void recordErrorLog(String logContent) {
    log.error(logContent);
  }

  /**
   * 构建执行成功的日志内容
   *
   * @param log         日志对象
   * @param paramsArray 参数数组
   * @param spendTime   方法执行时间
   * @return 日志内容
   */
  private String buildSuccessLogContent(Log log, Object[] paramsArray, long spendTime) {
    StringBuilder result = new StringBuilder();
    result.append(toLogString(log.value()));
    result.append(toLogString(log.businessType()));
    result.append(StrUtil.format(TIME_LOG_TEMPLATE, spendTime));
    if (log.isSaveParameterData()) {
      result.append(StrUtil.sub(paramDataToLogString(paramsArray), 0, 1000));
    }
    return result.toString();
  }

  /**
   * 构建执行失败的日志内容
   *
   * @param log         日志对象
   * @param paramsArray 参数数组
   * @param spendTime   方法执行时间
   * @param throwable   异常
   * @return 日志内容
   */
  private String buildErrorLogContent(Log log, Object[] paramsArray, long spendTime, Throwable throwable) {
    return buildSuccessLogContent(log, paramsArray, spendTime) + Arrays.toString(throwable.getStackTrace());
  }

  /**
   * 计算方法执行时间
   *
   * @param startTime 开始时间
   * @return 方法执行时间
   */
  private long computeSpendTime(long startTime) {
    return System.currentTimeMillis() - startTime;
  }

  /**
   * 拼接参数数据
   */
  private String paramDataToLogString(Object[] paramsData) {
    return ArrayUtil.isNotEmpty(paramsData) ?
        Arrays.stream(paramsData).filter(this::notFilterObject).map(this::toLogString).collect(Collectors.joining()) : "";
  }

  /**
   * 利用基础日志模板进行格式化字符串
   *
   * @param data 数据
   * @return 格式化后的字符串
   */
  private String toLogString(Object data) {
    return StrUtil.format(BASIC_LOG_TEMPLATE, data);
  }

  /**
   * 判断是否不是过滤该对象
   *
   * @param o 对象
   * @return 需要过滤返回false，否则返回true
   */
  private boolean notFilterObject(Object o) {
    return !(o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse);
  }

  /**
   * 获取日志注解 如果存在就获取
   */
  private Log getAnnotationLog(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    return method != null ? method.getAnnotation(Log.class) : null;
  }
}
