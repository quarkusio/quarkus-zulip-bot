package io.quarkus.bot.zulip.payload;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This payload is defined in https://zulip.com/api/outgoing-webhooks#outgoing-webhook-format
 */
public class OutgoingWebhookPayload {

    public String bot_email;

    public String bot_full_name;

    public String data;

    public Message message;

    public String token;

    public String trigger;

    public class Message {

        public String avatar_url;

        public String client;

        public String content;

        public JsonNode display_recipient;

        public int id;

        public boolean is_me_message;

        public int recipient_id;

        public String rendered_content;

        public String sender_email;

        public String sender_full_name;

        public int sender_id;

        public String sender_realm_str;

        public int stream_id;

        public String subject;

        public int timestamp;

        public List<TopicLink> topic_links;

        public String type;

        @Override
        public String toString() {
            return "Message{" +
                    "avatar_url='" + avatar_url + '\'' +
                    ", client='" + client + '\'' +
                    ", content='" + content + '\'' +
                    ", display_recipient='" + display_recipient + '\'' +
                    ", id=" + id +
                    ", is_me_message=" + is_me_message +
                    ", recipient_id=" + recipient_id +
                    ", rendered_content='" + rendered_content + '\'' +
                    ", sender_email='" + sender_email + '\'' +
                    ", sender_full_name='" + sender_full_name + '\'' +
                    ", sender_id=" + sender_id +
                    ", sender_realm_str='" + sender_realm_str + '\'' +
                    ", stream_id=" + stream_id +
                    ", subject='" + subject + '\'' +
                    ", timestamp=" + timestamp +
                    ", topic_links=" + topic_links +
                    ", type='" + type + '\'' +
                    '}';
        }
    }


    public static class TopicLink {
        public String text;

        public String url;

        @Override
        public String toString() {
            return "TopicLink{" +
                    "text='" + text + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Incoming{" +
                "bot_email='" + bot_email + '\'' +
                ", bot_full_name='" + bot_full_name + '\'' +
                ", data='" + data + '\'' +
                ", message=" + message +
                ", token='" + token + '\'' +
                ", trigger='" + trigger + '\'' +
                '}';
    }
}
