package ninhduynhat.com.haui_android_n16_manager_account.Model;

import java.io.Serializable;
public class ListTypePlan implements Serializable {
    private String imgpath;
    private String title;
    public ListTypePlan(String imgpath, String title ) {
        this.imgpath = imgpath;
        this.title = title;

    }
    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
