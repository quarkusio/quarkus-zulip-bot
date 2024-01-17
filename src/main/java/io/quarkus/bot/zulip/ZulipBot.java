package io.quarkus.bot.zulip;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import io.quarkus.bot.zulip.commands.Command;
import io.quarkus.bot.zulip.payload.OutgoingWebhookPayload;
import io.quarkus.bot.zulip.payload.OutgoingWebhookResponse;

@Path("/")
public class ZulipBot {

    @Inject
    Instance<Command> commands;

    @Inject
    Zulip zulip;

    @POST
    public OutgoingWebhookResponse onWebhook(OutgoingWebhookPayload payload) {
        if (payload.message.content.toLowerCase(Locale.ROOT).contains("help")) {
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer, true);
            for (Command command : commands) {
                pw.println(command.getHelpText());
            }
            zulip.addReaction(payload.message.id, "+1");
            return new OutgoingWebhookResponse(writer.toString());
        } else {
            for (Command command : commands) {
                if (command.test(payload)) {
                    zulip.addReaction(payload.message.id, "+1");
                    return command.process(payload);
                }
            }
            zulip.addReaction(payload.message.id, "oh_no");
            String contents = String.format("@**%s** Sorry, I couldn't understand your command. Type `@%s help` if you need assistance",
                                            payload.message.sender_full_name, payload.bot_full_name);
            return new OutgoingWebhookResponse(contents);
        }
    }
}