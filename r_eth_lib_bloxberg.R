#use eth lib to extract data
#https://cran.r-project.org/web/packages/ether/ether.pdf
#https://datawookie.netlify.app/blog/2018/01/an-ethereum-package-for-r/

if (!suppressWarnings(require(ether))) install.packages("ether")

#access bloxberg data
set_rpc_address("https://core.bloxberg.org")
get_rpc_address()

#validate connection by checking protocol version
eth_protocolVersion()

#Most recent block no.
eth_blockNumber()

#Mining?
eth_mining()

#Account Balance
eth_getBalance()