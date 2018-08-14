package com.airblock;

import com.airblock.dao.CityMapper;
import com.airblock.model.City;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.airblock.dao")
public class BaseSpringbootMybatisApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(BaseSpringbootMybatisApplication.class, args);
		CityMapper cityMapper = context.getBean(CityMapper.class);
		City city = cityMapper.selectByPrimaryKey(1);
		System.out.println(city.getCountrycode());
	}
}
