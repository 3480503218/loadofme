package com.dhm.ce;

import java.io.File;
import java.util.HashSet;

public class setFileReleaseNames {
    private void setFileReleaseNames(String mFileName) {
        File f = new File("/Users/dhm/000/000/");
        if (f.exists()) {//判断路径是否存在
            File[] files = f.listFiles();
            HashSet<String> hashSet = new HashSet<>();
            for (File file : files) {
                if (file.isFile()) {
                    String name = file.getName();
                    hashSet.add(name);
                }
            }
            int a = 1;
            while (true) {
                if (a != 1) {
                    String[] split = mFileName.split("\\.");
                    mFileName = split[0] + "(" + a + ")." + split[1];
                    System.out.println(mFileName);
                }
                if (!hashSet.contains(mFileName)) {
                    String fileReleaseName = mFileName;
                    break;
                } else {
                    a++;
                }

            }
        }
    }

    public static void main(String[] args) {
        setFileReleaseNames setFileReleaseNames=new setFileReleaseNames();
        setFileReleaseNames.setFileReleaseNames("sss(2).png");
    }
}
