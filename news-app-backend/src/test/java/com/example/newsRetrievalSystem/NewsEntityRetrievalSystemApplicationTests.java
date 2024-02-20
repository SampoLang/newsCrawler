package com.example.newsRetrievalSystem;


import com.example.newsRetrievalSystem.service.YleNewsCrawlerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class NewsEntityRetrievalSystemApplicationTests {
	@Autowired
	private ApplicationContext context;
	@Test
	void contextLoads() {
		assertThat(context).isNotNull();
	}
	@Test
	void yleNewsCrawlerImplBeanExists() {
		// Test to ensure the YleNewsCrawlerImpl bean is created
		YleNewsCrawlerImpl crawler = context.getBean(YleNewsCrawlerImpl.class);
		assertThat(crawler).isNotNull();
	}
}


