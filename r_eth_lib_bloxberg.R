#use eth lib to extract data
#https://cran.r-project.org/web/packages/ether/ether.pdf
#https://datawookie.netlify.app/blog/2018/01/an-ethereum-package-for-r/

if (!suppressWarnings(require(ether))) install.packages("ether")
library("ether")

#access bloxberg data
set_rpc_address("https://core.bloxberg.org")
get_rpc_address()

#validate connection by checking protocol version
eth_protocolVersion()

#Most recent block no.
eth_blockNumber()

# Get last 20 blocks (you should not add elements to vectors/lists in a loop in R, this is sloooooooow)
latestBlockNo <- as.hexmode(eth_blockNumber())
latestBlocks <- list()
i <- 0
while (i < 20) {
  blockNo <- paste("0x", latestBlockNo - i, sep="")
  block <- eth_getBlock(number = blockNo)
 
  latestBlocks[[i + 1]] <- block
  i = i + 1
}

#Mining?
eth_mining()

#Account Balance
eth_getBalance()