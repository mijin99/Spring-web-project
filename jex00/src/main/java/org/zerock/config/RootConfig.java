package org.zerock.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //자바로 설정하려면 xml 대신 어노테이션을 이용한 config 파일을 작성해줘야한다.
@ComponentScan(basePackages  = {"org.zerock.smaple"})
public class RootConfig {

}
