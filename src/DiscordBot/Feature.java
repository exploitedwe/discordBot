package DiscordBot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Feature {
    public void action(String[] args, MessageReceivedEvent event);
}
