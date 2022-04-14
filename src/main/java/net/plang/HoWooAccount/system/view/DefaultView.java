package net.plang.HoWooAccount.system.view;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class DefaultView extends WebMvcConfigurerAdapter{

	    @Override
	    public void addViewControllers(ViewControllerRegistry registry ) {
	        registry.addViewController( "/" ).setViewName( "redirect:/loginForm.jsp" );
	        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
	        super.addViewControllers( registry );
	    }
}
