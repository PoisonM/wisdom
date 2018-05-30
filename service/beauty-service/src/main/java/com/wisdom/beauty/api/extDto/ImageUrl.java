package com.wisdom.beauty.api.extDto;

import java.util.List;

/**
 * Created by Administrator on 2018/4/27.
 */
public class ImageUrl {
    private String imageId;
    private List<String> url;

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
