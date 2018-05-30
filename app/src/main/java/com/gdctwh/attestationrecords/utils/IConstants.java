package com.gdctwh.attestationrecords.utils;

/**
 * Created by CokaZhang on 2018/3/23 0005.
 */

public interface IConstants {



    String ISFIRSTIN = "isFirstIn"; //判断是否是第一次进入的key
    String SESSION = "session";
    String U_ACCOUNT = "u_account";
    String U_ID = "u_id";
    String U_NAME = "u_name";
    String U_HEAD = "u_head";
    String COUNTRY_CODE = "86";

    int CATEGROY_BANNER = 1;
    int CATEGROY_CTNEWS = 2;
    int CATEGROY_CULTURENEWS = 3;
    int CATEGROY_AUTHENTICAT = 4;
    int CATEGROY_COLLECT_ACTION = 5;


    interface FRAGMENTTAG {
        String FRAGMENT_NEWS = "news";
        String FRAGMENT_AUTHENTICATE = "authenticate";
        String FRAGMENT_ART = "art";
        String FRAGMENT_MINE = "mine";
    }


    String BASE_URL = "http://www.maxsourcemedia.com/";
    String TEXT_SIZE = "text_size";

    interface ACTION_STRING {
        String RESET_PSW = "reset_psw";
        String USER_CLAUSE = "user_clause";
        String SET_PSW = "set_psw";
    }

    interface NEWS_DATA {
        String NEWS_ID = "news_id";
        String NEWS_DATE = "news_date";
    }



    interface COMMENT_AND_COLLECT{
        String COMMENT_DATA = "get_pl";
        String COMMENT_NUM = "get_pl_info";
        String COLLECT_DATA = "get_sc";
        String COLLECT_NUM = "get_sc_info";
    }
}
