package online.fortis;

import helpers.CustomAllureListener;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class BookstoreTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://demoqa.com";
        //RestAssured.filters(new AllureRestAssured());
        RestAssured.filters(withCustomTemplates());
    }

    @Test
    @DisplayName("Получение списка книг")
    void getBooksTest() {
        given()
                //.filter(new AllureRestAssured())
                .log().uri()
                .log().body()
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("books.title[2]", is("Designing Evolvable Web APIs with ASP.NET"))
                .body("books.author[2]", is("Glenn Block et al."));
    }

    @Test
    @DisplayName("Получение книги по ISBN")
    void getBookWithISBNTest() {
        given()
                //.filter(new AllureRestAssured())
                .log().uri()
                .log().body()
                .params("ISBN", "9781449337711")
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("title", is("Designing Evolvable Web APIs with ASP.NET"))
                .body("author", is("Glenn Block et al."));
    }

    @Test
    @DisplayName("Авторизация пользователя")
    void authorizeTest() {
        String data = "{ \"userName\": \"alex\", \"password\": \"asdsad#frew_DFS2\" }";

        given()
                //.filter(new AllureRestAssured())
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("/Account/v1/Authorized")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body(is("true"));
    }
}
