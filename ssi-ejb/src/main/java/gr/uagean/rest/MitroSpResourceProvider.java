package gr.uagean.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class MitroSpResourceProvider implements RealmResourceProvider {

	private KeycloakSession session;

	public MitroSpResourceProvider(KeycloakSession session) {
        this.session = session;
    }

	@Override
	public Object getResource() {
		return new MitroSpRestResource(session);
	}
	
	@Override
	public void close() {
	}

}
