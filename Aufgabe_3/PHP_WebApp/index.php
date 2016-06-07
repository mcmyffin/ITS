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
    $querryMap      = querryAsMap();

    
    if( !(isset($_COOKIE[$coockieName]) and $_COOKIE[$coockieName] == true) and $_SERVER["HTTPS"] == "on" and isset($_SERVER["SSL_CLIENT_I_DN_Email"])){

        $email = $_SERVER["SSL_CLIENT_I_DN_Email"];
        if($userClass->isUserExists($email)){
            // user indentifiziert
            setcookie("webapp",true);
            setcookie("email",$email);
            include 'webapp.php';
        }else{
            echo "UNGUELTIGER USER";
            die();
        }
        
    }else if(isset($querryMap["token"]) and isset($querryMap["forgot_pass"]) and sizeof($querryMap) == 2) {
        // password zurueck setzen
        try{
            $userClass->resetPassword($querryMap["token"]);
            echo "<p>Das passwort wurde erfolgreich gesetzt</p>";
            echo "<p>Sie werden in 2 Sec. weitergeleitet</p>";
            header( "refresh:2;url=index.php" );

        }catch (Exception $e){
            echo $e->getMessage();
            header( "refresh:3;url=index.php" );
        }

    }else if(isset($querryMap["token"]) and sizeof($querryMap) == 1){
        // email verifizieren
        try{
            $token = $querryMap["token"];
            $result = $userClass->validateUser($token);
            echo "<p>Die Email $result wurde erfolgreich bestaetigt</p>";
            echo "<p>Sie werden in 2 Sec. weitergeleitet</p>";
            header( "refresh:2;url=index.php" );

        }catch (Exception $e){
            echo "<p>".$e->getMessage()."</p>";
            echo "<p>Sie werden in 5 Sec. weitergeleitet</p>";
            header( "refresh:5;url=index.php" );
        }

    } else if(isset($querryMap["register"]) and sizeof($querryMap) == 1) {
        // registrieren
        echo file_get_contents('register.html');
    }else if(isset($querryMap["forgot_pass"]) and sizeof($querryMap) == 1){
        // passwort vergessen
        echo file_get_contents("forgot.html");
    } else{

        // user angemeldet
        if(isset($_COOKIE[$coockieName]) and $_COOKIE[$coockieName] == true){

            if(isset($querryMap["logout"])){
                setcookie($coockieName,false);
                setcookie("email",null);
                echo "<p>Sie wurden abgemeldet</p>";
                echo "<p>Sie werden in 2 Sec. weitergeleitet</p>";
                header( "refresh:2;url=index.php" );
            }else{
                include 'webapp.php';
            }
        }else{
            // user nicht angemeldet
            echo file_get_contents('login.html');
        }

    }



    function querryAsMap(){
        $array = explode("&",$_SERVER['QUERY_STRING']); // get querry Map
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