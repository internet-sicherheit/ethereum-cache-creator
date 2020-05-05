# install lib

if (!suppressWarnings(require(rjson))) install.packages("rjson")
if (!suppressWarnings(require(igraph))) install.packages("igraph")
if (!suppressWarnings(require(dplyr))) install.packages("dplyr")
if (!suppressWarnings(require(jsonlite))) install.packages("jsonlite")

library("rjson")
library("igraph")
library("dplyr")
library("jsonlite")



#import json file from local machine

json_file <- "trans_mod.json"

json_data <- jsonlite::fromJSON( json_file )
summary(json_data)

transaction <- data.frame(
  from=c (json_data$fromAddress),
  to=c (json_data$toAddress)
)


network <- graph_from_data_frame(d=transaction, directed=F)
deg <- degree(network, mode="all")
plot(network, vertex.size=deg*1, vertex.color=rgb(0.1,0.7,0.8,0.5) )
