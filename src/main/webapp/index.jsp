
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SChedulify | Login </title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <header>
        <h1> <span class="highlight">sc</span>hedulify</h1>
    </header>
    <script src="${message}"></script>
    <section id="loginsignup">
    	<h1 class="tagline"><span class="yt">sc</span><span class="rt">hedule</span> your classes in a click</h1>
        <div class="container">
            <div class="box">
                <form name="login" method="POST" action="login">
                    <h3>Login</h3>
                    <input type="text" placeholder="username" name="lusername"><br>
                    <input type="password" placeholder="password" name="lpassword"><br>
                    <button type="submit">login</button>
             
                </form>
            </div>
            <div class="box">
                <form name="signup" method="POST" action="signup">
                    <h3>Sign Up</h3>
                    <input type="text" placeholder="username" name="susername"><br>
                    <input type="password" placeholder="password" name="spassword"><br>
                    <input type="password" placeholder="confirm password" name="scpassword"><br>
                    <button type="submit">sign up</button>                
                </form>
            </div>
        </div>
    </section>
</body>
</html>