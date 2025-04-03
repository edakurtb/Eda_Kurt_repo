package org;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APITest {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static long petId;

    @BeforeAll
    static void setup() {
        petId = System.currentTimeMillis(); 
    }

    @Test
    @Order(1)
    void createPet_positive() throws IOException, InterruptedException {
        String requestBody = """
            {
                "id": %d,
                "name": "TestDog",
                "status": "available"
            }
            """.formatted(petId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"name\":\"TestDog\""));
        assertTrue(response.body().contains("\"status\":\"available\""));
    }

    @Test
    @Order(2)
    void getPet_positive() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petId))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"id\":" + petId));
    }

    @Test
    @Order(3)
    void updatePet_positive() throws IOException, InterruptedException {
        String updatedBody = """
            {
                "id": %d,
                "name": "UpdatedDog",
                "status": "sold"
            }
            """.formatted(petId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(updatedBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("UpdatedDog"));
        assertTrue(response.body().contains("sold"));
    }

    @Test
    @Order(4)
    void deletePet_positive() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petId))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"message\":\"" + petId + "\""));
    }

    @Test
    @Order(5)
    void getPet_negative_notFound() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet/" + petId))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
        assertTrue(response.body().contains("Pet not found"));
    }

    @Test
    void createPet_negative_invalidJson() throws IOException, InterruptedException {
        String badJson = "{ \"id\":, , \"name\": }"; // malformed JSON

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(badJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.statusCode() == 400 || response.statusCode() == 500);
    }
}
