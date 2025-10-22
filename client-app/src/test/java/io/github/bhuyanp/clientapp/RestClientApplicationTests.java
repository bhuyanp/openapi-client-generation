package io.github.bhuyanp.clientapp;

import io.github.bhuyanp.clientapp.util.TokenUtil;
import io.github.bhuyanp.restapp.client.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "io.github.bhuyanp:rest-service-app:+:stubs:8009")
class RestClientApplicationTests {




	@Autowired
	private io.github.bhuyanp.restapp.client.api.ProductsApi productsApi;

	@MockitoBean
	private TokenUtil tokenUtil;


	@Test
	void testSomething(){
		when(tokenUtil.getToken()).thenReturn("dummy token");
		ResponseEntity<List<Product>> products = productsApi.getProducts();
		System.out.println(products.getBody());
	}

}
