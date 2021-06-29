package io.quarkus.bot.zulip.commands;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.bot.zulip.payload.OutgoingWebhookPayload;
import io.quarkus.bot.zulip.payload.OutgoingWebhookResponse;

@ApplicationScoped
public class EmptyCommand implements Command {

    @Override
    public OutgoingWebhookResponse process(OutgoingWebhookPayload payload) {
        return OutgoingWebhookResponse.EMPTY;
    }

    @Override
    public String getHelpText() {
        return "";
    }

    @Override
    public boolean test(OutgoingWebhookPayload payload) {
        String command = payload.message.content.replaceAll(String.format("@\\*\\*%s\\*\\*", payload.bot_full_name), "");
        return command.isBlank();
    }
}
