package cloud.allowme;

import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonString;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/hello")
public class HelloResource {

    @Inject
    SecurityIdentity identity;

    @Inject
    @Claim(standard = Claims.given_name)
    String givenName;

    @GET
    @Path("public")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String helloPublic() {
        return "public";
    }

    @GET
    @Path("private")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed({ "user", "admin" })
    public String helloPrivate() {
        return "private";
    }

    @GET
    @Path("private-admin")
    @Produces(MediaType.TEXT_PLAIN)
    @RolesAllowed("admin")
    public String helloPrivateAdmin() {
        return "private-admin";
    }

    @GET
    @Path("authenticated")
    @Produces(MediaType.TEXT_PLAIN)
    @Authenticated
    public String helloAuthenticated() {
        //return "authenticated";
        //return identity.getPrincipal().getName();
        return givenName;
    }

}