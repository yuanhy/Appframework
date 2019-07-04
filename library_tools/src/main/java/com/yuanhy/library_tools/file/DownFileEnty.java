package com.yuanhy.library_tools.file;

import java.io.Serializable;

public class DownFileEnty implements Serializable {
    String url;
    String path;
    String start;
    String end;
    long sizi;
    String threadId;

    public void setSizi(long sizi) {
        this.sizi = sizi;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
