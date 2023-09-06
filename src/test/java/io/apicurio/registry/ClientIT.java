package io.apicurio.registry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.apicurio.registry.rest.client.RegistryClient;
import io.apicurio.registry.rest.client.RegistryClientFactory;
import io.apicurio.registry.rest.v2.beans.ArtifactMetaData;
import io.apicurio.registry.rest.v2.beans.ArtifactSearchResults;
import io.apicurio.registry.rest.v2.beans.VersionSearchResults;
import io.apicurio.registry.types.bigquery.provider.BigQueryArtifactTypeUtilProvider;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
public class ClientIT {
    private static final String MYGROUP = "mygroup";
    private static final String MYARTIFACT = "myartifact";

    @TestHTTPResource("/")
    String url;
    
    @Test
    public void attachFileWithClient() {
        RegistryClient client = RegistryClientFactory.create(url);

        ArtifactMetaData createArtifact = client.createArtifact(MYGROUP, MYARTIFACT,
            new BigQueryArtifactTypeUtilProvider().getArtifactType(),
            getClass().getResourceAsStream("/bigqueryschemaexample.json"));
        final Long GLOBALID = createArtifact.getGlobalId();
        assertThat(GLOBALID)
            .isNotNull();

        VersionSearchResults versionSearchResults = client.listArtifactVersions(MYGROUP, MYARTIFACT, null, null);
        assertThat(versionSearchResults.getVersions())
            .hasSize(1)
            .first()
            .hasFieldOrPropertyWithValue("globalId", GLOBALID);
            

        ArtifactSearchResults artifactSearchResults = client.listArtifactsInGroup(MYGROUP);
        assertThat(artifactSearchResults.getArtifacts())
            .hasSize(1)
            .first()
            .hasFieldOrPropertyWithValue("id", MYARTIFACT);
    }
}
