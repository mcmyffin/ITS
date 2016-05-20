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
            echo "<p color='green'>Anmeldung war erfolgreich</p>";
            echo "Um die E-Mail zu verifizieren klicken Sie <a href='index.php?token=$token'>HIER</a>";
        }catch (Exception $e){
            $message = $e->getMessage();
            echo "<p color='red'>$message</p>";
            redirect(5);
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