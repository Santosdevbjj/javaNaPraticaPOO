package br.com.santosdev;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * BDD - Runner: Ponto de entrada para a execução dos cenários Gherkin.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", // Onde estão os arquivos .feature
    glue = "br.com.santosdev.steps",        // Onde estão as implementações Java
    plugin = {"pretty", "html:target/cucumber-reports.html"}, // Relatórios
    tags = "not @ignore"
)
public class ContaBancariaBDDRunner {
    // Esta classe fica vazia, apenas configura o Cucumber.
}
