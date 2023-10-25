package com.HarshTyagi.PurchiApp;

import com.HarshTyagi.PurchiApp.filter.PurchiFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class PurchiAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchiAppApplication.class, args);
	}
    @Bean
    public FilterRegistrationBean filterUrl(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new PurchiFilter());

        filterRegistrationBean.addUrlPatterns("/Purchi/totalWeight","/Purchi/addPurchi","/Purchi/totalPurchi",
				"/Purchi/totaltroliholderWeight/*","/Purchi/deletePurchi/*","/Purchi/totalAmountBetweenTwoDates/*");
        return filterRegistrationBean;
    }
	@Bean
	public FilterRegistrationBean filterRegistrationBean(){

		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("http://localhost:3000");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",corsConfiguration);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return bean;
	}

}
