package org.botparty.chattraining;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.deploy.util.OrderedHashSet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {

        List<List<String>> conversations = new ArrayList<>();

        String fileName = "AnnabelleResponses.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            // load in file
            StringBuilder builder = new StringBuilder();
            String aux;
            List<String> conversation = new ArrayList<>();
            boolean inConversation = false;
            while ((aux = reader.readLine()) != null) {
                if(aux == null || aux.equals("")) {
                    if(inConversation) {
                        conversations.add(conversation);
                    }
                    inConversation = false;
                    System.out.println("Next conversation");
                } else {
                    if(!inConversation) {
                        conversation = new ArrayList<>();
                    }
                    inConversation = true;
                    conversation.add(aux);
                }
                builder.append(aux);
            }
            String text = builder.toString();
            reader.close();

            conversations.add(conversation);

            List<String> combinedList = trainingList(conversations);
            for(String response : combinedList) {
                System.out.println(response);
            }

            writeToFile("conversations.txt", combinedList);

           // System.out.println(text);
          //  printConversations(conversations);
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }

    static List<String> trainingList(List<List<String>> conversations) {
        // loop through all conversations, making sure that they are even
        // add, even conversation to the total list of
        List<String> combinedList = new ArrayList<>();

        for(List<String> conversation : conversations) {
            int size = conversation.size();
            int lastSize = (size/2) * 2;
            for(int i = 0; i < lastSize; i++) {
                combinedList.add(conversation.get(i));
            }
        }

        return combinedList;
    }

    public static void printConversations(List<List<String>> conversations) {
        for(List<String> conversation : conversations) {
            System.out.println("Conversation");
            for(String part : conversation) {
                System.out.println("\t" + part);
            }
        }
    }

    static void writeToFile(String fileName, List<String> responses) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            // load in file
            StringBuilder builder = new StringBuilder();
            for(String response : responses) {
                builder.append(response).append("\n");
            }
            String text = builder.toString();
            writer.write(text);
            writer.flush();
            writer.close();

            // System.out.println(text);
            //  printConversations(conversations);
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }
}
