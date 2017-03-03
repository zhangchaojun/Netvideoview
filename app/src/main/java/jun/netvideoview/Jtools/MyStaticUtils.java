package jun.netvideoview.Jtools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by cj on 2017/2/10.
 * 常用的静态方法集合，手机的属性之类的
 */

public class MyStaticUtils {
    private static final String TAG = "cj";

    //是否挂载SD卡
    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else return false;
    }

    //获取SD卡总容量
    public static long getSDAllSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();

        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize)/1024/1024; //单位MB
    }

    //获取SD卡可用容量
    public static long getSDFreeSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize)/1024 /1024; //单位MB
    }

    //获取屏幕的宽度px
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        return  widthPixels;
    }

    //获取屏幕的高度px
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        return  heightPixels;
    }

    //创建文件夹的方法
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            Log.e(TAG, "目录" + destDirName + "已经存在" );
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            Log.e(TAG, "创建目录" + destDirName + "成功！" );
            return true;
        } else {
            Log.e(TAG, "创建目录" + destDirName + "失败！" );
            return false;
        }
    }

    //删除文件以及文件夹
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                String[] filePaths = file.list();
                for (String path : filePaths) {
                    deleteFile(filePath + File.separator + path);
                }
                file.delete();
            }
        }
    }

    //获取网络状态
    //返回值 -1：没有网络  1：WIFI网络  2：wap网络  3：net网络
    //需要权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    public static int getNetype(Context context) {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            //无网络
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
//            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
//                netType = 3;
//            } else {
//                netType = 2;
//            }

            netType = 2;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }

    //设置沉浸式状态栏的方法
    public static void immersive(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上
            Window window = ((Activity)context).getWindow();
            //清除导航栏和状态栏的半透明属性
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统的ui
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4版本到5.0以下
            Window window = ((Activity)context).getWindow();
            //将窗口的状态栏设置成透明的
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
