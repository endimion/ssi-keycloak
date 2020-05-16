<#import "template.ftl" as layout>




<!DOCTYPE html>
<html>
    <head>
<#-- <title>ESMO sp-ms</title> -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <meta charset="utf-8"></meta>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1"></meta>
        <meta name="description" content=""></meta>
        <meta name="author" content=""></meta>
        <title>Create a new account</title>
        <meta http-equiv="cache-control" content="max-age=0" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
        <meta http-equiv="pragma" content="no-cache" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"></link>
        <link rel="stylesheet" href="${url.resourcesPath}/css/main.css"></link>
        <link rel="icon"
              type="image/x-icon"
              href="favicon.ico"></link>
        <link rel="icon"
              type="image/x-icon"
              href="favicon.ico"></link>
        <!-- Overide the sidebar css -->
        <style>
            .sideBarClass{
                margin-top: 0;
            }

            .breadCrumbs{
                font-size: 18px;
                color:  #00be9f;
                padding-left: 0px;
            }
            </style>


        </head>
<#--<!-- <body onload="document.redirect.submit()"> -->
<#--<!-- <form action="${UrlToRedirect}" id="redirect" name="redirect" method="POST"> -->
<#--<!-- <input id="authResponse" type="hidden" value="${msToken}" name="msToken"/> -->
<#--<!-- </form> -->
    <body>


        
        <header>
            <div class="footerClass headerContainer">
                <!--<div class="container">-->
                <div class=" instructions" style="width:90%">
                    <div class="row">
                        <div class="col s6 m6 l6 " id="header-logo">
                            <img id="companyLogo" class="responsive-img" src= "${url.resourcesPath}/img/logo2.png" style="margin-top: 1.8em;
                                 max-height: 55px;float:left;    padding-left: 40.5%;"/>
                            </div>
                        <div class="col s6 m6 l6 " id="header-logo-aegean">
                            <img id="uAegeanLogo" class="responsive-img" src="${url.resourcesPath}/img/uaegean_logo.png" style="margin-top: 0em;
                                 margin-bottom: 1.2em;
                                 max-height: 85px;float:right; margin-right: 5%;"/>
                            </div>
                        </div>    

                    </div>
                    <!--</div>-->


                </div>
            </header>



        



<div class="row container" style="margin-top: 3rem;">

        <div class="col">
                <div class="row ">
                    <div class="col">
                        <h6 id="uPortMessage" >Scan the QR code with your <b>uPort Wallet</b> to access the service</h6>
                    </div>
                </div>
                <div class="row" >
                    <div class="col" id="isNotMobile" style="display:none;max-width: 70%;">
                        ${qr?no_esc}
                    </div>
                </div>
                <div class="row" id="isMobile" style="display:none">
                    <div style="margin: auto; width: 60%;">
                    <a  class="btn btn-primary" id="uport"
                        role="button"
                      >
                        Connect with uPort
                      </a>
                        </div>
                </div>
            
            
            
            
                <div class="row">Do not have the uPort app yet? Download it form your prefered app store</div>
               
                    <div class="row">
                        <div class="col">
                            <a href="https://apps.apple.com/us/app/uport-id/id1123434510" target="_blank" class="w-inline-block">
                                <img class="img-fluid" src="${url.resourcesPath}/img/app-store.png">
                            </a>
                        </div>
                        <div class="col">
                            
                                <a href="https://play.google.com/store/apps/details?id=com.uportMobile&amp;hl=en" target="_blank" class="w-inline-block">
                                    <img class="img-fluid" src="${url.resourcesPath}/img/play-store.png">
                                </a>
                            
                        </div>
                    </div>
               
        </div>
        <div class="border-left border-primary col">
            
                <div class="row">
                    <h6 id="uPortMessage" class="jsx-402463621">
                       The Service <b> ${clientId} </b> is requesting to connect your uPort wallet:
                    </h6>
                </div>
                <div class="row">
                    <table class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr style="background-color: lightgray;">
                                <th>#</th>
                                <th>Requested Permissions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>0</td>
                                <td>Push Notifications</td>
                                
                                
                            </tr>
                                <#list scopes as scope>
                                    <#if scope != "openid">
                                        <tr>
                                            <td>${scope?counter}</td>
                                            <td>${scope}</td>
                                        </tr>
                                    </#if>
                                </#list>
                            
                        </tbody>
                    </table>
                </div>
            
        </div>
    
</div>




                        <div class="row" th:replace="footer :: footer"></div>

                       
                    
                    

        <!--Import jQuery before materialize.js-->
                    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
                    <!-- Compiled and minified JavaScript -->
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
                    <script th:inline="javascript">
                                 
                                  
                                          
                                    (function() {
                                        
                                         let source = new EventSource("${ssEventSource}");
                                    source.addEventListener(
                                                        "vc_received",
                                                        evt => {
                                                          console.log(evt);//returns a string so needs further parsing into a JSON
                                                              //check if event sessionId is about our session
                                                                  // and if it is proceed with authentication
                                                                let sessionId = "${ssiSessionId}";
                                                                console.log("sessionID is " + sessionId);
                                                                if(sessionId === evt.lastEventId){
                                                                    console.log("sessions Match will proceed authentication");
                                                                        let form = document.createElement("form");
                                                                        form.setAttribute("method", "POST");
                                                                        form.setAttribute("action", "${responsePostEndpoint}");
                                                                        let hiddenField = document.createElement("input");
                                                                        hiddenField.setAttribute("type", "hidden");
                                                                        hiddenField.setAttribute("name", "sessionId");
                                                                        hiddenField.setAttribute("value", "${ssiSessionId}");
                                                                        form.appendChild(hiddenField);
                                                                        document.body.appendChild(form);
                                                                        form.submit();
                                                                }
                                                              
                                                              
                                                              })
                                        
                                        
                                        
                                        let isMobile = false; //initiate as false
                                         // device detection
                                         if(/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) 
                                             || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4))) { 
                                             isMobile = true;
                                         }
                                         if(!isMobile){
                                             document.getElementById("isMobile").style.display = "none";
                                             document.getElementById("isNotMobile").style.display = "block"
                                         }else{
                                             document.getElementById("isMobile").style.display = "block";
                                             document.getElementById("isNotMobile").style.display = "none";
                                             document.getElementById("uport").href=encodeURI(`me.uport:req/${mobile?no_esc}`)
                                         }
                                    })();
    
    
                                    
    
                        </script>

                    </body>
                    </html>




