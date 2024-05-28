import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ServerCheck {
    public ServerCheck() {
        String[] sites = {
                "https://www.apple.com/",
                "https://www.microsoft.com/",
                "https://www8.hp.com/",
                "https://www.oracle.com/",
                "https://www.informit.com/",
                "https://www.dell.com/"
        };
        try {
            load (sites);
        } catch (URISyntaxException oops) {
            System.out.println("Bad URI: " + oops.getMessage());
        }catch (IOException | InterruptedException oops) {
            System.out.println("Error: " + oops.getMessage());
        }
    }
    public void load (String[] sites) throws URISyntaxException,IOException,InterruptedException{
        for (String site : sites) {
            System.out.println("\nSite: " + site);

            HttpClient browser = HttpClient.newHttpClient();

            URI uri = new URI(site);
            HttpRequest.Builder bob = HttpRequest.newBuilder(uri);
            HttpRequest request = bob.build();

            HttpResponse<String> response = browser.send(request,
                    HttpResponse.BodyHandlers.ofString());

            HttpHeaders headers = response.headers();
            Optional<String> server = headers.firstValue("Server");
            if (server.isPresent()) {
                System.out.println("Server: " + server.get());
            } else {
                System.out.println("Server unidentified");
            }
        }
    }

    public static void main(String[] arguments) {
        new ServerCheck();
    }
}
