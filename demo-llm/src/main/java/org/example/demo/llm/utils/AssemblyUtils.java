package org.example.demo.llm.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Joword
 * @date: 2023/12/5 12:40
 * @version: 0.1.0
 * @description: parser JSON
 */
public class AssemblyUtils {
    
    private static List<String> wordLists(){
        List<String> lists = new ArrayList<>();
        lists.add("paper");
        lists.add("publication");
        lists.add("text");
        lists.add("information");
        lists.add("literature");
        lists.add("文献");
        lists.add("文章");
        lists.add("作者");
        lists.add("标题");
        lists.add("摘要");
        lists.add("介绍");
        return lists;
    }
    
    private static StringBuffer toRoleTemplate(){
        StringBuffer role = new StringBuffer();
        role.append("I want you act as a literature information AI assistant of BGI(华大基因). I will give you a literature(text/paper/information/publication) that need you summarizing, translating and understanding the information.")
            .append("First of all, when you read the following statements:\n")
            .append("......\n")
            .append("(1):hello\n")
            .append("(2):hi\n")
            .append("(3):nihao\n")
            .append("(4):who are u\n")
            .append("(5):你是谁\n")
            .append("(6):你好\n")
            .append("(7):usage\n")
            .append("(8):how can i use you\n")
            .append("......\n")
            .append("Your answer is that I am a literature information AI assistant of BGI(华大基因). To chat the paper, to summarize the paper or to translate the paper using concise statements. what can I do for you? in different languages.");
        return role;
    }
    
    private static StringBuffer toUseTemplate(){
        StringBuffer useTemplate = new StringBuffer();
        useTemplate.append("");
        return useTemplate;
    }
    
    private static StringBuffer toSummaryTemplate(){
        StringBuffer template = new StringBuffer();
        template.append("I need your help to read and summarize the following questions:\n")
                .append("Read the <Abstract>, mark the keywords of this text (use English or Chinese). Return string not a list.\n")
                .append("Read the <Abstract>, summarize according to the following four points.\n")
                .append("(1):What is the research background of this text?\n")
                .append("(2):What are the past methods? What are the problems with them? Is the approach well motivated?\n")
                .append("(3):What is the research methodology proposed in this information?\n")
                .append("(4):On what task and what performance is achieved by the methods in this information? Can the performance support their goals?\n")
                .append("Read the <Method> information, describe in detail the methodological idea of this text. For example, its steps are.\n")
                .append("(1):...\n").append("(2):...\n").append("(3):...\n")
                .append(".......\n")
                .append("Read the <Method> and <Conclusion> information, summarize the following questions:").append("\n")
                .append("(1):What is the significance of this piece of work?\n")
                .append("(2):Summarize the strengths and weaknesses of this text in three dimensions: innovation point, performance, and workload.\n")
                .append(".......\n")
                .append("Output a json object that contains the following keys: title, keywords, method, result.\n")
                .append("Statements as concise and academic as possible, do not have too much repetitive information. Only output json object itself, do not output the words:")
                .append("Output JSON\n").append("Output JSON:<json with title,keywords,method:(1),(2),(3) and result:(1),(2)>\n\n")
                .append("Text: {0}");
        return template;
    }
    
    private static StringBuffer toCheckSummaryTemplate(){
        StringBuffer checkSummary = new StringBuffer();
        checkSummary.append("You act as a natural language processing interpreter in English or Chinese.\n")
                    .append("first, You need to get the meaning of the text: {1}.\n")
                    .append("second, the following questions is some examples from user question, to understand the following question.\n")
                    .append(".......\n")
                    .append("(1):To sum up the paper\n")
                    .append("(2):Summarize the text\n")
                    .append("(3):Give me a conclusion\n")
                    .append("(4):Summary this article\n")
                    .append("(5):总结这篇文章\n")
                    .append(".......\n")
                    .append("third, the user question: {0}\n")
                    .append("fourth, translate the user question in English.\n")
                    .append("fifth, compare the meaning of user question and the list question.\n")
                    .append("sixth, the comparative result only use positive, negative.\n")
                    .append("seventh, Output the comparative result in English.");
        return checkSummary;
                
                
    }
    
    private static StringBuffer toCheckTemplate(){
        StringBuffer check = new StringBuffer();
        check.append("You act as a natural language processing interpreter in English or Chinese.\n")
             .append("You need to get the meaning of the text: {1}.\n")
             .append("second, the following questions is some examples from user question, to understand the following question.\n")
             .append(".......\n")
             .append("Read the <Paper>, <Publication>, <Text>, <Literature> or <information> summarize the text.\n")
             .append("(1):What is the main result of this paper?\n")
             .append("(2):What is the main conclusion of this publication?\n")
             .append("(3):What is the main meaning of this text?\n")
             .append("(3):What is the main result of this literature?\n")
             .append("(4):What is the main consequence of this paper?\n")
             .append("(5):who is the author of this paper?\n")
             .append("(6):what is the title of this publication?\n")
             .append("(7):what is the abstract of this text?\n")
             .append("(8):what is the introduction of this literature?\n")
             .append("(9):what is the Methods of this literature?\n")
             .append("(10):what is the discussion of this literature?\n")
             .append("(11):Summarize the paper\n")
             .append("(12):To sum up the literature\n")
             .append("(13):Give me a conclusion\n")
             .append(".......\n")
             .append("third, the user question: {0}\n")
             .append("fourth, translate the question in English.\n")
             .append("fifth, compare the meaning of user question and the list question.\n")
             .append("sixth, the comparative result only is positive, negative.\n")
             .append("seventh, Output the comparative result in English.");
        return check;
    }
    private static StringBuffer toChatTemplate(){
        StringBuffer chat = new StringBuffer();
        chat.append("To read the following text, answer the question: {0}\n")
            .append("Text: {1}\n");
        return chat;
    }
    
    private static StringBuffer toFullText(){
        JSONObject jsonObject = null;
        StringBuffer publication = null;
        try {
            File file = new File("D:\\BGIproject\\aigi\\aigi-manager\\src\\main\\java\\com\\bgi\\aigi\\manager\\file\\PMC3525152.json");
            Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            int count=0;
            StringBuffer fileString = new StringBuffer();
            while ((count = reader.read())!=-1){
                fileString.append((char) count);
            }
            jsonObject = JSONObject.parseObject(fileString.toString());
            String title = jsonObject.getString("Title");
            String introduction = JSONObject.parseArray(jsonObject.getJSONObject("sections").getString("Introduction"),String.class).stream().map(x->JSONObject.parseObject(x).getString("text")).collect(Collectors.joining());
            String method = JSONObject.parseArray(jsonObject.getJSONObject("sections").getString("Methods"),String.class).stream().map(x->JSONObject.parseObject(x).getString("text")).collect(Collectors.joining());
            String result = JSONObject.parseArray(jsonObject.getJSONObject("sections").getString("Results"),String.class).stream().map(x->JSONObject.parseObject(x).getString("text")).collect(Collectors.joining());
            String discussion = JSONObject.parseArray(jsonObject.getJSONObject("sections").getString("Discussion"),String.class).stream().map(x->JSONObject.parseObject(x).getString("text")).collect(Collectors.joining());
            publication = new StringBuffer();
            publication.append("Title\n").append(title).append("\n")
                    .append("Introduction\n").append(introduction).append("\n")
                    .append("Method\n").append(method).append("\n")
                    .append("Conclusion\n").append(result).append("\n")
                    .append("Discussion\n").append(discussion).append("\n");
            return publication;
        } catch (IOException e) {
            e.printStackTrace();
            publication = new StringBuffer();
            return publication.append("Assembly exception");
        }
    }
    
    public static String toRole(){
        StringBuffer role = AssemblyUtils.toRoleTemplate();
        return role.toString();
    }
    
    public static String toSummary(){
        StringBuffer paper = AssemblyUtils.toSummaryTemplate();
        StringBuffer text = AssemblyUtils.toFullText();
        return MessageFormat.format(paper.toString(),text.toString());
    }
    
    public static String toCheck(String question){
        StringBuffer text = AssemblyUtils.toFullText();
        StringBuffer checkTemplate = AssemblyUtils.toCheckTemplate();
        return MessageFormat.format(checkTemplate.toString(), question, text.toString());
    }

    public static String toChat(String question){
        StringBuffer text = AssemblyUtils.toFullText();
        StringBuffer chat = AssemblyUtils.toChatTemplate();
        return MessageFormat.format(chat.toString(), question, text.toString());
    }
    
    public static String toCheckSummary(String question){
        StringBuffer text = AssemblyUtils.toFullText();
        StringBuffer checkSum = AssemblyUtils.toCheckSummaryTemplate();
        return MessageFormat.format(checkSum.toString(), question, text.toString());
    }
}
