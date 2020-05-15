# ethereum-cache-creator
## What does the program do?
The program extracts data from an ethereum based blockchain. In its current iteration it only extracts the sender-address (*from_address*) and receiver-address (*to_address*) from every transaction within every block that is looked into. This program can extract data from the entire blockchain history.
## How to use program in the current iteration
* Download the [artifact](https://github.com/internet-sicherheit/ethereum-cache-creator/actions/runs/104782193)
* Extract the zip file
* open folder/directory in terminal
* Execute the program:
```
java -jar BloxbergCacheCreator.jar <arguments>
```
The list of additional command line arguments, if required.

indicator | value | defaults
----------- | ----------------- | --------------
--ui=gui      | to run with GUI | *no default*
--url=     | the url of the client | https://core.bloxberg.org
--filename= | the name of the file | transactions_from_to
--start=  | first block to extract data from | 0
--stop=  | last block to extract data from | 1000

**Notes**

- File will appear in folder "output".
- .json will be added automatically to filename.
- Start and stop need to be numbers between 0 and 6000000.
- Stop needs to be higher than start.    
