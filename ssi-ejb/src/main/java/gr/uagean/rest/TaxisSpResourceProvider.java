package gr.uagean.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class TaxisSpResourceProvider implements RealmResourceProvider {

	private KeycloakSession session;

	public TaxisSpResourceProvider(KeycloakSession session) {
        this.session = session;
    }

	@Override
	public Object getResource() {
		return new TaxisSpRestResource(session);
	}
	
	@Override
	public void close() {
	}

}
