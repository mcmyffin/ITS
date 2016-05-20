<?php

/**
 * Created by PhpStorm.
 * User: dima
 * Date: 10.05.16
 * Time: 18:47
 */
class DB
{
    
    public static $DB_HOST = "localhost";
    public static $DB_USERNAME = "app";
    public static $DB_PW = "";
    public static $DB_NAME = "app";

    public static $TABLE_USER_VALID   = "benutzer";
    public static $TABLE_USER_UNVILID = "unvalidbenutzer";
    public static $TABLE_PASS_RES     = "passreset";


    public static $COLUMN_ID    = "id";
    public static $COLUMN_EMAIL = "email";
    public static $COLUMN_PW    = "password";
    public static $COLUMN_IS_VALID = "isValid";

    public static $COLUMN_TOKEN      = "token";
    public static $COLUMN_VALID_TIME = "validtime";
    public static $COLUMN_IS_CREATE  = "isCreate";
    public static $COLUMN_IS_USED    = "isUsed";
    public static $COLUMN_USER_ID    = "userID";


}