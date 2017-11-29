package BotProject.DiscordBot;

import BotProject.commands.*;
import BotProject.features.*;
import BotProject.features.Welcome;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static BotProject.DiscordBot.BotListener.membersInGen;

/*
* Mitchell AKA livewire.io on YouTube created MUCH of this program
* Mitchell AKA exploitedweakness added to this
* */
//JDA was published on github

public class Main {

    public static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static final FeatureChecker check = new FeatureChecker();
    public static HashMap<String, Command> commands = new HashMap<String,Command>();
    public static HashMap<String, Feature> features = new HashMap<String, Feature> ();
    public static String prefix = "~!";

    public static void main(String[] args) {
        try{
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(DevBot.DISCORD_TOKEN()).buildBlocking();
            jda.setAutoReconnect(true);
            jda.getPresence().setGame(Game.of("with Riley"));
        }catch(Exception e){
            e.printStackTrace();
        }
        commands.put("ping", new Ping());
        commands.put("time", new Time());
        features.put("welcome", new Welcome());
    }

    public static void handleCommand(CommandParser.CommandContainer cmd){
        if(commands.containsKey(cmd.invoke)){
            boolean safe = commands.get(cmd.invoke).called(cmd.args,cmd.event);

            if(safe){
              commands.get(cmd.invoke).action(cmd.args, cmd.event);
              commands.get(cmd.invoke).executed(safe,cmd.event);

            } else{
                commands.get(cmd.invoke).executed(safe,cmd.event);
            }
        }
    }

    public static void handleFeature(String feature,MessageReceivedEvent event, int lastOnInGen){
        List<net.dv8tion.jda.core.entities.Member> members = event.getGuild().getVoiceChannelById(DevBot.TRAN_General_Audio_ID()).getMembers();

        switch (feature){
            case "welcome":
                for(; lastOnInGen < members.size(); lastOnInGen++){
                    event.getTextChannel().sendMessage("Welcome " + members.get(lastOnInGen).getEffectiveName() ).complete();
                }
                break;
            case "GodOnline":
                break;
        }
    }

    public static void log(String subject, String action){
        return;
    }

    public static void features(MessageReceivedEvent event){

    }
}
