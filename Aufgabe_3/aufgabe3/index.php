<?php
include 'User.php';

/**
 * Created by PhpStorm.
 * User: dima
 * Date: 10.05.16
 * Time: 17:24
 */

    $userClass = NEW User();
    $coockieName = "webapp";
    $querryMap      = querryAsMap(explode("&",$_SERVER['QUERY_STRING'])); // get querry Map

    



    if(isset($querryMap["token"])){
        // email verifizieren
        try{
            $token = $querryMap["token"];
            echo "TOKEN : ".print_r($token,true)." <br>";
            $result = $userClass->validateUser($token);
            print ("Die Email $result wurde erfolgreich bestätigt");
        }catch (Exception $e){
            echo $e->getMessage();
        }

    } else if(isset($querryMap["register"])) {
        // registrieren
        echo file_get_contents('register.html');
    }else if(isset($querryMap["forgot_pass"])){
        // passwort vergessen
        echo "TODO PASSWORT VERGESSEN";
    } else{

        // user angemeldet
        if(isset($_COOKIE[$coockieName]) && $_COOKIE[$coockieName] == true){

            if(isset($querryMap["logout"])){
                setcookie($coockieName,false);
                setcookie("email",null);
                echo "<p>Sie wurden abgemeldet</p>";
                header( "refresh:2;url=index.php" );
            }else{
                include 'webapp.php';
            }
        }else{
            // user nicht angemeldet
            echo file_get_contents('login.html');
        }

    }



    function querryAsMap(array $array){
        $arr = array();
        foreach ($array as $x){
            $xArr = explode("=",$x);
            array_push($arr[$xArr[0]] = $xArr[1]);
        }
        return $arr;
    }



//    $sql = 'SELECT * FROM '.DB::$TABLE_USER_VALID;
//    $rows = Sql::exe($sql);
//    var_dump($rows);


//    $userClass = NEW User();
//    $boolVar = $userClass->login("dada@dada.de","qweasd");
//    $boolVar = $userClass->login("dada@dada.com","123456111");
//    $boolVar = $userClass->createUser("dada@dada.com","123456111");
//    $boolVar = $userClass->createUser("dada@dada.de","qweasd");
//
//    print("Erstelle User: ". ($boolVar ? "True" : "False"));
//    print("login User: ". ($boolVar ? "True" : "False"));

?>