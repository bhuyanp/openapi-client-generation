package contracts.products

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus

Contract.make {
    description "should return all products"
    request {
        method GET()
        headers {
        }
        url("/api/v1/products") {

        }
    }
    response {
        status HttpStatus.OK.value()
        headers {
            contentType(applicationJson())
        }
        body("""
        [{"id":"id1","title":"Product A","type":"ELECTRONICS","price":12.12,"createdDate":[2024,1,1,12,12,12]},{"id":"id2","title":"Product B","type":"ELECTRONICS","price":12.21,"createdDate":[2025,1,1,12,12,12]}]
""")
    }
}