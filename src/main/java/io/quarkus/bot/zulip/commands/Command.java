package io.quarkus.bot.zulip.commands;

import java.util.function.Predicate;

import io.quarkus.bot.zulip.payload.OutgoingWebhookPayload;
import io.quarkus.bot.zulip.payload.OutgoingWebhookResponse;

/**
 * A Bot command that is triggered whenever an {@link OutgoingWebhookPayload} is received.
 *
 * Implementations should be CDI beans (@ApplicationScoped)
 */
public interface Command extends Predicate<OutgoingWebhookPayload> {

    OutgoingWebhookResponse process(OutgoingWebhookPayload payload);

    String getHelpText();
}