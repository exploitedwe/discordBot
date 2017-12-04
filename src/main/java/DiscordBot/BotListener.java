package DiscordBot;

import commands.Help;
import features.Responses;
import features.Welcome;
import net.dv8tion.jda.client.events.call.voice.CallVoiceJoinEvent;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.impl.RoleImpl;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Scanner;

public class BotListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMember().getUser().isBot())return;
        if(!event.getMessage().getMember().getRoles().contains(new RoleImpl(DevBot.getRegulars(),event.getGuild()))
                && event.getMessage().getContent().startsWith(Main.prefix)){

            event.getChannel().sendMessage(event.getMessage().getAuthor().getName() + " you lack permissions!").complete();
            return;
        }
        String[] content = event.getMessage().getContent().split("");
        if(event.getMessage().getContent().startsWith(Main.prefix)){
            content[0] = content[0].replace(Main.prefix,"").toLowerCase();
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event,Main.prefix));
        }else if(event.getMessage().getContent().equalsIgnoreCase("help")) Main.commands.get("help").action(content,event);
        else if(event.getMessage().getContent().toLowerCase().contains("@timmy")&&(event.getMessage().getContent().toLowerCase().contains("hello")||
            event.getMessage().getContent().toLowerCase().contains("hi")))
            Main.welcomeSound(event.getGuild().getTextChannelById(DevBot.musicID()),"https://www.youtube.com/watch?v=RPbyZuXNMUk");
        else if(event.getMessage().getContent().toLowerCase().contains("@timmy")) Responses.fuckOff();
    }

    public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent event) {
        if (event.getUser().getId() == DevBot.getDon()) {
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage("GOD IS ONLINE").complete();
        } else if (event.getGuild().getMemberById("131919578149289984").getOnlineStatus() != OnlineStatus.OFFLINE)
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage("GOD IS ONLINE");
        else if (event.getGuild().getMemberById("131919578149289984").getOnlineStatus() == OnlineStatus.OFFLINE)
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage("god is offline :(");
        else if (event.getUser().getId().equalsIgnoreCase(DevBot.getRiley()))
            event.getGuild().getTextChannelById(DevBot.textID()).sendMessage(":heart: Riley is online :heart:").complete();
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild().getTextChannelById(DevBot.textID()).sendMessage(event.getUser().getName() + " has joined the Guild!").complete();
    }

    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {

        //Role: God joins call
        if(event.getMember().getUser().isBot()) return;
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
