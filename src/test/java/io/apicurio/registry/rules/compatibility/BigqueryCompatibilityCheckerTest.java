package io.apicurio.registry.rules.compatibility;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.apicurio.registry.content.ContentHandle;
import io.apicurio.registry.rules.RuleViolationException;
import io.apicurio.registry.rules.validity.BigqueryContentValidator;
import io.apicurio.registry.rules.validity.ValidityLevel;

public class BigqueryCompatibilityCheckerTest {
    
    @Test
    public void smokeTestUsingExampleSchemaFile() throws Exception {
        BigqueryContentValidator uut = new BigqueryContentValidator();
        assertThatNoException()
            .isThrownBy(() -> uut.validate(ValidityLevel.NONE, ContentHandle.create(getClass().getResourceAsStream("/bigqueryschemaexample.json")), null));
    }

    @Test
    public void smokeTestUsingInvalidContent() throws Exception {
        BigqueryContentValidator uut = new BigqueryContentValidator();
        assertThrows(RuleViolationException.class,
            () -> uut.validate(ValidityLevel.NONE, ContentHandle.create("_invalid_"), null));
    }
}
