package contracts.products

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description "should delete product"
    request {
        method DELETE()
        headers {
        }
        url("/api/v1/products/id3") {
        }
    }
    response {
        status HttpStatus.NO_CONTENT.value()
    }
}