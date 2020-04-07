
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>USC Schedule Creator | Login </title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <header>
        <h1> <span class="highlight">USC</span> Schedule Creator</h1>
    </header>
    <section id="login/signup">
        <div class="container">
            <div class="box">
                <form name="login" method="POST" action="">
                    <h3>Login</h3>
                    <input type="text" placeholder="username" name="lusername"><br>
                    <input type="password" placeholder="password" name="lpassword"><br>
                    <button type="submit">login</button>
                </form>
            </div>
            <div class="box">
                <form name="signup" action="POST" action="">
                    <h3>Sign Up</h3>
                    <input type="text" placeholder="username" name="susername"><br>
                    <input type="password" placeholder="password" name="spassword"><br>
                    <input type="password" placeholder="confirm password" name="scpassword"><br>
                    <button type="submit">sign up</button>
                </form>
            </div>
        <div>
    </section>
</body>
</html>