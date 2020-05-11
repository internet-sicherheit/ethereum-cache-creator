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

json_file <- "extract.json"

json_data <- jsonlite::fromJSON( json_file )
summary(json_data)

transaction <- data.frame(
  from=c (json_data$fromAddress),
  to=c (json_data$toAddress)
)

#undirected
network <- graph_from_data_frame(d = transaction, directed=F)
deg <- degree(network, mode="all")
plot(network, vertex.size=deg*0.1, vertex.color=rgb(0.1,0.7,0.8,0.5), vertex.label.cex = 0.5  )

#Directed
network <- graph_from_data_frame(d=transaction, directed=T)
deg <- degree(network, mode="all")
plot(network, vertex.size=deg*0.1, vertex.color=rgb(0.1,0.7,0.8,0.5), vertex.label.cex = 0.5  )

#number of verticies in network
gorder(network)

#edges in network
gsize(network)

#self-loops, how would they look in data set?
is.loop(network)

# multiple edges of vertex per transaction?
cm <- count.multiple(network, eids=E(network))
boxplot (cm)
plot (cm)


# no. of edges per vertex including loops
degree <- degree(network, v = V(network), mode = c("all", "out", "in", "total"),
                 loops = TRUE, normalized = FALSE)

#no of edges per vertex excluding loops
degree_wl <- degree(network, v = V(network), mode = c("all", "out", "in", "total"),
                 loops = FALSE, normalized = FALSE)

#viz incl. loops
plot (degree)
boxplot (degree)

#viz excluding loops
plot (degree_wl)
boxplot (degree_wl)



