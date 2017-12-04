package commands;

import DiscordBot.Command;
import DiscordBot.DevBot;
import DiscordBot.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class Clean implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    private int deletionsLeft = 0;
    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        //System.out.println("Cleaning!");
        String[] content = event.getMessage().getContent().split(" ");
        deletionsLeft = Integer.parseInt(content[1]);
        List<Message> list = event.getChannel().getHistory().retrievePast(deletionsLeft).complete();
        //System.out.print("List Size: " + list.size() + " ");
        int i = 0;
        while(deletionsLeft > i) {
            list.get(i++).delete().complete();
        }
        event.getChannel().sendMessage("Cleaned "+i+" messages. :wastebasket:").complete();
        return;
    }


    private void deleteBotMessages(MessageReceivedEvent event){
        return;
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
