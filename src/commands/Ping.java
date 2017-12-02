package commands;

import DiscordBot.Command;
import DiscordBot.DevBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;


public class Ping implements Command {
    private String HELP = "USAGE: --!ping";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String message = "Pong! '" + event.getJDA().getPing() + "ms'\n";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);
        eb.setDescription(message);
        event.getTextChannel().sendMessage(eb.build()).complete();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
