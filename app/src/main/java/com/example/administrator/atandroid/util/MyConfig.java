package com.example.administrator.atandroid.util;

/**
 * URL_login 用户登录访问路径
 */

public class MyConfig {
   public static String URL_CharityProject  = "{\"code\":\"0\",\"msg\":\"列表获取成功\",\"result\":[{\"id\":1,\"icon\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496575233173&di=eadf94c69bf2e7b5d4fc66338da1e217&imgtype=0&src=http%3A%2F%2Fimg5.goumin.com%2Fattachments%2Fmonth_1307%2F04%2F946808669.jpg\",\"title\":\"公益领养活动\",\"love\":3024,\"organization\":\"小动物保护协会\",\"time_begin\":\"2016-5-26\",\"time_end\":\"2018-5-26\",\"content\":\"给流浪的它，一个温暖的家\"},{\"id\":2,\"icon\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496575233174&di=514ff3d1011392db982cd28277ef5314&imgtype=0&src=http%3A%2F%2Fpic26.nipic.com%2F20130126%2F9469730_170939006377_2.jpg\",\"title\":\"野生大象保护\",\"love\":305,\"organization\":\"野生动物协会\",\"time_begin\":\"2015-5-26\",\"time_end\":\"2028-5-26\",\"content\":\"关爱保护动物，给他们一份生机\"},{\"id\":3,\"icon\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496576034421&di=742d6b92744f3d55d436e687c8d85dfe&imgtype=0&src=http%3A%2F%2Fs13.sinaimg.cn%2Fbmiddle%2F001U55OTzy6IFMQiuCgac%26690\",\"title\":\"流浪动物关爱\",\"love\":805,\"organization\":\"重庆财富购物中心\",\"time_begin\":\"2017-5-26\",\"time_end\":\"2018-5-26\",\"content\":\"关爱它，就不要让它流浪\"}]}";
 //   public static String URL_charity_project_xiangxi  = "http://192.168.1.109:8080/Test2/servlet/StrategyServlet?method=getstrategyList";
    private String adress = "118.89.141.172";
    private String servlet;
    private String method;

    public MyConfig(String servlet, String method) {
        this.servlet = servlet;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getServlet() {
        return servlet;
    }

    public void setServlet(String servlet) {
        this.servlet = servlet;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getURL() {
        return"http://"+ adress + ":8080/ATService/servlet/" + servlet + "?" + "method=" + method + "&message=";
    }
}
