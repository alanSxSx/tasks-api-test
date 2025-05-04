package testApi;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8080/tasks-backend";
	}
	
	
	@Test
	public void deveRetornarTarefascomSucesso() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarTarefascomSucesso() {
		RestAssured.given()
			.body("{\"task\":\"Teste via API\",\"dueDate\":\"2025-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveRetornarTarefaInvalida() {
		RestAssured.given()
			.body("{\"task\":\"Teste via API\",\"dueDate\":\"2020-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() {
	    // Cria uma nova tarefa
	    Integer id = RestAssured.given()
	        .body("{\"task\":\"Tarefa a ser removida\",\"dueDate\":\"2025-12-30\"}")
	        .contentType(ContentType.JSON)
	    .when()
	        .post("/todo")
	    .then()
	        .statusCode(201)
	        .extract()
	        .path("id"); // Extrai o ID da resposta

	    // Remove a tarefa criada
	    RestAssured.given()
	    .when()
	        .delete("/todo/" + id)
	    .then()
	        .statusCode(204); // Ou 200, conforme a API
	}
	
}
