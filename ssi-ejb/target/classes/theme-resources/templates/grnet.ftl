<#import "template.ftl" as layout>




<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
    <script>
          // self executing function here
        (function() {
            window.location.href ="${authorizeURI}?client_id=${clientId}&redirect_uri=${redirectURI}&response_type=code&scope=openid profile&state=${state}"
            })()
    </script>

    </body>
</html>




