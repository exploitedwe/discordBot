package BotProject.commands;

import BotProject.DiscordBot.Command;
import BotProject.DiscordBot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Date;

public class Time implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("The time is: " + new Date().toString()).complete();
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }

}
