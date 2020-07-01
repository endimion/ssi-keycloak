package gr.uagean.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class EidasResourceProvider implements RealmResourceProvider {

	private KeycloakSession session;

	public EidasResourceProvider(KeycloakSession session) {
        this.session = session;
    }

	@Override
	public Object getResource() {
		return new EidasRestResource(session);
	}
	
	@Override
	public void close() {
	}

}
