package org.reallylastone.finnhub;

import java.io.Serializable;
import java.util.List;

public class FinnhubResponse implements Serializable {
    private static final long serialVersionUID = 65505049769316018L;
    private List<Entry> data;
    private String type;

    public List<Entry> getData() {
        return data;
    }

    public void setData(List<Entry> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
