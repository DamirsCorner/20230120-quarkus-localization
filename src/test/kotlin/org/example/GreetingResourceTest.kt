package org.example

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceTest {

    @Test
    fun `returns English response when no Accept-Language header`() {
        given()
          .`when`().get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello, world!"))
    }

    @Test
    fun `returns English response when English is first language in Accept-Language header`() {
        given()
          .`when`()
            .header("Accept-Language", "en")
            .get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello, world!"))
    }

    @Test
    fun `returns Slovenian response when Slovenian is first language in Accept-Language header`() {
        given()
          .`when`()
            .header("Accept-Language", "sl-SI")
            .get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Pozdravljen, svet!"))
    }

    @Test
    fun `returns English response when first language in Accept-Language header is not supported`() {
        given()
          .`when`()
            .header("Accept-Language", "de")
            .get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello, world!"))
    }

    @Test
    fun `returns first supported language in Accept-Language header`() {
        given()
          .`when`()
            .header("Accept-Language", "fr-CH, fr;q=0.9, de;q=0.8, sl;q=0.7, *;q=0.5")
            .get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Pozdravljen, svet!"))
    }

    @Test
    fun `returns English if no supported language in Accept-Language header`() {
        given()
          .`when`()
            .header("Accept-Language", "fr-CH, fr;q=0.9, de;q=0.8, *;q=0.5")
            .get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello, world!"))
    }

}