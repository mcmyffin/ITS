<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Willkommen</title>
</head>
<body>
<section class="container">
    <?php   $email = $_COOKIE["email"]; ?>
    <div class="login">
        <h1>Willkommen</h1>
        <form method="POST" action="change.php">
            <?php
            echo '<p>Email</p><p><input name="email" type="text" value="'.$email.'"><input type="submit" name="commit" value="Change"></p>';
            ?>
        </form>
        <br>
        <form method="POST" action="change.php">
            <p>Passwort Alt</p> <p><input type="password" name="passwordOld" value="" placeholder="Passwort Alt"></p>
            <p>Passwort Neu</p><p><input type="password" name="password" value="" placeholder="Passwort Neu"></p>
            <input type="submit" name="commit" value="Change"">
        </form>
    </div>

    <div class="login-help">
        <p class="submit"><a href="index.php?logout=true">logout</a></p>
    </div>
</section>
</body>
</html>