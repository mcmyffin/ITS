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
            echo "<p>Email aenderung akzeptiert</p>".
                 "<p>Bitte verifizieren Sie diese <a href='index.php?token=$token'>HIER</a></p>";

        }catch (Exception $e) {
            $message = $e->getMessage();
            echo "<p>$message</p>";
            redirect(2);
        }

        // "https://sadim.informatik.haw-hamburg.de/index.php?token=meintoken";
    // Wenn Passwort aendern
    }else if(isset($passwordnew)){

        try{
            $email = $_COOKIE['email'];
            (NEW User())->changePassword($email,$passwordold,$passwordnew);
            echo "Ihr passwort wurde erfolgreich geaendert";
            redirect(2);

        }catch (Exception $e){
            $message = $e->getMessage();
            echo "<p>$message</p>";
            redirect(2);
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