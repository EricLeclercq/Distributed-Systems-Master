
speedup <- function(n,f){
  return(n/(1+(n-1)*f))
}
sspeedup <- function(n,s){
  return(n+(1-n)*s)
}

n <- seq(1,64,1)
plot(speedup(n,0),type="l",col="blue")
lines(speedup(n,0.05),type="l",col="red")
lines(speedup(n,0.10),type="l",col="green")

lines(sspeedup(n,0.05),type="l",lty=2,col="red")
lines(sspeedup(n,0.10),type="l",lty=2,col="green")
