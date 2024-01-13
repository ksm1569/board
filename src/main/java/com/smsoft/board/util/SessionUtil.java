package com.smsoft.board.util;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    public SessionUtil() {
    }
    public static String getLoginMemberId(HttpSession httpSession) {
        return (String) httpSession.getAttribute(LOGIN_MEMBER_ID);
    }
    public static void setLoginMemberId(HttpSession httpSession, String id) {
        httpSession.setAttribute(LOGIN_MEMBER_ID, id);
    }
    public static String getLoginAdminId(HttpSession httpSession) {
        return (String) httpSession.getAttribute(LOGIN_ADMIN_ID);
    }
    public static void setLoginAdminId(HttpSession httpSession, String id) {
        httpSession.setAttribute(LOGIN_ADMIN_ID, id);
    }
    public static void clear(HttpSession session) {
        session.invalidate();
    }
}
