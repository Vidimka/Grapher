package ru.vidimka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ReplaceableArrayList<T> extends ArrayList<T>{
    public ReplaceableArrayList(List<T> list){
        this.addAll(list);
    }
    public ReplaceableArrayList(String s){
        List<String> list = Arrays.asList(s.split(""));
        this.addAll((Collection<? extends T>) list);
    }

    public void replaceByIndex(int index, T replacer){
        remove(index);
        add(index, replacer);
    }
    public void replaceRowByIndex(int beginIndex, int endIndex, T replacer){
        for(int i=beginIndex; i<=endIndex; ++i){
            replaceByIndex(i, replacer);
        }
    }
    public void replace(T element, T replacer){
        int index = indexOf(element);
        remove(element);
        add(index, replacer);
    }

    @Override
    public String toString(){
        String s = "";
        for(T elem : this){
            s += elem;
        }
        return s;
    }
}
