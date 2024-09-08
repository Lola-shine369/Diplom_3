import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client{
    private static final String PATH_CREATE = "/api/auth/register";
    private static final String PATH_DELETE = "/api/auth/user";

    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH_CREATE)
                .then();
    }

    public ValidatableResponse deleteUser(String accessToken) {
        if (accessToken != null) {
            return given()
                    .header("Authorization", accessToken)
                    .header("Accept", "*/*")
                    .spec(getSpecForDelete())
                    .delete(PATH_DELETE)
                    .then();
        }
        return null;
    }
}