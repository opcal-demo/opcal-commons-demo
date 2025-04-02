package xyz.opcal.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("xyz.opcal.demo.mapper")
public class MybatisPlusBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusBootApplication.class, args);
	}

}
