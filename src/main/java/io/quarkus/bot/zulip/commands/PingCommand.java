package io.quarkus.bot.zulip.commands;

import java.util.Locale;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.bot.zulip.payload.OutgoingWebhookPayload;
import io.quarkus.bot.zulip.payload.OutgoingWebhookResponse;

@ApplicationScoped
public class PingCommand implements Command {

    @Override
    public OutgoingWebhookResponse process(OutgoingWebhookPayload payload) {
        return new OutgoingWebhookResponse("@**%s** **PONG**!", payload.message.sender_full_name);
    }

    @Override
    public boolean test(OutgoingWebhookPayload payload) {
        return payload.message.content.toLowerCase(Locale.ROOT).contains("ping");
    }

    @Override
    public String getHelpText() {
        return "`ping` - Returns a pong to the caller";
    }
}
