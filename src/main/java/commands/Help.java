package commands;

import DiscordBot.Command;
import DiscordBot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help implements Command{
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String help =
                "Features are executed given any number of reasons, here are some features that TIMUR offers:\n"+
                        "\t\tONLY REGULARS AND UP CAN USE ME.\n"+
                        "\t\tSays hi back when you @timmy hi or hello him\n"+
                        "\t\tWelcomes Regulars and above.\n"+
                        "\t\tTimmy responds to @timmy. Sometimes ;)\n\n\n"+

                "Commands are executed with...\n [prefix][commandName]  [options]\n"+
                        "ONLY REGULARS AND UP CAN USE ME.\n\n"+
                        "Prefix: "+ Main.prefix+"\n\n"+
                        "Commands: \n"+
                        "\tMUSIC COMMANDS\n"+
                        "\t\tCurrently supported [source]'s:\n"+
                        "\t\t\tYouTube\n\t\t\tSoundCloud\n\t\t\tBandcamp\n\t\t\tVimeo\n\t\t\tTwitch\n\t\t\tLocal Files\n\t\t\tHttp URL's\n"+
                        "\t\tplay [source] --plays song from set of sources\n"+
                        "\t\tpause --pauses currently played song.\n"+
                        "\t\tresume --resumes paused song.\n"+
                        "\t\tskip --skips current song.\n"+
                        "\tADDITIONAL COMMANDS\n"+
                        "\t\tsummon --summons bot to callers voice channel.\n"+
                        "\t\tleave --forces bot out of voice channels.\n"+
                        "\t\ttime --relays time in EST.\n"+
                        "\t\tclean [amount] --cleans amount of messages specified.\n"+
                        "\t\tping --pongChamp :)\n";


        event.getChannel().sendMessage(help).complete();
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
