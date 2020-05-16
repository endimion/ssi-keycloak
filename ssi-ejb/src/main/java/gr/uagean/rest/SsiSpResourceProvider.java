package gr.uagean.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class SsiSpResourceProvider implements RealmResourceProvider {

	private KeycloakSession session;

	public SsiSpResourceProvider(KeycloakSession session) {
        this.session = session;
    }

	@Override
	public Object getResource() {
		return new SsiSpRestResource(session);
	}
	
	@Override
	public void close() {
	}

}
