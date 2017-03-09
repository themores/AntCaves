package antcaves.com.demo;

import java.io.Serializable;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/8 上午11:06
 */

public class User implements Serializable {
    private int id;
    private String name;

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
}
