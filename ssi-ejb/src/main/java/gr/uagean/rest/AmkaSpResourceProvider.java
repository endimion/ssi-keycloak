package gr.uagean.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class AmkaSpResourceProvider implements RealmResourceProvider {

	private KeycloakSession session;

	public AmkaSpResourceProvider(KeycloakSession session) {
        this.session = session;
    }

	@Override
	public Object getResource() {
		return new AmkaSpRestResource(session);
	}
	
	@Override
	public void close() {
	}

}
