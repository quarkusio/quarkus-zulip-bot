package io.quarkus.bot.zulip;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Interacts with the Zulip server using the Zulip REST API (https://zulip.com/api/rest)
 */
@ApplicationScoped
public class Zulip {

    @Inject
    @ConfigProperty(name = "zulip.email")
    String email;

    @Inject
    @ConfigProperty(name = "zulip.key")
    String key;

    @Inject
    @ConfigProperty(name = "zulip.site")
    URI site;

    CloseableHttpClient httpClient;

    @PostConstruct
    void init() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(email, key));

        this.httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
    }

    @PreDestroy
    void destroy() {
        if (httpClient != null) {
            try {
                this.httpClient.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }

    /**
     * @see {https://zulip.com/api/send-message}
     */
    public void sendMessage(String subject, String message) {
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("type", "stream"));
        form.add(new BasicNameValuePair("to", "general"));
        form.add(new BasicNameValuePair("subject", subject));
        form.add(new BasicNameValuePair("content", message));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

        HttpPost post = new HttpPost(site.resolve("/api/v1/messages"));
        post.setEntity(entity);
        execute(post);
    }

    /**
     * @see {https://zulip.com/api/add-reaction}
     */
    public void addReaction(int messageId, String reaction) {
        List<NameValuePair> form = new ArrayList<>();
        form.add(new BasicNameValuePair("message_id", String.valueOf(messageId)));
        form.add(new BasicNameValuePair("emoji_name", reaction));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

        String path = String.format("/api/v1/messages/%s/reactions", messageId);
        HttpPost post = new HttpPost(site.resolve(path));
        post.setEntity(entity);
        execute(post);
    }


    private String execute(HttpUriRequest method) {
        String responseBody = null;
        try {
            responseBody = httpClient.execute(method, response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity responseEntity = response.getEntity();
                    return responseEntity != null ? EntityUtils.toString(responseEntity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

}