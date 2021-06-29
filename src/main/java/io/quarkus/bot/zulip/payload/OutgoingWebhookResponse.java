package io.quarkus.bot.zulip.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This payload is described in https://zulip.com/api/outgoing-webhooks#example-response-payloads
 */
public class OutgoingWebhookResponse {
    public static final OutgoingWebhookResponse EMPTY = new OutgoingWebhookResponse(null);

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String content;

    public OutgoingWebhookResponse(String content, Object... args) {
        this.content = content == null ? null : String.format(content, args);
    }

    public boolean isResponseNotRequired() {
        return content != null;
    }

    public String getContent() {
        return content;
    }
}
