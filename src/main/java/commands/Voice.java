package commands;

import DiscordBot.Command;
import DiscordBot.DevBot;
import DiscordBot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
public class Voice implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) { return true; }

    /*        if(!Main.voice){
            Main.audioManager = event.getGuild().getAudioManager();
            Main.audioManager.openAudioConnection(event.getGuild().getVoiceChannelById(DevBot.audioID()));
            Main.voice = true;
        }*/

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(event.getMessage().getContent().toLowerCase().startsWith(Main.prefix+"summon"))voiceJoin(event);
        if(event.getMessage().getContent().toLowerCase().startsWith(Main.prefix+"leave"))voiceLeave(event);
    }

    @Override
    public String help() { return null; }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) { }

    private void voiceJoin(MessageReceivedEvent event){
        if(Main.audioManager == null || Main.audioManager.isConnected() == false) {
            Main.audioManager = event.getGuild().getAudioManager();
            Main.audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
            event.getChannel().sendMessage(":new_moon_with_face:").complete();
        }else{
            event.getChannel().sendMessage(event.getAuthor().getName()+" I'm already here!").complete();
        }
    }
    private void voiceLeave(MessageReceivedEvent event) {
        if (Main.audioManager.isConnected()) {
            Main.audioManager.closeAudioConnection();
            Main.musicManager.player.stopTrack();
            event.getChannel().sendMessage(":hear_no_evil:").complete();
        }else{
            event.getChannel().sendMessage(event.getAuthor().getName()+" I'm not in a voice call!").complete();
        }
    }
}
