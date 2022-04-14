package net.plang.HoWooAccount.system.common.advice;


import org.aopalliance.intercept.MethodInterceptor;		
import org.aopalliance.intercept.MethodInvocation;		
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAdvice implements MethodInterceptor{

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		String className = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		if (logger.isDebugEnabled()) {
			logger.debug(className + "  :  " + methodName + "() 시작");
			Object[] args = invocation.getArguments();
			if ((args != null) && (args.length > 0)) {
				for (int i = 0; i < args.length; i++) {
					logger.debug("매개값: [" + i + "]: " + args[i]);
				}
			}
		}
		
		Object returnValue = invocation.proceed();

		if (logger.isDebugEnabled()) {
			logger.debug(className + "  : " + methodName + "() 종료");
		}
		return returnValue;
	}
}

