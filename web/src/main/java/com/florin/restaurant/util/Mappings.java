package com.florin.restaurant.util;

public final class Mappings {


    public static final String PLAY = "play";
    public static final String REDIRECT_PLAY = "redirect:/" + PLAY;
    public static final String USERS="/users";
    public static final String ADMIN = "/admin";
    public static final String LOGIN = "/login";
    public static final String LIST = "/list";
    public static final String MENUS="/menus";
    public static final String MENU="/menu";
    public static final String MENUS_ID="/menus/{id}";
    public static final String ADD="/add";
    public static final String ALL="/all";
    public static final String ORDER="/order";
    public static final String REGISTER = "/register";
    public static final String CHECKOUT="checkout";
    public static final String USERS_ID="/user/{id}";
    public static final String USERS_SAVE ="/users/save";
    public static final String USERS_DELETE_ID="/users/delete/{id}";
    public static final String APPLY_CODE = "apply";

    private Mappings() {}
}
