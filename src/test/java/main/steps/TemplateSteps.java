package main.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.RestClientHelper;

import java.util.List;

public class TemplateSteps extends BaseResourceSteps{
    private static final String resource = "template";

    public TemplateSteps(RestClientHelper restClient) {
        super(resource, restClient);
    }

    @When("create many templates with data")
    public void create(List<String> data) {
        super.create(data);
    }

    @When("create a template with data {string}")
    public void create(String data) {
        super.create(data);
    }

    @When("update last created template with data {string} status code is {int}")
    public void update(String data, int expectedStatusCode) {
        super.update(data, expectedStatusCode);
    }

    @Then("get last created template status code is {int} and has data {string}")
    public void getLastCreated(int expectedStatusCode, String data) {
        super.getLastCreated(expectedStatusCode, data);
    }

    @When("delete last created template status code is {int}")
    public void delete(int expectedStatusCode) {
        super.delete(expectedStatusCode);
    }

    @Then("get all templates in offset {int} limit {int} should be")
    public void findAllByOffsetAndRange(Integer offset, Integer range, List<String> data) {
        super.findAllByOffsetAndRange(offset, range, data);
    }

    @Then("templates count should be {int}")
    public void countAll(Integer count) {
        super.countAll(count);
    }
}
