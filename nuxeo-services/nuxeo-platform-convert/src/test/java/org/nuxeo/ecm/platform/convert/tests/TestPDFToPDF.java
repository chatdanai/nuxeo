/*
 * (C) Copyright 2019 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Miguel Nixo
 */

package org.nuxeo.ecm.platform.convert.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.blobholder.BlobHolder;

/**
 * @since 11.1
 */
public class TestPDFToPDF extends BaseConverterTest {

    @Test
    public void testConverter() throws Exception {
        String converterName = "any2pdf";

        checkConverterAvailability(converterName);
        checkCommandAvailability("converter");

        BlobHolder pdfBH = getBlobFromPath("test-docs/hello.pdf");
        Map<String, Serializable> parameters = new HashMap<>();
        parameters.put("targetFilePath", "hello.pdf");

        BlobHolder result = cs.convert(converterName, pdfBH, parameters);
        assertNotNull(result);

        List<Blob> blobs = result.getBlobs();
        assertNotNull(blobs);
        assertEquals(1, blobs.size());

        Blob mainBlob = result.getBlob();
        assertEquals("hello.pdf", mainBlob.getFilename());

        // using "==" operator to check both references point on the same object, rather than comparing the result has the
        // same content, which wouldn't guarantee the original blob is returned, but maybe a perfect copy.
        assertTrue(pdfBH.getBlob() == mainBlob);
    }
}
