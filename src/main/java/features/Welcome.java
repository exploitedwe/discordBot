package features;

import DiscordBot.DevBot;
import DiscordBot.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class Welcome {

    public static void handleFeature(GuildVoiceJoinEvent event, int role) {
        switch (role) {
            case 1: // Regular
                    event.getGuild().getTextChannelById(DevBot.textID())
                            .sendMessage("Welcome " + event.getMember().getEffectiveName() + " :wave:").complete();
                    Main.welcomeSound(event.getGuild().getTextChannelById(DevBot.musicID()),"https://www.youtube.com/watch?v=a7Q_ymVD1U4");
                    //Main.welcomeSound(event.getGuild().getTextChannelById(DevBot.musicID()),"https://www.youtube.com/watch?v=RPbyZuXNMUk");
                break;
            case 2: // God
                    event.getGuild().getTextChannelById(DevBot.textID())
                            .sendMessage(new EmbedBuilder().setColor(Color.YELLOW)
                                    .setDescription("Gaming God " + event.getMember().getEffectiveName() +
                                    " has graced us with his presence.").setTitle("BOW DOWN PLEBS")
                                    .setThumbnail("https://ih0.redbubble.net/image.363620937.6389/flat,800x800,075,t.u2.jpg")
                                    .build())
                            .complete();
                    Main.welcomeSound(event.getGuild().getTextChannelById(DevBot.musicID()),"https://www.youtube.com/watch?v=zkVi0vs0nXs");
                break;
        }
    }
}
