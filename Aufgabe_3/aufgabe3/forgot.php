<?php

include 'User.php';
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 21.05.16
 * Time: 18:57
 */

if(!empty($_POST)){

    $email = $_POST['email'];
    $password = $_POST['password'];

    if(isset($email) and isset($password)){

        $userClass = NEW User();

        $token = $userClass->requestResetPassword($email,$password);
        echo "<p>Der Auftrag wurde erfolgreich verarbeitet</p>";
        echo "<p>Ein Link wurde an die angegebene Email-Adresse zugesandt</p>";
        echo "<p>Um Ihr passwort zu aendern klicken Sie auf den Link in Ihrer Email</p>";
        echo "<br><p><a href='/email'>zum Email Postfach</a></p>";
        echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";
    }
}else{
    redirect(0);
}

function redirect($sec){
    header( "refresh:$sec;url=index.php?register=true" );
    die();
}

?>