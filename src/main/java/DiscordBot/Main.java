package DiscordBot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import commands.*;
import lavaplayerAdditionals.GuildMusicManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import java.util.HashMap;
import java.util.Map;

/*
* Mitchell AKA livewire.io on YouTube created MUCH of this program
* Mitchell AKA exploitedweakness added to this
* */
//JDA was published on github

public class Main {

    public static JDA jda;
    public static final CommandParser parser = new DiscordBot.CommandParser();
    public static HashMap<String, Command> commands = new HashMap<String, Command>();
    public static String prefix = "~!";

    public static AudioPlayerManager playerManager;
    public static Map<Long, GuildMusicManager> musicManagers;
    public static GuildMusicManager musicManager;

    //Voice Channel stuffs
    public static AudioManager audioManager;

    public static void main(String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(DevBot.discordToken()).buildBlocking();
            jda.setAutoReconnect(true);
            jda.getPresence().setGame(Game.of("with Riley"));
            musicManagers = new HashMap<>();
            playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(playerManager);
            AudioSourceManagers.registerLocalSource(playerManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        commands.put("ping", new Ping());
        commands.put("time", new Time());
        commands.put("summon", new Voice());
        commands.put("leave", new Voice());
        commands.put("play", new Music());
        commands.put("skip", new Music());
        commands.put("pause", new Music());
        commands.put("resume", new Music());
        commands.put("volume", new Music());
        commands.put("help", new Help());
        commands.put("clean", new Clean());
        commands.put("queue", new Music());
    }

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            //commands.get(cmd.invoke.toLowerCase()).action(cmd.args,cmd.event);
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
            if (safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);

            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
        }else System.out.println("Commands does not have key: "+cmd.invoke);
    }

    //Audio stuffs
    private static synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public static void loadAndPlay(final TextChannel channel, final String trackUrl) {
        final GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    public static void welcomeSound(final TextChannel channel, final String trackUrl) {
        final GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {

                if(musicManager.player.getPlayingTrack() == null) {
                    //musicManager.player.setVolume(200);
                    play(channel.getGuild(), musicManager, track);
                    //musicManager.player.setVolume(20);
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
            }

            @Override
            public void noMatches() {
                //channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                //channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });
    }

    private static void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        connectToFirstVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);
    }

    public static void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }

    private static void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }

    public static void log(String subject, String action) {
        return;
    }
    public static void features(MessageReceivedEvent event) {

    }
}
