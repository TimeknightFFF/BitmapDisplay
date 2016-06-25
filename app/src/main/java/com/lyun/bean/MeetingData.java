package com.lyun.bean;

import java.util.List;

/**
 * Created by sm on 2016/6/25.
 */
public class MeetingData {

    /**
     * code : 0
     * msg : 轮播会议获取成功
     * records : [{"joinNumber":3,"id":10,"startDateStr":"2016-10-26 09:00 星期三","theme":"2016年度海外高层次人群聚会"},{"joinNumber":3,"id":1,"startDateStr":"2016-10-24 08:00 星期一","theme":"2016年度苏州医疗会议"}]
     */

    private int code;
    private String msg;
    /**
     * joinNumber : 3
     * id : 10
     * startDateStr : 2016-10-26 09:00 星期三
     * theme : 2016年度海外高层次人群聚会
     */

    private List<RecordsEntity> records;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setRecords(List<RecordsEntity> records) {
        this.records = records;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<RecordsEntity> getRecords() {
        return records;
    }

    public static class RecordsEntity {
        private int joinNumber;
        private int id;
        private String startDateStr;
        private String theme;

        public void setJoinNumber(int joinNumber) {
            this.joinNumber = joinNumber;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setStartDateStr(String startDateStr) {
            this.startDateStr = startDateStr;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public int getJoinNumber() {
            return joinNumber;
        }

        public int getId() {
            return id;
        }

        public String getStartDateStr() {
            return startDateStr;
        }

        public String getTheme() {
            return theme;
        }
    }
}
