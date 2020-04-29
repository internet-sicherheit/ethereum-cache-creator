
# install lib

install.packages("rjson")
install.packages("igraph")

library("rjson")
library("igraph")

#import json file from local machine

json_file <- "~/data/network_analysis/trans_mod.json"

json_data <- fromJSON(file=json_file)

dat <- json_data

transaction <- data.frame(
  from=c (dat$fromAddress),
  to=c(dat$toAddress)
)

network <- graph_from_data_frame(d=transaction, directed=F)
deg <- degree(network, mode="all")
plot(network, vertex.size=deg*6, vertex.color=rgb(0.1,0.7,0.8,0.5) )