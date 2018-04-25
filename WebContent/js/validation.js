function validate() {
            var str=true;
            document.getElementById("msg").innerHTML="";                    
            
            pwd = document.signupform.password.value;
            cpwd = document.signupform.confirm.value;
            
            
            if(pwd == cpwd) {
                
            }
            else {
                document.getElementById("msg").innerHTML="<h3 style=\"color: red\">" +"Password and confirm password must match.!"+"</h3>";
                str=false;
            }
            
            if(document.signupform.password.value == '')
            {
                document.getElementById("msg").innerHTML="<h3 style=\"color: red\">" +"Enter Password"+"</h3>";
                str=false;
            }
            
            if(document.signupform.username.value == '')
            {
                document.getElementById("msg").innerHTML="<h3 style=\"color: red\">" +"Enter Username"+"</h3>";
                str=false;
            }
            
            lastname = document.signupform.lastname.value;
            if(isNaN(lastname))
            {
            }
            else
            {
                document.getElementById("msg").innerHTML="<h3 style=\"color: red\">" +"Numbers are not allowed for last name.!"+"</h3>";
                str=false;
            }
            
            if(document.signupform.lastname.value == '')
            {
                document.getElementById("msg").innerHTML="<h3 style=\"color: red\">" +"Enter Lastname"+"</h3>";
                str=false;
            }
            
            firstname = document.signupform.firstname.value;
            if(isNaN(firstname))
            {
            }
            else
            {
                document.getElementById("msg").innerHTML="<h3 style=\"color: red\">" +"Numbers are not allowed for first name.!"+"</h3>";
                str=false;
            }
        
            if(document.signupform.firstname.value == '')
            {
                document.getElementById("msg").innerHTML="<h3 style=\"color: red\">" +"Enter Firstname"+"</h3>";
                str=false;
            }
        return str;       
        }