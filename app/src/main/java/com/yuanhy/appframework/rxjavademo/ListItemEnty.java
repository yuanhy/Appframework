package com.yuanhy.appframework.rxjavademo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListItemEnty implements Serializable {
   String curPage  ;//1,
    ArrayList<DataItem>        datas  ;//Array[15],
    String        offset  ;//0,
    String        over  ;//false,
    String        pageCount  ;//10,
    String        size  ;//15,
    String   total  ;//138

    public String getCurPage() {
        return curPage;
    }

    public void setCurPage(String curPage) {
        this.curPage = curPage;
    }

    public ArrayList<DataItem> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<DataItem> datas) {
        this.datas = datas;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ListItemEnty{" +
                "curPage='" + curPage + '\'' +
                ", datas=" + datas +
                ", offset='" + offset + '\'' +
                ", over='" + over + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", size='" + size + '\'' +
                ", total='" + total + '\'' +
                '}';
    }

    static class DataItem implements Serializable {


            String apkLink;//  ,
            String author;// digtal ,
            String chapterId;//294,
            String chapterName;// 完整项目 ,
            String collect;//false,
            String courseId;//13,
            String desc;// 一款用来学习安卓的微信小程序 ,
            String envelopePic;// https ;////www.wanandroid.com/blogimgs/58dbab50-0821-40fc-9db4-0f4c10803650.png ,
            String fresh;//false,
            String id;//8400,
            String link;// http ;////www.wanandroid.com/blog/show/2572 ,
            String niceDate;// 2019-05-12 ,
            String origin;//  ,
            String prefix;//  ,
            String projectLink;// https ;////github.com/digtal/wanandroid-program ,
            String publishTime;//1557670418000,
            String superChapterId;//294,
            String superChapterName;// 开源项目主Tab ,
            ArrayList<TagsItem> tags;
            String title;// 玩安卓微信小程序 ,
            String type;//0,
            String userId;//-1,
            String visible;//1,
            String zan;//0

            public String getApkLink () {
                return apkLink;
            }

            public void setApkLink (String apkLink){
                this.apkLink = apkLink;
            }

            public String getAuthor () {
                return author;
            }

            public void setAuthor (String author){
                this.author = author;
            }

            public String getChapterId () {
                return chapterId;
            }

            public void setChapterId (String chapterId){
                this.chapterId = chapterId;
            }

            public String getChapterName () {
                return chapterName;
            }

            public void setChapterName (String chapterName){
                this.chapterName = chapterName;
            }

            public String getCollect () {
                return collect;
            }

            public void setCollect (String collect){
                this.collect = collect;
            }

            public String getCourseId () {
                return courseId;
            }

            public void setCourseId (String courseId){
                this.courseId = courseId;
            }

            public String getDesc () {
                return desc;
            }

            public void setDesc (String desc){
                this.desc = desc;
            }

            public String getEnvelopePic () {
                return envelopePic;
            }

            public void setEnvelopePic (String envelopePic){
                this.envelopePic = envelopePic;
            }

            public String getFresh () {
                return fresh;
            }

            public void setFresh (String fresh){
                this.fresh = fresh;
            }

            public String getId () {
                return id;
            }

            public void setId (String id){
                this.id = id;
            }

            public String getLink () {
                return link;
            }

            public void setLink (String link){
                this.link = link;
            }

            public String getNiceDate () {
                return niceDate;
            }

            public void setNiceDate (String niceDate){
                this.niceDate = niceDate;
            }

            public String getOrigin () {
                return origin;
            }

            public void setOrigin (String origin){
                this.origin = origin;
            }

            public String getPrefix () {
                return prefix;
            }

            public void setPrefix (String prefix){
                this.prefix = prefix;
            }

            public String getProjectLink () {
                return projectLink;
            }

            public void setProjectLink (String projectLink){
                this.projectLink = projectLink;
            }

            public String getPublishTime () {
                return publishTime;
            }

            public void setPublishTime (String publishTime){
                this.publishTime = publishTime;
            }

            public String getSuperChapterId () {
                return superChapterId;
            }

            public void setSuperChapterId (String superChapterId){
                this.superChapterId = superChapterId;
            }

            public String getSuperChapterName () {
                return superChapterName;
            }

            public void setSuperChapterName (String superChapterName){
                this.superChapterName = superChapterName;
            }

            public ArrayList<TagsItem> getTags () {
                return tags;
            }

            public void setTags (ArrayList < TagsItem > tags) {
                this.tags = tags;
            }

            public String getTitle () {
                return title;
            }

            public void setTitle (String title){
                this.title = title;
            }

            public String getType () {
                return type;
            }

            public void setType (String type){
                this.type = type;
            }

            public String getUserId () {
                return userId;
            }

            public void setUserId (String userId){
                this.userId = userId;
            }

            public String getVisible () {
                return visible;
            }

            public void setVisible (String visible){
                this.visible = visible;
            }

            public String getZan () {
                return zan;
            }

            public void setZan (String zan){
                this.zan = zan;
            }

            static class TagsItem implements Serializable {
                String name;// 项目 ,
                String url;// /project/li

                    public String getName () {
                        return name;
                    }

                    public void setName (String name){
                        this.name = name;
                    }

                    public String getUrl () {
                        return url;
                    }

                    public void setUrl (String url){
                        this.url = url;
                    }
                }
            }
        }