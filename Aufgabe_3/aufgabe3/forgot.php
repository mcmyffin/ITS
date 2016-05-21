<?php
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 21.05.16
 * Time: 18:57
 */

if(!empty($_POST)){

    $email = $_POST['email'];

    if(isset($email)){

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
    header( "refresh:$sec;url=index.php?register=true" );
    die();
}

?>