package com.example.javamavenjunithelloworld;


import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


public class CurlTest {

    @Test
    public void testPost() throws IOException {

        String FEEDTYPE_PARAM   = "feedtype";
        String DATASOURCE_PARAM = "datasource";
        String XMLFILE_PARAM    = "data";

        // GSA XML Feed URL
        int PORT           = 19900;
        String FEED_URL    = "/xmlfeed";

        String gsaServer = "http://gspmsa1.parisgsa.lan"+ ":" + PORT + FEED_URL;;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("feed_Carl.xml").getFile());

            HttpClient client = HttpClientBuilder.create().build();


            HttpPost postPageRequest;
            postPageRequest = new HttpPost(gsaServer);

            InputStream is = new FileInputStream(file);

            // Add Form parameters
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.addTextBody(FEEDTYPE_PARAM, "incremental",
                ContentType.TEXT_PLAIN);
            builder.addTextBody(DATASOURCE_PARAM, "cespharm" ,
                ContentType.TEXT_PLAIN);
            builder.addBinaryBody(XMLFILE_PARAM, is);
            HttpEntity multipartEntity = builder.build();

            postPageRequest.setEntity(multipartEntity);

            HttpResponse postPageResponse = client.execute(postPageRequest);
            int status = postPageResponse.getStatusLine().getStatusCode();

            assertThat(status, is(equalTo(200)));

        } catch (Exception e) {
            fail("Exception "+ e.getMessage() + " in HTTP request" );
        }

    }


}
