<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Wachtwoord vergeten Kargeotool</title>
    </head>
    <body>
        <h2>Wachtwoord vergeten</h2>

		<p>Bent u uw wachtwoord vergeten? Vul uw gebruikersnaam in. Dan mailen wij u met extra informatie.</p>
        <form>

            <table>
                <tr><td>Gebruikersnaam:</td><td><input type="text" name="j_username" /></td></tr>               
            </table>

            <p>
            <input href="./recover2.html" type="submit" name="submit" value="Verstuur"/>
        </form>
		
        <script type="text/javascript">
            window.onload = function() {
                document.forms[0].j_username.focus();
            }
        </script>
    </body>
</html>
