package contracts.products

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description "should add product"
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url("/api/v1/products") {
        }
        body(
                title: "Product AA",
                type: "ELECTRONICS",
                price: 22.12,
        )
    }
    response {
        status HttpStatus.CREATED.value()
        headers {
            contentType(applicationJson())
        }
        body(
            id: "id3",
            title: "Product AAA",
            type: "ELECTRONICS",
            price: 22.12,
            createdDate: [2025,1,1,12,12,12]
        )
    }
}