package dev.quarkusfun;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingResource {
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "world!";
    }

    @POST
    @Path("greetings")
    @Consumes(MediaType.APPLICATION_JSON)
    public GreetingResponse greet(GreetingRequest request) {
        long started = System.nanoTime();
        return new GreetingResponse(request.message(), Instant.now(), System.nanoTime() - started);
    }

    public record GreetingRequest(String message) {}

    public record GreetingResponse(String message, Instant timestamp, long executionTimeNanos) {}
}
