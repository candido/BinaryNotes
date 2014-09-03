/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 Original sources are available at www.latestbit.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.bn.compiler.parser;

import org.bn.compiler.parser.model.ASN1Model;
import org.bn.compiler.parser.model.ASNModule;
import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import junit.framework.TestCase;

public class ASNParserTest extends TestCase {

    public ASNParserTest(String sTestName) {
        super(sTestName);
    }

    private ASN1Model createFromStream() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/test.asn");
        ASNLexer lexer = new ASNLexer(stream);
        ASNParser parser = new ASNParser(lexer);
        ASNModule module = new ASNModule();

        parser.module_definition(module);

        ASN1Model model = new ASN1Model();
        model.module = module;
        return model;
    }

    public void testJaxb() throws Exception {
        ASN1Model model = createFromStream();
        model.runtimeArguments = new String[]{"-inputFileName", "test.asn"};
        model.moduleDirectory = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "modules" + File.separator + "java";
        model.outputDirectory = "testworkdir" + File.separator + "output";
        model.moduleNS = "test_asn";
        
        Marshaller marshaller = JAXBContext.newInstance("org.bn.compiler.parser.model").createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(model, System.out);
        //marshaller.marshal(model, new FileOutputStream("temp.xml"));
    }

    /**
     * @see ASNParser#module_definition(ASNModule)
     */
    public void testModule_definition() throws Exception {
        ASN1Model model = createFromStream();

        assertEquals(model.module.moduleIdentifier.name, "TEST_ASN");
        assertEquals(model.module.asnTypes.sequenceSets.size(), 17);
        assertEquals(model.module.asnTypes.enums.size(), 2);
        assertEquals(model.module.asnTypes.characterStrings.size(), 6);
        assertEquals(model.module.asnTypes.octetStrings.size(), 1);
        assertEquals(model.module.asnTypes.sequenceSetsOf.size(), 7);
    }
}
