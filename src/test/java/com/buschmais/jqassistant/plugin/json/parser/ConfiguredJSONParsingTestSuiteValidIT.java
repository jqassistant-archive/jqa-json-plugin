package com.buschmais.jqassistant.plugin.json.parser;

import com.buschmais.jqassistant.plugin.json.impl.parser.JSONLexer;
import com.buschmais.jqassistant.plugin.json.impl.parser.JSONParser;
import com.buschmais.jqassistant.plugin.json.impl.scanner.*;
import org.antlr.v4.runtime.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class ConfiguredJSONParsingTestSuiteValidIT {

    private File jsonFile;

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws URISyntaxException {
        return DataProvider.validFilesOfJsonParsingTestSuite();
    }

    public ConfiguredJSONParsingTestSuiteValidIT(File file) {
        jsonFile = file;
    }

    @Test
    public void canHandleTheJSONParsingTestSuite() throws Exception {
        try (InputStream inputStream = Files.newInputStream(jsonFile.toPath())) {
            JSONLexer l = new ConfiguredJSONLexer(CharStreams.fromStream(inputStream), "/not/given");
            JSONParser p = new ConfiguredJSONParser(new CommonTokenStream(l), "/not/given");

            p.document();
        } catch (NullPointerException t) {
            boolean isFromANTLR = new IsNPECausedByANTLRIssue746Predicate().isNPECausedByANTLRIssue746Predicate(t);

            if (!isFromANTLR) {
                throw t;
            }
        }
    }
 }