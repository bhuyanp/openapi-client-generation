package io.github.bhuyanp.restapp.product;

import io.cucumber.spring.ScenarioScope;
import io.github.bhuyanp.restapp.client.model.Product;
import io.github.bhuyanp.restapp.common.CommonContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ToString(callSuper = true)
@ScenarioScope
public class ProductContext extends CommonContext {
    private List<Product> products = new ArrayList<>();
}
