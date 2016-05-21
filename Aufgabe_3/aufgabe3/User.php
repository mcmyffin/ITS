<?php

include 'Util/Time.php';
include 'Util/TokenService.php';
include 'Util/DB.php';
include 'Util/Sql.php';

/**
 * Created by PhpStorm.
 * User: dima
 * Date: 10.05.16
 * Time: 18:18
 */
    class User
    {


        /**
         * User constructor.
         */
        public function __construct()
        {
            Sql::connect(DB::$DB_HOST,DB::$DB_USERNAME,DB::$DB_PW, DB::$DB_NAME);
        }

        function login($email, $password)
        {
            if(!Sql::isConnected()) return false;

            try{

                $sql_getUser = "SELECT * FROM ".DB::$TABLE_USER_VALID." WHERE ".DB::$COLUMN_EMAIL." = '$email'";
                $getUserResponse = Sql::exe($sql_getUser);

                // email nicht gefunden
                if(sizeof($getUserResponse) != 1) return false;

                $isValid = $getUserResponse[0][DB::$COLUMN_IS_VALID];
                if(!$isValid) throw new UserNotValidException();

                $userID = $getUserResponse[0][DB::$COLUMN_ID];
                $email_DB = $getUserResponse[0][DB::$COLUMN_EMAIL];
                $pw_DB = $getUserResponse[0][DB::$COLUMN_PW];
                $pwHash = md5($password);
                $pwHash = md5($userID.$pwHash);

//                $sql = "SELECT ". DB::$COLUMN_ID ." FROM ". DB::$TABLE_USER_VALID ." WHERE ". DB::$COLUMN_EMAIL ." = '$email' AND ". DB::$COLUMN_PW ." = '$pwHash' ";
//                $raws = Sql::exe($sql);

//                $data = $raws[0];
//                $db_email = $data[DB::$COLUMN_EMAIL];

                return ($email == $email_DB) and ($pwHash == $pw_DB);

            }catch (RuntimeException $e){
                print $e;
                die();
            }
        }

        function createUser($email, $password)
        {

            try{
                $sql_isUserExists = "SELECT * FROM ".DB::$TABLE_USER_VALID." WHERE ".DB::$COLUMN_EMAIL." = '$email'";
                $response = Sql::exe($sql_isUserExists);
                if(!empty($response)) throw new Exception("Email bereits an einen User gebunden");

                // hash password
                $pwHsh = md5($password);

                // create User
                $sql_createUser = "INSERT INTO ".DB::$TABLE_USER_VALID." (`".DB::$COLUMN_EMAIL."`, `".DB::$COLUMN_PW."`, `".DB::$COLUMN_IS_VALID."`) VALUES ('$email', '$pwHsh','0')";
                Sql::exe($sql_createUser);

                // get User with ID
                $sql_getUser = "SELECT * FROM `".DB::$TABLE_USER_VALID."` WHERE `".DB::$COLUMN_EMAIL."`= '$email' ";
                $getUserResponse = Sql::exe($sql_getUser);

                // hash password with ID
                $userID = $getUserResponse[0][DB::$COLUMN_ID];
                $pwHsh = md5($userID.$pwHsh);

                // update password
                $sql_updateUser = "UPDATE ". DB::$TABLE_USER_VALID." SET `".DB::$COLUMN_PW."` = '$pwHsh' WHERE `".DB::$COLUMN_ID."` = '$userID'";
                Sql::exe($sql_updateUser);


                // create Token
                $token = TokenService::createToken($userID);
                $validtime = (NEW Time())->getTimeInSecPlus30min();
                $sql_createToken = "INSERT INTO `".DB::$TABLE_USER_UNVILID."`(`token`, `validtime`, `email`, `userID`, `isCreate`, `isUsed`) VALUES ('$token','$validtime','$email','$userID','1','0')";
                Sql::exe($sql_createToken);

                return $token;

            }catch (RuntimeException $e){
                print ($e);
                die();
            }
        }

        public function changePassword($email,$passwordOld, $passwordNew)
        {

            $isLoginSuccess = $this -> login($email,$passwordOld);
            if(!$isLoginSuccess) throw new Exception("Anmelde Daten waren nicht korrekt");

            try {
                // get User
                $sql_getUser = "SELECT * FROM " . DB::$TABLE_USER_VALID . " WHERE " . DB::$COLUMN_EMAIL . " = '$email'";
                $getUserResponse = Sql::exe($sql_getUser);

                $userID = $getUserResponse[0][DB::$COLUMN_ID];
                $pwHsh = md5($passwordNew);
                $pwHsh = md5($userID . $pwHsh);

                $sql_updateUser = "UPDATE " . DB::$TABLE_USER_VALID . " SET `" . DB::$COLUMN_PW . "` = '$pwHsh' WHERE `" . DB::$COLUMN_ID . "` = '$userID'";
                Sql::exe($sql_updateUser);

            } catch (RuntimeException $ex){
                print($ex);
                die();
            }
        }

        private static function isEmpty($raw)
        {
            if(!is_array($raw)) return false;
            return sizeof($raw) == 0;
        }

        public static function validateUser($token)
        {
            try{
                $sql_getToken = "SELECT * FROM `" . DB::$TABLE_USER_UNVILID . "` WHERE `" . DB::$COLUMN_TOKEN . "` = '$token'";
                $tokenResponse = Sql::exe($sql_getToken);

                if (empty($tokenResponse)) throw new Exception("Token nicht gefunden");
                if ($tokenResponse[0][DB::$COLUMN_IS_USED]) throw new Exception("Token bereits eingeloest");

                $validTime = $tokenResponse[0][DB::$COLUMN_VALID_TIME];
                $nowTime = (NEW Time())->getTimeInSec();

                if ($nowTime > $validTime) throw new Exception("Token ist abgelaufen");

                // set TOKEN USED
                $sql_setTokenUsed = "UPDATE `" . DB::$TABLE_USER_UNVILID . "` SET `" . DB::$COLUMN_IS_USED . "`= '1' WHERE `" . DB::$COLUMN_TOKEN . "` = '$token'";
                $sql_responseTokenUsed = Sql::exe($sql_setTokenUsed);
                echo "TOKEN UPDATE: ".print_r($sql_responseTokenUsed,true);

                // UPDATE USER
                $isCreate = $tokenResponse[0][DB::$COLUMN_IS_CREATE];
                if ($isCreate == "1" || $isCreate) {
                    // UPDATE VALID BOOLEAN
                    $userID = $tokenResponse[0][DB::$COLUMN_USER_ID];
                    $email = $tokenResponse[0][DB::$COLUMN_EMAIL];
                    $sql_setValid = "UPDATE `" . DB::$TABLE_USER_VALID . "` SET `" . DB::$COLUMN_IS_VALID . "`= '1' WHERE `" . DB::$COLUMN_ID . "` = '$userID'";

                    $sql_responseUpdateUser = Sql::exe($sql_setValid);
                    echo "UPDATE USER: ".print_r($sql_responseUpdateUser,true);
                    return $email;

                } else {

                    // UPDATE EMAIL AND VALID BOOLEAN
                    $userID = $tokenResponse[0][DB::$COLUMN_USER_ID];
                    $email = $tokenResponse[0][DB::$COLUMN_EMAIL];
                    $sql_setValid = "UPDATE `" . DB::$TABLE_USER_VALID . "` SET `" . DB::$COLUMN_EMAIL . "` = '$email' , `" . DB::$COLUMN_IS_VALID . "`= '1' WHERE `" . DB::$COLUMN_ID . "` = '$userID'";

                    $sql_responseUpdateUser = Sql::exe($sql_setValid);
                    echo "UPDATE USER: ".print_r($sql_responseUpdateUser,true);
                    return $email;
                }
            }catch (RuntimeException $e){
                echo $e->getMessage();
                die();
            }
        }

        public function changeEmail($emailOld, $emailNew)
        {
            try{
                // is Email Exists
                $sql_getUser = "SELECT * FROM " . DB::$TABLE_USER_VALID . " WHERE " . DB::$COLUMN_EMAIL . " = '$emailNew'";
                $sql_isExists = Sql::exe($sql_getUser);
                if(!empty($sql_isExists)) throw new Exception("Email wird bereits verwendet");

                // get User
                $sql_getUser = "SELECT * FROM " . DB::$TABLE_USER_VALID . " WHERE " . DB::$COLUMN_EMAIL . " = '$emailOld'";
                $getUserResponse = Sql::exe($sql_getUser);

                $userID = $getUserResponse[0][DB::$COLUMN_ID];

                // create token
                $token = TokenService::createToken($userID);
                $validtime = (NEW Time())->getTimeInSecPlus30min();
                $sql_createToken = "INSERT INTO `".DB::$TABLE_USER_UNVILID."`(`token`, `validtime`, `email`, `userID`, `isCreate`, `isUsed`) VALUES ('$token','$validtime','$emailNew','$userID','0','0')";
                Sql::exe($sql_createToken);

                return $token;

            }catch (RuntimeException $e){
                echo $e->getMessage();
                die();
            }
        }
    }
?>