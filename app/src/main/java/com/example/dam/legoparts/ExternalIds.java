package com.example.dam.legoparts;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExternalIds {

    @SerializedName("BrickOwl")
    @Expose
    private List<String> brickOwl = null;

    public List<String> getBrickOwl() {
        return brickOwl;
    }

    public void setBrickOwl(List<String> brickOwl) {
        this.brickOwl = brickOwl;
    }

}