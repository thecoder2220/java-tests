package com.example.javamavenjunithelloworld;


import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.util.EntityUtils;

public class CurlTest {

    @Test
    public void testPost() throws IOException {

        String FEEDTYPE_PARAM   = "feedtype";
        String DATASOURCE_PARAM = "datasource";
        String XMLFILE_PARAM    = "data";

        // GSA XML Feed URL
        int PORT           = 19900;
        String FEED_URL    = "/xmlfeed";

        String gsaServer;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("feed_Carl.xml").getFile());

            HttpClient client = HttpClientBuilder.create().build();


            HttpPost postPageRequest;
            postPageRequest = new HttpPost("http://gspmsa1.parisgsa.lan:19900/xmlfeed");

            InputStream is = new FileInputStream(file);

            // Add Form parameters
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            builder.addTextBody(FEEDTYPE_PARAM, "cespharm",
                ContentType.TEXT_PLAIN);
            builder.addTextBody(DATASOURCE_PARAM, "incremental",
                ContentType.TEXT_PLAIN);
            builder.addBinaryBody(XMLFILE_PARAM, is);
            HttpEntity multipartEntity = builder.build();

            postPageRequest.setEntity(multipartEntity);

            HttpResponse postPageResponse = client.execute(postPageRequest);
            int status = postPageResponse.getStatusLine().getStatusCode();

            if (status == 200)
                System.out.println("The status = " + status);
            else
                System.out.println("XML was posted successfully to GSA!!");

        } catch (Exception e) {
            System.out.println("Exception "+ e.getMessage() + " in HTTP request" );
        }

    }


}
