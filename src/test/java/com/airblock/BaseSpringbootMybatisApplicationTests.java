package com.airblock;

import com.airblock.dao.CityMapper;
import com.airblock.model.City;
import com.airblock.model.CityExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseSpringbootMybatisApplicationTests {
	@Autowired
	private CityMapper cityMapper;

	@Test
	public void contextLoads() {
		List<City> list = cityMapper.selectByExample(new CityExample());
		System.out.println(list.size());
	}

}
