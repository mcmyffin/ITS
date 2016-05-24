<?php

include '../Util/DB.php';
include '../Util/Sql.php';
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 22.05.16
 * Time: 14:28
 */

$querryMap = querryAsMap();
Sql::connect(DB::$DB_HOST,DB::$DB_USERNAME,DB::$DB_PW, DB::$DB_NAME);



echo "<html>
     <head><title>Ihr Email Service</title></head>
     <body>
     <form method=\"GET\" action=\"index.php\">
     <input type='text' name='email' value='".urldecode($querryMap['email'])."'>
     <input type='submit' value='Anzeigen'>
     </form>";

echo "<h9>========================</h9><br>";
if(isset($querryMap['email'])){
    try{
        $email = urldecode($querryMap['email']);
        $sql_getUser = "SELECT * FROM `".DB::$TABLE_USER_VALID."` WHERE `".DB::$COLUMN_EMAIL."` = '$email';";
        $response_getUser = Sql::exe($sql_getUser);

        if(empty($response_getUser)) throw new Exception("Email-Adresse nicht gefunden");

        $userID = $response_getUser[0][DB::$COLUMN_ID];

        $sql_getList2 = "SELECT `".DB::$COLUMN_TOKEN."` FROM `".DB::$TABLE_PASS_RES."` WHERE `".DB::$COLUMN_USER_ID."` = '$userID' ORDER BY `".DB::$COLUMN_VALID_TIME."` ASC";
        $response_getList2 = Sql::exe($sql_getList2);

        echo "<head><title>$email</title></head>";
        // passwort Wiederherstellen
        $i = 1;
        echo "<h2>Passwort Wiederherstellen[".sizeof($response_getList2)."]</h2>";
        foreach ($response_getList2 as $item){
            $token = $item[DB::$COLUMN_TOKEN];
            echo "<p><a href='../index.php?token=$token&forgot_pass=true'>Link_$i</a></p>";
            $i = $i+1;
        }

        $sql_getList1 = "SELECT `".DB::$COLUMN_TOKEN."` FROM `".DB::$TABLE_USER_UNVILID."` WHERE `".DB::$COLUMN_USER_ID."` = '$userID' ORDER BY `".DB::$COLUMN_VALID_TIME."` ASC";
        $response_getList1 = Sql::exe($sql_getList1);

        // email verifizieren
        $i = 0;
        echo "<h2>Email verifizieren[".sizeof($response_getList1)."]</h2>";
        foreach ($response_getList1 as $item) {
            $token = $item[DB::$COLUMN_TOKEN];
            echo "<p><a href='../index.php?token=$token'>Link_$i</a></p>";
            $i = $i+1;
        }


    }catch (RuntimeException $e){
        // DEBUG
        echo $e;
    }catch (Exception $e){
        echo $e->getMessage();
        die();
    }

}

echo "</body>
     </html>";


function querryAsMap(){
    $array = explode("&",$_SERVER['QUERY_STRING']); // get querry Map
    $arr = array();
    foreach ($array as $x){
        $xArr = explode("=",$x);
        array_push($arr[$xArr[0]] = $xArr[1]);
    }
    return $arr;
}

?>