import com.brunobat.tomee.Legume;
import com.brunobat.tomee.LegumeResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(Arquillian.class)
public class LegumeResourceTest {

    public static final String TEST = "test";

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, TEST + ".war")
                .addClasses(LegumeResource.class, Legume.class)
                .addAsResource(new ClassLoaderAsset("META-INF/persistence.xml"), "META-INF/persistence.xml");
    }


    @Test
    public void testList() {
        given()
                .header("Content-Type", MediaType.APPLICATION_JSON_TYPE.getType())
                .when().post(TEST + "/legumes")
                .then()
                .statusCode(201);

        given()
                .when().get(TEST + "/legumes")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("Carrot", "Zucchini"),
                        "description", containsInAnyOrder("Root vegetable, usually orange", "Summer squash"));
    }
}
