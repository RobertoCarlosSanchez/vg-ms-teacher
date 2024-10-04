package pe.edu.vallegrande.teacher.infrastructure.client;

import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.teacher.infrastructure.util.TokenValidation;
import reactor.core.publisher.Mono;

@Component
public class AuthServiceClient {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AuthServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.baseUrl("https://vg-ms-user-production-49bd.up.railway.app/firebase-users");
    }

    public Mono<TokenValidation> validateToken(String token) {
        return webClientBuilder.build()
                .get()
                .uri("/validate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(TokenValidation.class);
    }

}
