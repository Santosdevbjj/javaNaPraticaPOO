package br.com.santosdev.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BDD - Steps: Implementação em Java da lógica de teste (Given, When, Then).
 * Utiliza RestAssured para fazer chamadas HTTP.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
public class ContaBancariaSteps {

    private Response response;
    private String contaId;
    private static final String BASE_URL = "http://localhost:8080";
    private static final String AUTH_USER = "user";
    private static final String AUTH_PASS = "password";

    // --- GIVEN ---
    @Given("que a aplicação está rodando \\(Docker-Compose)")
    public void que_a_aplicacao_esta_rodando() {
        // Garante que o microsserviço está no ar antes de começar.
        RestAssured.given()
                .get(BASE_URL + "/actuator/health")
                .then()
                .statusCode(200);
    }

    // --- WHEN ---
    @When("eu envio uma requisição POST autenticada para {string} com o corpo:")
    public void eu_envio_uma_requisicao_POST_autenticada_com_o_corpo(String path, DataTable dados) {
        Map<String, String> body = dados.asMaps().get(0);
        
        response = RestAssured.given()
                .auth().basic(AUTH_USER, AUTH_PASS) // Inclui a autenticação
                .contentType("application/json")
                .body(body)
                .when()
                .post(BASE_URL + path);
        
        // Se for sucesso, salva o ID para uso posterior
        if (response.statusCode() == 201) {
            contaId = response.jsonPath().getString("id");
        }
    }

    @When("eu envio uma requisição GET autenticada para {string}")
    public void eu_envio_uma_requisicao_GET_autenticada(String path) {
        response = RestAssured.given()
                .auth().basic(AUTH_USER, AUTH_PASS)
                .when()
                .get(BASE_URL + path);
    }

    @When("eu envio uma requisição GET não autenticada para {string}")
    public void eu_envio_uma_requisicao_GET_nao_autenticada(String path) {
        response = RestAssured.given()
                // Sem autenticação
                .when()
                .get(BASE_URL + path);
    }

    // --- THEN ---
    @Then("o código de resposta deve ser {int}")
    public void o_codigo_de_resposta_deve_ser(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Then("a resposta deve conter {string}")
    public void a_resposta_deve_conter(String expectedBody) {
        assertTrue(response.getBody().asString().contains(expectedBody));
    }

    @Then("a resposta de erro deve conter {string}")
    public void a_resposta_de_erro_deve_conter(String expectedError) {
        assertTrue(response.getBody().asString().contains(expectedError));
    }

    @Then("eu devo conseguir buscar a conta recém-criada por ID")
    public void eu_devo_conseguir_buscar_a_conta_recem_criada_por_ID() {
        // Act: Busca a conta usando o ID salvo na criação
        response = RestAssured.given()
                .auth().basic(AUTH_USER, AUTH_PASS)
                .when()
                .get(BASE_URL + "/api/contas/" + contaId);
        
        // Assert: Verifica se a busca foi bem sucedida (200) e se o ID está correto
        assertEquals(200, response.statusCode());
        assertTrue(response.getBody().asString().contains(contaId));
    }
}
