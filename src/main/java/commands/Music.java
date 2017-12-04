package commands;

import DiscordBot.Command;
import DiscordBot.DevBot;
import DiscordBot.Main;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Music implements Command{
    private static boolean instantiated = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(!event.getChannel().getId().equals(DevBot.musicID())){
            event.getChannel().sendMessage("Write MUSIC COMMANDS in music chat." +
                    "This cuts down on bot-spam in general.").complete();
            return;
        }
        String[] content = event.getMessage().getContent().split(" ");
        content[0] = content[0].replace(Main.prefix,"");

        switch (content[0].toLowerCase()){
            case "play":
                Main.loadAndPlay(event.getTextChannel(), content[1]);
                if(!instantiated){
                    Main.musicManager.player.setVolume(50);
                    instantiated = true;
                }
                break;

            case "skip":
                Main.skipTrack(event.getTextChannel());
                break;

            case "pause":
                if(Main.musicManager == null || Main.musicManager.player == null || Main.musicManager.player.getPlayingTrack() == null)
                    event.getChannel().sendMessage("Nothing playing.").complete();
                else {
                    Main.musicManager.player.setPaused(true);
                    event.getChannel().sendMessage("Pausing...").complete();
                }
                break;

            case "resume":
                if(Main.musicManager == null || Main.musicManager.player == null || Main.musicManager.player.getPlayingTrack() == null)
                    event.getChannel().sendMessage("Nothing playing.").complete();
                else {
                    Main.musicManager.player.setPaused(false);
                    event.getChannel().sendMessage("Resuming " + Main.musicManager.player.getPlayingTrack().getInfo().title).complete();
                }
                break;

            case "volume":
                int vol = Integer.parseInt(content[1]);
                if (vol < 0) vol = 0;
                else if (vol > 100) vol = 100;
                Main.musicManager.player.setVolume(vol);
                event.getChannel().sendMessage("Setting volume to: " + vol).complete();
                break;
            case "queue":
                break;
        }
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
