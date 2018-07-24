package com.wisdom.beauty.api.extDto;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by Administrator on 2018/4/27.
 */
public class ImageUrl {
    private String imageId;
    private ObjectId _id;
    private List<String> url;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
