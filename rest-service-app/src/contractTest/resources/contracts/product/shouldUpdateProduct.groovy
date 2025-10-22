package contracts.products

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description "should update product"
    request {
        method PUT()
        headers {
            contentType(applicationJson())
        }
        url("/api/v1/products/id3") {
        }
        body(
                title: "Product AAA",
                type: "ELECTRONICS",
                price: 32.12,
        )
    }
    response {
        status HttpStatus.NO_CONTENT.value()
    }
}