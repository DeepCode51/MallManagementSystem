package com.huang.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2 //开启Swagger2
public class SwaggerConfig {

    @Bean
    public Docket docket1() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }


    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }


    @Bean
    public Docket docket3() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }


    //配置Swagger的Docket的Bean实例
    @Bean
    public Docket docket(Environment environment) {

        //设置要显示的Swagger环境,
        Profiles profiles = Profiles.of("dev", "test");

        //通过environment.acceptsProfiles判断是不是处在设定的环境中，如果存在下列环境，则返回true
        boolean flag = environment.acceptsProfiles(profiles);


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("huang")
                .enable(false) //是否开启swagger，如果为false则无法在浏览器上访问
                .select()
                //RequestHandlerSelectors:配置要扫描接口的方式
                //basePackage:指定要扫描的包
                //any()：扫描全部
                //none()：不扫描
                //withMethodAnnotation：扫描方法上的注解
                //withClassAnnotation：扫描类上的注解
                .apis(RequestHandlerSelectors.basePackage("com.huang.swagger.controller"))
                //paths()：过滤器，过滤什么路径
                //.paths(PathSelectors.ant("/kuang/**"))
                .build();
    }


    //配置Swagger信息就是apiInfo
    private ApiInfo apiInfo() {

        //作者信息
        Contact contact = new Contact("你的名字", "https://blog.kuangstudy.com/", "huanglei19990829@163.com");

        return new ApiInfo(
                "你的名字的SwaggerAPI文档",
                "活着",
                "v1.0",
                "https://blog.kuangstudy.com/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }


}
