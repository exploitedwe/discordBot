package BotProject.DiscordBot;

import net.dv8tion.jda.client.events.call.voice.CallVoiceJoinEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ReconnectedEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class BotListener extends ListenerAdapter{
    public static int membersInGen = 0;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Command Checker and Parse handoff
        if (event.getMessage().getContent().startsWith(Main.prefix) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())
            Main.handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
        //Login Welcome for Regulars

    }
    public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent event){
        if(event.getUser().getId() == DevBot.GET_GOD()){
            event.getGuild().getTextChannelById(DevBot.TRAN_General_Text_ID()).sendMessage("GOD IS ONLINE").complete();
        }
        else if(event.getGuild().getMemberById("131919578149289984").getOnlineStatus() != OnlineStatus.OFFLINE)
                event.getGuild().getTextChannelById(DevBot.TRAN_General_Text_ID()).sendMessage("GOD IS ONLINE");
        else if(event.getGuild().getMemberById("131919578149289984").getOnlineStatus() == OnlineStatus.OFFLINE)
                event.getGuild().getTextChannelById(DevBot.TRAN_General_Text_ID()).sendMessage("god is offline :(");
    }
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild().getTextChannelById(DevBot.TRAN_General_Text_ID()).sendMessage(event.getUser().getName() + " has joined the Guild!").complete();
    }
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
        if(event.getMember().getUser().getId() == DevBot.GET_GOD()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setImage("https://ih0.redbubble.net/image.363620937.6389/flat,800x800,075,t.u2.jpg");
            eb.setDescription("GOD has joined voice!");
            eb.setTitle("GOD IS BACK!");
            eb.setColor(Color.YELLOW);
            event.getGuild().getTextChannelById(DevBot.TRAN_General_Text_ID()).sendMessage(eb.build()).complete();
            event.getGuild().getTextChannelById(DevBot.GET_Music_Text_ID()).sendMessage("!play https://www.youtube.com/watch?v=a7Q_ymVD1U4").complete();
            event.getGuild().getTextChannelById(DevBot.GET_Music_Text_ID()).sendMessage("!clean").complete();
        }else{
            event.getGuild().getTextChannelById(DevBot.TRAN_General_Text_ID()).sendMessage("Welcome").complete();
            event.getGuild().getTextChannelById(DevBot.GET_Music_Text_ID()).sendMessage("!play https://www.youtube.com/watch?v=a7Q_ymVD1U4").complete();
            event.getGuild().getTextChannelById(DevBot.GET_Music_Text_ID()).sendMessage("!clean").complete();
        }
    }
    public void onCallVoiceJoin(CallVoiceJoinEvent event){
        event.getPrivateChannel().sendMessage("Welcome "+event.getCallUser().getUser().getName()+" to the channel!").complete();
    }
}
