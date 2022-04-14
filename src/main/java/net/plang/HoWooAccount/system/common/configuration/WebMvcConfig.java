package net.plang.HoWooAccount.system.common.configuration;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.plang.HoWooAccount.system.common.interceptor.LoggerInterceptor;
import net.plang.HoWooAccount.system.common.interceptor.LoginInterceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //인터셉터 관련
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //로그인 인터셉터
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/*")
                .addPathPatterns("/*/*.html")
                .excludePathPatterns("/hr/empinsertForm.html")
                .excludePathPatterns("/loginForm.html")
                .excludePathPatterns("/*login*")
                .excludePathPatterns("/*logout*")
                .excludePathPatterns("/*/hello*");

        registry.addInterceptor(new LoggerInterceptor());   //로깅 인터셉터

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    //파일 인코딩 설정, 업로드 되는 파일 크기 제한
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSizePerFile(10 * 1024 * 1024);
        return commonsMultipartResolver;

    }

    // 2개의 빈은 인코딩 관련.
    @Bean
    public Filter characterEncodingFilter() {

        //CharacterEncodingFilter는 스프링이 제공하는 클래스로 웹에서 주고받는 데이터의 헤더값을 UTF-8로 인코딩 해줌.
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);  //기본값은 false로 설정되어 있음.

        return characterEncodingFilter;
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {

        //StringHttpMessageConverter는 @ResponseBody를 이용하여 결과를 출력할 때 결과를 UTF-8 로 설정함.
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }
}
