package br.edu.ifrs.canoas.tads.tcc.web.config;

import br.edu.ifrs.canoas.tads.tcc.web.page.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.configuration.FluentConfiguration;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Wait
@FluentConfiguration(webDriver="chrome")
public abstract class MyFluentTest extends FluentTest {

    @LocalServerPort
    protected int port;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Page
    LoginPage loginPage;

    public void loginUser() {
        loginPage.go(port);
        loginPage.fillAndSubmitForm("user", "user")
                .awaitUntilFormDisappear();
        assertThat(window().title()).isEqualTo("Header");
    }

    public void loginProfessor() {
        loginPage.go(port);
        loginPage.fillAndSubmitForm("orientador", "user")
                .awaitUntilFormDisappear();
        assertThat(window().title()).isEqualTo("Header");
    }

    public void loginUserWithoutSubmittedTheme() {
    	loginPage.go(port);
    	loginPage.fillAndSubmitForm("userWithoutSubmission", "user");
    	await().atMost(1, TimeUnit.SECONDS);
    	assertThat(window().title()).isEqualTo("Header");
    }

	public void loginUserInEvaluationAfterExpirationTime() {
		loginPage.go(port);
		loginPage.fillAndSubmitForm("userExpiredEvaluation", "user");
		await().atMost(1, TimeUnit.SECONDS);
		assertThat(window().title()).isEqualTo("Header");
	}
}