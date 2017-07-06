package com.shrimpcolo.johnnytam.idouban.beans;

import java.io.Serializable;

/**
 * Created by Johnny Tam on 2017/7/5.
 */

public class Blog implements Serializable{

    /**
     * id : 0
     * name : RecyclerView的重构之路(一)
     * url : http://www.jianshu.com/p/99cd83778373
     */

    private int id;
    private String name;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
