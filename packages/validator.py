import re

def phoneNumValidate(num):
    pattern='^[6-9][0-9]{9}$|[0][6-9][0-9]{9}$'
    if re.match(pattern,str(num)):
        print("Valid number")
    else:
        print("Invalid Number")
    return
#phoneNumValidate(9849161938)
phoneNumValidate(1234567890)
def emailValidate(email):
    pattern='^[0-9a-z][0-9a-z_.]{4,13}[0-9a-z][@][a-z0-9]{3,18}[.][a-z]{2,4}$'
    if re.match(pattern,str(email)):
        print("Valid email")
    else:
        print("Invalid email")
    return
#phoneNumValidate(9849161938)
emailValidate("muni.apssdc@gmail.com")