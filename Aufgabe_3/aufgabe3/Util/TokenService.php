<?php

/**
 * Created by PhpStorm.
 * User: dima
 * Date: 19.05.16
 * Time: 17:42
 */
class TokenService
{
    public static function createToken($param){
        return md5(time()).md5($param.rand(0,9));
    }

}