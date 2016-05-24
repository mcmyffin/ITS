<?php
include 'User.php';
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 20.05.16
 * Time: 18:08
 */


if(!empty($_POST)){

    $email = $_POST['email'];
    $password = $_POST['password'];
    $password_rep = $_POST['password_repeat'];

    if($password != $password_rep){
        echo "<p color='red'>Passwort unstimmig</p>";
        redirect(1);
    }

    if(isset($email) and isset($password)){

        $userClass = NEW User();

        try{
            $token = $userClass->createUser($email,$password);
            echo "<p>Registrierung war erfolgreich</p>";
            echo "<p>Wir haben Ihnen eine Email mit dem Bestaetigungslink zugesandt</p>";
            echo "<p>Um die E-Mail zu verifizieren klicken Sie auf den Link in Ihrer Email</p>";
            echo "<br><p><a href='/email'>zum Email Postfach</a></p>";
            echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";
            redirect(10);
            
        }catch (Exception $e){
            $message = $e->getMessage();
            echo "<p>$message</p>";
            echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";
        }
    }
}else{
    redirect(0);
}

function redirect($sec){
    $url = "http://localhost";
    header( "refresh:$sec;url=index.php?register=true" );
    die();
}

?>