#determine if a number is a special number or not
# Funtion to check if number is prime 
    def isPrime(n):
    flag =1
    if n==2:
        return True
    for i in range(2,n//2 + 1):
        if n %  i== 0:
            flag =0
            return False
    if flag ==1:
        return True
#isPrime()
        
        
#Function to Determine number of prime factors for a given number 
#step 2:
def numberPrimeFactors(n):# 2,3,5
    if isPrime(n):
        return 1
    count=0
    for i in range(2,n//2):#dobule // is not a flot
        if isPrime(i) and n%i==0:
            count +=1
    return count

