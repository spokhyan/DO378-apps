package org.acme.rest.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;

public class EndPointTest {

    URI expensesURI;

    @Test
    public void testEndpointPort(){
        assertEquals(8888, expensesURI.getPort());
    }

}