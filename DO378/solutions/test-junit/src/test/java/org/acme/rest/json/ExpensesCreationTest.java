package org.acme.rest.json;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.acme.rest.json.Expense.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class ExpensesCreationTest {

    @Inject
    EntityManager entityManager;

    @BeforeEach
    @Transactional
    public void cleanDatabase(){
        entityManager.createQuery("delete from Expense").executeUpdate();
    }

    @Test
    public void testCreateExpense() {
        given()
            .body(Expense.of("Test Expense", PaymentMethod.CASH, "1234")).contentType(ContentType.JSON).post("/expenses");
        when()
            .get("/expenses")
        .then()
            .statusCode(200)
            .assertThat().body("size()", is(1))
            .body(
                containsString("\"name\":\"Test Expense\""),
                containsString("\"paymentMethod\":\"" + PaymentMethod.CASH + "\""),
                containsString("\"amount\":1234.0")
            );
    }
    
}