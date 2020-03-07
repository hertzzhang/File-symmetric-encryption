package com.zxh.pojo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component(value = "ifile")
public class IFile implements Serializable {
    private String uuid;
    private String size;
    private String type;
    private String filename;
    private String time;
    private String address;

    @Override
    public String toString() {
        return "IFile{" +
                "uuid='" + uuid + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", filename='" + filename + '\'' +
                ", time='" + time + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
