package brianlu.a2a1b;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by eebrian123tw93 on 2017/5/21.
 */

public class RankDocument {
    SharedPreferences sharedPreferences;
    HashMap<String,Long> rank;
    private TreeMap<String,Long> rankSorted;
    String digits;

    RankDocument(Context context, String str, String digits){

        sharedPreferences=context.getSharedPreferences(str, Context.MODE_PRIVATE);
        this.digits=digits;
        rankSorted=new TreeMap<String,Long>();
        rank=new HashMap<String,Long>();
    }
    public void readRank(){
        Set<String>date =sharedPreferences.getStringSet(digits,null)==null?new HashSet<String>():sharedPreferences.getStringSet(digits,null);

        List<String> list=new ArrayList<String>();
        if (date != null) {
            list.addAll(date);
        }
        for (String it:list) {
            rank.put(it,sharedPreferences.getLong(it,0L));
        }
        rankSorted=sortMapByValue(rank);

    }

    public int writeRank(Date date,String grade,String record){

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");

        String dateString=dateFormat.format(date);



        int inWhere=0;
        rank.put(dateString,Long.parseLong(grade));
        rankSorted=sortMapByValue(rank);
        if(rankSorted.size()>=11){
            String key= rankSorted.lastEntry().getKey();
            String value=rankSorted.lastEntry().getValue().toString();
            rank.remove(key);
            sharedPreferences.edit().remove(key).apply();
            sharedPreferences.edit().remove(key+value).apply();
        }
        rankSorted=sortMapByValue(rank);

        for(Map.Entry<String, Long> entry : rankSorted.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            sharedPreferences.edit().putLong(key,value).apply();
            //
           // sharedPreferences.edit().putString(key+value,record).apply();
        }
        sharedPreferences.edit().putStringSet(digits,rankSorted.keySet()).apply();

        for(Map.Entry<String, Long> entry : rankSorted.entrySet()) {
            inWhere++;
            String key = entry.getKey();
            if(key==dateString){
                sharedPreferences.edit().putString(key+grade,record).apply();
                return inWhere;

            }

        }
        inWhere=0;
        return inWhere;
    }

    public HashMap<String, Long> getRank() {
        return rank;
    }

    public TreeMap<String, Long> getRankSorted() {
        return rankSorted;
    }

    private static TreeMap<String,  Long> sortMapByValue(HashMap<String,  Long> map){
        Comparator<String> comparator = new ValueComparator(map);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String,  Long> result = new TreeMap<String,Long>(comparator);
        result.putAll(map);
        return result;
    }
}
class ValueComparator implements Comparator<String>{

    private HashMap<String, Long> map = new HashMap<String,  Long>();

    ValueComparator(HashMap<String, Long> map){
        this.map.putAll(map);
    }

    @Override
    public int compare(String s1, String s2) {
        if(map.get(s1) >= map.get(s2)){
            return 1;
        }else{
            return -1;
        }
    }
}
