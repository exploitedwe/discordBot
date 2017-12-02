package DiscordBot;

import features.Responses;
import features.Welcome;
import net.dv8tion.jda.client.events.call.voice.CallVoiceJoinEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.impl.RoleImpl;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Random;

public class BotListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Command Checker and Parse handoff
        if (event.getMessage().getContent().startsWith(Main.prefix) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event,Main.prefix));
        else if (event.getMessage().getContent().startsWith("@Timmy hello")) {
            event.getGuild().getTextChannelById(DevBot.textID())
                    .sendMessage(event.getMessage().getMember().getEffectiveName() + " hello! :wave:").complete();
        }else if(event.getMessage().getContent().startsWith("@Timmy") && event.getMessage().getContent().contains("cool")){
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage("@Don is cooler.");
        }
        else if (event.getMessage().getContent().startsWith("@Timmy")) {
            String response = Responses.fuckOff();
            if(response != "") event.getGuild().getTextChannelById(DevBot.textID())
                    .sendMessage(response).complete();
        }

    }

    public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent event) {
        if (event.getUser().getId() == DevBot.getDon()) {
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage("GOD IS ONLINE").complete();
        } else if (event.getGuild().getMemberById("131919578149289984").getOnlineStatus() != OnlineStatus.OFFLINE)
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage("GOD IS ONLINE");
        else if (event.getGuild().getMemberById("131919578149289984").getOnlineStatus() == OnlineStatus.OFFLINE)
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage("god is offline :(");
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild().getTextChannelById(DevBot.textID()).sendMessage(event.getUser().getName() + " has joined the Guild!").complete();
    }

    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {

        //Role: God joins call
        if(event.getMember().getRoles().contains(new RoleImpl(DevBot.getGod(),event.getGuild()))){
            Welcome.handleFeature(event,2);
        }else
        //Role: Regular joins call
        if(event.getMember().getRoles().contains(new RoleImpl(DevBot.getRegulars(),event.getGuild()))){
            Welcome.handleFeature(event,1);
        }
    }

    public void onCallVoiceJoin(CallVoiceJoinEvent event) {
        event.getPrivateChannel().sendMessage("Welcome " + event.getCallUser().getUser().getName() + " to the channel!").complete();
    }
}
