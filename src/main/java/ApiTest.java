
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;

public class ApiTest {

    static String url = System.getProperty("baseURI","http://www.omdbapi.com/");

    public String getMovie(String apiKey, String searchWord, String movieTitle) {
        RestAssured.baseURI = url;
        String id = " ";
        try {
            Response response = getResponse(apiKey,searchWord);
            JsonPath path = response.jsonPath();
            List<MovieProperties> data = path.getList("Search", MovieProperties.class);
            System.out.println("\nAradığınız kelimeyle ilgili "+data.size()+" adet film var\n");

            for (MovieProperties obj : data)
            {
                if (obj.getTitle().equals(movieTitle))
                {
                    id = obj.getImdbID();
                }
            }
            return id;
        }
        catch (Exception exception){
            System.out.println("Hata var!!" + exception.getMessage());return null; }
    }
    public static void getResponseTime(){
        System.out.println("Response için geçen süre"+" "+get(url).timeIn(TimeUnit.MILLISECONDS) +" "+ " mili saniye");
    }

    public void searchID(String apiKey, String id)
    {
        try {
            given()
                    .param("apikey", apiKey)
                    .param("i",id)
                    .when().get(url).then().log().all().statusCode(200)
                    .body("Title", not(emptyOrNullString()))
                    .body("Year", not(emptyOrNullString()))
                    .body("Released",not(emptyOrNullString()));
            getResponseTime();
             }
        catch (Exception exception){
            System.out.println("Hata var!! "+exception.getMessage());}
    }

    private Response getResponse(String apiKey, String searchW)
    {
        try {
            return given()
                    .param("apikey", apiKey)
                    .param("s", searchW)
                    .when().get(url).then().log().all().contentType(ContentType.JSON).statusCode(200).and()
                    .body("Search.Title",not(emptyOrNullString()))
                    .body("Search.Year",not(emptyOrNullString()))
                    .extract().response();

            }
        catch (Exception exception){
            System.out.println("Hata var!! "+exception.getMessage());return null; }
    }

}
