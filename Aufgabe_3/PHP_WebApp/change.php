<?php

include 'User.php';
/**
 * Created by PhpStorm.
 * User: dima
 * Date: 21.05.16
 * Time: 14:42
 */

if(!empty($_POST)){

    $email = $_POST['email'];
    $passwordnew = $_POST['password'];
    $passwordold = $_POST['passwordOld'];

    // Wenn Email aendern
    if(isset($email)){

        try {
            $emailOld = $_COOKIE['email'];
            $token = (NEW User())->changeEmail($emailOld,$email);
            echo "<p>Email &auml;nderung akzeptiert</p>";
            echo "<p>Ein Link wurde an die angegebene Email-Adresse zugesandt</p>";
            echo "<p>Um Ihre Email-Adresse zu &auml;ndern klicken Sie auf den Link in Ihrer Email</p>";
            echo "<br><p><a href='/email'>zum Email Postfach</a></p>";
            echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";

        }catch (Exception $e) {
            $message = $e->getMessage();
            echo "<p>$message</p>";
            echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";
            redirect(60);
        }

        // "https://sadim.informatik.haw-hamburg.de/index.php?token=meintoken";
    // Wenn Passwort aendern
    }else if(isset($passwordnew)){

        try{
            $email = $_COOKIE['email'];
            (NEW User())->changePassword($email,$passwordold,$passwordnew);
            echo "Ihr passwort wurde erfolgreich geaendert";
            echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";
            redirect(10);

        }catch (Exception $e){
            $message = $e->getMessage();
            echo "<p>$message</p>";
            echo "<p><a href='index.php'>Zur&uuml;ck</a> </p>";
            redirect(60);
        }
    }else{
        redirect(0);
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