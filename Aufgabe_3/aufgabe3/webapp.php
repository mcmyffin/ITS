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
            echo '<p>Email    <input type="text" value="'.$email.'"></p><p><input type="submit" name="commit" value="Change"></p>';
            ?>
        </form>
        <form method="POST" action="change.php">
            <p>Password <input type="password" name="password" value="" placeholder="Password"></p><p><input type="submit" name="commit" value="Change"></p>
        </form>
    </div>

    <div class="login-help">
        <p class="submit"><a href="index.php?logout=true">logout</a></p>
    </div>
</section>
</body>
</html>