import org.junit.Test;

public class AppEntegration {

    ApiTest apiTest=new ApiTest();
    String apiKey=System.getProperty("apiKey","cd3e9cfd");
    String search = System.getProperty("search","harry potter");
    String movieTitle = System.getProperty("movieTitle","Harry Potter and the Sorcerer's Stone");


    @Test
    public void HarrySearch(){

        String id=apiTest.getMovie(apiKey,search,movieTitle);

        apiTest.searchID(apiKey,id);
    }
}
