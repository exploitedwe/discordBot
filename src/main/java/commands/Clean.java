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

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        System.out.println("Made it to delete Message Parent.");
        String[] command = event.getMessage().getContent().toLowerCase().split(" ");
        if(command[1].contains("all")) deleteAllMessages(event);
        if(command[1].contains("bot")) deleteBotMessages(event);
    }


    private void deleteBotMessages(MessageReceivedEvent event){
        System.out.println("Made it to deleteBotMessages");
        String[] content = event.getMessage().getContent().split(" ");

        int messagesToSift = Integer.parseInt(content[2]);
        List<Message> list = event.getChannel().getHistory().retrievePast(50).complete();
        //System.out.print("List Size: " + list.size() + " ");
        int i = 0;
        int deleted = 0;

        while(i < messagesToSift) {
            if(list.get(i).getAuthor().isBot()&&
                    !list.get(i).getContent().toLowerCase().contains("has joined the")) {
                list.get(i).delete().complete();
                deleted++;
            }
            i++;
        }
        event.getChannel().sendMessage("Cleaned "+deleted+" bot messages. :wastebasket:").complete();
    }

    private void deleteAllMessages(MessageReceivedEvent event){
        System.out.println("Made it to deleteAllMessages!");
        String[] content = event.getMessage().getContent().split(" ");
        int deletionsLeft = Integer.parseInt(content[2]);
        List<Message> list = event.getChannel().getHistory().retrievePast(deletionsLeft).complete();
        //System.out.print("List Size: " + list.size() + " ");
        int i = 0;
        while(deletionsLeft > i) {
            list.get(i++).delete().complete();
        }
        event.getChannel().sendMessage("Cleaned "+i+" messages. :wastebasket:").complete();
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
