package org.example.demo.commons.utils;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joword
 * @date: 2023/8/31 10:48
 * @version: 0.1.0
 * @description: Mongodb usage
 */
@Component
public class MongoUtil {
    
    public static List<String> criteriaLists(){
        List<String> criteria = new ArrayList<>();
        criteria.add("ba1");
        criteria.add("bs1");
        criteria.add("bs2");
        criteria.add("pm2");
        criteria.add("pvs1");
        criteria.add("pm1");
        criteria.add("pp3");
        criteria.add("bp4");
        criteria.add("pm4");
        criteria.add("bp3");
        criteria.add("bp7");
        criteria.add("ps1");
        criteria.add("pm5");
        criteria.add("pm3");
        criteria.add("bp2");
        criteria.add("pp1");
        criteria.add("bs4");
        criteria.add("pp4");
        criteria.add("bp5");
        criteria.add("ps2");
        criteria.add("pm6");
        criteria.add("ps3");
        criteria.add("bs3");
        criteria.add("ps4");
        return criteria;
    }
    
    public static String loading(JSONObject jsonObject){
//        MongoClient client = MongoClients.create("mongodb://admin:12345@172.16.56.33:28017/aigi?authSource=admin&socketTimeoutMS=120000&connectTimeoutMS=6000");
        MongoClient client = MongoClients.create("mongodb://admin:12345@172.16.56.33:28017/aigi?authSource=admin");
        MongoCollection<Document> collection = client.getDatabase("aigi").getCollection("dev");
        collection.insertOne(new Document(jsonObject));
        return "success";
    }
    
    public static String multiLoading(List<JSONObject> jsonObjects){
        List<Document> documents = new ArrayList<>();
        MongoClient client = MongoClients.create("mongodb://aigi:aigi#20931!@172.19.205.21:28017/aigidb?authSource=admin");
        MongoCollection<Document> collection = client.getDatabase("aigidb").getCollection("decoding");
        for (JSONObject object: jsonObjects){
            Document document = new Document(object);
            documents.add(document);
        }
        collection.insertMany(documents);
        return "success";
    }

}
