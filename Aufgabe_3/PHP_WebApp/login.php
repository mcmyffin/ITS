<?php

include 'User.php';
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 20.05.16
 * Time: 12:04
 */

if(!empty($_POST)){


    $email = $_POST['email'];
    $password = $_POST['password'];

    if(isset($email) and isset($password)){

        try{
            $userClass = NEW User();
            $isSuccess = $userClass->login($email,$password);

            if($isSuccess){
                setcookie("webapp",true);
                setcookie("email",$email);
                echo "Anmeldung war erfolgreich";
                redirect(1);
            }else{
                echo "<p>Benutzername oder Passwort nicht gefunden/falsch</p>";
                echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";
                redirect(10);
            }
        }catch (Exception $e){
            $message = $e->getMessage();
            echo $message;
            redirect(2);
        }


    }
}else{
    redirect(0);
}

function redirect($sec){
//    sleep($sec);
    $url = "http://localhost";
    header( "refresh:$sec;url=index.php" );
    die();
}

?>