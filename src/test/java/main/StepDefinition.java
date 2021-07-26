package main;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class StepDefinition {

    String data = "";

    @When("creating a template with data {string}")
    public void createTemplate(String data) {
        System.out.println("Create template with data: " + data);
        this.data = data;
    }

    @Then("last created template has data {string}")
    public void findLastTemplateCreated(String data) {
        System.out.println("Find template with data: " + data);
        assertEquals("Data found don't have the expected value.", this.data, data);
    }
}
