package com.antcaves;

import android.content.Context;

/**
 * @author liyuan
 * @description 蚁穴整个router控制
 * @email thisuper@qq.com
 * @date 17/3/2 上午10:02
 */

public class AntCavesRouter {
    private final static AntCavesRouter antCavesRouter = new AntCavesRouter();

    private AntCavesRouter() {

    }

    public static AntCavesRouter getInstance() {
        return antCavesRouter;
    }


    public Ant prepare(Context context, String path) {
        try {
            if (context == null)
                throw new Exception("Context is null");
            if (path == null || path.isEmpty())
                throw new Exception("path is null");
            String url[] = path.split("\\?");
            if (url.length > 0 && AntCaves.getRouters().get(url[0]) == null)
                throw new Exception("failure: your path mismatch,please check again");
            Ant ant = new Ant(context, path);
            return ant;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
