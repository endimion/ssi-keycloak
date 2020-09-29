package gr.uagean.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class GrnetSpResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;

    public GrnetSpResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return new GrnetSpRestResource(session);
    }

    @Override
    public void close() {
    }

}
