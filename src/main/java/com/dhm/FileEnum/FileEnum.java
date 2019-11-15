package com.dhm.FileEnum;

public enum FileEnum {
    PDF("pdf"),
    PPT("ppt"),
    PPTX("pptx"),
    DOC("doc"),
    DOCX("docx"),
    XLS("xls"),
    XLSX("xlsx"),
    XLSM("xlsm"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp"),
    MP3("mp3"),
    WAV("wav"),
    OGG("ogg"),
    GP3("3gp"),
    MPEG4("mpeg4"),
    WEBM("webm"),
    MP4("mp4"),
    M3U8("m3u8"),
    FLV("flv"),
    RAM("ram"),
    SVG("svg"),
    TXT("txt"),
    All("pdf,ppt,pptx,doc,docx,xls,xlsx,jpg,jpeg,png,gif,bmp,mp3,wav,ogg,3gp,mpeg4,webm,mp4,m3u8,flv,ram");

    private String value;

    FileEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FileEnum enValueOf(String value) {
        switch (value) {
            case "doc":
                return DOC;
            case "docx":
                return DOCX;
            case "ppt":
                return PPT;
            case "pptx":
                return PPTX;
            case "pdf":
                return PDF;
            case "xls":
                return XLS;
            case "xlsx":
                return XLSX;
            case "xlsm":
                return XLSM;
            case "bmp":
                return BMP;
            case "jpeg":
                return JPEG;
            case "svg":
                return SVG;
            case "jpg":
                return JPG;
            case "png":
                return PNG;
            case "gif":
                return GIF;
            case "txt":
                return TXT;
            case "all":
                return All;
            default:
                return null;
        }
    }}



