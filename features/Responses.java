package features;

import java.util.Random;

public class Responses {
    static Random r = new Random();
    //Triggered by @Timmy
    public static String fuckOff(){
        String response = "";
        int i = r.nextInt() % 15 + 15;
        switch(i){
            case 0:
                response = "Fuck off.";
                break;
            case 1:
                response = "Go away.";
                break;
            case 2:
                response = "Leave me be.";
                break;
            case 3:
                response = "I don't care.";
                break;
            case 4:
                response = "Like totally, literally, not at all listening. mhmmk...";
                break;
            case 5:
                response = "I'm not real, find a real friend to talk to.";
                break;
            default:
                response = "";
                break;
        }

        return response;
    }
}
