function validate() {
            var str=true;
            document.getElementById("msg").innerHTML="";                    
            
            pwd = document.signupform.password.value;
            cpwd = document.signupform.confirm.value;
            
            
            if(pwd == cpwd) {
                
            }
            else {
                document.getElementById("msg").innerHTML="Password and confirm password must match.!";
                str=false;
            }
            
            if(document.signupform.password.value == '')
            {
                document.getElementById("msg").innerHTML="Enter Password";
                str=false;
            }
            
            if(document.signupform.username.value == '')
            {
                document.getElementById("msg").innerHTML="Enter Username";
                str=false;
            }
            
            lastname = document.signupform.lastname.value;
            if(isNaN(lastname))
            {
            }
            else
            {
                document.getElementById("msg").innerHTML="Numbers are not allowed for last name.!";
                str=false;
            }
            
            if(document.signupform.lastname.value == '')
            {
                document.getElementById("msg").innerHTML="Enter Lastname";
                str=false;
            }
            
            firstname = document.signupform.firstname.value;
            if(isNaN(firstname))
            {
            }
            else
            {
                document.getElementById("msg").innerHTML="Numbers are not allowed for first name.!";
                str=false;
            }
        
            if(document.signupform.firstname.value == '')
            {
                document.getElementById("msg").innerHTML="Enter Firstname";
                str=false;
            }
        return str;       
        }