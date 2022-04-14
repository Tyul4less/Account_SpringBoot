package net.plang.HoWooAccount.system.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.jfree.util.Log;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Log.debug("=========          START         ==============");
            Log.debug(" Request URI \t:  " + request.getRequestURI());

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        if (Log.isDebugEnabled()) {
            Log.debug("==========      END  Controller처리됨         =============\n");
        }
    }

}
