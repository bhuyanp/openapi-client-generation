package contracts.products

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description "should update product"
    request {
        method PATCH()
        headers {
            contentType(applicationJson())
        }
        url("/api/v1/products/id3") {
        }
        body(
                type: "ELECTRONICS",
        )
    }
    response {
        status HttpStatus.NO_CONTENT.value()
    }
}